package SmartLegalSearch.controller;

import SmartLegalSearch.repository.AccountSystemDao;
import SmartLegalSearch.service.ifs.AccountSystemService;
import SmartLegalSearch.vo.*;
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

    @Autowired
    private AccountSystemDao accountSystemDao;

    // 註冊的路徑為 /accountSystem/register
    @PostMapping("register")
    public RegisterRes register(@Valid @RequestBody RegisterReq req) {
        return accountSystemService.register(req);
    }

    // 驗證 email 的路徑為 /accountSystem/verify-email
    @GetMapping("verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token, HttpSession session) {
        return accountSystemService.verifyEmail(token, session);
    }

    // 登入的路徑為 /accountSystem/login
    @PostMapping("login")
    public BasicRes login(@Valid @RequestBody LoginReq req, HttpSession session) {
        return accountSystemService.login(req, session);
    }

    // 登出的路徑為 /accountSystem/logout
    @PostMapping("logout")
    public BasicRes logout(HttpSession session) {
        return accountSystemService.logout(session);
    }

    // 更新的路徑為 /accountSystem/updateInfo
    @PostMapping("updateInfo")
    public BasicRes updateInfo(@Valid @RequestBody UpdateInfoReq req, HttpSession session) {
        return accountSystemService.updateInfo(req, session);
    }
}
