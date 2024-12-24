package SmartLegalSearch.service.impl;

import SmartLegalSearch.entity.Case;
import SmartLegalSearch.repository.CriminalCaseDao;
import SmartLegalSearch.service.ifs.CriminalCaseService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CriminalCaseImpl implements CriminalCaseService {
	
	@Autowired
	private CriminalCaseDao criminalCaseDao;
	
	// 儲存實體至資料庫
//	@Transactional
//	@Override
//    public Case saveLegalCase(Case legalCase) {
//		
//    	criminalCaseDao.saveAll();
//        return null;
//    }
}
