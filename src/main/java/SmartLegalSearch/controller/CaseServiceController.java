package SmartLegalSearch.controller;

import SmartLegalSearch.service.ifs.CaseService;
import SmartLegalSearch.vo.SearchReq;
import SmartLegalSearch.vo.SearchRes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("case/")
@RestController
public class CaseServiceController {

	@Autowired
	private CaseService caseService;

	// 搜尋功能
	@PostMapping(value = "searchCriminalCase")
	public SearchRes searchCriminalCase(@RequestBody SearchReq req) {
		return caseService.searchCriminalCase(req);
	};
}
