package JudgmentService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JudgmentService {

    // 資料庫連線資訊
    private static final String DB_URL = "jdbc:mysql://localhost:3306/judgments"; // 替換為您的資料庫URL
    private static final String DB_USER = "root"; // 替換為您的資料庫用戶名
    private static final String DB_PASSWORD = "root"; // 替換為您的資料庫密碼

    public static void insertJudgments(List<Judgment> judgments) throws SQLException {
        String insertSQL = "INSERT INTO judgments (JID, JYEAR, JCASE, JNO, JDATE, JTITLE) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            for (Judgment judgment : judgments) {
                pstmt.setString(1, judgment.getJID());
                pstmt.setString(2, judgment.getJYEAR());
                pstmt.setString(3, judgment.getJCASE());
                pstmt.setString(4, judgment.getJNO());
                pstmt.setString(5, judgment.getJDATE());
                pstmt.setString(6, judgment.getJTITLE());
                pstmt.addBatch(); // 加入批次
            }

            pstmt.executeBatch(); // 執行批次插入
        }
    }
}
