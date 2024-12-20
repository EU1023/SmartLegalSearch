package SmartLegalSearch.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "criminal_penalties")
public class CriminalPenalties {

    @Column(name = "penalty_id")
    private int penaltyId;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "id")
    private String id;

    @Column(name = "type")
    private String type;

    @Column(name = "years")
    private int years;

    @Column(name = "months")
    private int months;

    @Column(name = "days")
    private int days;

    @Column(name = "probation")
    private boolean probation;

    @Column(name = "probation_years")
    private int probationYears;

    @Column(name = "fine_type")
    private int fineType;

    @Column(name = "fine")
    private int fine;

    public CriminalPenalties() {
    }

    public CriminalPenalties(int penaltyId, String groupId, String id, String type, int years, int months, int days, boolean probation, int probationYears, int fineType, int fine) {
        this.penaltyId = penaltyId;
        this.groupId = groupId;
        this.id = id;
        this.type = type;
        this.years = years;
        this.months = months;
        this.days = days;
        this.probation = probation;
        this.probationYears = probationYears;
        this.fineType = fineType;
        this.fine = fine;
    }

    public int getPenaltyId() {
        return penaltyId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getYears() {
        return years;
    }

    public int getMonths() {
        return months;
    }

    public int getDays() {
        return days;
    }

    public boolean isProbation() {
        return probation;
    }

    public int getProbationYears() {
        return probationYears;
    }

    public int getFineType() {
        return fineType;
    }

    public int getFine() {
        return fine;
    }
}
