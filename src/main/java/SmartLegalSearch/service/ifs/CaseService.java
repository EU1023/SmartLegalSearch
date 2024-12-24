package SmartLegalSearch.service.ifs;

import SmartLegalSearch.vo.SearchReq;
import SmartLegalSearch.vo.SearchRes;

public interface CaseService {

	// 搜尋功能
    public SearchRes searchCriminalCase(SearchReq req);

}
