package SmartLegalSearch;

import java.time.LocalDate;
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
	private ReadJsonVo data = readJson
			.readJson("D:\\JavaProject\\�O�W�򶩦a��k�|�D��\\KLDM,111,���D,198,20240513,7.json");
	
	// ���o�P�M�D��
//	private String text = new String(data.getFull());

	// ��z�峹���h�l�Ů�(�@��ťաB���Ϊť�)�����Ÿ� : �|�S��k�� matcher
	private String cleanText = data.getFull().replaceAll("\n|\r|�@| ", "");

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

	// �P�M���(�䴩�c�餤��Ʀr����ԧB�Ʀr)
	@Test
	public void date() {
		// �P�M��
		String pattern = "���إ���([�@�G�T�|�����C�K�E�Q]|\\d){1,3}�~" + "([�@�G�T�|�����C�K�E�Q]|\\d){1,2}��([�@�G�T�|�����C�K�E�Q]|\\d){1,3}��";
		ArrayList<String> dataStrList = readJsonTest(pattern);

		// ���X�Ĥ@�ӫ�h�� ���إ��� ����ΥX���ԧB�Ʀr
		String[] dataStr = dataStrList.get(0).replaceAll("���إ���", "").split("�~|��|��");
		// ��l��
		LocalDate date = null;
		int year = 0;
		int mon = 0;
		int day = 0;
		// �P�w ����O�c�餤���
		boolean isChinese = false;
		if (number.containsKey(dataStr[0].split("")[0])) {
			isChinese = true;
		}
		for (int i = 0; i < dataStr.length; i++) {
			if (i == 0) {
				if (isChinese) {
					String[] CyearList = dataStr[i].split("");
					for (int j = 0; j < CyearList.length; j++) {
						if (!CyearList[j].equalsIgnoreCase("�Q")) {
							year += number.get(CyearList[j]);
						} else {
							year *= number.get(CyearList[j]);
						}
					}
					year += 1991;
					continue;
				}
				year = Integer.parseInt(dataStr[i]) + 1911;
				continue;
			}
			if (i == 1) {
				if (isChinese) {
					String[] CyearList = dataStr[i].split("");
					for (int j = 0; j < CyearList.length; j++) {
							mon += number.get(CyearList[j]);
					}
					continue;
				}
				mon = Integer.parseInt(dataStr[i]);
				continue;
			}
			if (i == 2) {
				if (isChinese) {
					String[] CyearList = dataStr[i].split("");
					for (int j = 0; j < CyearList.length; j++) {
						if (!CyearList[j].equalsIgnoreCase("�Q")) {
							day += number.get(CyearList[j]);
						} else {
							if(day == 0) {
								day = 1;
							}
							day *= number.get(CyearList[j]);
						}
					}
					continue;
				}
				day = Integer.parseInt(dataStr[i]);
			}
			date = LocalDate.of(year, mon, day);
		}
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
	    //encodedId = encodedId.replace("���D", "%e9%87%91%e8%a8%b4"); // �B�z����]�i�H�ھڻݨD�X�i�s�X�W�h�^

	    // ��^�զX�n�����}
	    return "https://judgment.judicial.gov.tw/FJUD/data.aspx?ty=JD&id=" + encodedId;
	}

	

	
	
	
}
