package SmartLegalSearch.vo;

import java.time.LocalDate;
import java.util.List;

public class SearchReq {

    public LocalDate verdictStartYear;

    public LocalDate verdictEndYear;

    public String charge;
    
    public List<String> courtList;

    public List<String> lawList;



}
