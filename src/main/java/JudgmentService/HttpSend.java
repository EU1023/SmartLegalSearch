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
	
	private static final String DB_USER = "root"; // 資料庫使用者名稱
	private static final String DB_PASSWORD = "root"; // 資料庫密碼

	// 生成網址
	public static String generateUrl(String id) {
		String encodedId = id.replace(",", "%2c"); // 替換逗號
		return "https://judgment.judicial.gov.tw/FJUD/data.aspx?ty=JD&id=" + encodedId;
	}

	// 儲存生成的網址到資料庫
	public static void saveUrlToDatabase(Judgment judgment) throws SQLException {
		String updateSQL = "UPDATE judgments SET JHTTP = ? WHERE JID = ?";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

			String url = generateUrl(judgment.getJID()); // 使用 JID 生成網址
			pstmt.setString(1, url); // 設定網址
			pstmt.setString(2, judgment.getJID()); // 使用 JID 作為條件更新對應資料

			pstmt.executeUpdate(); // 執行更新操作
		}
	}

	// 主流程：從資料庫讀取數據，生成網址並儲存
	public static void processJudgments() {
		try {
			List<Judgment> judgments = getJudgmentsFromDatabase();

			for (Judgment judgment : judgments) {
				// 為每個 Judgment 生成網址並儲存回資料庫
				saveUrlToDatabase(judgment);
				System.out.println("生成的網址並儲存: " + judgment.getJID());
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
        

                // 建立 Judgment 物件並加入列表
                Judgment judgment = new Judgment(JID, JYEAR, JCASE, JNO, JDATE, JTITLE);
                judgments.add(judgment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return judgments;
    }

}
