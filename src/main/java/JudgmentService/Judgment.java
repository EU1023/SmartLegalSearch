package JudgmentService;

public class Judgment {
    private String JID;       // �P�M��ID
    private String JYEAR;     // �~��
    private String JCASE;     // �ץ�����
    private String JNO;       // �ץ󸹽X
    private String JDATE;     // �P�M���
    private String JTITLE;    // �P�M���D   
	public Judgment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Judgment(String jID, String jYEAR, String jCASE, String jNO, String jDATE, String jTITLE) {
		super();
		JID = jID;
		JYEAR = jYEAR;
		JCASE = jCASE;
		JNO = jNO;
		JDATE = jDATE;
		JTITLE = jTITLE;
	}
	public String getJID() {
		return JID;
	}
	public void setJID(String jID) {
		JID = jID;
	}
	public String getJYEAR() {
		return JYEAR;
	}
	public void setJYEAR(String jYEAR) {
		JYEAR = jYEAR;
	}
	public String getJCASE() {
		return JCASE;
	}
	public void setJCASE(String jCASE) {
		JCASE = jCASE;
	}
	public String getJNO() {
		return JNO;
	}
	public void setJNO(String jNO) {
		JNO = jNO;
	}
	public String getJDATE() {
		return JDATE;
	}
	public void setJDATE(String jDATE) {
		JDATE = jDATE;
	}
	public String getJTITLE() {
		return JTITLE;
	}
	public void setJTITLE(String jTITLE) {
		JTITLE = jTITLE;
	}
	
}