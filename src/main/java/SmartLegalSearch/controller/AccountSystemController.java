package SmartLegalSearch.controller;

import SmartLegalSearch.constants.ResMessage;
import SmartLegalSearch.service.ifs.AccountSystemService;
import SmartLegalSearch.vo.BasicRes;
import SmartLegalSearch.vo.LoginReq;
import SmartLegalSearch.vo.RegisterReq;
import SmartLegalSearch.vo.RegisterRes;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/accountSystem")
@RestController
public class AccountSystemController {

    @Autowired
    private AccountSystemService accountSystemService;

    // 註冊的路徑為 /accountSystem/register
    @PostMapping("register")
    public RegisterRes register(@Valid @RequestBody RegisterReq req) {
        return accountSystemService.register(req);
    }

    // 驗證 email 的路徑為 /accountSystem/verify-email
    @GetMapping("verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        return accountSystemService.verifyEmail(token);
    }

    // 登入的路徑為 /accountSystem/login
    @PostMapping("login")
    public BasicRes login(@Valid @RequestBody LoginReq req, HttpSession session) {
        // 檢查是否已有登入
        String attr = (String) session.getAttribute(req.getEmail());
        if (attr != null) {
            return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
        }

        // 若無登入，檢查是否登入成功，及設定 session 的 attribute 和 有效時間
        BasicRes res = accountSystemService.login(req);
        if (res.getCode() == 200) {
            session.setAttribute(req.getEmail(), req.getEmail());
            session.setMaxInactiveInterval(3600); // 60分鐘
        }

        return res;
    }

    @PostMapping("logout")
    public BasicRes logout(HttpSession session) {
        session.invalidate(); // 使 session 失效
        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }

}
