package SmartLegalSearch;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import SmartLegalSearch.dto.CaseInfo;
import SmartLegalSearch.entity.LegalCase;
import SmartLegalSearch.readJson.ReadJson;
import SmartLegalSearch.service.impl.CaseImpl;
import SmartLegalSearch.vo.ReadJsonVo;

public class LegalCaseParser {
}
