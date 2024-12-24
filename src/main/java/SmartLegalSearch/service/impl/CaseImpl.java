package SmartLegalSearch.service.impl;

import SmartLegalSearch.entity.Case;
import SmartLegalSearch.repository.CaseDao;
import SmartLegalSearch.service.ifs.CaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CaseImpl implements CaseService {

	@Autowired
	private CaseDao caseDao;
	
	@Transactional
    @Override
    public void searchCriminalCase() {
    }
	
	@Transactional
	@Override
	public Case saveJudgment(Case res) {
		
		caseDao.save(res);
		return null;
	}
}
