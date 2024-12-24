package SmartLegalSearch.service.ifs;

import SmartLegalSearch.entity.Case;

public interface CaseService {
    void searchCriminalCase();
    
    public Case saveJudgment(Case res);

}
