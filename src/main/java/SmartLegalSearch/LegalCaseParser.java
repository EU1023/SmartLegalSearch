package SmartLegalSearch;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import SmartLegalSearch.dto.CaseInfo;
import SmartLegalSearch.entity.LegalCase;
import SmartLegalSearch.readJson.ReadJson;
import SmartLegalSearch.service.impl.CaseImpl;
import SmartLegalSearch.vo.ReadJsonVo;

//@SpringBootApplication
public class LegalCaseParser {
	/*
	 * group_id id court date text judge_name defendant_name url charge case_type
	 * doc_type案件群組識別碼 案件的唯一識別碼 審理法院 案由 判決日期 判決內容 法官姓名 被告姓名 判決書連結
	 * 案件類型，如刑事(M)、民事(V)、行政(A) 文件類型，裁定或判決或釋字等
	 */

	// 讀取本地端 Json 檔案用
	private static ReadJson readJson = new ReadJson();
	// 設置 JSON 文件的路徑
	private static ReadJsonVo data = readJson
			.readJson("C:\\Users\\user\\Desktop\\202405\\臺灣士林地方法院刑事\\SLDM,111,附民,1579,20240509,1.json");

	// 整理文章中多餘空格(一般空白、全形空白)跟跳脫符號 : 會沒辦法用 matcher
	static String cleanContent = data.getFull().replaceAll("[\\r|\\n|\\s|'　']+", "");
	// 未整理原文
	static String unorganizedContent = data.getFull();

	public ArrayList<String> readJson1Test(String pattern) {
		// 找出判決書中的符合 pattern 的段落
		Pattern lowPattern = Pattern.compile(pattern);
		// 進行比對: .group可取出符合條件的字串段、.start或.end會回傳符合條件的開始位置或結束位置
		Matcher matcher = lowPattern.matcher(cleanContent);
		// 用於蒐集所有符合條件的字串段
		ArrayList<String> lowList = new ArrayList<>();
		// 用於紀錄符合條件的字串段最後一個位置index位置
		int index = 0;
		// 透過迴圈尋找是否有符合條件的內容 (index 為上一個符合條件的文字段中最後一個字在字串中的位置)
		while (matcher.find(index)) {
			// 蒐集所有符合條件的字串段
			lowList.add(matcher.group());
			// 紀錄符合條件的字串段最後一個字在index的位置
			index = matcher.end();
		}
		// 列印出所有蒐集到的內容
//		lowList.forEach(item -> {
//			System.out.println(item);
//		});
		return lowList;
	}

	// 群組案號、唯一案號、審理法院、案由
	public String[] courtAndCharge() {
		String[] result = new String[4]; // 用於存放案號、法院代號、案由

		// 群組案號
		String idPattern = "([一二三四五六七八九十]|\\d){2,4}年度(.){1,6}字第([一二三四五六七八九十]|\\d){1,5}號";
		String group_id = readJson1Test(idPattern).get(0);
		result[0] = group_id;
		// ==========================================
		// 唯一案號的處理
		String jid = data.getId(); // 假設 data.getJID() 返回 JSON 中的 "JID" 欄位
		if (jid != null && !jid.isEmpty()) {
			// 定義更靈活的正則模式，匹配案件類型和日期時間編號
			String uniqueIdPattern = "([一-龥\\w]+),\\s*(\\d+),\\s*(\\d{8}),\\s*(\\d+)$";
			Matcher matcher = Pattern.compile(uniqueIdPattern).matcher(jid);
			if (matcher.find()) {
				String caseType = matcher.group(1); // 案件類型（例如 金訴 或 訴 等）
				String uniqueId = matcher.group(2); // 唯一案號編號
				String date = matcher.group(3); // 日期（例如 20240516）
				String finalNumber = matcher.group(4); // 最後的數字編號（例如 1 或 2）

				// 在文中組合查找對應的唯一案號
				String specificCasePattern = "([一二三四五六七八九十]|\\d){2,4}年度" + caseType + "字第" + uniqueId + "號";
				List<String> matchedCases = readJson1Test(specificCasePattern);
				if (!matchedCases.isEmpty()) {
					result[1] = matchedCases.get(0); // 取匹配到的第一個案號
				} else {
					result[1] = "未找到符合的唯一案號";
				}
			} else {
				result[1] = "未能從 JID 提取唯一案號相關資訊";
			}
		} else {
			result[1] = "JID 欄位為空";
		}

		// ==========================================
		// 審理法院
		String court = data.getId().substring(0, 3);
		result[2] = court;

		// 案由
		String charge = data.getTitle();
		result[3] = charge;

		return result; // 回傳結果
	}

	// 判決日期(阿拉伯數字)
	public LocalDate verdictDate() {
		// 匹配日期段落
		String pattern = "中\\s*華\\s*民\\s*國\\s*([一二三四五六七八九十零百千\\d]{1,4})\\s*年\\s*([一二三四五六七八九十零\\d]{1,2})\\s*月\\s*([一二三四五六七八九十零\\d]{1,2})\\s*日";
		ArrayList<String> dateStrList = readJson1Test(pattern);

		if (dateStrList.isEmpty()) {
			throw new IllegalArgumentException("未找到符合格式的判決日期！");
		}

		try {
			String firstDateStr = dateStrList.get(0).replaceAll("中\\s*華\\s*民\\s*國\\s*|\\s+", "");
			String[] dateParts = firstDateStr.split("年|月|日");
			int year = convertChineseToArabic(dateParts[0]) + 1911;
			int month = convertChineseToArabic(dateParts[1]);
			int day = convertChineseToArabic(dateParts[2]);

			// 範圍檢查
			if (month < 1 || month > 12 || day < 1 || day > 31) {
				throw new IllegalArgumentException("日期超出範圍: " + firstDateStr);
			}

			// 回傳 LocalDate 物件
			return LocalDate.of(year, month, day);
		} catch (Exception e) {
			throw new RuntimeException("解析判決日期時發生錯誤: " + e.getMessage());
		}
	}

	// URL 生成
	public String httpTest() {
		// 假設 data.getId() 返回的 id 字串
		String id = data.getId();
		return generateUrl(id);
	}

	// 生成網址的方法
	public String generateUrl(String id) {
		// 替換逗號為 URL 兼容格式
		String encodedId = id.replace(",", "%2c"); // 處理逗號
		// encodedId = encodedId.replace("金訴", "%e9%87%91%e8%a8%b4"); //
		// 處理中文（可以根據需求擴展編碼規則）
		return "https://judgment.judicial.gov.tw/FJUD/data.aspx?ty=JD&id=" + encodedId;
	}

//=====================================================================
	// 中文數字轉數字
	public static int convertChineseToArabic(String chineseNumber) {
		// 如果輸入是純數字，直接返回其整數值
		if (chineseNumber.matches("\\d+")) {
			return Integer.parseInt(chineseNumber);
		}

		int result = 0; // 最終的結果
		int temp = 0; // 暫時累積的部分
		// 處理「百」、「十」、「個位數」
		if (chineseNumber.contains("百")) {
			String[] parts = chineseNumber.split("百");
			temp = parts[0].isEmpty() ? 1 : processDigits(parts[0]);
			result += temp * 100;
			chineseNumber = parts.length > 1 ? parts[1] : "";
		}
		if (chineseNumber.contains("十")) {
			String[] parts = chineseNumber.split("十");
			temp = parts[0].isEmpty() ? 1 : processDigits(parts[0]);
			result += temp * 10;
			chineseNumber = parts.length > 1 ? parts[1] : "";
		}
		if (!chineseNumber.isEmpty()) {
			result += processDigits(chineseNumber);
		}
		return result;
	}

	// 處理剩餘的數字部分
	private static int processDigits(String digits) {
		int value = 0;
		// 中文數字轉換映射
		Map<String, Integer> number = Map.of("一", 1, "二", 2, "三", 3, "四", 4, "五", 5, "六", 6, "七", 7, "八", 8, "九", 9);
		for (int i = 0; i < digits.length(); i++) {
			String currentChar = digits.substring(i, i + 1);
			if (number.containsKey(currentChar)) {
				value += number.get(currentChar);
			}
		}
		return value;
	}

	// 方法：將文件內文中的中文數字轉換為阿拉伯數字
	public static String convertTextChineseNumbers(String text) {
		// 匹配中文數字的正則表達式（針對 "第十四條" 和 "第十四項" 等格式）
		Pattern pattern = Pattern.compile("第([一二三四五六七八九十百零]+)(條|項)");
		Matcher matcher = pattern.matcher(text);
		// 用於存放轉換後的結果
		StringBuffer result = new StringBuffer();
		// 遍歷匹配的中文數字
		while (matcher.find()) {
			String chineseNumber = matcher.group(1); // 提取中文數字部分
			int arabicNumber = convertChineseToArabic(chineseNumber); // 轉換為阿拉伯數字
			String replacement = "第" + arabicNumber + matcher.group(2); // 組合成替換後的字符串
			matcher.appendReplacement(result, replacement); // 替換匹配到的部分
		}
		matcher.appendTail(result); // 添加剩餘部分
		return result.toString();
	}

	// 提取第一個法條
	public static String readJson2Test(String pattern) {
		// 找出判決書中的符合 pattern 的段落
		Pattern lowPattern = Pattern.compile(pattern);
		// 使用正則匹配
		// 清理空白字串，["正 文"]，以及將中文數字轉換成阿拉伯數字
		// 清理空白字串並處理中文數字
		String cleanText1 = convertTextChineseNumbers(cleanContent.replaceAll("\\s+", ""));
		// 定義主文結束標記的正則表達式
		Pattern endPattern = Pattern.compile("(事實及理由|事實|理由|)");
		Matcher endMatcher = endPattern.matcher(cleanText1);
		// 初始化主文結束索引
		int mainEndIndex = cleanText1.length(); // 默認為全文結尾
		if (endMatcher.find()) {
			mainEndIndex = endMatcher.start(); // 如果找到匹配的標記，取其起始位置
		}
		// 1. 嘗試從主文段落中提取法條
		int mainStartIndex = cleanText1.indexOf("主文");
		if (mainStartIndex != -1 && mainEndIndex > mainStartIndex) {
			String mainText = cleanText1.substring(mainStartIndex, mainEndIndex).trim();
//			System.out.println("主文範圍內容: " + mainText); // 調試用
			Matcher matcher = lowPattern.matcher(mainText);
			if (matcher.find()) {
//				System.out.println("主文匹配到的法條: " + matcher.group());
				return matcher.group(); // 如果找到法條，直接返回
			}
		}
		// 2. 從附錄中提取法條
		int appendixStartIndex = cleanText1.indexOf("附錄本案論罪科刑法條");
		if (appendixStartIndex != -1) {
			String appendixText = cleanText1.substring(appendixStartIndex).trim();
//			System.out.println("附錄範圍: " + appendixText); // 調試用
			Matcher matcher = lowPattern.matcher(appendixText);
			if (matcher.find()) {
//				System.out.println("附錄匹配到的法條: " + matcher.group());
				return matcher.group();
			}
		}
		// 3. 從附表中提取法條，從附表一開始
		// 找到附表一的起始位置
		Pattern tablePattern = Pattern.compile("附表一：");
		Matcher tableMatcher = tablePattern.matcher(cleanText1);
		int tableStartIndex = -1;
		if (tableMatcher.find()) {
			tableStartIndex = tableMatcher.start();
		}
		// 如果找到了附表一
		if (tableStartIndex != -1) {
			// 嘗試找到附表三的起始位置
			int tableThreeStartIndex = cleanText1.indexOf("附表三", tableStartIndex);
			if (tableThreeStartIndex == -1) {
				return "未找到任何法條";
			}
			// 找到附表三的結尾範圍
			int tableEndIndex = cleanText1.indexOf("附表四", tableThreeStartIndex);
			if (tableEndIndex == -1) {
				tableEndIndex = cleanText1.length(); // 如果沒有附表四，取全文結尾
			}
			// 確保範圍有效
			if (tableEndIndex > tableThreeStartIndex) {
				// 提取附表三到結尾的文本
				String tableText = cleanText1.substring(tableThreeStartIndex, tableEndIndex).trim();
//				System.out.println("附表三範圍內容: " + tableText);
				// 匹配犯罪事實部分
				Pattern crimeFactPattern = Pattern.compile("犯罪事實.*?(附表四|$)", Pattern.DOTALL);
				Matcher crimeFactMatcher = crimeFactPattern.matcher(tableText);
				if (crimeFactMatcher.find()) {
					String crimeFacts = crimeFactMatcher.group();
//			            System.out.println("犯罪事實內容: " + crimeFacts);
					// 在犯罪事實段落中提取法條
					Matcher matcher = lowPattern.matcher(crimeFacts);
					boolean found = false;
					while (matcher.find()) {
//						System.out.println("犯罪事實匹配到的法條: " + matcher.group());
						found = true;
						return matcher.group(); // 返回找到的第一個法條
					}
				}
			}
		}
		// 如果所有段落中均未找到法條，返回提示
		return "未找到任何法條";

	}

	// 提取全文中所有法條的方法
	public static List<String> extractAllLaws(String fullText, String pattern) {
		List<String> laws = new ArrayList<>(); // 用於存放匹配到的法條

		// 清理空白字元並處理中文數字
		String cleanText = convertTextChineseNumbers(fullText.replaceAll("\\s+", ""));

		// 定義正則模式並匹配全文
		Pattern lawPattern = Pattern.compile(pattern);
		Matcher matcher = lawPattern.matcher(cleanText);

		// 匹配所有符合的法條並存入列表
		while (matcher.find()) {
			laws.add(matcher.group());
		}

		return laws; // 返回所有匹配到的法條
	}

//==============================================================================

	// 被告姓名
	private static String DefendantName(String fullText) {
		// 匹配 "被告" 後的 2~4 個中文字符
		Pattern pattern = Pattern.compile("被告([\\u4E00-\\u9FFF]{2,3})");
		Matcher matcher = pattern.matcher(fullText);
		if (matcher.find()) {
			// 提取純粹的姓名
			return matcher.group(1).trim();
		}
		return "未知";
	}

	// 法官姓名
	private static String JudgesName(String fullText) {
		// 找到標記「以上正本證明與原本無異」
		String marker = "以上正本證明與原本無異。";
		int markerIndex = fullText.indexOf(marker);
		if (markerIndex != -1) {
			// 提取標記前的一段文字
			String precedingText = fullText.substring(0, markerIndex);
			// 匹配 "法官" 後的姓名
			Pattern pattern = Pattern.compile("法\\s*官\\s*([\\u4E00-\\u9FFF]{2,4})");
			Matcher matcher = pattern.matcher(precedingText);
			if (matcher.find()) {
				return matcher.group(1).trim(); // 僅返回法官姓名
			}
		}
		return "未知";
	}

	// 內容
	private static String JudgmentContent(String fullText) {
		// 起始標記：主文
		String startMarker = "主    文";
		// 結束標記：論罪科刑
		String endMarker = "據上論斷";

		// 找到起始與結束位置
		int startIndex = fullText.indexOf(startMarker);
		int endIndex = fullText.indexOf(endMarker);

		if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
			return fullText.substring(startIndex, endIndex + endMarker.length()).trim();
		}
		return "未找到完整的判決內容區域";
	}

	// 案件類型，如刑事(M)、民事(V)、行政(A)
	private static String CaseType() {
		String jid = data.getId();
		if (!jid.isEmpty() & jid != null) {
			String uniqueIdPattern = "^([一-龥\\w]*([MVAPC]))";
			Matcher matcher = Pattern.compile(uniqueIdPattern).matcher(jid);
			if (matcher.find()) {
				String caseType = matcher.group(2);
				// 刑事 : M 民事 : V 行政 : A 懲戒 : P 審裁 : C
				if (caseType.equals("M")) {
					return "刑事";
				}
				if (caseType.equals("V")) {
					return "民事";
				}
				if (caseType.equals("A")) {
					return "行政";
				}
				if (caseType.equals("P")) {
					return "懲戒";
				}
				if (caseType.equals("C")) {
					return "審裁";
				}
			}
		}
		return "未知";
	}

	// 文件類型，裁定或判決或釋字等
	private static String DocType(String fullText) {

		// 正規表示式：匹配包含法院名稱和「判決」、「裁定」、「釋字」的段落
	    String patternStr = "(\\S*法院\\S*)\\s*(刑事|民事|行政)?\\s*(判決|裁定|釋字)";
	    Pattern pattern = Pattern.compile(patternStr);
	    Matcher matcher = pattern.matcher(fullText);
	    // 檢查是否找到匹配
	    if (matcher.find()) {
	    	// 找到標記「判決、裁定、釋字」
	        String docType = matcher.group(3); // 判決/裁定/釋字
	        // 拼接結果並返回
	        return docType;
	    }
		return "未知";
	}

//====================================================================

	// 執行方法
	public static void main(String[] args) {
		// 處理後的全文變數
		String fullText = cleanContent;

		// 被告姓名
		String defendantName = DefendantName(fullText);
		System.out.println("被告姓名: " + defendantName);

		// 法官姓名
		String judgeName = JudgesName(fullText);
		System.out.println("法官姓名: " + judgeName);

		// 判決內容
		String judgmentContent = JudgmentContent(unorganizedContent);
		System.out.println("判決內容: "+ judgmentContent);

		// 建立 LegalCaseParser 物件
		LegalCaseParser parser = new LegalCaseParser();

		// 呼叫 courtAndCharge，並接收回傳值
		String[] courtAndCharge = parser.courtAndCharge();

		System.out.println("群組案號: " + courtAndCharge[1]); // 暫時與唯一案號相同
		System.out.println("唯一案號: " + courtAndCharge[1]);
		System.out.println("法院代號: " + courtAndCharge[2]);
		System.out.println("案由: " + courtAndCharge[3]);

		// 呼叫 verdictDate，並接收回傳值
		LocalDate verdictDate = parser.verdictDate();
		System.out.println("判決日期: " + verdictDate);

		// 呼叫 httpTest，並接收回傳值
		String url = parser.httpTest();
		System.out.println("生成的網址: " + url);

		// 案件類型
		String caseType = CaseType();
		System.out.println("案件類型: " + caseType);

		// 文件類型
		String docType = DocType(fullText);
		System.out.println("文件類型: " + docType);

		// 提取法條
		String lawPattern = "(洗錢防制法|毒品危害防制條例|陸海空軍刑法|煙害防制法|貪污治罪條例|山坡地保育利用條例|銀行法|刑法)第\\d+條(第\\d+項)?";
		String extractedLaws = readJson2Test(lawPattern);
		if (!extractedLaws.isEmpty()) {
//			System.out.println("找到的法條: " + extractedLaws);
		} else {
//			System.out.println("沒有找到任何法條。");
		}
		// =========================================================
		// 調用新方法來提取所有法條
		List<String> laws = extractAllLaws(fullText, lawPattern);

		// 使用 Set 去重，並保持插入順序
		Set<String> uniqueLaws = new LinkedHashSet<>(laws);

		// 將去重後的法條轉換為 JSON 格式字串
		String lawString = uniqueLaws.stream().collect(Collectors.joining(";"));
		System.out.println("找到的法條: " + lawString);

		// 設定實體存放變數容器
		LegalCase saveCase = new LegalCase();
		saveCase.setGroupId(courtAndCharge[1]);
		saveCase.setId(courtAndCharge[1]);
		saveCase.setCourt(courtAndCharge[2]);
		saveCase.setDate(verdictDate);
		saveCase.setUrl(url);
		saveCase.setCharge(courtAndCharge[3]);
		saveCase.setText(data.getFull());
		saveCase.setDefendantName(defendantName);
		saveCase.setJudgeName(judgeName);
		saveCase.setLaw(lawString);
		saveCase.setCaseType(caseType);
		saveCase.setDocType(docType);

		ApplicationContext context = SpringApplication.run(LegalCaseParser.class, args);

		CaseImpl caseImpl =context.getBean(CaseImpl.class);

		LegalCase saveJudgment =caseImpl.saveJudgment(saveCase);
	}

}
