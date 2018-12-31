package com.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.constant.Constant;
import com.domain.Attachment;
import com.domain.ConfigProperties;
import com.domain.Marks;
import com.domain.Rating;
import com.domain.User;
import com.domain.UserRole;
import com.services.AttachmentService;
import com.services.CoordinatorService;
import com.services.MarksService;
import com.services.RatingService;
import com.services.UserService;
import com.util.CommonUtil;
import com.util.ValidatorUtil;

@Controller
public class StudentController {    
    
	@Autowired
	AttachmentService attachmentService;
	
	@Autowired
	MarksService marksService;
	
	@Autowired
	RatingService ratingService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	ValidatorUtil validatorUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CoordinatorService coordinatorService;
	
	@Autowired
	private ConfigProperties configProperties;
    
	
	@RequestMapping(value="/studentConfirm", method = RequestMethod.GET) 
	public String handleRegistrationRequest(Model model, String error, String logout) {
        return "studentConfirmPage";
    }
	
	
	// Process confirmation link
	@RequestMapping(value="/studentConfirm", method = RequestMethod.POST)
	public String processConfirmationForm(Model model,RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws IOException {
		
		
		String password = (String)request.getParameter("password");
		String token = (String)request.getParameter("token");
		String semester = (String)request.getParameter("semester");
		String batch = (String)request.getParameter("batch");
		String course = (String)request.getParameter("course");
		// Find the user associated with the token
		User user = userService.findByConfirmationToken(token);
		
		//set field for error page
		model.addAttribute("confirmationToken", token);
		model.addAttribute("semester", semester);
		model.addAttribute("batch", batch);
		model.addAttribute("course", course);
		
		
		if( user != null && validatorUtil.isConfirmationLinkRequestedAtleastFifteenMinutesAgo(user.getRequestTime()) ){
			//link is more than 15 minutes old, hence expired
		    userService.deleteExistingUser(user);
			redirectAttributes.addFlashAttribute("failureMessage", "Oops! Your confirmation link has been expired, please generate a new confirmation link and try.");
			return "redirect:/registerUser"; 
		}else  if( null == password || password.isEmpty()) {
			model.addAttribute("errorMessage", " * Password cannot be empty");
			model.addAttribute("isEmptyPasswordError", true);
			model.addAttribute("signInType", "Student");
			return "studentConfirmPage";
		}else if(!validatorUtil.validatorPassword(password)) {
			String message = "<b>Password requirements:</b>" + "<br/><br/>" 
		                       + "1. must be at least 8 characters" + "<br/>"
					           + "2. must contains a minimum of 1 numeric character [0-9]" + "<br/>"
		                       + "3. must contains a minimum of 1 special character [@#$%^& only.]"; 
			model.addAttribute("failureMessage", message);
			model.addAttribute("isPasswordNotValidError", true);
			model.addAttribute("signInType", "Student");
			return "studentConfirmPage";
		}else if( !((String)validatorUtil.validateStudentParamter(semester,batch,course).get("errorMessage")).isEmpty() ){
			Map<String,Object> errorMap = validatorUtil.validateStudentParamter(semester,batch,course);
			model.addAttribute("isSemsterError", (boolean)errorMap.get("isSemsterError"));
			model.addAttribute("isBatachError", (boolean)errorMap.get("isBatachError"));
			model.addAttribute("isCourseError", (boolean)errorMap.get("isCourseError"));
			model.addAttribute("failureMessage", (String)errorMap.get("errorMessage"));
			model.addAttribute("signInType", "Student");
			return "studentConfirmPage";
		}else {
			// Set new password
			user.setPassword(CommonUtil.encoder(password));
			
			// Set new semester
			user.setSemester(semester);
			
			// Set new batch
			user.setBatch(batch);
					
			// Set new course
			user.setCourse(course);		
	
			// Set user to enabled
			user.setEnabled(true);
			
			//Set user role
			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRole("ROLE_STUDENT");
			user.getUserRoles().add(userRole);
			
			// Save user
			userService.saveUser(user);
			redirectAttributes.addFlashAttribute("successMessage", "Congrats you have been successfully register!");
			
			return "redirect:/login";	
		}	
	}
	
	
	@RequestMapping(value="/std-upload-pdf-page", method = RequestMethod.GET) 
	public String loadUploadPdfPage(Model model,HttpServletRequest request,HttpServletResponse response) {		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if( null != auth){
				User user = (User) auth.getPrincipal();
				user = userService.findByEmail(user.getEmail());
				model.addAttribute("isSubmissionAllowed", coordinatorService.isSubmissionAllowed()); 
				model.addAttribute("loggedInUser", user.getUsername()); 
				model.addAttribute("loggedInUserEmail", user.getEmail());
			}
			model.addAttribute("profileDetailsDeclarationNotDone", "No");
		}catch(ParseException e) {
			
		}catch(Exception e) {
			
		}
		
		return "upload";
	}
	
	
	@RequestMapping(value ="/std-file-upload", method = RequestMethod.POST)
	public String saveFileObject(Model model,@RequestParam("myfile") MultipartFile file,
			@RequestParam("title") String title,@RequestParam("category") String category,
			@RequestParam("description") String description,HttpServletRequest request){
		
		String returnString = "uploadDetailForStudents";
		try {
			 String[] profileDetailsDeclarationFlag = request.getParameterValues("profileDetailsDeclarationFlag");
			 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			 User user = (User) auth.getPrincipal();
			 user = userService.findByEmail(user.getEmail());
			 boolean isSubmissionAllowed = coordinatorService.isSubmissionAllowed();
			 if (!isSubmissionAllowed) {
				 model.addAttribute("loggedInUser", user.getUsername()); 
				 model.addAttribute("submissionNotAllowedMessage", "Sorry! cannot accept it because you are not on time.");
				 model.addAttribute("isSubmissionAllowed", isSubmissionAllowed); 
				 returnString = "upload";  
			 }else {
				 if( null != profileDetailsDeclarationFlag && null != profileDetailsDeclarationFlag[0] ) {
					 if (!file.isEmpty()) {
					       
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
			                
			                Attachment attachment1 = attachmentService.saveAttachment(attachment);
			                
			                //Creating the directory to store file
							//String rootPath = System.getProperty("catalina.home");
			                String rootPath = configProperties.getUploadProperties().getFilePath()+category+"\\"+attachment1.getId();
			                
		                    //Saving the file path to database
			                attachment.setFilePath(rootPath+"\\");
			                attachmentService.saveAttachment(attachment);
			                
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
				 }else {
					 model.addAttribute("loggedInUser", user.getUsername()); 
					 model.addAttribute("missingInformationMessage", "** Necessary information missing"); 
					 model.addAttribute("profileDetailsDeclarationNotDone", "Yes");
					 model.addAttribute("isSubmissionAllowed", isSubmissionAllowed); 
					 returnString = "upload"; 
				 } 
			 }
		 } catch (Exception e) {
		        e.printStackTrace();
		 }
		if(returnString.equals("upload")) {
			return returnString;
		}
		return "redirect:/loginSuccess";
	}
	
	@RequestMapping(value="/std-upload-details", method = RequestMethod.GET) 
	public String showUploadDetails(Model model,HttpServletRequest request,HttpServletResponse response) {		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if( null != auth){
			User user = (User) auth.getPrincipal();
			user = userService.findByEmail(user.getEmail());
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
			List<Attachment> attachmentLst = attachmentService.getAllAttachment();
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
		    user = userService.findByEmail(user.getEmail());
		    
			Rating ratingObj = new Rating();
			ratingObj.setAttachmentId(attachmentId);
			ratingObj.setAuthor(user.getUsername());
			ratingObj.setComment(comment);
			ratingObj.setRating(rating);
			ratingService.saveRatingAndComment(ratingObj);
			returnJson = new JSONObject();
			returnJson.put("showRatingLink",false);
			response.getWriter().write(returnJson.toString());
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/std-activity-history", method = RequestMethod.GET)
	public String loadUserHistory(Model model,HttpServletRequest request, 
			HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String view = "studentActivityHistory"; 
		if( null != auth){
			User user = (User) auth.getPrincipal();
		    if(null != user){
		    	List<Attachment> attachmentLst = attachmentService.findAttachmentByEmailId(user.getEmail());
		    	Map<Long,Object> marksMap = new HashMap<Long,Object>();
		    	if( null != attachmentLst && !attachmentLst.isEmpty()) {
		    		for (Attachment attachment : attachmentLst) {
		    			Marks marks = marksService.getMarksByAttachmentId(attachment.getId());
		    			marksMap.put(attachment.getId(), marks);
					}
		    	    model.addAttribute("marksMap", marksMap);
		    		model.addAttribute("attachmentLst", attachmentLst);
		    	}else {
		    		model.addAttribute("attachmentLst", new ArrayList<Attachment>());
		    		model.addAttribute("marksMap", marksMap);
		    	}
		    	model.addAttribute("user", user);
		    }
		}    
		return view;
	}
}