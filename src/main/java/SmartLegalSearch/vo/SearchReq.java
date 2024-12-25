package SmartLegalSearch.vo;

import java.time.LocalDate;
import java.util.List;

public class SearchReq {

	// 模糊搜尋
	private String searchName;

	// 裁判字號 id
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
	
	// 法條
	private String law;

	// 法院
	private List<String> courtList;

	public SearchReq() {
		super();
	}

	public SearchReq(String searchName, String verdictId, LocalDate verdictStartYear, LocalDate verdictEndYear,
			String charge, String caseType, String docType, String law, List<String> courtList) {
		super();
		this.searchName = searchName;
		this.verdictId = verdictId;
		this.verdictStartYear = verdictStartYear;
		this.verdictEndYear = verdictEndYear;
		this.charge = charge;
		this.caseType = caseType;
		this.docType = docType;
		this.law = law;
		this.courtList = courtList;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getVerdictId() {
		return verdictId;
	}

	public void setVerdictId(String verdictId) {
		this.verdictId = verdictId;
	}

	public LocalDate getVerdictStartYear() {
		return verdictStartYear;
	}

	public void setVerdictStartYear(LocalDate verdictStartYear) {
		this.verdictStartYear = verdictStartYear;
	}

	public LocalDate getVerdictEndYear() {
		return verdictEndYear;
	}

	public void setVerdictEndYear(LocalDate verdictEndYear) {
		this.verdictEndYear = verdictEndYear;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public List<String> getCourtList() {
		return courtList;
	}

	public void setCourtList(List<String> courtList) {
		this.courtList = courtList;
	}

	public String getLaw() {
		return law;
	}

	public void setLaw(String law) {
		this.law = law;
	}

}
