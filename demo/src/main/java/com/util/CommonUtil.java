package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.domain.Attachment;

@Component
public class CommonUtil {
    
	
	public JSONObject getDetailsForTable(List<Attachment> attachmentLst, JSONObject returnJson) throws JSONException, ParseException{
			
			List<String> lst = null;
			List<List<String>> finalLst = new ArrayList<>();
			
			for (Attachment attachment : attachmentLst) {
				
				lst = new ArrayList<String>();
				lst.add(""+attachment.getId());
				lst.add(attachment.getFileName());
				lst.add(attachment.getAuthor());
				lst.add(attachment.getTitle());
				lst.add(attachment.getDescription());
				lst.add(attachment.getCategory());
				lst.add(fileSizeToMb(attachment.getFileSize()));
				lst.add(dateFormatter(attachment.getUploadedDate()));
				//lst.add(""+attachment.getContentType());
				
				finalLst.add(lst);
			}	
				
			returnJson.put("data", finalLst);
			
			return returnJson;
		}
	
	
	public String fileSizeToMb(Long byteSize){
		
		long sizeInMB = byteSize / (1024 * 1024);
		if (sizeInMB == 0 || sizeInMB < 1) {
			return  ""+(byteSize / 1024)+" KB"  ; 
		}else{
			return ""+(byteSize / (1024 * 1024))+" MB";
		}
	}
	
	public String dateFormatter(Date dbDate) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date fechaNueva = format.parse(dbDate.toString());
		System.out.println(format.format(fechaNueva));
		return format.format(fechaNueva);
	}
}
