package SmartLegalSearch;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import SmartLegalSearch.constants.ResMessage;
import SmartLegalSearch.service.ifs.CaseService;
import SmartLegalSearch.vo.SearchReq;
import SmartLegalSearch.vo.SearchRes;

@SpringBootTest
public class CaseServiceTest {

	@Autowired
	private CaseService caseService ;
	
	@Test
	public void searchTest() {
		SearchReq req = new SearchReq();
		
		// 測試開始時間比結束時間晚
		req.setVerdictStartYear(LocalDate.of(2024, 05, 30));
		req.setVerdictEndYear(LocalDate.of(2024, 05, 10));
		SearchRes res = caseService.searchCriminalCase(req);
		Assert.isTrue(res.getMessage().equalsIgnoreCase(ResMessage.DATE_ERROR.getMessage()), "時間順序測試失敗");
		System.out.println("時間順序測試成功");
		
		// 搜尋測試
		req.setVerdictStartYear(LocalDate.of(2024, 05, 1));
		req.setVerdictEndYear(LocalDate.of(2024, 05, 30));
		req.setSearchName("");
		req.setVerdictId("111年度金訴字第809號");
		req.setCharge("違反洗錢防制法等");
		req.setCourtList(List.of("SLD"));
		req.setCaseType("刑事");
		req.setDocType("判決");
		
		res = caseService.searchCriminalCase(req);
		System.out.println(res.getCaseList().size());
	}
}
