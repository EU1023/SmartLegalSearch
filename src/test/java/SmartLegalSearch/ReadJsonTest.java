package SmartLegalSearch;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import SmartLegalSearch.readJson.ReadJson;
import SmartLegalSearch.vo.ReadJsonVo;

@SpringBootTest
public class ReadJsonTest {

	// 讀取本地端 Json 檔案用
	private ReadJson readJson = new ReadJson();

	// 檔案路徑
	// 銀行法第125條第1項
//    private ReadJsonVo data = readJson.readJson("D:\\JavaProject\\臺灣基隆地方法院刑事\\KLDM,112,金重訴,2,20240528,2.json");
	// 洗錢防制法第十四條第一項
//    private ReadJsonVo data = readJson.readJson("D:\\JavaProject\\臺灣基隆地方法院刑事\\KLDM,112,金訴,562,20240516,1.json");
	// 山坡地保育利用條例第三十四條
//    private ReadJsonVo data = readJson.readJson("D:\\JavaProject\\臺灣基隆地方法院刑事\\KLDM,112,訴,237,20240520,1.json");
	// 洗錢防制法第十四條第一項
//	private ReadJsonVo data = readJson.readJson("D:\\JavaProject\\臺灣基隆地方法院刑事\\KLDM,113,基金簡,58,20240520,1.json");
	// 附錄本案論罪科刑法條刑法第320條
//	private ReadJsonVo data = readJson.readJson("D:\\JavaProject\\臺灣基隆地方法院刑事\\KLDM,112,易,393,20240516,1.json");
	// 附表3:洗錢防制法第十四條
	private ReadJsonVo data = readJson.readJson("D:\\JavaProject\\臺灣基隆地方法院刑事\\刑事\\判決\\KLDM,112,金訴,562,20240516,1.json");

	// 取得判決主文
//	private String text = new String(data.getFull());

	// 整理文章中多餘空格(一般空白、全形空白)跟跳脫符號 : 會沒辦法用 matcher
//	private String cleanText = data.getFull().replaceAll("\n|\r|　| ", " ");
	String cleanText = data.getFull().replaceAll("[\\r|\\n|\\s]+", "");
//	String cleanText = data.getFull().replaceAll("[\\r\\n]+", " "); 
//	String cleanText = data.getFull().replaceAll("[\\r\\n]+", " ").replaceAll("\\s{2,}", " "); // 替換多餘的空白符

	// 中文數字轉數字
	public static int convertChineseToArabic(String chineseNumber) {
		int result = 0; // 最終的結果，存儲轉換後的數字
		int temp = 0; // 用來累積每一部分的數字
		// 處理「百」的部分，這一步先行
		if (chineseNumber.contains("百")) {
			String[] parts = chineseNumber.split("百");
			// 處理「百」前的部分
			if (!parts[0].isEmpty()) {
				temp = processDigits(parts[0]);
			} else {
				temp = 1; // 如果「百」前面沒有數字，則視為 1
			}
			result += temp * 100; // 給結果加上「百」後的部分
			chineseNumber = parts.length > 1 ? parts[1] : ""; // 去掉已經處理過的「百」後面部分
		}
		// 處理「十」的部分
		if (chineseNumber.contains("十")) {
			String[] parts = chineseNumber.split("十");
			// 處理「十」前的部分
			if (!parts[0].isEmpty()) {
				temp = processDigits(parts[0]);
			} else {
				temp = 1; // 如果「十」前面沒有數字，則視為 1 十
			}
			result += temp * 10; // 給結果加上「十」後的部分
			chineseNumber = parts.length > 1 ? parts[1] : ""; // 去掉已經處理過的「十」後面部分
		}
		// 處理剩下的數字部分
		if (!chineseNumber.isEmpty()) {
			result += processDigits(chineseNumber); // 將剩下的部分轉換
		}
		return result; // 返回轉換後的數字
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
	public String convertTextChineseNumbers(String text) {
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

	// 提取正文
	public String readJsonTest(String pattern) {
		// 找出判決書中的符合 pattern 的段落
		Pattern lowPattern = Pattern.compile(pattern);
//		String cleanText2 = convertTextChineseNumbers(cleanText);
		// 使用正則匹配
//		Matcher matcher = lowPattern.matcher(cleanText1); // cleanText 是您的判決書正文

		// 清理空白字串，["正 文"]，以及將中文數字轉換成阿拉伯數字
		// 清理空白字串並處理中文數字
		String cleanText1 = convertTextChineseNumbers(cleanText.replaceAll("\\s+", ""));
//		System.out.println("清理後的文本: " + cleanText1); // 調試用

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
			System.out.println("主文範圍內容: " + mainText); // 調試用
			Matcher matcher = lowPattern.matcher(mainText);
			if (matcher.find()) {
				System.out.println("主文匹配到的法條: " + matcher.group());
				return matcher.group(); // 如果找到法條，直接返回
			}
		}

		// 2. 從附錄中提取法條
		int appendixStartIndex = cleanText1.indexOf("附錄本案論罪科刑法條");
		if (appendixStartIndex != -1) {
			String appendixText = cleanText1.substring(appendixStartIndex).trim();
			System.out.println("附錄範圍: " + appendixText); // 調試用
			Matcher matcher = lowPattern.matcher(appendixText);
			if (matcher.find()) {
				System.out.println("附錄匹配到的法條: " + matcher.group());
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
				System.out.println("附表三範圍內容: " + tableText);
				// 匹配犯罪事實部分
				Pattern crimeFactPattern = Pattern.compile("犯罪事實.*?(附表四|$)", Pattern.DOTALL);
				Matcher crimeFactMatcher = crimeFactPattern.matcher(tableText);
				if (crimeFactMatcher.find()) {
					String crimeFacts = crimeFactMatcher.group();
//		            System.out.println("犯罪事實內容: " + crimeFacts);
					// 在犯罪事實段落中提取法條
					Matcher matcher = lowPattern.matcher(crimeFacts);
					boolean found = false;
					while (matcher.find()) {
						System.out.println("犯罪事實匹配到的法條: " + matcher.group());
						found = true;
						return matcher.group(); // 返回找到的第一個法條
					}
				}
			}
		}
		// 如果所有段落中均未找到法條，返回提示
		return "未找到任何法條";

	}

	@Test // 主方法來提取並轉換法律條文
	public void extractAndConvertLegalText() {
		// 定義法律條文的正則表達式，這個正則表達式會匹配各種法條
		String lawPattern = "(洗錢防制法|毒品危害防制條例|陸海空軍刑法|煙害防制法|貪污治罪條例|山坡地保育利用條例|銀行法|刑法)第\\d+條(第\\d+項)?";
		// 提取法條
		String extractedLaws = readJsonTest(lawPattern);
		System.out.println(extractedLaws);
		// 輸出結果
		if (!extractedLaws.isEmpty()) {
			System.out.println("找到的法條: " + extractedLaws); // 列出所有法條
//			System.out.println("第 1 個法條: " + extractedLaws.get(0)); // 取第一個法條
		} else {
			System.out.println("沒有找到任何法條。");
		}
	}

	// 事實
//	@Test
//	public void facts() {
//		// [\\s\\S] 表示會匹配所有空白和非空白字元，*?表示理由前的內容
//		String pattern = "事\\s*實([\\s\\S]*?)理\\s*由";
//		Pattern compiledPattern = Pattern.compile(pattern);
//		Matcher matcher = compiledPattern.matcher(data.getFull());
//
//		if (matcher.find()) {
//			System.out.println("事實：");
//			System.out.println(matcher.group(1).trim());
//		}
//	}

	// 理由
//	@Test
//	public void motive() {
//		String pattern = "理\\s*由([\\s\\S]*?依法論科。)";
//		Pattern compiledPattern = Pattern.compile(pattern);
//		Matcher matcher = compiledPattern.matcher(data.getFull());
//
//		if (matcher.find()) {
//			System.out.println("理由：");
//			System.out.println(matcher.group(1).trim());
//		}
//	}

}