package SmartLegalSearch.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import SmartLegalSearch.constants.ResMessage;
import SmartLegalSearch.entity.LegalCase;
import SmartLegalSearch.repository.CaseDao;
import SmartLegalSearch.service.ifs.CaseService;
import SmartLegalSearch.vo.SearchReq;
import SmartLegalSearch.vo.SearchRes;
import jakarta.transaction.Transactional;

@Service
public class CaseImpl implements CaseService {

	@Autowired
	private CaseDao caseDao;

	@Override
	public SearchRes searchCriminalCase(SearchReq req) {
		// 參數驗證

		// 模糊搜尋
		String name = req.getSearchName();
		if (!StringUtils.hasText(name)) {
			name = "";
		}

		// 裁判字號
		String id = req.getVerdictId();
		if (!StringUtils.hasText(id)) {
			id = "%";
		}

		// 開始時間
		LocalDate startDate = req.getVerdictStartDate();
		if (startDate == null) {
			startDate = LocalDate.of(1950, 1, 1);
		}

		// 結束時間
		LocalDate nedDate = req.getVerdictEndDate();
		if (nedDate == null) {
			nedDate = LocalDate.of(9999, 12, 31);
		}

		// 確認開始時間不能比結束時間晚
		if (startDate.isAfter(nedDate)) {
			return new SearchRes(ResMessage.DATE_ERROR.getCode(), //
					ResMessage.DATE_ERROR.getMessage());
		}

		// 案由
		String charge = req.getCharge();
		if (!StringUtils.hasText(charge)) {
			charge = "%";
		}

		// 案件類型
		String caseType = req.getCaseType();
		if (!StringUtils.hasText(caseType)) {
			caseType = "%";
		}

		// 文件類型
		String docType = req.getDocType();
		if (!StringUtils.hasText(docType)) {
			docType = "%";
		}

		// 法條
		String law = req.getLaw();
		if (!StringUtils.hasText(law)) {
			law = "%";
		}

		// 法院
		List<String> courtList = req.getCourtList();
		if (CollectionUtils.isEmpty(courtList)) {
			courtList = new ArrayList<>();
		}

		return new SearchRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(), //
				caseDao.searchByConditions(name, startDate, nedDate, id, //
						charge, caseType, docType, law, courtList));
	}

	@Transactional
	@Override
	public LegalCase saveJudgment(LegalCase res) {
		caseDao.save(res);
		return null;
	}

}