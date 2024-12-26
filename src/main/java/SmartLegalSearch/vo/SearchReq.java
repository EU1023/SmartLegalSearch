package SmartLegalSearch.vo;

import java.time.LocalDate;
import java.util.List;

public class SearchReq {

	// 模糊搜尋
	private String searchName;

	// 裁判字號 id
	private String verdictId;

	// 開始時間
	private LocalDate verdictStartDate;

	// 結束時間
	private LocalDate verdictEndDate;

	// 案由
	private String charge;

	// 案件類型:刑法、名法等等
	private String caseType;

	// 文件類型:裁定、判決
	private String docType;

	// 法條
	private String law;

	// 法院
	private List<String> courtList;

	public SearchReq() {
		super();
	}

	public SearchReq(String searchName, String verdictId, LocalDate verdictStartDate, LocalDate verdictEndDate,
			String charge, String caseType, String docType, String law, List<String> courtList) {
		super();
		this.searchName = searchName;
		this.verdictId = verdictId;
		this.verdictStartDate = verdictStartDate;
		this.verdictEndDate = verdictEndDate;
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

	public LocalDate getVerdictStartDate() {
		return verdictStartDate;
	}

	public void setVerdictStartDate(LocalDate verdictStartDate) {
		this.verdictStartDate = verdictStartDate;
	}

	public LocalDate getVerdictEndDate() {
		return verdictEndDate;
	}

	public void setVerdictEndDate(LocalDate verdictEndDate) {
		this.verdictEndDate = verdictEndDate;
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

	public String getLaw() {
		return law;
	}

	public void setLaw(String law) {
		this.law = law;
	}

	public List<String> getCourtList() {
		return courtList;
	}

	public void setCourtList(List<String> courtList) {
		this.courtList = courtList;
	}

}
