package SmartLegalSearch.service.ifs;

import SmartLegalSearch.vo.RegisterReq;
import SmartLegalSearch.vo.RegisterRes;

public interface AccountSystemService {

    // 註冊功能
    RegisterRes register(RegisterReq req);

}
