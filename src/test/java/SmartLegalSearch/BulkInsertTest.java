package SmartLegalSearch;

import SmartLegalSearch.entity.LegalCase;
import SmartLegalSearch.readJson.ReadJson;
import SmartLegalSearch.repository.CaseDao;
import SmartLegalSearch.vo.ReadJsonVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SpringBootTest
public class BulkInsertTest {

	@Autowired
	CaseDao caseDao;
	
	private String folderPath = "C:\\Users\\user\\Desktop\\202405\\金城簡易庭刑事"; // 替換為實際目錄路徑

	@Test
	public void test() throws IOException {
		// 初始化 ReadJson 和保存結果的列表
		ReadJson readJson = new ReadJson();
		List<ReadJsonVo> dataList = new ArrayList<>();

		// 遍歷目錄中的所有 JSON 檔案
		Files.walk(new File(folderPath).toPath()).filter(Files::isRegularFile) // 只處理檔案
				.filter(path -> path.toString().endsWith(".json")) // 篩選 JSON 檔案
				.forEach(file -> {
					// 讀取 JSON 文件，並添加到 dataList
					ReadJsonVo data = readJson.readJsonByPath(file.toAbsolutePath().toString());
					if (data != null) {
						dataList.add(data);
					}
				});

		// 以正規化取關鍵字
		List<LegalCase> legalCaseList = new ArrayList<>();
		for (ReadJsonVo data : dataList) {
			String contentStr = data.getFull();
			LegalCase legalCase = new LegalCase();
			String[] courtAndCharge = courtAndCharge(data);
			
			// id 案號
			legalCase.setGroupId(courtAndCharge[1]);
			legalCase.setId(courtAndCharge[1]);
			
			// 法院代碼
			legalCase.setCourt(courtAndCharge[2]);
			
			// 案由
			legalCase.setCharge(courtAndCharge[3]);

			// 判決日期
			legalCase.setDate(verdictDate(data));

			// 判決書連結
			legalCase.setUrl(httpUrl(data));
			
			// 判決內容
			legalCase.setText(contentStr);
			
			// 被告姓名
			legalCase.setDefendantName(DefendantName(contentStr));
			
			// 法官姓名
			legalCase.setJudgeName(JudgesName(contentStr));
			
			// 相關法條
			legalCase.setLaw(extractAllLaws(contentStr));
			
			// 案件類型，如刑事、民事、行政
			legalCase.setDocType(CaseType(data));
			
			// 文件類型，裁定或判決或釋字等
			legalCase.setDocType(DocType(contentStr));
			
			legalCaseList.add(legalCase);
		}
		caseDao.saveAll(legalCaseList);
	}

	private ArrayList<String> readJson1Test(String pattern, ReadJsonVo data) {
		// 整理文章中多餘空格(一般空白、全形空白)跟跳脫符號 : 會沒辦法用 matcher
		String cleanContent = data.getFull().replaceAll("[\\r|\\n|\\s|'　']+", "");

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
	private String[] courtAndCharge(ReadJsonVo data) {
		String[] result = new String[4]; // 用於存放案號、法院代號、案由

		// 群組案號
		String idPattern = "([一二三四五六七八九十]|\\d){2,4}年度(.){1,6}字第([一二三四五六七八九十]|\\d){1,5}號";
		String group_id = readJson1Test(idPattern, data).get(0);
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
				List<String> matchedCases = readJson1Test(specificCasePattern, data);
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

	// 中文數字轉數字
	private int convertChineseToArabic(String chineseNumber) {
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
	private int processDigits(String digits) {
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

	// 判決日期
	private LocalDate verdictDate(ReadJsonVo data) {
		// 匹配日期段落
		String pattern = "中\\s*華\\s*民\\s*國\\s*([一二三四五六七八九十零百千\\d]{1,4})\\s*年\\s*([一二三四五六七八九十零\\d]{1,2})\\s*月\\s*([一二三四五六七八九十零\\d]{1,2})\\s*日";
		ArrayList<String> dateStrList = readJson1Test(pattern, data);

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

	// 方法：將文件內文中的中文數字轉換為阿拉伯數字
	private String convertTextChineseNumbers(String text) {
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

	// 提取全文中所有法條的方法
	private String extractAllLaws(String fullText) {
		List<String> laws = new ArrayList<>(); // 用於存放匹配到的法條

		// 清理空白字元並處理中文數字
		String cleanText = convertTextChineseNumbers(fullText.replaceAll("\\s+", ""));

		// 定義正則模式並匹配全文
		Pattern lawPattern = Pattern.compile("(洗錢防制法|毒品危害防制條例|陸海空軍刑法|煙害防制法|貪污治罪條例|山坡地保育利用條例|銀行法|刑法)第\\d+條(第\\d+項)?");
		Matcher matcher = lawPattern.matcher(cleanText);

		// 匹配所有符合的法條並存入列表
		while (matcher.find()) {
			laws.add(matcher.group());
		}
		String lawString = laws.stream().collect(Collectors.joining(";"));

		return lawString; // 返回所有匹配到的法條
	}

	// 被告姓名
	private String DefendantName(String fullText) {
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
	private String JudgesName(String fullText) {
		// 定義正則表達式以匹配「法 官」後的姓名
		// 假設姓名是2-4個中文字，並且停止於段行符號或跳脫符號
		// 標準化空格（將全形空格替換為半形空格，並壓縮多餘空白）
		fullText = fullText.replaceAll("\\s+", " ").replaceAll("　", " ");

		// 定義正則表達式：匹配「法 官」後緊接的姓名，並在段行符號或標點符號前停止
		Pattern pattern = Pattern.compile("法\\s*官\\s*([\\u4E00-\\u9FFF]{2,4})(?=\\s*[\\n\\r。，；、\\s])");

		// 搜索匹配
		Matcher matcher = pattern.matcher(fullText);
		// 如果找到匹配
		if (matcher.find()) {
			// 提取法官姓名 (第一組匹配)
			return matcher.group(1).trim();
		}
		// 如果未找到，返回未知
		return "未知";
	}

	// 案件類型，如刑事(M)、民事(V)、行政(A)
	private String CaseType(ReadJsonVo data) {
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
	private String DocType(String fullText) {

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

	// URL 生成
	private String httpUrl(ReadJsonVo data) {
		// 假設 data.getId() 返回的 id 字串
		String id = data.getId();
		// 替換逗號為 URL 兼容格式
		String encodedId = id.replace(",", "%2c"); // 處理逗號
		return "https://judgment.judicial.gov.tw/FJUD/data.aspx?ty=JD&id=" + encodedId;
	}

}