package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.domain.Attachment;
import com.domain.Rating;
import com.repository.RatingRepository;

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
	
	
	public JSONObject getDetailsForPanel(List<Attachment> attachmentLst, JSONObject returnJson, RatingRepository ratingRepository) throws JSONException, ParseException{
		
		List<JSONObject> lst = new ArrayList<JSONObject>();
		List<JSONObject> ratinglst = new ArrayList<JSONObject>();
		
		for (Attachment attachment : attachmentLst) {
			
			JSONObject json = new JSONObject();
			json.put("id",attachment.getId());
			json.put("fileName",attachment.getFileName());
			json.put("author",attachment.getAuthor());
			json.put("title",attachment.getTitle());
			json.put("description",attachment.getDescription());
			json.put("category",attachment.getCategory());
			json.put("fileSize",fileSizeToMb(attachment.getFileSize()));
			json.put("uploadedDate",(attachment.getUploadedDate()));
			List<Rating> ratingLst = ratingRepository.findRatingByAttachmentId(attachment.getId());
			if(null != ratingLst && !ratingLst.isEmpty()){
				json.put("ratingExists",true);
				for (Rating rating : ratingLst) {
					JSONObject ratingJson = new JSONObject();
					ratingJson.put("authorName",rating.getAuthor());
					ratingJson.put("commentTime",rating.getCommentTime());
					ratingJson.put("comment",rating.getComment());
					ratingJson.put("rating",rating.getRating());
					ratinglst.add(ratingJson);
				}
				json.put("ratinglst",ratinglst);
			}else{
				json.put("ratingExists",false);
			}
			lst.add(json);
		}	
			
		returnJson.put("attachmentLst", lst);
		
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
	
	public String encoder(String value) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        // This is a required password for Jasypt. You will have to use the same password to
        // retrieve decrypted password later. T
        // This password is not the password we are trying to encrypt taken from properties file.
        encryptor.setPassword("This password is not the password we are trying to encrypt taken from properties file.");
        return encryptor.encrypt(value);
	}
	
	public String decoder(String value) {
	       StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
	       encryptor.setPassword("This password is not the password we are trying to encrypt taken from properties file.");
	       return encryptor.decrypt(value);
	}
	
}
