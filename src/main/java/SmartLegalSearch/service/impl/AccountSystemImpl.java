package SmartLegalSearch.service.impl;

import SmartLegalSearch.constants.ResMessage;
import SmartLegalSearch.entity.AccountSystem;
import SmartLegalSearch.repository.AccountSystemDao;
import SmartLegalSearch.service.ifs.AccountSystemService;
import SmartLegalSearch.vo.*;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    // 註冊
    @Transactional
    @Override
    public RegisterRes register(RegisterReq req) {
        // 檢查 email是否有存在
        if (accountSystemDao.existsByEmail(req.getEmail()) > 0) {
            return new RegisterRes(ResMessage.EMAIL_DUPLICATED.getCode(), ResMessage.EMAIL_DUPLICATED.getMessage());
        }

        // 創建新帳戶
        AccountSystem newUser = new AccountSystem();
        newUser.setEmail(req.getEmail());
        newUser.setPassword(passwordEncoder.encode(req.getPassword()));
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
                req.getEmail(), RegisterRes.RegisterStatus.EMAIL_VERIFICATION_PENDING);
    }

    // 寄送身分驗證 email
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

    // 根據 token 驗證 email
    @Transactional
    @Override
    public ResponseEntity<String> verifyEmail(String token, HttpSession session) {
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

        // 設置 session 狀態
        session.setAttribute("role", user.getRole());
        session.setMaxInactiveInterval(3600); // 60分鐘

        // 若驗證成功，重新導向到註冊帳號的網址
        String redirectUrl = "https://www.youtube.com/@HoshimachiSuisei";
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY) // HTTP 301永久導向
                .header(HttpHeaders.LOCATION, redirectUrl)
                .build();
    }

    // 登入
    @Override
    public BasicRes login(LoginReq req,  HttpSession session) {
        // 檢查是否已有登入
        String attr = (String) session.getAttribute("role");
        if (attr != null) {
            return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
        }

        // 取得使用者資料
        AccountSystem user = accountSystemDao.findByEmail(req.getEmail());

        // 檢查 email 是否存在及被驗證
        if (user == null || !user.isEmailVerified()) {
            return new BasicRes(ResMessage.NOT_FOUND.getCode(), ResMessage.NOT_FOUND.getMessage());
        }

        // 核對密碼
        if (!(passwordEncoder.matches(req.getPassword(), user.getPassword()))) {
            return new BasicRes(ResMessage.PASSWORD_ERROR.getCode(), ResMessage.PASSWORD_ERROR.getMessage());
        }

        // 若登入成功，設定 session 的 attribute 和有效時間
        session.setAttribute("role", user.getRole());
        session.setMaxInactiveInterval(3600); // 60分鐘

        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }

    // 登出
    @Override
    public BasicRes logout(HttpSession session) {
        // 檢查是否為登入狀態
        String attr = (String) session.getAttribute("role");
        if (attr == null) {
            return new BasicRes(ResMessage.NOT_FOUND.getCode(), ResMessage.NOT_FOUND.getMessage());
        }

        // 若為登入狀態，則使 session 失效
        session.invalidate();

        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }

    // 更新用戶資訊
    @Transactional
    @Override
    public BasicRes updateInfo(UpdateInfoReq req, HttpSession session) {
        // 檢查是否為登入狀態
        String attr = (String) session.getAttribute("role");
        if (attr == null) {
            return new BasicRes(ResMessage.NOT_FOUND.getCode(), ResMessage.NOT_FOUND.getMessage());
        }

        // 更新用戶身分、姓名、電話
        AccountSystem user = accountSystemDao.findByEmail(req.getEmail());
        user.setRole(req.getRole());
        user.setName(req.getName());
        user.setPhone(req.getPhone());

        // 若登入成功，設定 session 的 attribute 和有效時間
        session.setAttribute("role", user.getRole());
        session.setMaxInactiveInterval(3600); // 60分鐘

        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }


}
