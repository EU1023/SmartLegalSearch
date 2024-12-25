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
        // �i�H�ϥΩR�O�C�޼ƨӫ��w��Ƨ����|
        String folderPath = args.length > 0 ? args[0] : "D:\\JavaProject\\�O�W�򶩦a��k�|�D��";

        // �i�t�m�����h��F���A�ھڻݨD�i��ק�
        String regex1 = "((�D�k|���k|�~������k|�D�ƶD�^�k)[\\s]*��([\\d�@�G�T�|�����C�K�E�Q�ʤd]+)(��|����)[\\s]*��([\\d�@�G�T�|�����C�K�E�Q�ʤd]+)(��|����)[\\s]*(.{0,30}?�o))";

        boolean outputToFile = true; // �O�_�ݭn��X����

        // �M����Ƨ������Ҧ� JSON �ɮ�
        try {
            Files.walk(Paths.get(folderPath))
                    .filter(Files::isRegularFile) // �u�B�z�ɮ�
                    .filter(path -> path.toString().endsWith(".json")) // �z�� JSON �ɮ�
                    .forEach(file -> processFile(file.toFile(), regex1, outputToFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // �B�z�C�� JSON �ɮ�
    private static void processFile(File file, String regex1, boolean outputToFile) {
        try {
            // Ū���ɮפ��e
            String content = new String(Files.readAllBytes(file.toPath()), "UTF-8");

            // �ˬd�O�_�]�t�u���w�v�r���A�Y�]�t�h���L���ɮ�
            if (content.contains("���w")) {
                System.out.println("�ɮ�: " + file.getName() + " �]�t '���w' �r���A�w���L!");
                return; // ���L���ɮ�
            }

            // �ϥ� Jackson �ѪR JSON ���
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(content);

            // ���o����奻
            String fullText = jsonNode.get("JFULL").asText();

            // �ϥΥ��h��F���i��ǰt
            Pattern pattern1 = Pattern.compile(regex1, Pattern.DOTALL);
            Matcher matcher1 = pattern1.matcher(fullText);

            // �ˬd�O�_�ǰt���\
            if (matcher1.find()) {
                String matchedContent = matcher1.group(); // �Ȩ��o�ǰt�����e

                // �p�G�ݭn��X����
                if (outputToFile) {
                    writeToFile(file.getName(), matchedContent);
                }

                System.out.println("�ɮ�: " + file.getName() + " �ǰt���\!");
                System.out.println("�ǰt���e:\n" + matchedContent);
            } else {
                // �p�G�S���ǰt���\�A��X���
                System.out.println("�ɮ�: " + file.getName());
                System.out.println("--------------------�������󥿫h��F���ǰt����ơA���p�U�G\n" + fullText);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // �x�s�ǰt�쪺��ƨ��ɮ�
    private static void writeToFile(String fileName, String content) {
        try {
            String outputDir = "D:\\JavaProject\\out";
            File dir = new File(outputDir);
            if (!dir.exists()) {
                dir.mkdirs(); // �p�G��Ƨ����s�b�A�h�Ыظ�Ƨ�
            }

            // �ϥ��ɮצW�٨ӳЫطs���ɮ�
            File outputFile = new File(outputDir, fileName);
            FileWriter writer = new FileWriter(outputFile, true); // �ϥ� append �Ҧ�
            writer.write(content + "\n"); // �ȼg�J�ǰt�����e
            writer.close();

            System.out.println("�x�s�ǰt��ƨ��ɮ�: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
