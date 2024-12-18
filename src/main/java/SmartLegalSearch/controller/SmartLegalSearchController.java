package SmartLegalSearch.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "")
@RequestMapping("/api/")
public class SmartLegalSearchController {

	@GetMapping("hello")
	public String getHello() {
		return "{\"message\": \"Hello from Java\"}";
	}

	@PostMapping("/upload")
    public ResponseEntity<String> uploadJson(@RequestBody Map<String, Object> jsonData) {
        // ��X�����쪺 JSON ���
        System.out.println("�����쪺 JSON ��ơG" + jsonData);

        // �d�ҡG�����Y�����i��B�z
        String jid = (String) jsonData.get("JID");
        String title = (String) jsonData.get("JTITLE");
        System.out.println("�ץ� ID: " + jid + ", ���D: " + title);

        // �^�����\�T��
        return ResponseEntity.ok("JSON ��Ʊ������\�I");
    }
}
