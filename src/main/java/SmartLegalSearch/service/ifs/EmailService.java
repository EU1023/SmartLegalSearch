package SmartLegalSearch.service.ifs;

public interface EmailService {

    // 寄送身分驗證 email
    void sendVerificationEmail(String receiver, String token);
}
