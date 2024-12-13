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
        // �]�w��Ƨ����|
        File folder = new File("D:\\JavaProject\\�O�W�򶩦a��k�|�D��");

        // Ū���Ӹ�Ƨ������Ҧ��ɮ�
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json")); // �u��� JSON �ɮ�

        // �ˬd�O�_�� JSON �ɮ�
        if (files != null && files.length > 0) {
            Gson gson = new Gson();
            JudgmentService judgmentService = new JudgmentService(); // ���]�A�����J�޿�b�o��

            // �v��Ū���C�� JSON �ɮ�
            for (File file : files) {
                try (FileReader reader = new FileReader(file)) {
                    // ���] JSON �ɮ׬O��@����榡
                    Judgment judgment = gson.fromJson(reader, Judgment.class);

                    // �N��@�� Judgment ����]�˦� List
                    judgmentService.insertJudgments(List.of(judgment));
                    System.out.println("���\���J�ɮסG" + file.getName());
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                    System.out.println("�ɮ׳B�z���ѡG" + file.getName());
                }
            }
        } else {
            System.out.println("���w����Ƨ����S�� JSON �ɮסI");
        }
    }
}