package SmartLegalSearch.service.ifs;

import SmartLegalSearch.vo.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

public interface AccountSystemService {

    // 註冊
    RegisterRes register(RegisterReq req);

    // 寄送身分驗證 email
    void sendVerificationEmail(String receiver, String token);

    // 根據 token 驗證 email
    ResponseEntity<String> verifyEmail(String token, HttpSession session);

    // 登入
    BasicRes login(LoginReq req, HttpSession session);

    // 登出
    BasicRes logout(HttpSession session);

    // 更新用戶資訊
    BasicRes updateInfo(UpdateInfoReq req, HttpSession session);
}
