package JudgmentService;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JsonFileChecker {
    public static void main(String[] args) {
        // �ɮ׸�Ƨ����|
        File folder = new File("D:\\JavaProject\\�O�W�򶩦a��k�|�D��");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        
        if (files != null) {
            for (File file : files) {
                try (FileReader reader = new FileReader(file)) {
                    // ���ոѪR JSON
                    JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                    System.out.println("�ɮ� " + file.getName() + " �榡���T");
                } catch (JsonSyntaxException e) {
                    System.out.println("�ɮ� " + file.getName() + " �榡���~: " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("�ɮ� " + file.getName() + " Ū�����~: " + e.getMessage());
                }
            }
        }
    }
}
