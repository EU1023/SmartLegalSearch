package SmartLegalSearch.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "legal_case")
@IdClass(CaseId.class)
public class LegalCase { // 刑事案件

    @Id
    @Column(name = "group_id")
    private String groupId; // 案件群組識別碼

    @Id
    @Column(name = "id")
    private String id; // 案件的唯一識別碼

    @Id
    @Column(name = "court")
    private String court; // 審理法院

    @Column(name = "date")
    private LocalDate date; // 判決日期

    @Column(name = "url")
    private String url; // 判決書連結

    @Column(name = "charge")
    private String charge; // 案由

    @Column(name = "text")
    private String text; // 判決內容

    @Column(name = "defendant_name")
    private String defendantName; // 被告姓名

    @Column(name = "judge_name")
    private String judgeName; // 法官姓名

    @Column(name = "law")
    private String law; // 相關法條

    @Column(name = "case_type")
    private String caseType; // 案件類型，如刑事、民事、行政

    @Column(name = "doc_type")
    private String docType; // 文件類型，裁定或判決或釋字等

    public LegalCase() {
    }

    public LegalCase(String groupId, String id, String court, LocalDate date, String url, String charge, String text, String defendantName, String judgeName, String law, String caseType, String docType) {
        this.groupId = groupId;
        this.id = id;
        this.court = court;
        this.date = date;
        this.url = url;
        this.charge = charge;
        this.text = text;
        this.defendantName = defendantName;
        this.judgeName = judgeName;
        this.law = law;
        this.caseType = caseType;
        this.docType = docType;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getId() {
        return id;
    }

    public String getCourt() {
        return court;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getCharge() {
        return charge;
    }

    public String getText() {
        return text;
    }

    public String getDefendantName() {
        return defendantName;
    }

    public String getJudgeName() {
        return judgeName;
    }

    public String getLaw() {
        return law;
    }

    public String getCaseType() {
        return caseType;
    }

    public String getDocType() {
        return docType;
    }

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCourt(String court) {
		this.court = court;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setDefendantName(String defendantName) {
		this.defendantName = defendantName;
	}

	public void setJudgeName(String judgeName) {
		this.judgeName = judgeName;
	}

	public void setLaw(String law) {
		this.law = law;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}
    
}