//package SmartLegalSearch;
//
//import java.io.File;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import SmartLegalSearch.dto.CaseInfo;
//import SmartLegalSearch.readJson.ReadJson;
//import SmartLegalSearch.vo.ReadJsonVo;
//
//public class LegalCaseParser {
//	/*
//	 * group_id id court date text judge_name defendant_name url charge 獢辣蝢斤��蝣�
//	 * 獢辣��銝�霅蝣� 撖拍�� 獢 �瘙箸��� �瘙箏摰� 瘜���� 鋡怠���� �瘙箸��蝯�
//	 */
//
//	// 霈����蝡� Json 瑼�
//	private static ReadJson readJson = new ReadJson();
//	// 閮剔蔭 JSON ��辣��楝敺�
//	private static ReadJsonVo data = readJson
//			.readJson("D:\\JavaProject\\������瘜���\���\�瘙暝\KLDM,112,��迄,600,20240516,1.json");
//
//	 // �����葉憭�征�(銝��蝛箇�敶Ｙ征�)頝歲�蝚西�� : ���齒瘜 matcher
//	static String cleanText = data.getFull().replaceAll("[\\r|\\n|\\s|'��']+", "");
//
//	public ArrayList<String> readJson1Test(String pattern) {
//		// ���瘙箸銝剔�泵��� pattern ��挾�
//		Pattern lowPattern = Pattern.compile(pattern);
//		// �脰����: .group���蝚血��辣���葡畾萸��.start���.end���蝚血��辣�����蔭�����蔭
//		Matcher matcher = lowPattern.matcher(cleanText);
//		// ��������泵���辣���葡畾�
//		ArrayList<String> lowList = new ArrayList<>();
//		// ��蝝���泵���辣���葡畾菜�敺���蔭index雿蔭
//		int index = 0;
//		// ��艘�������泵���辣��摰� (index �銝��泵���辣����挾銝剜�敺���摮葡銝剔��蔭)
//		while (matcher.find(index)) {
//			// ������泵���辣���葡畾�
//			lowList.add(matcher.group());
//			// 蝝���泵���辣���葡畾菜�敺���index���蔭
//			index = matcher.end();
//		}
//		// �����������摰�
////		lowList.forEach(item -> {
////			System.out.println(item);
////		});
//		return lowList;
//	}
//
//	// 獢��祟�����
//	public void courtAndCharge() {
//		// 獢��
//		String idPattern = "([銝�鈭���銝銋�|\\d){2,4}撟游漲(.){1,6}摮洵([銝�鈭���銝銋�|\\d){1,5}���";
//		String id = readJson1Test(idPattern).get(0);
//		System.out.println("獢��: " + id);
//
//		// 撖拍��
//		String court = data.getId().substring(0, 3);
//		System.out.println("瘜隞����: " + court);
//
//		// 獢
//		String charge = data.getTitle();
//		System.out.println("獢: " + charge);
//	}
//
//	// �瘙箸���(���摩�摮�)
//	public void verdictDate() {
//		// ������蝛箸��摮�蝛箸嚗蒂皜���征�
//		cleanText = cleanText.replaceAll("\\u3000", " ").replaceAll("\\s+", " ");
//		System.out.println("�����: " + cleanText);
//
//		// ���瘙箸���迤��”���������蝛箸
//		String pattern = "銝苒\s*�\\s*瘞\s*��\s*([銝�鈭���銝銋����\d]{1,4})\\s*撟廄\s*([銝�鈭���銝銋�\\d]{1,2})\\s*��\s*([銝�鈭���銝銋�\\d]{1,2})\\s*�";
//
//		// �����挾�
//		ArrayList<String> dateStrList = readJson1Test(pattern);
//		System.out.println("�������挾�: " + dateStrList);
//
//		if (dateStrList.isEmpty()) {
//			System.out.println("���蝚血�撘�瘙箸����");
//			return;
//		}
//
//		try {
//			// ���洵銝��������挾�
//			String firstDateStr = dateStrList.get(0).replaceAll("銝苒\s*�\\s*瘞\s*��\s*|\\s+", "");
//			System.out.println("�������挾�: " + firstDateStr);
//
//			// ��撟港遢��遢����
//			String[] dateParts = firstDateStr.split("撟揉���");
//			// 銝剜�摮�����摩�摮�
//			int year = convertChineseToArabic(dateParts[0]) + 1911;
//			int month = convertChineseToArabic(dateParts[1]);
//			int day = convertChineseToArabic(dateParts[2]);
//
//			// �撘����
//			String formattedDate = String.format("%04d-%02d-%02d", year, month, day);
//			System.out.println("�瘙箸���: " + formattedDate);
//		} catch (Exception e) {
//			System.out.println("閫���瘙箸�����隤�: " + e.getMessage());
//		}
//	}
//
//	// url
//	public void httpTest() {
//		// ��身 data.getId() 餈��� id 摮葡
//		String id = data.getId();
//		System.out.println("���� ID: " + id);
//		// 雿輻���雯����瘜�撱箇雯��
//		String url = generateUrl(id);
//		// ��������雯��
//		System.out.println(url);
//	}
//
//	// ���雯����瘜�
//	public String generateUrl(String id) {
//		// ����� URL �摰寞撘�
//		String encodedId = id.replace(",", "%2c"); // ������
//		// encodedId = encodedId.replace("��迄", "%e9%87%91%e8%a8%b4"); //
//		// ���葉���隞交���瘙撅楊蝣潸����
//
//		// 餈���末��雯��
//		return "https://judgment.judicial.gov.tw/FJUD/data.aspx?ty=JD&id=" + encodedId;
//	}
//
////=====================================================================
//	// 銝剜�摮�摮�
//	public static int convertChineseToArabic(String chineseNumber) {
//		int result = 0; // ��蝯�����頧���摮�
//		int temp = 0; // �靘敞蝛������摮�
//		// �����������甇亙���
//		if (chineseNumber.contains("�")) {
//			String[] parts = chineseNumber.split("�");
//			// ����������
//			if (!parts[0].isEmpty()) {
//				temp = processDigits(parts[0]);
//			} else {
//				temp = 1; // 憒����瘝�摮��� 1
//			}
//			result += temp * 100; // 蝯衣�����������
//			chineseNumber = parts.length > 1 ? parts[1] : ""; // ���歇蝬�����������
//		}
//		// ����������
//		if (chineseNumber.contains("���")) {
//			String[] parts = chineseNumber.split("���");
//			// �����������
//			if (!parts[0].isEmpty()) {
//				temp = processDigits(parts[0]);
//			} else {
//				temp = 1; // 憒�����瘝�摮��� 1 ���
//			}
//			result += temp * 10; // 蝯衣������������
//			chineseNumber = parts.length > 1 ? parts[1] : ""; // ���歇蝬������������
//		}
//		// ���銝�摮���
//		if (!chineseNumber.isEmpty()) {
//			result += processDigits(chineseNumber); // 撠銝������
//		}
//		return result; // 餈�����摮�
//	}
//
//	// ���擗�摮���
//	private static int processDigits(String digits) {
//		int value = 0;
//		// 銝剜�摮�����
//		Map<String, Integer> number = Map.of("銝�", 1, "鈭�", 2, "銝�", 3, "���", 4, "鈭�", 5, "�", 6, "銝�", 7, "�", 8, "銋�", 9);
//		for (int i = 0; i < digits.length(); i++) {
//			String currentChar = digits.substring(i, i + 1);
//			if (number.containsKey(currentChar)) {
//				value += number.get(currentChar);
//			}
//		}
//		return value;
//	}
//
//	// �瘜���辣���葉��葉��摮�����摩�摮�
//	public static String convertTextChineseNumbers(String text) {
//		// ���葉��摮�迤��”������� "蝚砍����" ��� "蝚砍����" 蝑撘��
//		Pattern pattern = Pattern.compile("蝚�([銝�鈭���銝銋��]+)(璇���)");
//		Matcher matcher = pattern.matcher(text);
//		// ��摮頧������
//		StringBuffer result = new StringBuffer();
//		// ��風����葉��摮�
//		while (matcher.find()) {
//			String chineseNumber = matcher.group(1); // ���葉��摮���
//			int arabicNumber = convertChineseToArabic(chineseNumber); // 頧����摩�摮�
//			String replacement = "蝚�" + arabicNumber + matcher.group(2); // 蝯�������泵銝�
//			matcher.appendReplacement(result, replacement); // ����������
//		}
//		matcher.appendTail(result); // 瘛餃�擗���
//		return result.toString();
//	}
//
//	// ���迤���
//	public static String readJson2Test(String pattern) {
//		// ���瘙箸銝剔�泵��� pattern ��挾�
//		Pattern lowPattern = Pattern.compile(pattern);
//		// 雿輻甇������
//		// 皜�征�摮葡嚗"甇� ���"]嚗誑���葉��摮�����摩�摮�
//		// 皜�征�摮葡銝西��葉��摮�
//		String cleanText1 = convertTextChineseNumbers(cleanText.replaceAll("\\s+", ""));
//		// 摰儔銝餅������迤��”����
//		Pattern endPattern = Pattern.compile("(鈭祕���|鈭祕|��|)");
//		Matcher endMatcher = endPattern.matcher(cleanText1);
//		// ����蜓����揣撘�
//		int mainEndIndex = cleanText1.length(); // 暺�����偏
//		if (endMatcher.find()) {
//			mainEndIndex = endMatcher.start(); // 憒����������韏瑕��蔭
//		}
//		// 1. ��岫敺蜓��挾�銝剜�����
//		int mainStartIndex = cleanText1.indexOf("銝餅��");
//		if (mainStartIndex != -1 && mainEndIndex > mainStartIndex) {
//			String mainText = cleanText1.substring(mainStartIndex, mainEndIndex).trim();
////			System.out.println("銝餅���摰�: " + mainText); // 隤輯岫�
//			Matcher matcher = lowPattern.matcher(mainText);
//			if (matcher.find()) {
////				System.out.println("銝餅��������: " + matcher.group());
//				return matcher.group(); // 憒��瘜���餈��
//			}
//		}
//		// 2. 敺��葉������
//		int appendixStartIndex = cleanText1.indexOf("���獢�蔽蝘����");
//		if (appendixStartIndex != -1) {
//			String appendixText = cleanText1.substring(appendixStartIndex).trim();
////			System.out.println("������: " + appendixText); // 隤輯岫�
//			Matcher matcher = lowPattern.matcher(appendixText);
//			if (matcher.find()) {
////				System.out.println("����������: " + matcher.group());
//				return matcher.group();
//			}
//		}
//		// 3. 敺�”銝剜�������”銝�����
//		// ����”銝���絲憪�蔭
//		Pattern tablePattern = Pattern.compile("��”銝�嚗�");
//		Matcher tableMatcher = tablePattern.matcher(cleanText1);
//		int tableStartIndex = -1;
//		if (tableMatcher.find()) {
//			tableStartIndex = tableMatcher.start();
//		}
//		// 憒��鈭�”銝�
//		if (tableStartIndex != -1) {
//			// ��岫����”銝�絲憪�蔭
//			int tableThreeStartIndex = cleanText1.indexOf("��”銝�", tableStartIndex);
//			if (tableThreeStartIndex == -1) {
//				return "���隞颱����";
//			}
//			// ����”銝��偏蝭��
//			int tableEndIndex = cleanText1.indexOf("��”���", tableThreeStartIndex);
//			if (tableEndIndex == -1) {
//				tableEndIndex = cleanText1.length(); // 憒����”�������偏
//			}
//			// 蝣箔������
//			if (tableEndIndex > tableThreeStartIndex) {
//				// ����”銝蝯偏���
//				String tableText = cleanText1.substring(tableThreeStartIndex, tableEndIndex).trim();
////				System.out.println("��”銝��摰�: " + tableText);
//				// ���蝵芯�祕����
//				Pattern crimeFactPattern = Pattern.compile("�蝵芯�祕.*?(��”��$)", Pattern.DOTALL);
//				Matcher crimeFactMatcher = crimeFactPattern.matcher(tableText);
//				if (crimeFactMatcher.find()) {
//					String crimeFacts = crimeFactMatcher.group();
////			            System.out.println("�蝵芯�祕�摰�: " + crimeFacts);
//					// ��蝵芯�祕畾菔銝剜�����
//					Matcher matcher = lowPattern.matcher(crimeFacts);
//					boolean found = false;
//					while (matcher.find()) {
////						System.out.println("�蝵芯�祕��������: " + matcher.group());
//						found = true;
//						return matcher.group(); // 餈����洵銝�����
//					}
//				}
//			}
//		}
//		// 憒����挾�銝剖���瘜�����內
//		return "���隞颱����";
//
//	}
//
//	public static void main(String[] args) {
//		// 霈���� JSON ���摰�
//		String fullText = cleanText; // ��雿輻��������
//
////		System.out.println(fullText);
//		// ���◤�����
//		String defendantName = DefendantName(fullText);
//		System.out.println("鋡怠����: " + defendantName);
//
//		// ��������
//		String judgeName = JudgesName(fullText);
//		System.out.println("瘜����: " + judgeName);
//
//		// ���瘙箏摰�
//		String judgmentContent = JudgmentContent(fullText);
//		System.out.println("�瘙箏摰�:\n" + judgmentContent);
//
//		// 撱箇�� LegalCaseParser �隞�
//		LegalCaseParser parser = new LegalCaseParser();
//		parser.courtAndCharge();
//
//		parser.verdictDate();
//
//		parser.httpTest();
//
//		// 摰儔瘜����迤��”������迤��”�������車瘜��
//		String lawPattern = "(瘣��瘜瘥�摰喲�璇��瘚瑞征頠����拿��瘜鞎芣情瘝餌蔽璇�撅勗�靽��璇���銵�����)蝚枯\d+璇�(蝚枯\d+���)?";
//		// ������
//		String extractedLaws = readJson2Test(lawPattern);
////		System.out.println(extractedLaws);
//		// 頛詨蝯��
//		if (!extractedLaws.isEmpty()) {
//			System.out.println("�������: " + extractedLaws); // ���������
////					System.out.println("蝚� 1 ����: " + extractedLaws.get(0)); // ��洵銝�����
//		} else {
//			System.out.println("瘝��隞颱�����");
//		}
//	}
//
//	// 鋡怠����
//	private static String DefendantName(String fullText) {
//		// ���� "鋡怠��" 敺�� 2~4 �葉���泵
//	    Pattern pattern = Pattern.compile("鋡怠��([\\u4E00-\\u9FFF]{2,3})");
//	    Matcher matcher = pattern.matcher(fullText);
//	    if (matcher.find()) {
//	        // ����硃�����
//	        return matcher.group(1).trim();
//	    }
//	    return "��";
//	}
//
//	// 瘜����
//	private static String JudgesName(String fullText) {
//		// ��璅��誑銝迤�霅�������
//		String marker = "隞乩�迤�霅�������";
//		int markerIndex = fullText.indexOf(marker);
//		if (markerIndex != -1) {
//			// ��������畾菜���
//			String precedingText = fullText.substring(0, markerIndex);
//			// ���� "瘜��" 敺����
//			Pattern pattern = Pattern.compile("瘜\s*摰\s*([\\u4E00-\\u9FFF]{2,4})");
//			Matcher matcher = pattern.matcher(precedingText);
//			if (matcher.find()) {
//				return matcher.group(1).trim(); // ���������
//			}
//		}
//		return "��";
//	}
//
//	// �瘙箏摰�
//	private static String JudgmentContent(String fullText) {
//		// 韏瑕����蜓���
//		String startMarker = "銝餅��";
//		// 蝯�����蔽蝘��
//		String endMarker = "����";
//
//		// ��韏瑕�����蔭
//		int startIndex = fullText.indexOf(startMarker);
//		int endIndex = fullText.indexOf(endMarker);
//
//		if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
//			return fullText.substring(startIndex, endIndex + endMarker.length()).trim();
//		}
//		return "���摰��瘙箏摰孵����";
//	}
//
//}
