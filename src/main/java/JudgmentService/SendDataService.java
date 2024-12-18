package JudgmentService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;  // �ޤJ StandardCharsets
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

    // ��Ʈw�s�u��T�]�Юھڱz�����Ұt�m�^
    private static final String DB_URL = "jdbc:mysql://localhost:3306/judgments?useUnicode=true&characterEncoding=utf8";
    private static final String DB_USER = "root"; // �д������z����Ʈw�Τ�W
    private static final String DB_PASSWORD = "root"; // �д������z����Ʈw�K�X

    // Ū����Ƨ����� JSON �ɮרøѪR�� Judgment ����
    public static List<Judgment> loadJudgmentsFromFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        List<Judgment> judgments = new ArrayList<>();
        Gson gson = new Gson();

        if (files != null) {
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                    // �ϥ� JsonReader �]�m���e�P�Ҧ�
                    JsonReader jsonReader = new JsonReader(reader);
                    jsonReader.setLenient(true);  // �ҥμe�P�Ҧ�

                    // Ū�� JSON ����
                    JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);

                    // �p�G JFULL ���s�b�A�M�z�����
                    if (jsonObject.has("JFULL")) {
                        String jFull = jsonObject.get("JFULL").getAsString();
                        jFull = jFull.replace("\n", " ").replace("\r", " ");
                        jsonObject.addProperty("JFULL", jFull);
                    }

                    // �ഫ�� Judgment ����
                    Judgment judgment = gson.fromJson(jsonObject, Judgment.class);
                    judgments.add(judgment);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Ū���ɮ׿��~�G" + file.getName());
                }
            }
        }
        return judgments;
    }

    // �妸���J�P�M��ƨ��Ʈw
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
                pstmt.addBatch(); // �[�J�妸  
            }
            pstmt.executeBatch(); // ����妸���J
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // �i�H�[�W�۩w�q�����`�B�z�޿�
        }
    }
    
}

  
