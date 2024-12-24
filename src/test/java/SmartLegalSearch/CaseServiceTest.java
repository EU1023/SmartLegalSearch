package SmartLegalSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import SmartLegalSearch.service.ifs.CaseService;
import SmartLegalSearch.vo.SearchReq;
import SmartLegalSearch.vo.SearchRes;

@SpringBootTest
public class CaseServiceTest {

	@Autowired
	private CaseService caseService ;
	
	public void searchTest() {
		SearchReq req = new SearchReq();
		SearchRes res = caseService.searchCriminalCase(req);
		System.out.println(res.getCaseList().size());
	}
}
