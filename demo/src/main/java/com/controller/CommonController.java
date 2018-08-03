package com.controller;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.domain.User;
import com.domain.UserRole;
import com.services.EmailService;
import com.services.UserService;
import com.util.CommonUtil;

@Controller
public class CommonController {
    
	
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
		userRole.setRole("ROLE_STUDENT");
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
		String view = "uploadDetailNew"; 
		if( null != auth){
			User user = (User) auth.getPrincipal();
			if(null != user.getUserRoles() && !user.getUserRoles().isEmpty() && user.getUserRoles().get(0).getRole().equals("TEACHER")){
				view = "adminUploadDetailNew";
			}
			model.addAttribute("loggedInUser", user.getUsername()); 
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
