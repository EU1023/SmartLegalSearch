package JudgmentService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HttpSend {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/judgments?useUnicode=true&characterEncoding=utf8";
	
	private static final String DB_USER = "root"; // ��Ʈw�ϥΪ̦W��
	private static final String DB_PASSWORD = "root"; // ��Ʈw�K�X

	// �ͦ����}
	public static String generateUrl(String id) {
		String encodedId = id.replace(",", "%2c"); // �����r��
		return "https://judgment.judicial.gov.tw/FJUD/data.aspx?ty=JD&id=" + encodedId;
	}

	// �x�s�ͦ������}���Ʈw
	public static void saveUrlToDatabase(Judgment judgment) throws SQLException {
		String updateSQL = "UPDATE judgments SET JHTTP = ? WHERE JID = ?";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

			String url = generateUrl(judgment.getJID()); // �ϥ� JID �ͦ����}
			pstmt.setString(1, url); // �]�w���}
			pstmt.setString(2, judgment.getJID()); // �ϥ� JID �@�������s�������

			pstmt.executeUpdate(); // �����s�ާ@
		}
	}

	// �D�y�{�G�q��ƮwŪ���ƾڡA�ͦ����}���x�s
	public static void processJudgments() {
		try {
			List<Judgment> judgments = getJudgmentsFromDatabase();

			for (Judgment judgment : judgments) {
				// ���C�� Judgment �ͦ����}���x�s�^��Ʈw
				saveUrlToDatabase(judgment);
				System.out.println("�ͦ������}���x�s: " + judgment.getJID());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static List<Judgment> getJudgmentsFromDatabase() {
        List<Judgment> judgments = new ArrayList<>();
        String selectSQL = "SELECT JID, JYEAR, JCASE, JNO, JDATE, JTITLE FROM judgments";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(selectSQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String JID = rs.getString("JID");
                String JYEAR = rs.getString("JYEAR");
                String JCASE = rs.getString("JCASE");
                String JNO = rs.getString("JNO");
                String JDATE = rs.getString("JDATE");
                String JTITLE = rs.getString("JTITLE");
        

                // �إ� Judgment ����å[�J�C��
                Judgment judgment = new Judgment(JID, JYEAR, JCASE, JNO, JDATE, JTITLE);
                judgments.add(judgment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return judgments;
    }

}
