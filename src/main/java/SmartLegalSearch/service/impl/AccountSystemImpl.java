package SmartLegalSearch.service.impl;

import SmartLegalSearch.constants.ResMessage;
import SmartLegalSearch.entity.AccountSystem;
import SmartLegalSearch.repository.AccountSystemDao;
import SmartLegalSearch.service.ifs.AccountSystemService;
import SmartLegalSearch.vo.RegisterReq;
import SmartLegalSearch.vo.RegisterRes;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountSystemImpl implements AccountSystemService {

    @Autowired
    private AccountSystemDao accountSystemDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // 密碼加密

    @Autowired
    private JavaMailSender mailSender; // 注入的內容在 properties

    @Override
    public RegisterRes register(RegisterReq req) {
        // 檢查 email是否有存在
        if (accountSystemDao.existsByEmail(req.getEmail()) > 0) {
            return new RegisterRes(ResMessage.EMAIL_DUPLICATED.getCode(), ResMessage.EMAIL_DUPLICATED.getMessage());
        }

        // 創建新帳戶
        AccountSystem newUser = new AccountSystem();
        newUser.setEmail(req.getEmail());
        newUser.setName(req.getName());
        newUser.setPassword(passwordEncoder.encode(req.getPassword()));
        newUser.setPhone(req.getPhone());
        newUser.setRole(req.getRole());
        newUser.setIdentity(req.getIdentity());
        newUser.setEmailVerified(false);

        // 生成驗證 token 和驗證時間(15分鐘)
        String verificationToken = UUID.randomUUID().toString();
        LocalDateTime tokenExpiry = LocalDateTime.now().plusMinutes(15);
        newUser.setEmailVerificationToken(verificationToken);
        newUser.setTokenExpiry(tokenExpiry);

        // 保存到資料庫
        accountSystemDao.save(newUser);

        // 寄送驗證 email
        try {
            sendVerificationEmail(req.getEmail(), verificationToken);
        } catch (Exception e) {
            return new RegisterRes(ResMessage.EMAIL_SEND_FAILED.getCode(), ResMessage.EMAIL_SEND_FAILED.getMessage());
        }

        return new RegisterRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(),
                req.getEmail(), req.getName(), RegisterRes.RegisterStatus.EMAIL_VERIFICATION_PENDING, req.getRole());
    }

    @Override
    public void sendVerificationEmail(String receiver, String token) {
        try {
            String verificationLink = "http://localhost:8080/accountSystem/verify-email?token=" + token;

            // 創建 MimeMessage 來發送郵件
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // 設定郵件內容
            helper.setTo(receiver); // 收件人
            helper.setSubject("會員申請確認"); // 主旨
            helper.setText("請在15分鐘內點擊以下連結確認會員申請：\n" + verificationLink); // 內文

            mailSender.send(message); // 寄出郵件
        } catch (Exception e) {
            throw new IllegalArgumentException("Email error.");
        }
    }

    @Override
    public ResponseEntity<String> verifyEmail(String token) {
        // 根據 token 查找用戶
        AccountSystem user = accountSystemDao.findByEmailVerificationToken(token);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification link.");
        }

        // 檢查 token 是否過期
        if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The verification link has expired, " +
                    "please re-register or request a new verification email.");
        }

        // 更新用戶狀態
        user.setEmailVerified(true);
        user.setEmailVerificationToken(null);
        user.setTokenExpiry(null);
        accountSystemDao.save(user);

        // 若成功，返回200及 Email verified.
        return ResponseEntity.ok("Email verified.");
    }
}
