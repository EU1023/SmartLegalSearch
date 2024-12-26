package SmartLegalSearch.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

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

	// 動態資料查詢用
	@Autowired
	private DataSource dataSource;

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
		List<String> lawList = req.getLawList();
		if (CollectionUtils.isEmpty(lawList)) {
			lawList = new ArrayList<>();;
		}

		// 法院
		List<String> courtList = req.getCourtList();
		if (CollectionUtils.isEmpty(courtList)) {
			courtList = new ArrayList<>();
		}

		List<LegalCase> res = new ArrayList<>();
		try (Connection connection = dataSource.getConnection()) {
			res = caseDao.searchByConditions(connection, name, startDate, nedDate, //
					id, charge, caseType, docType, courtList, lawList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new SearchRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(), res) //
		;
	}

	@Transactional
	@Override
	public LegalCase saveJudgment(LegalCase res) {
		caseDao.save(res);
		return null;
	}

}
