package SmartLegalSearch.entiy;

public class CriminalLawArticles {

	// �o�W�s��
	private String id;
	// �o�W�W��
	private String crimeName;

	public CriminalLawArticles() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CriminalLawArticles(String id, String crimeName) {
		super();
		this.id = id;
		this.crimeName = crimeName;
	}

	public String getId() {
		return id;
	}

	public String getCrimeName() {
		return crimeName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCrimeName(String crimeName) {
		this.crimeName = crimeName;
	}
	
	
}
