package SmartLegalSearch;

import SmartLegalSearch.constants.ResMessage;
import SmartLegalSearch.service.ifs.AccountSystemService;
import SmartLegalSearch.vo.RegisterReq;
import SmartLegalSearch.vo.RegisterRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class AccountSystemTest {

    @Autowired
    private AccountSystemService accountSystemService;

    @Test
    public void testRegister() {
        RegisterReq req = new RegisterReq("abc@gmail.com", "abc", "abc123",
                "user", "", "");

        RegisterRes res = accountSystemService.register(req);

        // 測試 res 是否為 null，以及 code、email、status 是否正確
        Assert.notNull(res, "res is null");
        Assert.isTrue(res.getCode() == ResMessage.SUCCESS.getCode(), "code error");
        Assert.isTrue(res.getEmail().equals("abc@gmail.com"), "email error");
        Assert.isTrue(res.getStatus().equals(RegisterRes.RegisterStatus.EMAIL_VERIFICATION_PENDING), "status error");
    }
}
