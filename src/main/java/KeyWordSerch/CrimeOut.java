package KeyWordSerch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CrimeOut {

    public static void main(String[] args) {
        // 可以使用命令列引數來指定資料夾路徑
        String folderPath = args.length > 0 ? args[0] : "D:\\JavaProject\\臺灣基隆地方法院刑事";

        // 可配置的正則表達式，根據需求進行修改
        String regex1 = "((刑法|民法|洗錢防制法|刑事訴訟法)[\\s]*第([\\d一二三四五六七八九十百千]+)(條|條文)[\\s]*第([\\d一二三四五六七八九十百千]+)(項|項目)[\\s]*(.{0,30}?罪))";

        boolean outputToFile = true; // 是否需要輸出到文件

        // 遍歷資料夾中的所有 JSON 檔案
        try {
            Files.walk(Paths.get(folderPath))
                    .filter(Files::isRegularFile) // 只處理檔案
                    .filter(path -> path.toString().endsWith(".json")) // 篩選 JSON 檔案
                    .forEach(file -> processFile(file.toFile(), regex1, outputToFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 處理每個 JSON 檔案
    private static void processFile(File file, String regex1, boolean outputToFile) {
        try {
            // 讀取檔案內容
            String content = new String(Files.readAllBytes(file.toPath()), "UTF-8");

            // 檢查是否包含「裁定」字眼，若包含則跳過該檔案
            if (content.contains("裁定")) {
                System.out.println("檔案: " + file.getName() + " 包含 '裁定' 字眼，已跳過!");
                return; // 跳過此檔案
            }

            // 使用 Jackson 解析 JSON 資料
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(content);

            // 取得完整文本
            String fullText = jsonNode.get("JFULL").asText();

            // 使用正則表達式進行匹配
            Pattern pattern1 = Pattern.compile(regex1, Pattern.DOTALL);
            Matcher matcher1 = pattern1.matcher(fullText);

            // 檢查是否匹配成功
            if (matcher1.find()) {
                String matchedContent = matcher1.group(); // 僅取得匹配的內容

                // 如果需要輸出到文件
                if (outputToFile) {
                    writeToFile(file.getName(), matchedContent);
                }

                System.out.println("檔案: " + file.getName() + " 匹配成功!");
                System.out.println("匹配內容:\n" + matchedContent);
            } else {
                // 如果沒有匹配成功，輸出原文
                System.out.println("檔案: " + file.getName());
                System.out.println("--------------------未抓到任何正則表達式匹配的資料，原文如下：\n" + fullText);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 儲存匹配到的資料到檔案
    private static void writeToFile(String fileName, String content) {
        try {
            String outputDir = "D:\\JavaProject\\out";
            File dir = new File(outputDir);
            if (!dir.exists()) {
                dir.mkdirs(); // 如果資料夾不存在，則創建資料夾
            }

            // 使用檔案名稱來創建新的檔案
            File outputFile = new File(outputDir, fileName);
            FileWriter writer = new FileWriter(outputFile, true); // 使用 append 模式
            writer.write(content + "\n"); // 僅寫入匹配的內容
            writer.close();

            System.out.println("儲存匹配資料到檔案: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
