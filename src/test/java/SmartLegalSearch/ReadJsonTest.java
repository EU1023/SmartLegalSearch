package SmartLegalSearch;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import SmartLegalSearch.readJson.ReadJson;
import SmartLegalSearch.vo.ReadJsonVo;

//@SpringBootTest
public class ReadJsonTest {

	// Ū�����a�� Json �ɮץ�
	private ReadJson readJson = new ReadJson();

	// �ɮ׸��|
	private ReadJsonVo data = readJson.readJson("D:\\JavaProject\\�O�W�򶩦a��k�|�D��\\KLDM,111,���D,198,20240513,7.json");

	// ���o�P�M�D��
//	private String text = new String(data.getFull());

	// ��z�峹���h�l�Ů�(�@��ťաB���Ϊť�)�����Ÿ� : �|�S��k�� matcher
	private String cleanText = data.getFull().replaceAll("\n|\r|�@| ", " ");

	// ����Ʀr�ഫ���ԧB�Ʀr�Τu�� Map
	private Map<String, Integer> number = Map.of("�@", 1, "�G", 2, "�T", 3, "�|", 4, "��", 5, "��", 6, "�C", 7, "�K", 8, "�E", 9,
			"�Q", 10);

	public ArrayList<String> readJsonTest(String pattern) {
		// ��X�P�M�Ѥ����ŦX pattern ���q��
		Pattern lowPattern = Pattern.compile(pattern);
		// �i����: .group�i���X�ŦX���󪺦r��q�B.start��.end�|�^�ǲŦX���󪺶}�l��m�ε�����m
		Matcher matcher = lowPattern.matcher(cleanText);
		// �Ω�`���Ҧ��ŦX���󪺦r��q
		ArrayList<String> lowList = new ArrayList<>();
		// �Ω�����ŦX���󪺦r��q�̫�@�Ӧ�mindex��m
		int index = 0;
		// �z�L�j��M��O�_���ŦX���󪺤��e (index ���W�@�ӲŦX���󪺤�r�q���̫�@�Ӧr�b�r�ꤤ����m)
		while (matcher.find(index)) {
			// �`���Ҧ��ŦX���󪺦r��q
			lowList.add(matcher.group());
			// �����ŦX���󪺦r��q�̫�@�Ӧr�bindex����m
			index = matcher.end();
		}
		// �C�L�X�Ҧ��`���쪺���e
		lowList.forEach(item -> {
			System.out.println(item);
		});
		return lowList;
	}

	// �k�|�N���B�ץ�
	@Test
	public void courtIdAndchargeTest() {
		// �k�|�N��
		String courtId = data.getId().substring(0, 3);
		System.out.println(courtId);
		// �ץ�
		String charge = data.getTitle();
		System.out.println(charge);
	}

	// �D�k
	@Test
	public void lawTest1() {
		String lawPattern1 = "�D�k��\\d+��(��\\d+��)?(.{0,10}�B�D�k��\\d+��(��\\d+��)?)?";
		readJsonTest(lawPattern1);
	}

	// �D�ƶD�^�k
	@Test
	public void lawTest2() {
		String lawPattern2 = "�D�ƶD�^�k��\\d+��(��\\d+��)?";
		readJsonTest(lawPattern2);
	}

	// �׸�
	@Test
	public void idTest() {
		String pattern = "([�@�G�T�|�����C�K�E�Q]|\\d){2,4}�~��(.){1,6}�r��([�@�G�T�|�����C�K�E�Q]|\\d){1,5}��";
		readJsonTest(pattern);
	}

	// �P�M���(���ԧB�Ʀr)
	@Test
	public void date() {
		// �P�M��
		String pattern = "���إ���([�@�G�T�|�����C�K�E�Q]|\\d){1,3}�~" + "([�@�G�T�|�����C�K�E�Q]|\\d){1,2}��([�@�G�T�|�����C�K�E�Q]|\\d){1,3}��";
		ArrayList<String> dateStrList = readJsonTest(pattern);

		// ���X�Ĥ@�ӫ�h�� ���إ��� ����ΥX���ԧB�Ʀr
		String[] dateStr = dateStrList.get(0).replaceAll("���إ���", "").split("�~|��|��");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		int year = (Integer.parseInt(dateStr[0]) + 1911);
		if (dateStr[1].length() == 1) {
			dateStr[1] = "0" + dateStr[1];
		}
		if (dateStr[2].length() == 1) {
			dateStr[2] = "0" + dateStr[2];
		}
		String dateChar = year + "-" + dateStr[1] + "-" + dateStr[2];
		LocalDate date = LocalDate.parse(dateChar, formatter);
		System.out.println(date);

	}

	@Test
	public void httpTest() {
		// ���] data.getId() ��^�� id �r��
		String id = data.getId();
		System.out.println("��l ID: " + id);

		// �ϥΥͦ����}����k�ӳЫغ��}
		String url = generateUrl(id);

		// �����L�X�ͦ������}
		System.out.println(url);
	}

	// �ͦ����}����k
	public String generateUrl(String id) {
		// �����r���� URL �ݮe�榡
		String encodedId = id.replace(",", "%2c"); // �B�z�r��
		// encodedId = encodedId.replace("���D", "%e9%87%91%e8%a8%b4"); //
		// �B�z����]�i�H�ھڻݨD�X�i�s�X�W�h�^

		// ��^�զX�n�����}
		return "https://judgment.judicial.gov.tw/FJUD/data.aspx?ty=JD&id=" + encodedId;
	}

}
