package JudgmentService;

public class Judgment {
    private String JID;       // 判決書ID
    private String JYEAR;     // 年度
    private String JCASE;     // 案件類型
    private String JNO;       // 案件號碼
    private String JDATE;     // 判決日期
    private String JTITLE;    // 判決標題

    // Getters and Setters
    public String getJID() {
        return JID;
    }

    public void setJID(String JID) {
        this.JID = JID;
    }

    public String getJYEAR() {
        return JYEAR;
    }

    public void setJYEAR(String JYEAR) {
        this.JYEAR = JYEAR;
    }

    public String getJCASE() {
        return JCASE;
    }

    public void setJCASE(String JCASE) {
        this.JCASE = JCASE;
    }

    public String getJNO() {
        return JNO;
    }

    public void setJNO(String JNO) {
        this.JNO = JNO;
    }

    public String getJDATE() {
        return JDATE;
    }

    public void setJDATE(String JDATE) {
        this.JDATE = JDATE;
    }

    public String getJTITLE() {
        return JTITLE;
    }

    public void setJTITLE(String JTITLE) {
        this.JTITLE = JTITLE;
    }
}
