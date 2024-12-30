package SmartLegalSearch.controller;

import SmartLegalSearch.service.ifs.AccountSystemService;
import SmartLegalSearch.vo.RegisterReq;
import SmartLegalSearch.vo.RegisterRes;
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
}
