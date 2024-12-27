package SmartLegalSearch.controller;

import SmartLegalSearch.service.ifs.AccountSystemService;
import SmartLegalSearch.service.ifs.EmailService;
import SmartLegalSearch.vo.RegisterReq;
import SmartLegalSearch.vo.RegisterRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/accountSystem")
@RestController
public class AccountSystemController {

    @Autowired
    private AccountSystemService accountSystemService;

    @Autowired
    private EmailService emailService;

    @PostMapping("register")
    public RegisterRes register(RegisterReq req) {
        return null;
    }
}
