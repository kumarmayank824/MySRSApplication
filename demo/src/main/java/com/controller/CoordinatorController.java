package com.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.constant.Constant;
import com.domain.ConfigProperties;
import com.domain.CoordinatorAttachment;
import com.domain.User;
import com.services.CoordinatorService;
import com.services.EmailService;
import com.services.UserService;
import com.util.CommonUtil;

@Controller
public class CoordinatorController {
   
	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	CoordinatorService coordinatorService;
	
	@Autowired
	private ConfigProperties configProperties;
	
	@RequestMapping(value="/coord-save-submission-start-and-end-date", method = RequestMethod.POST) 
	public void saveStartAndEndTime(@RequestParam("coordinatorDaterange") String coordinatorDaterange,HttpServletResponse response) {		
	    try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(null != auth && !coordinatorDaterange.isEmpty() && coordinatorDaterange.contains(Constant.submissionDateSeparator)) {
				//save submission schedule
				coordinatorService.saveSubmissionSchedule(coordinatorDaterange);
				JSONObject returnJson = new JSONObject();
				returnJson.put("startAndEndTimeResponseMessage","Updated Successfully..!!");
				returnJson.put("coordinatorDaterange","");
				response.getWriter().write(returnJson.toString());
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/coord-get-new-secret-code", method = RequestMethod.GET)
	public void getNewSecretCode(HttpServletResponse response) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if( null != auth){
				
				JSONObject returnJson = new JSONObject();
				returnJson.put("newSecretCode",coordinatorService.getSecretCode());
				returnJson.put("newCodeSuccessMessage","New Code Generated Successfully..!!");
				response.getWriter().write(returnJson.toString());
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/coord-save-document", method = RequestMethod.POST)
	public void saveCoordinatorDocument(Model model,@RequestParam("coordinatorFile") MultipartFile file,
			HttpServletRequest request,HttpServletResponse response) {
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) auth.getPrincipal();
			user = userService.findByEmail(user.getEmail());
			if( null == auth || !user.getUserRoles().get(0).getRole().equals("ROLE_COORDINATOR")) {
				JSONObject returnJson = new JSONObject();
				returnJson.put("documentUploadResponseMessage","You are not authorize to upload from here, only user with COORDINATOR role can upload, also make sure you are correctly logged in.");
				returnJson.put("isDocumentUploadResponseSuccess",false);
				response.getWriter().write(returnJson.toString());
			}else {
				if (!file.isEmpty()) {
					CoordinatorAttachment attachment = new CoordinatorAttachment();
				    attachment.setAuthor(user.getUsername());
				    attachment.setAuthorEmail(user.getEmail());
				    attachment.setFileName(file.getOriginalFilename());
				    attachment.setImageFileName(CommonUtil.changeFileExtensionToPng(file.getOriginalFilename()));
		            attachment.setFileSize(file.getSize());
		            if(file.getContentType().equalsIgnoreCase("application/pdf")){
		            	attachment.setContentType("PDF");
		            }
		            attachment.setFilePath("Path will be updated later");
		            
		            CoordinatorAttachment attachment1 = coordinatorService.saveAttachment(attachment);
		            //Creating the directory to store file
		            String rootPath = configProperties.getUploadProperties().getCoordinatorFilePath()+attachment1.getId();
		            
		            //Saving the file path to database
		            attachment.setFilePath(rootPath+"\\");
		            attachment.setImageFilePath(rootPath+"\\");
		            coordinatorService.saveAttachment(attachment);
		            
					File dir = new File(rootPath);
					if (!dir.exists())
						dir.mkdirs();
		
					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath()
							+ File.separator + file.getOriginalFilename());
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(file.getBytes());
					stream.close();
					
					//save PDF first page as image on server
					CommonUtil.convertPdfFirstPageToImageAndSave(dir.getAbsolutePath()
							+ File.separator, file.getOriginalFilename());
					
					
					JSONObject returnJson = new JSONObject();
					returnJson.put("documentUploadResponseMessage","Document successfully uploaded..!!");
					returnJson.put("isDocumentUploadResponseSuccess",true);
					response.getWriter().write(returnJson.toString());
			   }else {
				    JSONObject returnJson = new JSONObject();
					returnJson.put("documentUploadResponseMessage","No file or empty file cannot be uploaded");
					returnJson.put("isDocumentUploadResponseSuccess",false);
					response.getWriter().write(returnJson.toString());
			   }
			}
			
	}catch (Exception e) {
        e.printStackTrace();
    }
  }	
	
	@RequestMapping(value="/coordinator-attachment-download/{requestType}/{fileType}/{attachmentId}", method = RequestMethod.GET) 
	public @ResponseBody void downloadAttachment(@PathVariable("attachmentId") Long attachmentId,
			      @PathVariable("requestType") String requestType,@PathVariable("fileType") String fileType,
			      HttpServletRequest request,HttpServletResponse response) {	
		
		try {
		
			CoordinatorAttachment attachment =  coordinatorService.findOne(attachmentId);
			File file  = null;
			if(fileType.equalsIgnoreCase("image")) {
				file = new File(attachment.getImageFilePath() + attachment.getImageFileName());
			}else {
				file = new File(attachment.getFilePath() + attachment.getFileName());
			}
		    if (file.exists()) {
		        //get the mimetype
		        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		        if (mimeType == null) {
		            //unknown mimetype so set the mimetype to application/octet-stream
		            mimeType = "application/octet-stream";
		        }
		        response.setContentType(mimeType);
		        /**
		         * In a regular HTTP response, the Content-Disposition response header is a
		         * header indicating if the content is expected to be displayed inline in the
		         * browser, that is, as a Web page or as part of a Web page, or as an
		         * attachment, that is downloaded and saved locally.
		         * 
		         */
	
		        /**
		         * Here we have mentioned it to show inline
		         */
		        if(requestType.equalsIgnoreCase("preview")){
	        	    response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
	        	}else if(requestType.equalsIgnoreCase("download")){
	        	    //Here we have mentioned it to show as attachment
	        	    response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
	        	}
		        response.setContentLength((int) file.length());
		        InputStream inputStream;
				inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				
			 } 
		    
		}catch (FileNotFoundException e) {
		  e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
	    }
	}
	
	@RequestMapping(value="/coord-block-user", method = RequestMethod.GET)
	public String blockUser(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if( null != auth){
				
				//disable suspicious user
				String email = (String)request.getParameter("email");
				
				User user = userService.findByEmail(email);
				
				if( user.isEnabled() ) {
					userService.disableUserAccount(0,email);
					
					//notify suspicious user, that his/her account is blocked by coordinator
					String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
					String text = "<p>This email was sent to you because your account is blocked due to suspicious registration.</p>"
							      +"<p>Please verify login at: <a target='_blank' href='" + appUrl + "/login'>" + appUrl + "/login" + "</a></p>";
					MimeMessage mimeMessage = emailService.getMimeMessageObj();
					mimeMessage = CommonUtil.htmlMailMessage(mimeMessage, email, "noreply@domain.com", null, "MyApplication account blocked", text);
					emailService.getJavaMailSender().send(mimeMessage);
					
					//account blocked confirmation to coordinator
					redirectAttributes.addFlashAttribute("successMessage", "Suspicious user is successfully blocked.");
					return "redirect:/loginSuccess";
				} else {
					redirectAttributes.addFlashAttribute("successMessage", "Suspicious user is already blocked.");
					return "redirect:/loginSuccess";
				}
			}
		}catch (MessagingException e) {
			//do nothing
		}catch (Exception e) {
			//do nothing
		}
		return null;
		
		
	}
	
}
