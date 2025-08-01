package seleniumFrameDesign.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {
	
	public List<HashMap<String, String>> getJsonDataToMap() throws IOException
	{
		//read the json file into a string
	    String Jsoncontent = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//Purchase.json"), StandardCharsets.UTF_8);
		
		//convert the above string to HashMap - for this we shud get new dependency Jackson databind
	    
	   ObjectMapper mapper = new ObjectMapper();
	   List<HashMap<String,String>> data = mapper.readValue(Jsoncontent, new TypeReference<List<HashMap<String,String>>>(){
	   });
	   return data;
	   
	   //data will have the hashmap in format {map,map}
		
	}

}
