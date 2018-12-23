package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.constant.Constant;
import com.domain.Attachment;
import com.domain.Marks;
import com.domain.Rating;
import com.domain.SecretCode;
import com.repository.MarksRepository;
import com.repository.RatingRepository;
import com.repository.SecretCodeRepository;

@Component
public class CommonUtil {
    
	@Autowired
	SecretCode secretCodeObj;
	
	@Autowired
	SecretCodeRepository secretCodeRepository;
	
	@Autowired
	RatingRepository ratingRepository;
	
	@Autowired
	MarksRepository marksRepository;
	
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
				lst.add(dateFormatterReturnStringObj(attachment.getUploadedDate().toString(), Constant.dateFormat1));
				//lst.add(""+attachment.getContentType());
				
				finalLst.add(lst);
			}	
				
			returnJson.put("data", finalLst);
			
			return returnJson;
	}
	
	
	public JSONObject getDetailsForPanel(List<Attachment> attachmentLst, JSONObject returnJson,
			String ObjectType) throws JSONException, ParseException{
		
		List<JSONObject> lst = new ArrayList<JSONObject>();
		List<JSONObject> ratinglst = null;
		double totalRating = 0.0;
		for (Attachment attachment : attachmentLst) {
			
			JSONObject json = new JSONObject();
			json.put("objectType",ObjectType);
			json.put("id",attachment.getId());
			json.put("fileName",attachment.getFileName());
			json.put("author",attachment.getAuthor());
			json.put("title",attachment.getTitle());
			json.put("description",attachment.getDescription());
			json.put("category",attachment.getCategory());
			json.put("semester",attachment.getSemester());
			json.put("batch",attachment.getBatch());
			json.put("course",attachment.getCourse());
			json.put("fileSize",fileSizeToMb(attachment.getFileSize()));
			json.put("uploadedDate",dateFormatterReturnStringObj(attachment.getUploadedDate().toString(), Constant.dateFormat1));
			if(ObjectType.equals(Constant.ratingObjectType)){
				totalRating = 0.0;
				ratinglst = new ArrayList<JSONObject>();
				List<Rating> ratingLst = ratingRepository.findRatingByAttachmentId(attachment.getId());
				json = processRatings(ratingLst,ratinglst,totalRating,json);
			}else{
				Marks marks = marksRepository.getMarksByAttachmentId(attachment.getId());
				json = processMarks(marks,json);
			}
			lst.add(json);
		}	
			
		returnJson.put("attachmentLst", lst);
		
		return returnJson;
     }
	
	
	private JSONObject processRatings(List<Rating> ratingLst,List<JSONObject> ratinglst,
			double totalRating,JSONObject json) throws JSONException, ParseException{
		
		if(null != ratingLst && !ratingLst.isEmpty()){
			json.put("ratingExists",true);
			for (Rating rating : ratingLst) {
				JSONObject ratingJson = new JSONObject();
				ratingJson.put("authorName",rating.getAuthor());
				ratingJson.put("commentTime",dateFormatterReturnStringObj(rating.getCommentTime().toString(), Constant.dateFormat1));
				ratingJson.put("comment",rating.getComment());
				ratingJson.put("rating",rating.getRating());
				totalRating += rating.getRating();
				ratinglst.add(ratingJson);
			}
			json.put("averageRating",Math.round((totalRating/ratingLst.size()) * 10.0) / 10.0);
			json.put("ratinglst",ratinglst);
			json.put("noOfRating", ratingLst.size());
		}else{
			json.put("averageRating","N/A");
			json.put("ratingExists",false);
			json.put("noOfRating", 0);
		}
		
		return json;
	}
	
	private JSONObject processMarks(Marks marks,JSONObject json) 
			throws JSONException, ParseException{
		
		if(null != marks){
			json.put("marksExists",true);
			json.put("authorName",marks.getAuthor());
			json.put("commentTime",marks.getCommentTime());
			
			json.put("markPara1",marks.getMarkPara1());
			json.put("markPara2",marks.getMarkPara2());
			json.put("markPara3",marks.getMarkPara3());
			json.put("markPara4",marks.getMarkPara4());
			json.put("markPara5",marks.getMarkPara5());
			//json.put("remarks",marks.getRemarks());
			json.put("marks",marks.getMarks());
		}else{
			json.put("marksExists",false);
		}
		
		return json;
	}
	
	public String fileSizeToMb(Long byteSize){
		
		long sizeInMB = byteSize / (1024 * 1024);
		if (sizeInMB == 0 || sizeInMB < 1) {
			return  ""+(byteSize / 1024)+" KB"  ; 
		}else{
			return ""+(byteSize / (1024 * 1024))+" MB";
		}
	}
	
	public String dateFormatterReturnStringObj(String strDate,String dateFormat) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date fechaNueva = format.parse(strDate);
		return format.format(fechaNueva);
	}
	
	public Date dateFormatterReturnDateObj(String strDate,String dateFormat) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.parse(strDate);
	}
	
	public static String encoder(String value) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        // This is a required password for Jasypt. You will have to use the same password to
        // retrieve decrypted password later. T
        // This password is not the password we are trying to encrypt taken from properties file.
        encryptor.setPassword(Constant.encrpytorPassWord);
        return encryptor.encrypt(value);
	}
	
	public static String decoder(String value) {
       StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
       encryptor.setPassword(Constant.encrpytorPassWord);
       return encryptor.decrypt(value);
	}
	
	public static SimpleMailMessage emailTemplate(String to, String from, String subject
			, String text) {
		
		SimpleMailMessage registrationEmail = new SimpleMailMessage();
		registrationEmail.setTo(to);
		registrationEmail.setSubject(subject);
		registrationEmail.setText(text);
		registrationEmail.setFrom(from);
		return registrationEmail;
		
	}
	
	public static MimeMessage htmlMailMessage(MimeMessage mimeMessage ,String to, String from, String subject
			, String text) throws MessagingException {
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
		mimeMessage.setContent(text, "text/html");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setFrom(from);
		return mimeMessage;
	}
	
	@Scheduled(cron = "0 0 */6 ? * *")
	public void scheduleFixedDelayTask() {
		String secretCode = Constant.secretCodeInitial+UUID.randomUUID().toString();
		secretCodeObj.setSecretCode(CommonUtil.encoder(secretCode));
		secretCodeRepository.deleteAll();
		secretCodeRepository.save(secretCodeObj);
	}

}
