package SmartLegalSearch.service.ifs;

import SmartLegalSearch.entity.Case;
import SmartLegalSearch.vo.SearchReq;
import SmartLegalSearch.vo.SearchRes;

public interface CaseService {

	// 搜尋功能
    public SearchRes searchCriminalCase(SearchReq req);

    // 儲存判決書內容
    Case saveJudgment(Case res);

}
