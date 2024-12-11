package SmartLegalSearch.readJson;

import com.fasterxml.jackson.databind.ObjectMapper;

import SmartLegalSearch.vo.ReadJsonVo;

import java.io.File;
import java.io.IOException;

public class ReadJson {
	
	public ReadJsonVo readJson(String filePosition){
		ObjectMapper objectMapper = new ObjectMapper();
	     try {
	         // 讀取 JSON 檔案並轉換為 Map
	    	 File file = new File(filePosition);
	         ReadJsonVo data = objectMapper.readValue(file, ReadJsonVo.class);
	         return data;
	     } catch (IOException e) {
	         e.printStackTrace();
	     }	
	     return null;
	}

	
}
