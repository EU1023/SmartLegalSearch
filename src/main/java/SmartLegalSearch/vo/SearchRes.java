package SmartLegalSearch.vo;

import java.util.List;

import SmartLegalSearch.entity.Case;

public class SearchRes extends BasicRes {

	private List<Case> CaseList;

	public SearchRes() {
		super();
	}

	public SearchRes(int code, String message) {
		super(code, message);
	}

	public SearchRes(int code, String message, List<Case> caseList) {
		super(code, message);
		CaseList = caseList;
	}

	public List<Case> getCaseList() {
		return CaseList;
	}

}
