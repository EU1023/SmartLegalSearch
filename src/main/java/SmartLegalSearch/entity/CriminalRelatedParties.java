package SmartLegalSearch.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "criminal_related_parties")
public class CriminalRelatedParties {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AI
    @Column(name = "parry_id")
    private int partyId;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    public CriminalRelatedParties() {
    }

    public CriminalRelatedParties(int partyId, String groupId, String id, String name, String role) {
        this.partyId = partyId;
        this.groupId = groupId;
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public int getPartyId() {
        return partyId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
