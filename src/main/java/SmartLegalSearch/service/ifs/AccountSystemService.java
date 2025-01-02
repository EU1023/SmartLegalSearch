package SmartLegalSearch.service.ifs;

import SmartLegalSearch.vo.BasicRes;
import SmartLegalSearch.vo.LoginReq;
import SmartLegalSearch.vo.RegisterReq;
import SmartLegalSearch.vo.RegisterRes;
import org.springframework.http.ResponseEntity;

public interface AccountSystemService {

    // 註冊功能
    RegisterRes register(RegisterReq req);

    // 寄送身分驗證 email
    void sendVerificationEmail(String receiver, String token);

    // 根據 token 驗證 email
    ResponseEntity<String> verifyEmail(String token);

    // 註冊
    BasicRes login(LoginReq req);
}
