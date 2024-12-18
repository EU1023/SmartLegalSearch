package JudgmentService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;  // 引入 StandardCharsets
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class SendDataService {

    // 資料庫連線資訊（請根據您的環境配置）
    private static final String DB_URL = "jdbc:mysql://localhost:3306/judgments?useUnicode=true&characterEncoding=utf8";
    private static final String DB_USER = "root"; // 請替換為您的資料庫用戶名
    private static final String DB_PASSWORD = "root"; // 請替換為您的資料庫密碼

    // 讀取資料夾中的 JSON 檔案並解析成 Judgment 物件
    public static List<Judgment> loadJudgmentsFromFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        List<Judgment> judgments = new ArrayList<>();
        Gson gson = new Gson();

        if (files != null) {
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                    // 使用 JsonReader 設置為寬鬆模式
                    JsonReader jsonReader = new JsonReader(reader);
                    jsonReader.setLenient(true);  // 啟用寬鬆模式

                    // 讀取 JSON 物件
                    JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);

                    // 如果 JFULL 欄位存在，清理換行符
                    if (jsonObject.has("JFULL")) {
                        String jFull = jsonObject.get("JFULL").getAsString();
                        jFull = jFull.replace("\n", " ").replace("\r", " ");
                        jsonObject.addProperty("JFULL", jFull);
                    }

                    // 轉換為 Judgment 物件
                    Judgment judgment = gson.fromJson(jsonObject, Judgment.class);
                    judgments.add(judgment);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("讀取檔案錯誤：" + file.getName());
                }
            }
        }
        return judgments;
    }

    // 批次插入判決資料到資料庫
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // 可以加上自定義的異常處理邏輯
        }
    }
    
}

  
