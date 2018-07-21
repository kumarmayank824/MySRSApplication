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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.domain.Attachment;
import com.domain.Rating;
import com.domain.User;
import com.domain.UserRole;
import com.repository.AttachmentRepository;
import com.repository.RatingRepository;
import com.services.EmailService;
import com.services.UserService;
import com.util.CommonUtil;

@Controller
public class MainController {    
    
	
	@Autowired
    AttachmentRepository attachmentRepository;
	
	@Autowired
	RatingRepository ratingRepository;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailService;
	
	@RequestMapping(value={"/", "/home"}, method = RequestMethod.GET) 
	public String showHomePage(Map<String, Object> model,HttpServletRequest request,HttpServletResponse response) {		
		return "home";
	 }  
	
	@RequestMapping(value="/login", method = RequestMethod.GET) 
	public String loadLoginPage(Model model, String error, String logout) {
        return "login";
    }
	
	@RequestMapping(value="/login", method = RequestMethod.POST) 
	public String login(Model model, String error, String logout) {
        return "login";
    }
	
	@RequestMapping(value="/registerRequest", method = RequestMethod.GET) 
	public String handleRegistrationRequest(Model model, String error, String logout) {
        return "registration";
    }
	
	@RequestMapping(value="/registerUser", method = RequestMethod.POST) 
	public String registerUser(@Valid @ModelAttribute("user") User user, 
			  BindingResult bindingResult,  Model model, HttpServletRequest request) {
		
	        // Lookup user in database by e-mail
			User userExists = userService.findByEmail(user.getEmail());
			
			System.out.println(userExists);
			
			if (userExists != null) {
				model.addAttribute("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
				bindingResult.reject("email");
				return "registration";
				
			}
				
			if (bindingResult.hasErrors()) { 
				return "registration";
			} else { // new user so we create user and send confirmation e-mail
						
				// Disable user until they click on confirmation link in email
			    user.setEnabled(false);
			      
			    // Generate random 36-character string token for confirmation link
			    user.setConfirmationToken(UUID.randomUUID().toString());
			        
			    userService.saveUser(user);
					
				String appUrl = request.getScheme() + "://" + request.getServerName();
				
				SimpleMailMessage registrationEmail = new SimpleMailMessage();
				registrationEmail.setTo(user.getEmail());
				registrationEmail.setSubject("Registration Confirmation");
				registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
						+ appUrl + ":9099/confirm?token=" + user.getConfirmationToken());
				registrationEmail.setFrom("noreply@domain.com");
				
				emailService.sendEmail(registrationEmail);
				
				model.addAttribute("confirmationMessage", "A confirmation e-mail has been sent to " + user.getEmail());
				return "registration";
			}
    }
	
	
    // Process confirmation link
	@RequestMapping(value="/confirm", method = RequestMethod.GET)
	public String showConfirmationPage(Model model, @RequestParam("token") String token) {
			
		User user = userService.findByConfirmationToken(token);
			
		if (user == null) { // No token found in DB
			model.addAttribute("invalidToken", "Oops!  This is an invalid confirmation link.");
		} else { // Token found
			model.addAttribute("confirmationToken", user.getConfirmationToken());
		}
			
		return "confirm";		
	}
	
	// Process confirmation link
	@RequestMapping(value="/confirm", method = RequestMethod.POST)
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
		user.setPassword(commonUtil.encoder((String)requestParams.get("password")));

		// Set user to enabled
		user.setEnabled(true);
		
		//Set user role
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole("ROLE_USER");
		user.getUserRoles().add(userRole);
		
		// Save user
		userService.saveUser(user);
		modelAndView.addObject("successMemberMessage", "Congrats you are our memeber now!");
		
		User user2 = userService.findByEmail(user.getEmail());
		
		String orginalPassword = commonUtil.decoder(user2.getPassword());
		System.out.println(orginalPassword+orginalPassword);
		
		return modelAndView;		
	}
	
	
	
	@RequestMapping(value="/loginSuccess", method = RequestMethod.GET) 
	public String  loadLoginSuccessPage(Model model,HttpServletRequest request,HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if( null != auth){
			User user = (User) auth.getPrincipal();
			model.addAttribute("loggedInUser", user.getUsername()); 
		}
		return "uploadDetailNew";
	}
	
	@RequestMapping(value="/to-upload-pdf-page", method = RequestMethod.GET) 
	public String loadUploadPdfPage(Model model,HttpServletRequest request,HttpServletResponse response) {		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if( null != auth){
			User user = (User) auth.getPrincipal();
			model.addAttribute("loggedInUser", user.getUsername()); 
		}
		return "upload";
	}
	
	
	@RequestMapping(value ="/tofileUpload", method = RequestMethod.POST)
	public String saveFileObject(Model model,@RequestParam("myfile") MultipartFile file,
			@RequestParam("title") String title,@RequestParam("category") String category,
			@RequestParam("description") String description){
		
		String returnString = "uploadDetailNew";
		
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
                    String rootPath = "F:\\my-srs-boot\\uploadedFile\\"+category+"\\"+attachment1.getId();
                    
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
		return returnString;
	}
	
	@RequestMapping(value="/to-upload-details", method = RequestMethod.GET) 
	public String showUploadDetails(Model model,HttpServletRequest request,HttpServletResponse response) {		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if( null != auth){
			User user = (User) auth.getPrincipal();
			model.addAttribute("loggedInUser", user.getUsername()); 
		}
		return "uploadDetailNew";
	}
	
	/*@RequestMapping(value="/to-apiAttachment", method = RequestMethod.GET) 
	public void getAllAttachmentDetail(HttpServletResponse response) {		
		
		JSONObject returnJson = null;
		
		try {
			
			List<Attachment> attachmentLst = attachmentRepository.findAll();
			if( null != attachmentLst){
				returnJson = new JSONObject();
				returnJson = commonUtil.getDetailsForTable(attachmentLst,returnJson);
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
	
	
	//Added for pagination With Server Call Each Time On Each Click
	/*@RequestMapping(value="/to-apiAttachment/{itemsPerPage}/{pagenumber}", method = RequestMethod.GET) 
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
	
	@RequestMapping(value="/to-apiAttachment", method = RequestMethod.GET) 
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
	
	
	@RequestMapping(value="/toAttachmentDownload/{type}/{attachmentId}", method = RequestMethod.GET) 
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
	
	@RequestMapping(value="/to-saveRatingAndComment", method = RequestMethod.POST) 
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
	
	
	@RequestMapping(value="/tologout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null && auth.getDetails() != null){   
	    	request.getSession().invalidate();
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    //We can redirect wherever we want, but generally it's a good practice to show login screen again.
	    return "logoutSuccess";
	    //return "redirect:/tologoutSuccess";
	}
	
}