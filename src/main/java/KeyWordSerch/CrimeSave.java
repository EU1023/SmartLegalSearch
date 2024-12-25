package KeyWordSerch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrimeSave {

    // 資料庫連線設定
    private static final String DB_URL = "jdbc:mysql://localhost:3306/judgments?useUnicode=true&characterEncoding=utf8";  // 修改為 utf8mb4
    private static final String DB_USER = "root"; // 替換為你的資料庫使用者名稱
    private static final String DB_PASSWORD = "root"; // 替換為你的資料庫密碼

    public static void main(String[] args) {
        String folderPath = "D:\\JavaProject\\out"; // JSON 檔案所在的目錄

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // 遍歷目錄中的所有 JSON 檔案
            Files.walk(new File(folderPath).toPath()).filter(Files::isRegularFile) // 只處理檔案
                    .filter(path -> path.toString().endsWith(".json")) // 篩選 JSON 檔案
                    .forEach(file -> saveToDatabase(file.toFile(), connection));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // 儲存 JSON 檔案內容到資料庫
    private static void saveToDatabase(File file, Connection connection) {
        String insertSQL = "INSERT INTO crime_records (file_name, content) VALUES (?, ?)";

        try {
            // 讀取檔案內容
            String content = new String(Files.readAllBytes(file.toPath())).trim();  // 確保使用 UTF-8 編碼

            // 插入資料到資料庫
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, file.getName()); // 僅儲存檔案名稱
                preparedStatement.setString(2, content); // 儲存檔案內容
                preparedStatement.executeUpdate(); // 執行插入操作
                System.out.println("已儲存檔案到資料庫: " + file.getName());
            }
        } catch (IOException | SQLException e) {
            System.err.println("儲存檔案失敗: " + file.getName());
            e.printStackTrace();
        }
    }
}
