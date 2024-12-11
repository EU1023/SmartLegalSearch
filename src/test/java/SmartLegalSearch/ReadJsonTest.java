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
		ReadJsonVo data = readJson.readJson("C:\\Users\\user\\Desktop\\Jerry\\Java\\�O�W�h�L�a��k�|�D��\\SLDM,87,��,3,20000810.json");
		// �k�| �N��
		String court = data.getId().substring(0, 3);
		// �ץ�
		String charge = data.getTitle();
		// �D�@
		String pattern = "�D�k��.*��(��.*��)?";
		Pattern lowPattern = Pattern.compile(pattern);
		Matcher matcher = lowPattern.matcher(data.getFull());
		ArrayList<String> lowList = new ArrayList<>();
		int index = 0;
		while(matcher.find(index)) {
			System.out.println(matcher.group() + " " +matcher.start() + " " + matcher.end());
			lowList.add(matcher.group());
			index = matcher.end();
		}
		lowList.forEach(item -> {
			System.out.println(item);
		});
	}
}
