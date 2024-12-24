package SmartLegalSearch.vo;

import java.time.LocalDate;
import java.util.List;

public class SearchReq {

	// 模糊搜尋
	private String searchName;

	// 裁判字號
	private String verdictId;

	// 開始時間
	private LocalDate verdictStartYear;

	// 結束時間
	private LocalDate verdictEndYear;

	// 案由
	private String charge;

	// 案件類型
	private String caseType;

	// 文件類型
	private String docType;

	// 法院
	private List<String> courtList;

	// 法條
	private List<String> lawList;

	public SearchReq() {
		super();
	}

	public SearchReq(String searchName, String verdictId, LocalDate verdictStartYear, LocalDate verdictEndYear,
			String charge, List<String> courtList, List<String> lawList, String caseType, String docType) {
		super();
		this.searchName = searchName;
		this.verdictId = verdictId;
		this.verdictStartYear = verdictStartYear;
		this.verdictEndYear = verdictEndYear;
		this.charge = charge;
		this.courtList = courtList;
		this.lawList = lawList;
		this.caseType = caseType;
		this.docType = docType;
	}

	public String getSearchName() {
		return searchName;
	}

	public String getVerdictId() {
		return verdictId;
	}

	public LocalDate getVerdictStartYear() {
		return verdictStartYear;
	}

	public LocalDate getVerdictEndYear() {
		return verdictEndYear;
	}

	public String getCharge() {
		return charge;
	}

	public List<String> getCourtList() {
		return courtList;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public void setVerdictId(String verdictId) {
		this.verdictId = verdictId;
	}

	public void setVerdictStartYear(LocalDate verdictStartYear) {
		this.verdictStartYear = verdictStartYear;
	}

	public void setVerdictEndYear(LocalDate verdictEndYear) {
		this.verdictEndYear = verdictEndYear;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public void setCourtList(List<String> courtList) {
		this.courtList = courtList;
	}

	public void setLawList(List<String> lawList) {
		this.lawList = lawList;
	}

	public List<String> getLawList() {
		return lawList;
	}

	public String getCaseType() {
		return caseType;
	}

	public String getDocType() {
		return docType;
	}

}
