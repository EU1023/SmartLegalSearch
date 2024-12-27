package SmartLegalSearch.service.impl;

import SmartLegalSearch.constants.ResMessage;
import SmartLegalSearch.entity.AccountSystem;
import SmartLegalSearch.repository.AccountSystemDao;
import SmartLegalSearch.service.ifs.AccountSystemService;
import SmartLegalSearch.service.ifs.EmailService;
import SmartLegalSearch.vo.RegisterReq;
import SmartLegalSearch.vo.RegisterRes;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountSystemImpl implements AccountSystemService {

    @Autowired
    private AccountSystemDao accountSystemDao;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public RegisterRes register(RegisterReq req) {
        // 瑼Ｘ鞈�澈�� email ��撌脣�
        if (accountSystemDao.existsByEmail(req.getEmail())) {
            return new RegisterRes(ResMessage.EMAIL_DUPLICATED.getCode(), ResMessage.EMAIL_DUPLICATED.getMessage());
        }

        // �撱箸撣單
        AccountSystem newUser = new AccountSystem();
        newUser.setEmail(req.getEmail());
        newUser.setName(req.getName());
//        newUser.setPassword(passwordEncoder.encode(req.getPassword()));
        newUser.setPhone(req.getPhone());
        newUser.setRole(req.getRole());
        newUser.setIdentity(req.getIdentity());
        newUser.setEmailVerified(false);

        String verificationToken = UUID.randomUUID().toString();
        LocalDateTime tokenExpiry = LocalDateTime.now().plusMinutes(15);
        newUser.setEmailVerificationToken(verificationToken);
//        newUser.setEmailVerificationToken();

        AccountSystem savedUser = accountSystemDao.save(newUser);

        return new RegisterRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(),
                req.getEmail(), req.getName(), RegisterRes.RegisterStatus. EMAIL_VERIFICATION_PENDING, req.getRole());
    }
}
