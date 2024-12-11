package SmartLegalSearch;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import SmartLegalSearch.readJson.ReadJson;
import SmartLegalSearch.vo.ReadJsonVo;

//@SpringBootTest
public class ReadJsonTest {

	
	@Test
	public void readJsonTest() {
		ReadJson readJson = new ReadJson();
		ReadJsonVo data = readJson.readJson("C:\\Users\\user\\Desktop\\Jerry\\Java\\�O�W�h�L�a��k�|�D��\\SLDM,87,��,241,20000811.json");
		// �k�| �N��
		String court = data.getId().substring(0, 3);
		// �ץ�
		String charge = data.getTitle();
		// �D�k
		String pattern = "�D�k��.*��(��.*��)?";
		// �D�ƶD�^�k
		String pattern2 = "�D�ƶD�^�k��.*��(��.*��)?";
		Pattern lowPattern = Pattern.compile(pattern2);
		Matcher matcher = lowPattern.matcher(data.getFull());
		ArrayList<String> lowList = new ArrayList<>();
		int index = 0;
		while(matcher.find(index)) {
			lowList.add(matcher.group());
			index = matcher.end();
		}
		lowList.forEach(item -> {
			System.out.println(item);
		});
	}
}
