package SmartLegalSearch.controller;

import SmartLegalSearch.service.ifs.CaseService;
import SmartLegalSearch.vo.SearchReq;
import SmartLegalSearch.vo.SearchRes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("case/")
@RestController
@Controller
public class CaseServiceController {

	@Autowired
	private CaseService caseService;

	// 搜尋功能
	@PostMapping(value = "search")
	public SearchRes searchCriminalCase(@RequestBody SearchReq req) {
		return caseService.searchCriminalCase(req);
	};
	
	// 聊天室功能
	
}
