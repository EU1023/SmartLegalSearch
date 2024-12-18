package JudgmentService;

import java.sql.*;
import java.util.*;

public class DataGetService {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/judgments";

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    // 從資料庫讀取數據
    public static List<Judgment> getJudgmentsFromDatabase() throws SQLException {
        List<Judgment> judgments = new ArrayList<>();
        String query = "SELECT JID, JYEAR, JCASE, JNO, JDATE, JTITLE FROM judgments "; // 只選擇尚未生成網址的記錄

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String JID = rs.getString("JID");
                String JYEAR = rs.getString("JYEAR");
                String JCASE = rs.getString("JCASE");
                String JNO = rs.getString("JNO");
                String JDATE = rs.getString("JDATE");
                String JTITLE = rs.getString("JTITLE");
 

                // 創建 Judgment 物件並將其加入 list
                judgments.add(new Judgment(JID, JYEAR, JCASE, JNO, JDATE, JTITLE));
            }
        }

        return judgments;
    }
}
