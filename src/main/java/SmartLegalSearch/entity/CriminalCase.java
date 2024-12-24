package SmartLegalSearch.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "criminal_case")
@IdClass(CriminalCaseId.class)
public class CriminalCase { // 刑事案件

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
    private String law;


    public CriminalCase() {
    }

    public CriminalCase(String groupId, String id, String court, LocalDate date, String url, String charge, String text, String defendantName, String judgeName, String law) {
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
}
