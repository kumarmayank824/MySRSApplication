package com.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.domain.User;
import com.domain.UserRole;
import com.repository.SecretCodeRepository;
import com.services.UserService;
import com.util.CommonUtil;

@Controller
public class TeacherController {
    
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	SecretCodeRepository secretCodeRepository;
	
	// Process confirmation link
	@RequestMapping(value="/teacherConfirm", method = RequestMethod.POST)
	public ModelAndView processConfirmationForm(ModelAndView modelAndView, BindingResult bindingResult,
			@RequestParam Map requestParams, RedirectAttributes redir) {
		
		String secretCode = (String)requestParams.get("secretCode");
		
		if(!secretCode.equals(secretCodeRepository.findAll().get(0).getSecretCode())){
			modelAndView.setViewName("teacherConfirmPage");
			modelAndView.addObject("secretCodeError", "Wrong Secret Provided!");
		}else{
			
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

			// Set user to enabled
			user.setEnabled(true);
			
			//Set user role
			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRole("ROLE_TEACHER");
			user.getUserRoles().add(userRole);
			
			// Save user
			userService.saveUser(user);
			modelAndView.addObject("successMemberMessage", "Congrats you have been successfully register!");
			
			User user2 = userService.findByEmail(user.getEmail());
			
			String orginalPassword = CommonUtil.decoder(user2.getPassword());
			System.out.println(orginalPassword+orginalPassword);
			
		}
		return modelAndView;		
	}
	
}
