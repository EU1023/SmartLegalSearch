package SmartLegalSearch;

import SmartLegalSearch.readJson.ReadJson;
import SmartLegalSearch.vo.ReadJsonVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BulkInsertTest {

    private String folderPath = "C:\\Users\\user\\Desktop\\202405\\金城簡易庭刑事"; // 替換為實際目錄路徑

    @Test
    public void test() throws IOException {
        // 初始化 ReadJson 和保存結果的列表
        ReadJson readJson = new ReadJson();
        List<ReadJsonVo> dataList = new ArrayList<>();

        // 遍歷目錄中的所有 JSON 檔案
        Files.walk(new File(folderPath).toPath())
                .filter(Files::isRegularFile) // 只處理檔案
                .filter(path -> path.toString().endsWith(".json")) // 篩選 JSON 檔案
                .forEach(file -> {
                    // 讀取 JSON 文件，並添加到 dataList
                    ReadJsonVo data = readJson.readJsonByPath(file.toAbsolutePath().toString());
                    if (data != null) {
                        dataList.add(data);
                    }
                });
    }
}
