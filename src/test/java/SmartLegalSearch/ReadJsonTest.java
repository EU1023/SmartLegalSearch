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
		ReadJsonVo data = readJson.readJson("C:\\Users\\user\\Desktop\\Jerry\\Java\\臺灣士林地方法院刑事\\SLDM,87,自,241,20000811.json");
		// 法院 代號
		String court = data.getId().substring(0, 3);
		// 案由
		String charge = data.getTitle();
		// 刑法
		String pattern = "刑法第.*條(第.*項)?";
		// 刑事訴訟法
		String pattern2 = "刑事訴訟法第.*條(第.*項)?";
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
