package SmartLegalSearch;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import SmartLegalSearch.dto.CaseInfo;
import SmartLegalSearch.entity.LegalCase;
import SmartLegalSearch.readJson.ReadJson;
import SmartLegalSearch.service.impl.CaseImpl;
import SmartLegalSearch.vo.ReadJsonVo;

<<<<<<< HEAD
=======
//@SpringBootApplication
>>>>>>> dev_jerry
public class LegalCaseParser {
<<<<<<< HEAD
	
	public static void main(String[] args) throws IOException {
//		BatchProcessingOfJudgments("D:\\JavaProject\\臺灣基隆地方法院刑事\\刑事\\判決");
=======
	/*
	 * group_id id court date text judge_name defendant_name url charge case_type
	 * doc_type案件群組識別碼 案件的唯一識別碼 審理法院 案由 判決日期 判決內容 法官姓名 被告姓名 判決書連結
	 * 案件類型，如刑事(M)、民事(V)、行政(A) 文件類型，裁定或判決或釋字等
	 */

	// 讀取本地端 Json 檔案用
	private static ReadJson readJson = new ReadJson();
	// 設置 JSON 文件的路徑
	private static ReadJsonVo data = readJson
<<<<<<< HEAD
			.readJson("C:\\Users\\mm312\\Downloads\\臺灣基隆地方法院刑事\\KLDM,113,基簡,52,20240531,1.json");
=======
			.readJson("C:\\Users\\user\\Desktop\\202405\\臺灣士林地方法院刑事\\SLDM,111,附民,1579,20240509,1.json");
>>>>>>> dev_jerry

	// 整理文章中多餘空格(一般空白、全形空白)跟跳脫符號 : 會沒辦法用 matcher
	static String cleanContent = data.getFull().replaceAll("[\\r|\\n|\\s|'　']+", "");
	// 未整理原文
	static String unorganizedContent = data.getFull();

	public ArrayList<String> readJson1Test(String pattern) {
		// 找出判決書中的符合 pattern 的段落
		Pattern lowPattern = Pattern.compile(pattern);
		// 進行比對: .group可取出符合條件的字串段、.start或.end會回傳符合條件的開始位置或結束位置
		Matcher matcher = lowPattern.matcher(cleanContent);
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
//		lowList.forEach(item -> {
//			System.out.println(item);
//		});
		return lowList;
>>>>>>> dev_rikka
	}
	
	public static void BatchProcessingOfJudgments() throws IOException {};
}
