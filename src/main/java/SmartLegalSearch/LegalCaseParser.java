package SmartLegalSearch;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import SmartLegalSearch.dto.CaseInfo;

public class LegalCaseParser {

	public static void main(String[] args) {
		// 設置 JSON 文件的路徑
//	    String filePath = "D:\\JavaProject\\KSDM,86,訴,3155,20000828.json";
//		String filePath = "D:\\JavaProject\\KSDM,87,訴,1410,20000831.json";
		String filePath = "D:\\JavaProject\\KLDM,113,訴,29,20240524,1.json";
		// 創建 ObjectMapper 來讀取 JSON 文件
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			// 讀取 JSON 文件並將其映射為 CaseInfo 類
			CaseInfo caseInfo = objectMapper.readValue(new File(filePath), CaseInfo.class);

			// 輸出解析後的結果
			System.out.println(caseInfo);

			// 從 fullText 中提取關鍵資料
			String fullText = caseInfo.getFullText();

			// 這裡可以使用正規表達式或簡單的字符串處理來提取關鍵信息
			String defendantName = extractDefendantName(fullText);
			String legalArticles = extractLegalArticles(fullText);
			String sentence = extractSentence(fullText);
			String violationContent = extractViolationContent(fullText);
			String sentenceDate = extractSentenceDate(fullText);

			// 顯示提取的關鍵資料
			System.out.println("Defendant Name: " + defendantName);
			System.out.println("Legal Articles: " + legalArticles);
			System.out.println("Sentence: " + sentence);
			System.out.println("Violation Content: " + violationContent);
			System.out.println("Sentence Date: " + sentenceDate);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 假設的提取方法，可以根據需要進行擴展和改進
	private static String extractDefendantName(String fullText) {
		// 假設被告名字是「被告」之後的第一個詞
		if (fullText.contains("/被　　　告|被告/")) {
			return fullText.split("被　　　告")[1].split("\n")[0].trim();
		}
		return "未知";
	}

	private static String extractLegalArticles(String fullText) {
		// 「毒品危害防制條例」
		if (fullText.contains("毒品危害防制條例")) {
			return "毒品危害防制條例第十條第一項、第二項";
		}
		return "未知";
	}

	private static String extractSentence(String fullText) {
		// 「判決如左」和「免刑」
		if (fullText.contains("判決如左")) {
			return "免刑";
		}
		return "未知";
	}

	private static String extractViolationContent(String fullText) {
		// 提取違法內容
		if (fullText.contains("施用安非他命")) {
			return "施用安非他命及海洛因";
		}
		return "未知";
	}

	private static String extractSentenceDate(String fullText) {
		// 假設判決日期出現在全文最後
		if (fullText.contains("中    華    民    國")||fullText.contains("中華民國")) {
			return fullText.split("中    華    民    國")[1].split("日")[0].trim();
		}
		return "未知";
	}
}
