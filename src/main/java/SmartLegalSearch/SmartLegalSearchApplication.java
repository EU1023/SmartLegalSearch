package SmartLegalSearch;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import JudgmentService.Judgment;
import JudgmentService.JudgmentService;

public class SmartLegalSearchApplication {

    public static void main(String[] args) throws SQLException, IOException {
        // 設定資料夾路徑
        File folder = new File("D:\\JavaProject\\臺灣基隆地方法院刑事");

        // 讀取該資料夾中的所有檔案
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json")); // 只選取 JSON 檔案

        // 檢查是否有 JSON 檔案
        if (files != null && files.length > 0) {
            Gson gson = new Gson();
            JudgmentService judgmentService = new JudgmentService(); // 假設你的插入邏輯在這裡

            // 逐個讀取每個 JSON 檔案
            for (File file : files) {
                try (FileReader reader = new FileReader(file)) {
                    // 假設 JSON 檔案是單一物件格式
                    Judgment judgment = gson.fromJson(reader, Judgment.class);

                    // 將單一的 Judgment 物件包裝成 List
                    judgmentService.insertJudgments(List.of(judgment));
                    System.out.println("成功插入檔案：" + file.getName());
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                    System.out.println("檔案處理失敗：" + file.getName());
                }
            }
        } else {
            System.out.println("指定的資料夾內沒有 JSON 檔案！");
        }
    }
}