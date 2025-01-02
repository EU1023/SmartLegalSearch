package SmartLegalSearch.service.impl;

import SmartLegalSearch.repository.AccountSystemDao;
import SmartLegalSearch.service.ifs.AccountSystemService;
import SmartLegalSearch.vo.RegisterReq;
import SmartLegalSearch.vo.RegisterRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class AccountSystemImpl implements AccountSystemService {

    @Autowired
    private AccountSystemDao loginDao;


    @Override
    public RegisterRes register(RegisterReq req) {


        return null;
    }
}
