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
import java.text.ParseException;
import java.util.List;

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

import com.domain.Attachment;
import com.domain.Rating;
import com.domain.User;
import com.repository.AttachmentRepository;
import com.repository.RatingRepository;
import com.services.UserService;
import com.util.CommonUtil;

@Controller
public class StudentController {    
    
	
	@Autowired
    AttachmentRepository attachmentRepository;
	
	@Autowired
	RatingRepository ratingRepository;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/std-upload-pdf-page", method = RequestMethod.GET) 
	public String loadUploadPdfPage(Model model,HttpServletRequest request,HttpServletResponse response) {		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if( null != auth){
			User user = (User) auth.getPrincipal();
			model.addAttribute("loggedInUser", user.getUsername()); 
		}
		return "upload";
	}
	
	
	@RequestMapping(value ="/std-file-upload", method = RequestMethod.POST)
	public String saveFileObject(Model model,@RequestParam("myfile") MultipartFile file,
			@RequestParam("title") String title,@RequestParam("category") String category,
			@RequestParam("description") String description){
		
		String returnString = "uploadDetailForStudents";
		
		try {
			   
			   if (!file.isEmpty()) {
                    
				    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				    User user = (User) auth.getPrincipal();
			       
				    Attachment attachment = new Attachment();
				    attachment.setAuthor(user.getUsername());
	                attachment.setTitle(title);
	                attachment.setCategory(category);
	                attachment.setDescription(description);
	                attachment.setFileName(file.getOriginalFilename());
	                attachment.setFileSize(file.getSize());
	                
	                if(file.getContentType().equalsIgnoreCase("application/pdf")){
	                	attachment.setContentType("PDF");
	                }else{
	                	attachment.setContentType("Word Document");
	                }
	                		
	                
	                attachment.setFilePath("Path will be updated later");
	                
	                Attachment attachment1 = attachmentRepository.save(attachment);
	                
	                //Creating the directory to store file
					//String rootPath = System.getProperty("catalina.home");
                    String rootPath = "D:\\Mayank_Work\\setup\\MySRSApplication\\demo\\uploadedFile\\"+category+"\\"+attachment1.getId();
                    
                    //Saving the file path to database
	                attachment.setFilePath(rootPath+"\\");
	                attachmentRepository.save(attachment);
	                
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


				} else if(file.getSize() > 2097152) {
					returnString = "upload";
					 model.addAttribute("maxSizeError", "Bad Upload");
				}
		 } catch (Exception e) {
		        e.printStackTrace();
		 }
		//return returnString;
		return "redirect:/loginSuccess";
	}
	
	@RequestMapping(value="/std-upload-details", method = RequestMethod.GET) 
	public String showUploadDetails(Model model,HttpServletRequest request,HttpServletResponse response) {		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if( null != auth){
			User user = (User) auth.getPrincipal();
			model.addAttribute("loggedInUser", user.getUsername()); 
		}
		return "uploadDetailForStudents";
	}
	
	
	//Added for pagination With Server Call Each Time On Each Click
	/*@RequestMapping(value="/std-api-attachment/{itemsPerPage}/{pagenumber}", method = RequestMethod.GET) 
	public void getAllAttachmentDetail(@PathVariable("itemsPerPage") Integer itemsPerPage,
		      @PathVariable("pagenumber") Integer pagenumber,HttpServletResponse response) {		
		
		JSONObject returnJson = null;
		
		try {
			Integer offset = (pagenumber-1)*itemsPerPage;
			List<Attachment> attachmentLst = attachmentRepository.findAll(offset,itemsPerPage);
			if( null != attachmentLst){
				returnJson = new JSONObject();
				returnJson = commonUtil.getDetailsForPanel(attachmentLst,returnJson);
				returnJson.put("totalCount", attachmentRepository.count());
			}
			response.getWriter().write(returnJson.toString());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
	@RequestMapping(value="/std-api-attachment", method = RequestMethod.GET) 
	public void getAllAttachmentDetail(HttpServletResponse response) {		
		
		JSONObject returnJson = null;
		
		try {
			List<Attachment> attachmentLst = attachmentRepository.findAll();
			if( null != attachmentLst){
				returnJson = new JSONObject();
				returnJson = commonUtil.getDetailsForPanel(attachmentLst,returnJson,ratingRepository);
			}
			response.getWriter().write(returnJson.toString());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@RequestMapping(value="/std-attachment-download/{type}/{attachmentId}", method = RequestMethod.GET) 
	public @ResponseBody void downloadAttachment(@PathVariable("attachmentId") Long attachmentId,
			      @PathVariable("type") String type,
			      HttpServletRequest request,HttpServletResponse response) {	
		
		try {
		
			Attachment attachment =  attachmentRepository.findOne(attachmentId);
			File file = new File(attachment.getFilePath() + attachment.getFileName());
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
		        if(type.equalsIgnoreCase("preview")){
	        	    response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
	        	}else if(type.equalsIgnoreCase("download")){
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
	
	@RequestMapping(value="/std-save-rating-and-comment", method = RequestMethod.POST) 
	public void saveRatingAndComment(@RequestParam("attachmentId") Long attachmentId ,@RequestParam("rating") Long rating,
			@RequestParam("comment") String comment,HttpServletResponse response) {		
		
		JSONObject returnJson = null;
		
		try {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    User user = (User) auth.getPrincipal();
		    
			Rating ratingObj = new Rating();
			ratingObj.setAttachmentId(attachmentId);
			ratingObj.setAuthor(user.getUsername());
			ratingObj.setComment(comment);
			ratingObj.setRating(rating);
			ratingRepository.save(ratingObj);
			returnJson = new JSONObject();
			returnJson.put("showRatingLink",false);
			response.getWriter().write(returnJson.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}