package SmartLegalSearch.entity;


import java.io.Serial;
import java.io.Serializable;

public class CriminalCaseId implements Serializable {

    @Serial
    private static final long serialVersionUID = -7790140973026611129L;

    private String groupId; // 案件群組識別碼

    private String id; // 案件的唯一識別碼

    private String court;

    public CriminalCaseId() {
    }

    public CriminalCaseId(String groupId, String id, String court) {
        this.groupId = groupId;
        this.id = id;
        this.court = court;
    }
}
