package SmartLegalSearch;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import SmartLegalSearch.readJson.ReadJson;
import SmartLegalSearch.vo.ReadJsonVo;

//@SpringBootTest
public class ReadJsonTest {

	// 讀取本地端 Json 檔案用
	private ReadJson readJson = new ReadJson();

	// 檔案路徑
//    private ReadJsonVo data = readJson.readJson("D:\\JavaProject\\KLDM,113,訴,29,20240524,1.json");
	private ReadJsonVo data = readJson.readJson("D:\\JavaProject\\臺灣基隆地方法院刑事\\KLDM,112,訴,382,20240502,1.json");

	// 取得判決主文
//	private String text = new String(data.getFull());

	// 整理文章中多餘空格(一般空白、全形空白)跟跳脫符號 : 會沒辦法用 matcher
	private String cleanText = data.getFull().replaceAll("\n|\r|　| ", " ");

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

	// 將正文中的中文數字轉換為阿拉伯數字
	public static String replaceChineseNumbers(String text) {
		// 正則表達式匹配中文數字
		String pattern = "[一二三四五六七八九十百]+";
		Pattern compiledPattern = Pattern.compile(pattern);
		Matcher matcher = compiledPattern.matcher(text);

		// 使用 StringBuffer 儲存處理後的結果
		StringBuffer processedText = new StringBuffer();
		while (matcher.find()) {
			// 取得匹配到的中文數字
			String chineseNumber = matcher.group();
			// 將中文數字轉換為阿拉伯數字
			int arabicNumber = convertChineseToArabic(chineseNumber);
			// 用阿拉伯數字替換掉中文數字
			matcher.appendReplacement(processedText, String.valueOf(arabicNumber));
		}
		// 添加剩餘的文本
		matcher.appendTail(processedText);
		return processedText.toString();
	}

	// 提取正文
	public ArrayList<String> readJsonTest(String pattern) {
	    // 找出判決書中的符合 pattern 的段落
	    Pattern lowPattern = Pattern.compile(pattern);
	    Matcher matcher = lowPattern.matcher(cleanText);
	    HashSet<String> lowSet = new HashSet<>(); // 使用 HashSet 來去重
	    int index = 0;
	    
	    // 透過迴圈尋找是否有符合條件的內容
	    while (matcher.find(index)) {
	        // 使用 HashSet 來自動去重
	        lowSet.add(matcher.group());
	        index = matcher.end();
	    }

	    // 返回轉換為 ArrayList
	    return new ArrayList<>(lowSet);
	}

	// 主方法來提取並轉換法律條文
	@Test
	public void extractAndConvertLegalText() {
	    // 定義法律條文的正則表達式
	    String lawPattern1 = "(毒品危害防制條例|陸海空軍刑法|煙害防制法|貪污治罪條例|山坡地保育利用條例|刑法|銀行法)第\\d+條(第\\d+項)?";
	    // 使用 readJsonTest 方法提取所有符合條件的法律條文
	    ArrayList<String> result = readJsonTest(lawPattern1);
	    if (result.size() > 0) {
	        // 顯示第一條法律條文
	        String firstLawText = result.get(0);
	        System.out.println("第一個法律條文: " + firstLawText);
	    } else {
	        System.out.println("沒有找到法律條文。");
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