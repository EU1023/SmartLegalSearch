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

	// 讀取本地端 Json 檔案用
	private ReadJson readJson = new ReadJson();

	// 檔案路徑
	private ReadJsonVo data = readJson
			.readJson("D:\\JavaProject\\臺灣基隆地方法院刑事\\KLDM,111,金訴,198,20240513,7.json");
	
	// 取得判決主文
//	private String text = new String(data.getFull());

	// 整理文章中多餘空格(一般空白、全形空白)跟跳脫符號 : 會沒辦法用 matcher
	private String cleanText = data.getFull().replaceAll("\n|\r|　| ", "");

	// 中文數字轉換阿拉伯數字用工具 Map
	private Map<String, Integer> number = Map.of("一", 1, "二", 2, "三", 3, "四", 4, "五", 5, "六", 6, "七", 7, "八", 8, "九", 9,
			"十", 10);

	public ArrayList<String> readJsonTest(String pattern) {
		// 找出判決書中的符合 pattern 的段落
		Pattern lowPattern = Pattern.compile(pattern);
		// 進行比對: .group可取出符合條件的字串段、.start或.end會回傳符合條件的開始位置或結束位置
		Matcher matcher = lowPattern.matcher(cleanText);
		// 用於蒐集所有符合條件的字串段
		ArrayList<String> lowList = new ArrayList<>();
		// 用於紀錄符合條件的字串段最後一個位置index位置
		int index = 0;
		// 透過迴圈尋找是否有符合條件的內容 (index 為上一個符合條件的文字段中最後一個字在字串中的位置)
		while (matcher.find(index)) {
			// 蒐集所有符合條件的字串段
			lowList.add(matcher.group());
			// 紀錄符合條件的字串段最後一個字在index的位置
			index = matcher.end();
		}
		// 列印出所有蒐集到的內容
		lowList.forEach(item -> {
			System.out.println(item);
		});
		return lowList;
	}

	// 法院代號、案由
	@Test
	public void courtIdAndchargeTest() {
		// 法院代號
		String courtId = data.getId().substring(0, 3);
		System.out.println(courtId);
		// 案由
		String charge = data.getTitle();
		System.out.println(charge);
	}

	// 刑法
	@Test
	public void lawTest1() {
		String lawPattern1 = "刑法第\\d+條(第\\d+項)?(.{0,10}、刑法第\\d+條(第\\d+項)?)?";
		readJsonTest(lawPattern1);
	}

	// 刑事訴訟法
	@Test
	public void lawTest2() {
		String lawPattern2 = "刑事訴訟法第\\d+條(第\\d+項)?";
		readJsonTest(lawPattern2);
	}

	// 案號
	@Test
	public void idTest() {
		String pattern = "([一二三四五六七八九十]|\\d){2,4}年度(.){1,6}字第([一二三四五六七八九十]|\\d){1,5}號";
		readJsonTest(pattern);
	}

	// 判決日期(支援繁體中文數字跟阿拉伯數字)
	@Test
	public void date() {
		// 判決日
		String pattern = "中華民國([一二三四五六七八九十]|\\d){1,3}年" + "([一二三四五六七八九十]|\\d){1,2}月([一二三四五六七八九十]|\\d){1,3}日";
		ArrayList<String> dataStrList = readJsonTest(pattern);

		// 取出第一個後去除 中華民國 後分割出阿拉伯數字
		String[] dataStr = dataStrList.get(0).replaceAll("中華民國", "").split("年|月|日");
		// 初始值
		LocalDate date = null;
		int year = 0;
		int mon = 0;
		int day = 0;
		// 判定 日期是繁體中文時
		boolean isChinese = false;
		if (number.containsKey(dataStr[0].split("")[0])) {
			isChinese = true;
		}
		for (int i = 0; i < dataStr.length; i++) {
			if (i == 0) {
				if (isChinese) {
					String[] CyearList = dataStr[i].split("");
					for (int j = 0; j < CyearList.length; j++) {
						if (!CyearList[j].equalsIgnoreCase("十")) {
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
						if (!CyearList[j].equalsIgnoreCase("十")) {
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
	    // 假設 data.getId() 返回的 id 字串
	    String id = data.getId(); 
	    System.out.println("原始 ID: " + id);
	    
	    // 使用生成網址的方法來創建網址
	    String url = generateUrl(id);
	    
	    // 直接印出生成的網址
	    System.out.println(url);
	}

	// 生成網址的方法
	public String generateUrl(String id) {
	    // 替換逗號為 URL 兼容格式
	    String encodedId = id.replace(",", "%2c"); // 處理逗號
	    //encodedId = encodedId.replace("金訴", "%e9%87%91%e8%a8%b4"); // 處理中文（可以根據需求擴展編碼規則）

	    // 返回組合好的網址
	    return "https://judgment.judicial.gov.tw/FJUD/data.aspx?ty=JD&id=" + encodedId;
	}

	

	
	
	
}
