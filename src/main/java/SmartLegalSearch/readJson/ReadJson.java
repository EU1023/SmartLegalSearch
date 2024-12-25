package SmartLegalSearch.readJson;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import SmartLegalSearch.vo.ReadJsonVo;

public class ReadJson {

	/**
	 *
	 * @param filePosition 檔案的絕對路徑
	 * @return  路徑正確的話會回傳 ReadJsonVo ，不正確的話會回傳 null
	 */
	public ReadJsonVo readJson(String filePosition){
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// 讀取 JSON 檔案並轉換為 ReadJsonVo 類別
			File file = new File(filePosition);
			ReadJsonVo data = objectMapper.readValue(file, ReadJsonVo.class);
			return data;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
