package SmartLegalSearch.readJson;

import com.fasterxml.jackson.databind.ObjectMapper;

import SmartLegalSearch.vo.ReadJsonVo;

import java.io.File;
import java.io.IOException;

public class ReadJson {
	
	public ReadJsonVo readJson(String filePosition){
		ObjectMapper objectMapper = new ObjectMapper();
	     try {
	         // Ū�� JSON �ɮר��ഫ�� Map
	    	 File file = new File(filePosition);
	         ReadJsonVo data = objectMapper.readValue(file, ReadJsonVo.class);
	         return data;
	     } catch (IOException e) {
	         e.printStackTrace();
	     }	
	     return null;
	}

	
}
