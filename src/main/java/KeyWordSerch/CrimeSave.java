package KeyWordSerch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrimeSave {

    // ��Ʈw�s�u�]�w
    private static final String DB_URL = "jdbc:mysql://localhost:3306/judgments?useUnicode=true&characterEncoding=utf8";  // �קאּ utf8mb4
    private static final String DB_USER = "root"; // �������A����Ʈw�ϥΪ̦W��
    private static final String DB_PASSWORD = "root"; // �������A����Ʈw�K�X

    public static void main(String[] args) {
        String folderPath = "D:\\JavaProject\\out"; // JSON �ɮשҦb���ؿ�

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // �M���ؿ������Ҧ� JSON �ɮ�
            Files.walk(new File(folderPath).toPath()).filter(Files::isRegularFile) // �u�B�z�ɮ�
                    .filter(path -> path.toString().endsWith(".json")) // �z�� JSON �ɮ�
                    .forEach(file -> saveToDatabase(file.toFile(), connection));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // �x�s JSON �ɮפ��e���Ʈw
    private static void saveToDatabase(File file, Connection connection) {
        String insertSQL = "INSERT INTO crime_records (file_name, content) VALUES (?, ?)";

        try {
            // Ū���ɮפ��e
            String content = new String(Files.readAllBytes(file.toPath())).trim();  // �T�O�ϥ� UTF-8 �s�X

            // ���J��ƨ��Ʈw
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, file.getName()); // ���x�s�ɮצW��
                preparedStatement.setString(2, content); // �x�s�ɮפ��e
                preparedStatement.executeUpdate(); // ���洡�J�ާ@
                System.out.println("�w�x�s�ɮר��Ʈw: " + file.getName());
            }
        } catch (IOException | SQLException e) {
            System.err.println("�x�s�ɮץ���: " + file.getName());
            e.printStackTrace();
        }
    }
}
