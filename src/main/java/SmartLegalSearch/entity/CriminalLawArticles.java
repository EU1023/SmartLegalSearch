package SmartLegalSearch.entity;

public class CriminalLawArticles {

	// 罪名編號
	private String id;
	// 罪名名稱
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
