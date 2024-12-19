package SmartLegalSearch;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import SmartLegalSearch.dto.CaseInfo;

public class LegalCaseParser {

	public static void main(String[] args) {
		// �]�m JSON ��󪺸��|
//	    String filePath = "D:\\JavaProject\\KSDM,86,�D,3155,20000828.json";
//		String filePath = "D:\\JavaProject\\KSDM,87,�D,1410,20000831.json";
		String filePath = "D:\\JavaProject\\KLDM,113,�D,29,20240524,1.json";
		// �Ы� ObjectMapper ��Ū�� JSON ���
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			// Ū�� JSON ���ñN��M�g�� CaseInfo ��
			CaseInfo caseInfo = objectMapper.readValue(new File(filePath), CaseInfo.class);

			// ��X�ѪR�᪺���G
			System.out.println(caseInfo);

			// �q fullText ������������
			String fullText = caseInfo.getFullText();

			// �o�̥i�H�ϥΥ��W��F����²�檺�r�Ŧ�B�z�Ӵ�������H��
			String defendantName = extractDefendantName(fullText);
			String legalArticles = extractLegalArticles(fullText);
			String sentence = extractSentence(fullText);
			String violationContent = extractViolationContent(fullText);
			String sentenceDate = extractSentenceDate(fullText);

			// ��ܴ�����������
			System.out.println("Defendant Name: " + defendantName);
			System.out.println("Legal Articles: " + legalArticles);
			System.out.println("Sentence: " + sentence);
			System.out.println("Violation Content: " + violationContent);
			System.out.println("Sentence Date: " + sentenceDate);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ���]��������k�A�i�H�ھڻݭn�i���X�i�M��i
	private static String extractDefendantName(String fullText) {
		// ���]�Q�i�W�r�O�u�Q�i�v���᪺�Ĥ@�ӵ�
		if (fullText.contains("/�Q�@�@�@�i|�Q�i/")) {
			return fullText.split("�Q�@�@�@�i")[1].split("\n")[0].trim();
		}
		return "����";
	}

	private static String extractLegalArticles(String fullText) {
		// �u�r�~�M�`������ҡv
		if (fullText.contains("�r�~�M�`�������")) {
			return "�r�~�M�`������ҲĤQ���Ĥ@���B�ĤG��";
		}
		return "����";
	}

	private static String extractSentence(String fullText) {
		// �u�P�M�p���v�M�u�K�D�v
		if (fullText.contains("�P�M�p��")) {
			return "�K�D";
		}
		return "����";
	}

	private static String extractViolationContent(String fullText) {
		// �����H�k���e
		if (fullText.contains("�I�Φw�D�L�R")) {
			return "�I�Φw�D�L�R�ή����]";
		}
		return "����";
	}

	private static String extractSentenceDate(String fullText) {
		// ���]�P�M����X�{�b����̫�
		if (fullText.contains("��    ��    ��    ��")||fullText.contains("���إ���")) {
			return fullText.split("��    ��    ��    ��")[1].split("��")[0].trim();
		}
		return "����";
	}
}
