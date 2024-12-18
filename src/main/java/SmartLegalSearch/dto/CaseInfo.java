package SmartLegalSearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CaseInfo {
	
	@JsonProperty("JID")
    private String caseId;

    @JsonProperty("JYEAR")
    private String caseYear;

    @JsonProperty("JCASE")
    private String caseType;

    @JsonProperty("JNO")
    private String caseNo;

    @JsonProperty("JDATE")
    private String caseDate;

    @JsonProperty("JTITLE")
    private String caseTitle;

    @JsonProperty("JFULL")
    private String fullText;

    @JsonProperty("JPDF")  
    private String pdfFilePath;

	public CaseInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CaseInfo(String caseId, String caseYear, String caseType, String caseNo, String caseDate, String caseTitle,
			String fullText, String pdfFilePath) {
		super();
		this.caseId = caseId;
		this.caseYear = caseYear;
		this.caseType = caseType;
		this.caseNo = caseNo;
		this.caseDate = caseDate;
		this.caseTitle = caseTitle;
		this.fullText = fullText;
		this.pdfFilePath = pdfFilePath;
	}

	public String getCaseId() {
		return caseId;
	}

	public String getCaseYear() {
		return caseYear;
	}

	public String getCaseType() {
		return caseType;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public String getCaseDate() {
		return caseDate;
	}

	public String getCaseTitle() {
		return caseTitle;
	}

	public String getFullText() {
		return fullText;
	}

	public String getPdfFilePath() {
		return pdfFilePath;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public void setCaseYear(String caseYear) {
		this.caseYear = caseYear;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public void setCaseDate(String caseDate) {
		this.caseDate = caseDate;
	}

	public void setCaseTitle(String caseTitle) {
		this.caseTitle = caseTitle;
	}

	public void setFullText(String fullText) {
		this.fullText = fullText;
	}

	public void setPdfFilePath(String pdfFilePath) {
		this.pdfFilePath = pdfFilePath;
	}
    
    

}
