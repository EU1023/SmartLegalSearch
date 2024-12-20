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

    @Column(name = "verdict_date")
    private LocalDate verdictDate; // 判決日期

    @Column(name = "law")
    private String law; // 相關法條

    @Column(name = "law_detail")
    private String lawDetail; // 法條細節

    @Column(name = "url")
    private String url; // 判決書連結

    @Column(name = "charge")
    private String charge; // 案由

    @Column(name = "facts")
    private String facts; // 事實描述

    @Column(name = "motive")
    private String motive; // 動機/理由描述

    public CriminalCase() {
    }

    public CriminalCase(String groupId, String id, String court, LocalDate verdictDate, String law, String lawDetail, String url, String charge, String facts, String motive) {
        this.groupId = groupId;
        this.id = id;
        this.court = court;
        this.verdictDate = verdictDate;
        this.law = law;
        this.lawDetail = lawDetail;
        this.url = url;
        this.charge = charge;
        this.facts = facts;
        this.motive = motive;
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

    public LocalDate getVerdictDate() {
        return verdictDate;
    }

    public String getLaw() {
        return law;
    }

    public String getLawDetail() {
        return lawDetail;
    }

    public String getUrl() {
        return url;
    }

    public String getCharge() {
        return charge;
    }

    public String getFacts() {
        return facts;
    }

    public String getMotive() {
        return motive;
    }
}
