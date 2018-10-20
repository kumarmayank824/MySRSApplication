package com.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.constant.Constant;
import com.domain.Attachment;
import com.domain.Rating;
import com.domain.User;
import com.domain.UserRole;
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
	
	
	// Process confirmation link
	@RequestMapping(value="/studentConfirm", method = RequestMethod.POST)
	public ModelAndView processConfirmationForm(ModelAndView modelAndView, BindingResult bindingResult,
			@RequestParam Map requestParams, RedirectAttributes redir) {
		
		modelAndView.setViewName("login");
		
		//Zxcvbn passwordCheck = new Zxcvbn();
		
		//Strength strength = passwordCheck.measure(requestParams.get("password"));
		
		/*if (strength.getScore() < 3) {
			bindingResult.reject("password");
			
			redir.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");

			modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
			System.out.println(requestParams.get("token"));
			return modelAndView;
		}*/
	
		// Find the user associated with the reset token
		User user = userService.findByConfirmationToken(requestParams.get("token"));

		// Set new password
		user.setPassword(CommonUtil.encoder((String)requestParams.get("password")));
		
		// Set new semester
		user.setSemester((String)requestParams.get("semester"));
		
		// Set new batch
		user.setBatch((String)requestParams.get("batch"));
				
		// Set new course
		user.setCourse((String)requestParams.get("course"));		

		// Set user to enabled
		user.setEnabled(true);
		
		//Set user role
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole("ROLE_STUDENT");
		user.getUserRoles().add(userRole);
		
		// Save user
		userService.saveUser(user);
		modelAndView.addObject("successMemberMessage", "Congrats you have been successfully register!");
		
		User user2 = userService.findByEmail(user.getEmail());
		
		String orginalPassword = CommonUtil.decoder(user2.getPassword());
		System.out.println(orginalPassword+orginalPassword);
			
		
		return modelAndView;		
	}
	
	
	@RequestMapping(value="/std-upload-pdf-page", method = RequestMethod.GET) 
	public String loadUploadPdfPage(Model model,HttpServletRequest request,HttpServletResponse response) {		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if( null != auth){
			User user = (User) auth.getPrincipal();
			model.addAttribute("loggedInUser", user.getUsername()); 
			model.addAttribute("loggedInUserEmail", user.getEmail());
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
				    attachment.setAuthorEmail(user.getEmail());
				    attachment.setSemester(user.getSemester());
				    attachment.setBatch(user.getBatch());
				    attachment.setCourse(user.getCourse());
	                attachment.setTitle(title);
	                attachment.setCategory(category);
	                attachment.setDescription(description);
	                attachment.setFileName(file.getOriginalFilename());
	                attachment.setFileSize(file.getSize());
	                attachment.setCount(0);
	                
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
				returnJson = commonUtil.getDetailsForPanel(attachmentLst,returnJson,Constant.ratingObjectType);
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