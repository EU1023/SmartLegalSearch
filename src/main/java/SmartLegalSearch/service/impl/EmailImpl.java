package SmartLegalSearch.service.impl;

import SmartLegalSearch.service.ifs.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender; // 注入的內容在 properties

    @Override
    public void sendVerificationEmail(String receiver, String token) {
        try {
            String verificationLink = "http://localhost:8080/verify-email?token=" + token;

            // 創建 MimeMessage 來發送郵件
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(receiver); // 收件人
            helper.setSubject("會員申請確認"); // 主旨
            helper.setText("請點選以下連結確認會員申請：\n" + verificationLink); // 內文

            mailSender.send(message); // 寄出郵件
        } catch (Exception e) {
            throw new IllegalArgumentException("Email 發信異常");
        }
    }
}
