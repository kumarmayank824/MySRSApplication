package com.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.domain.Attachment;
import com.domain.User;
import com.services.AttachmentService;
import com.services.CoordinatorService;
import com.services.EmailService;
import com.services.UserService;
import com.util.CommonUtil;
import com.util.ValidatorUtil;

@Controller
public class CommonController {
    
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	ValidatorUtil validatorUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	AttachmentService attachmentService;
	
	@Autowired
	CoordinatorService coordinatorService;
	
	@RequestMapping(value="/login", method = RequestMethod.GET) 
	public String loadLoginPage(Model model, String error, String logout) {
        return "login";
    }
	
	@RequestMapping(value="/login", method = RequestMethod.POST) 
	public String login(Model model, String error, String logout) {
        return "login";
    }
	
	@RequestMapping(value="/forgotPassword", method = RequestMethod.GET) 
	public String forgotPassword(Model model, String error, String logout) {
        return "forgotPassword";
    }
	
	@RequestMapping(value="/forgotPassword", method = RequestMethod.POST) 
	public String forgotPasswordSave(Model model,  @RequestParam("email") String email,
			RedirectAttributes redirectAttributes ,HttpServletRequest request) {
        
		try {
			// Lookup user in database by e-mail
			User userExists = userService.findByEmail(email);
			if (userExists == null) {
				redirectAttributes.addFlashAttribute("previousEmail", email);
				redirectAttributes.addFlashAttribute("failureMessage", "Oops! No user exists with " + email);
				return "redirect:/forgotPassword"; 
			}else {
				//send forgot password email
				String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
				String text = "<p>This email was sent to you because someone requested a password reset on your account.</p>"
						      +"<p>Visit the following URL to set a new password:</p>" 
						      + "<p><a target='_blank' href='"+ appUrl + "/forgot-password-reset?token="+ UUID.randomUUID().toString() + "&signInType="+ userExists.getSignInType() + "&email="+ userExists.getEmail() + "'>" + appUrl + "/forgot-password-reset?token="+ UUID.randomUUID().toString() + "&signInType="+ userExists.getSignInType() + "&email="+ userExists.getEmail() + "</a></p>"
				              +"<p>You can do a regular login at: <a target='_blank' href='" + appUrl + "/login'>" + appUrl + "/login" + "</a></p>";
				MimeMessage mimeMessage = emailService.getMimeMessageObj();
				//SimpleMailMessage forgotPasswordEmail = CommonUtil.emailTemplate(mimeMessage, email, "noreply@domain.com", "MyApplication password reset", text);
				mimeMessage = CommonUtil.htmlMailMessage(mimeMessage, email, "noreply@domain.com", "MyApplication password reset", text);
				emailService.getJavaMailSender().send(mimeMessage);
				redirectAttributes.addFlashAttribute("forgotPasswordEmailSendSuccessfully", "true");
				redirectAttributes.addFlashAttribute("successMessage", "An e-mail with password reset link has been successfully sent to " + email);
				return "redirect:/forgotPassword"; 
			}
		} catch (MessagingException ex) {
			redirectAttributes.addFlashAttribute("failureMessage", "Oops!  There is some problem occurs while constructing password reset e-mail .");
       	    return "redirect:" + request.getRequestURI(); 
        } catch (Exception ex) {
			redirectAttributes.addFlashAttribute("failureMessage", "Oops!  There is some problem with e-mail delivery system.");
       	    return "redirect:" + request.getRequestURI(); 
        } 
    }
	
	@RequestMapping(value="/forgot-password-reset", method = RequestMethod.GET) 
	public String handlePasswordChangeRequest(Model model,@RequestParam("token") String token
			,@RequestParam("signInType") String signInType, @RequestParam("email") String email) {
		model.addAttribute("signInType", signInType);
		model.addAttribute("email", email);
		model.addAttribute("token", token);
		return "changePassword";
    }
	
	@RequestMapping(value="/forgot-password-reset", method = RequestMethod.POST) 
	public String saveNewPassword(Model model,@RequestParam("newPassword") String newPassword,
			@RequestParam("signInType") String signInType, @RequestParam("email") String email,
			@RequestParam("token") String token,
			RedirectAttributes redirectAttributes,HttpServletRequest request) {
		
		Map<String,Object> urlParamsMap = new LinkedHashMap<>();
		urlParamsMap.put("token", token);
		urlParamsMap.put("signInType", signInType);
		urlParamsMap.put("email", email);
		
		if( null == email || email.isEmpty() || null == signInType || signInType.isEmpty() ){
			redirectAttributes.addFlashAttribute("failureMessage", "Oops! Password reset link has been modified. ");
			return "redirect:"+CommonUtil.constructReturnUrl(request, urlParamsMap, null);	
		}else{
			User userExists = userService.findByEmail(email);
			if( null == userExists || ( null != userExists && !userExists.isEnabled() ) ){
				redirectAttributes.addFlashAttribute("failureMessage", "Oops! No active user has been associated with this email id, please check your e-mail id and try again. ");
				return "redirect:"+CommonUtil.constructReturnUrl(request, urlParamsMap, null);	
			} else if( null != userExists && !userExists.getSignInType().equals(signInType) ) { 
				redirectAttributes.addFlashAttribute("failureMessage", "Oops! Password reset link has been modified. ");
				return "redirect:"+CommonUtil.constructReturnUrl(request, urlParamsMap, null);	
			} else if (!validatorUtil.validatorPassword(newPassword)) {
				String message = "<b>Password requirements:</b>" + "<br/><br/>" 
	                    + "1. must be at least 8 characters" + "<br/>"
				           + "2. must contains a minimum of 1 numeric character [0-9]" + "<br/>"
	                    + "3. must contains a minimum of 1 special character [@#$%^& only.]";
				redirectAttributes.addFlashAttribute("isNewPasswordError", true);
				redirectAttributes.addFlashAttribute("failureMessage", message);
				return "redirect:"+CommonUtil.constructReturnUrl(request, urlParamsMap, null);	
			}else {
				redirectAttributes.addFlashAttribute("successMessage", "Your password has been changed successfully");
				newPassword = CommonUtil.encoder(newPassword);
				userExists.setPassword(newPassword);
				userService.saveUser(userExists);
				return "redirect:/login"; 
			}
		}	
    }
	
	@RequestMapping(value="/registerUser", method = RequestMethod.GET) 
	public String handleRegistrationRequest(Model model, String error, String logout) {
        return "registration";
    }
	
	@RequestMapping(value="/registerUser", method = RequestMethod.POST) 
	public String registerUser(@Valid @ModelAttribute("user") User user, 
			  BindingResult bindingResult,  Model model,
			  RedirectAttributes redirectAttributes, HttpServletRequest request) {
		 
		     if(user.getSignInType().equals("Student") && !validatorUtil.validateWiproEmail(user.getEmail())) {
		    	 redirectAttributes.addFlashAttribute("failureMessage", "Please note, only WIPRO Mail id is allowed for sign in");
			     redirectAttributes.addFlashAttribute("isFailure", true);
			     redirectAttributes.addFlashAttribute("username", user.getUsername());
		    	 bindingResult.reject("email");
		    	 return "redirect:" + request.getRequestURI(); 
		     }else {
		    	// Lookup user in database by e-mail
				User userExists = userService.findByEmail(user.getEmail());
				
				if (userExists != null && userExists.isEnabled()) { //user already exists and also enabled
					redirectAttributes.addFlashAttribute("failureMessage", "Oops!  There is already a user registered with the email provided.");
			    	redirectAttributes.addFlashAttribute("isFailure", true);
			    	redirectAttributes.addFlashAttribute("username", user.getUsername());
			    	bindingResult.reject("email");
					return "redirect:" + request.getRequestURI(); 
				}else if(userExists != null && !userExists.isEnabled()) { //user already exists but not enabled
					//delete existing and disabled user
					userService.deleteExistingUser(userExists);
				}
				if (bindingResult.hasErrors()) {
					redirectAttributes.addFlashAttribute("username", user.getUsername());
					return "redirect:" + request.getRequestURI(); 
				} else { // new user so we create user and send confirmation e-mail
							
					// Disable user until they click on confirmation link in email
				    user.setEnabled(false);
				      
				    // Generate random 36-character string token for confirmation link
				    user.setConfirmationToken(UUID.randomUUID().toString());
				        
				    userService.saveUser(user);
					
				      try {    
						    //send registration email
							String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
							String text = "<p>This email was sent to you because someone requested a registration on your email.</p>"
									      +"<p>Visit the following URL to complete the registration process:</p>" 
									      + "<p><a target='_blank' href='"+ appUrl + "/confirm?token="+ user.getConfirmationToken() + "&signInType="+ user.getSignInType() + "'>" + appUrl + "/confirm?token="+  user.getConfirmationToken() + "&signInType="+ user.getSignInType() + "</a></p>"
							              +"<p>You can check the site at: <a target='_blank' href='" + appUrl + "/home'>" + appUrl + "/home" + "</a></p>";
							MimeMessage mimeMessage = emailService.getMimeMessageObj();
							mimeMessage = CommonUtil.htmlMailMessage(mimeMessage, user.getEmail(), "noreply@domain.com", "MyApplication registration confirmation", text);
							emailService.getJavaMailSender().send(mimeMessage);
							
							redirectAttributes.addFlashAttribute("successMessage", "A confirmation e-mail has been sent to " + user.getEmail());
						
						    return "redirect:" + request.getRequestURI(); 
						    
				      } catch (MessagingException ex) {
							redirectAttributes.addFlashAttribute("failureMessage", "Oops!  There is some problem occurs while constructing password reset e-mail .");
				       	    return "redirect:" + request.getRequestURI(); 
				      } catch (Exception ex) {
							redirectAttributes.addFlashAttribute("failureMessage", "Oops!  There is some problem with e-mail delivery system.");
				       	    return "redirect:" + request.getRequestURI(); 
				      }     
				 } 
		   }
    }
	
    // Process confirmation link
	@RequestMapping(value="/confirm", method = RequestMethod.GET)
	public String showConfirmationPage(Model model, @RequestParam("token") String token
			,@RequestParam("signInType") String signInType,RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws Exception {
		
		Map<String,Object> urlParamsMap = new LinkedHashMap<>();
		urlParamsMap.put("token", token);
		User user = userService.findByConfirmationToken(token);
		String returnPageName = "redirect:"+CommonUtil.constructReturnUrl(request, urlParamsMap, "/studentConfirm");
		if (user == null) { // No token found in DB
			redirectAttributes.addFlashAttribute("failureMessage", "Oops! That was an invalid confirmation link.");
			return "redirect:/registerUser";
		}else if( !user.isEnabled() && null != user.getSignInType() && !user.getSignInType().equals(signInType)){
			redirectAttributes.addFlashAttribute("failureMessage", "Oops! Your sign in type has been modified.");
			return "redirect:/registerUser";
		}else if( user != null && validatorUtil.isConfirmationLinkRequestedAtleastFifteenMinutesAgo(user.getRequestTime())){
			//link is more than 15 minutes old, hence expired
			userService.deleteExistingUser(user);
			redirectAttributes.addFlashAttribute("failureMessage", "Oops! That confirmation link has been expired, please generate a new link and try.");
			return "redirect:/registerUser"; 
		}else if( user != null && user.isEnabled() ){
			redirectAttributes.addFlashAttribute("failureMessage", "Oops! That confirmation link has been already used for other sign up, please generate a new link and try.");
			return "redirect:/registerUser"; 
		}else { // Token found	
			if(user.getSignInType().equals("Teacher")){
				returnPageName =  "redirect:"+CommonUtil.constructReturnUrl(request, urlParamsMap, "/teacherConfirm");
			}
		}
		return returnPageName;		
	}
	
	@RequestMapping(value="/loginSuccess", method = RequestMethod.GET) 
	public String  loadLoginSuccessPage(Model model,HttpServletRequest request,HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String view = "uploadDetailForStudents"; 
		if( null != auth){
			User user = (User) auth.getPrincipal();
			if(null != user.getUserRoles() && !user.getUserRoles().isEmpty() && user.getUserRoles().get(0).getRole().equals("ROLE_TEACHER")){
				view = "uploadDetailForTeachers";
			}else if(null != user.getUserRoles() && !user.getUserRoles().isEmpty() && user.getUserRoles().get(0).getRole().equals("ROLE_COORDINATOR")){
				view = "coordinatorActionsPage";
				model.addAttribute("existingSecretCode", coordinatorService.getSecretCode()); 
			}
			model.addAttribute("loggedInUser", user.getUsername()); 
		}
		return view;
	}
	
	
	@RequestMapping(value="/attachment-download/{type}/{attachmentId}", method = RequestMethod.GET) 
	public @ResponseBody void downloadAttachment(@PathVariable("attachmentId") Long attachmentId,
			      @PathVariable("type") String type,
			      HttpServletRequest request,HttpServletResponse response) {	
		
		try {
		
			Attachment attachment =  attachmentService.findOne(attachmentId);
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
	
	@RequestMapping(value="/user-profile", method = RequestMethod.GET)
	public String loadUserProfile (Model model,HttpServletRequest request, 
			HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String view = "userProfile"; 
		if( null != auth){
			User user = (User) auth.getPrincipal();
		    if(null != user){
		    	user = userService.findByEmail(user.getEmail());
		    	model.addAttribute("user", user);
		    	model.addAttribute("passwordHint", CommonUtil.decoder(user.getPassword()).substring(0, 3));
		    	model = userService.setUserProfileExtraParameter(model,user);
		    	model.addAttribute("showProfileInEditModeWhileError", false);
		    }else {
				//later get the referer and do
		    	//user need to login to see the profile
				return null;
			}
		}else {
			//later get the referer and do
			//user need to login to see the profile
		}
		return view;
	}
	
	@RequestMapping(value="/updateProfileDetails", method = RequestMethod.POST)
	public String updateProfileDetails(@Valid @ModelAttribute("user") User newUser,BindingResult bindingResult,
			Model model,HttpServletRequest request, HttpServletResponse response) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String view = "userProfile"; 
		if( null != auth){
			User oldUser = (User) auth.getPrincipal();
			if( null != oldUser && null != newUser) {
				userService.updateUserDetail(newUser, oldUser);
				newUser = userService.findByEmail(oldUser.getEmail());
		    	model.addAttribute("user", newUser);
				model.addAttribute("newDetailsUpdated", "Record updated successfully");
				model = userService.setUserProfileExtraParameter(model, newUser);
				model.addAttribute("showProfileInEditModeWhileError", false);
			}else {
				model.addAttribute("user", oldUser);
				model = userService.setUserProfileExtraParameter(model, oldUser);
				model.addAttribute("newDetailsRejected", "Problem while updating the record");
				model.addAttribute("showProfileInEditModeWhileError", true);
			}
		}else {
			//later get the referer and do
			//user need to login to see the profile
		}
		return view;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
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
	
	@RequestMapping(value="/access_denied", method = RequestMethod.GET) 
	public String handleAccessDeniedRequest(Model model, String error, String logout) {
        return "accessDenied";
    }
	
	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
		return "error";
	}
	
} 
