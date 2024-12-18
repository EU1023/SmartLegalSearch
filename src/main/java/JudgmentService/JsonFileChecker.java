package JudgmentService;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JsonFileChecker {
    public static void main(String[] args) {
        // 檔案資料夾路徑
        File folder = new File("D:\\JavaProject\\臺灣基隆地方法院刑事");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        
        if (files != null) {
            for (File file : files) {
                try (FileReader reader = new FileReader(file)) {
                    // 嘗試解析 JSON
                    JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                    System.out.println("檔案 " + file.getName() + " 格式正確");
                } catch (JsonSyntaxException e) {
                    System.out.println("檔案 " + file.getName() + " 格式錯誤: " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("檔案 " + file.getName() + " 讀取錯誤: " + e.getMessage());
                }
            }
        }
    }
}
