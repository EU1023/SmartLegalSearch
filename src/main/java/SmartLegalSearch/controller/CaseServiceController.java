package SmartLegalSearch.controller;

import SmartLegalSearch.service.ifs.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("case/")
@RestController
public class CaseServiceController {

    @Autowired
    private CaseService criminalCaseService;
}
