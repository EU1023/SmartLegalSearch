package SmartLegalSearch;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import SmartLegalSearch.readJson.ReadJson;
import SmartLegalSearch.vo.ReadJsonVo;

//@SpringBootTest
public class ReadJsonTests {

	// 讀取本地端 Json 檔案用
	private ReadJson readJson = new ReadJson();

	// 檔案路徑
    private ReadJsonVo data = readJson.readJson("C:\\Users\\mm312\\Downloads\\臺灣基隆地方法院刑事\\KLDM,112,金訴,625,20240523,1.json");
//	private ReadJsonVo data = readJson.readJson("C:\\Users\\mm312\\Downloads\\臺灣基隆地方法院刑事\\KLDM,112,原金訴,26,20240503,5.json");

	// 取得判決主文
// private String text = new String(data.getFull());

	// 整理文章中多餘空格(一般空白、全形空白)跟跳脫符號 : 會沒辦法用 matcher
	private String cleanText = data.getFull().replaceAll("\n|\r|　| ", " ");

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
//        lowList.forEach(item -> {
//            System.out.println(item);
//        });
		return lowList;
	}

	// 案號、審理法院、案由
	@Test
	public void courtAndCharge() {
		// 案號
		String idPattern = "([一二三四五六七八九十]|\\d){2,4}年度(.){1,6}字第([一二三四五六七八九十]|\\d){1,5}號";
		String id = readJsonTest(idPattern).get(0);
		System.out.println("案號: " + id);


		// 審理法院
		String court = data.getId().substring(0, 3);
		System.out.println("法院代號: " + court);

		// 案由
		String charge = data.getTitle();
		System.out.println("案由: " + charge);
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


	// 判決日期(阿拉伯數字)
	@Test
	public void verdictDate() {
		// 判決日
		String pattern = "中\\s{2}華\\s+民\\s{2}國\\s{2}([一二三四五六七八九十]|\\d){1,3}\\s+年\\s+" + "([一二三四五六七八九十]|\\d){1,2}\\s+月\\s{2}([一二三四五六七八九十]|\\d){1,3}\\s+日";
		ArrayList<String> dateStrList = readJsonTest(pattern);

		// 取出第一個後去除 中華民國 後分割出阿拉伯數字
		String[] dateStr = dateStrList.get(0).replaceAll("中\\s+華\\s+民\\s+國|\\s+", "").split("年|月|日");
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
		// encodedId = encodedId.replace("金訴", "%e9%87%91%e8%a8%b4"); //
		// 處理中文（可以根據需求擴展編碼規則）

		// 返回組合好的網址
		return "https://judgment.judicial.gov.tw/FJUD/data.aspx?ty=JD&id=" + encodedId;
	}

	// 事實
	@Test
	public void facts() {
		// [\\s\\S] 表示會匹配所有空白和非空白字元，*?表示理由前的內容
		String pattern = "事\\s*實([\\s\\S]*?)理\\s*由";
		Pattern compiledPattern = Pattern.compile(pattern);
		Matcher matcher = compiledPattern.matcher(data.getFull());

		if (matcher.find()) {
			System.out.println("事實：");
			System.out.println(matcher.group(1).trim());
		}
	}

	// 理由
	@Test
	public void motive() {
		String pattern = "理\\s*由([\\s\\S]*?應依法論科。)";
		Pattern compiledPattern = Pattern.compile(pattern);
		Matcher matcher = compiledPattern.matcher(data.getFull());

		if (matcher.find()) {
			System.out.println("理由：");
			System.out.println(matcher.group(1).trim());
		}
	}

	// 刑罰
	@Test
	public void penalties() {
		// 刑罰內容
		String penalties = "";
		String penaltiesPattern = "主\\s*文([\\s\\S]+?)事\\s*實";

		Pattern compiledPenalties = Pattern.compile(penaltiesPattern);
		Matcher penaltiesMatcher = compiledPenalties.matcher(data.getFull());

		if (penaltiesMatcher.find()) {
			// 去除文本首尾空白，及移除文內多餘空白(半形、全形)
			penalties = penaltiesMatcher.group(1).trim()
					.replaceAll("[\\s\\u3000]+", "");
//            System.out.println(penalties);
		}

		// 刑罰類型
		String type = "";
		String typePattern = "處([\\s\\S]*?刑)";

		Pattern compiledType = Pattern.compile(typePattern);
		Matcher typeMatcher = compiledType.matcher(penalties);

		if (typeMatcher.find()) {
			type = typeMatcher.group(1).replaceAll("[\\s\\u3000]+", "");
		}
		System.out.println("刑罰類型: " + type);

		// 刑期月數
		String months = "";
		String monthsPattern = "處有期徒刑([\\s\\S]*?)月";

		Pattern compiledMonths = Pattern.compile(monthsPattern);
		Matcher monthsMatcher = compiledMonths.matcher(penalties);

		if (monthsMatcher.find()) {
			months = monthsMatcher.group(1);
		}
		System.out.println("刑罰月數: " + months);

		// 罰金類型
		String fineType = "";
		String fineTypePattern = "([專選併易]科)罰金";

		Pattern compiledFineType = Pattern.compile(fineTypePattern);
		Matcher fineTypeMatcher = compiledFineType.matcher(penalties);

		if (fineTypeMatcher.find()) {
			fineType = fineTypeMatcher.group(1);
		}
		System.out.println("罰金類型: " + fineType);


		// 罰金金額
		String fine = "";
		String finePattern = "新臺幣([\\s\\S]*?)元";

		Pattern compiledFine = Pattern.compile(finePattern);
		Matcher fineMatcher = compiledFine.matcher(penalties);

		if (fineMatcher.find()) {
			fine = fineMatcher.group(1);
		}
		System.out.println("罰金金額: " + fine);
	}

	// 關係人
	@Test
	public void relatedParties() {
		// 類型角色：被告
		String pattern = "被\\s*告\\s*(\\p{IsHan}+)\\s*上列";  // \\p{IsHan} 可匹配任何漢字。
		Pattern compiledPattern = Pattern.compile(pattern);
		Matcher matcher = compiledPattern.matcher(data.getFull());

		List<String> defendantName = new ArrayList<>();

		while (matcher.find()) {
			defendantName.add(matcher.group(1));
//            System.out.println("被告姓名: " + matcher.group(1));
		}

		System.out.println("被告姓名: " + defendantName);

		// 類型角色：法官
		String context = "";
		String contextPattern = "刑事第.*庭.*法.*官.*([\\s\\S]*?)以上正本證明與原本無異";

		Pattern compiledContext = Pattern.compile(contextPattern);
		Matcher contextMatcher = compiledContext.matcher(data.getFull());

		// 先取得部分文本
		if (contextMatcher.find()) {
			context = contextMatcher.group();
		}

		List<String> judgeName = new ArrayList<>();
		String judgeNamePattern = "官\\s*([\\S\\s]+?)以上正本";
		Pattern compiledJudgeName = Pattern.compile(judgeNamePattern);
		Matcher judgeNameMatcher = compiledJudgeName.matcher(context);

		while (judgeNameMatcher.find()) {
			judgeName.add(judgeNameMatcher.group(1).trim()
					.replaceAll("[\\s\\u3000]+", ""));
//            System.out.println("法官姓名: " + judgeNameMatcher.group(1));
		}

		System.out.println("法官姓名: " + judgeName);
	}

	// 相關法條
	@Test
	public void law() {
		String pattern = "主\\s*文\\s*\\S*犯([\\s\\S]+?罪)";
		Pattern compiledPattern = Pattern.compile(pattern);
		Matcher matcher = compiledPattern.matcher(data.getFull());

		if (matcher.find()) {
			System.out.println(matcher.group(1));
		}
	}


}
