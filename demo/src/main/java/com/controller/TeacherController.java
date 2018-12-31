package com.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.constant.Constant;
import com.domain.Attachment;
import com.domain.Marks;
import com.domain.User;
import com.domain.UserRole;
import com.services.AttachmentService;
import com.services.MarksService;
import com.services.SecretCodeService;
import com.services.UserService;
import com.util.CommonUtil;
import com.util.ValidatorUtil;

@Controller
public class TeacherController {
    
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	ValidatorUtil validatorUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	SecretCodeService secretCodeService;
	
	@Autowired
	AttachmentService attachmentService;
	
	@Autowired
	MarksService marksService;
	
 	// Process confirmation link
	@RequestMapping(value="/teacherConfirm", method = RequestMethod.POST)
	public String processConfirmationForm(Model model,
			  RedirectAttributes redirectAttributes,HttpServletRequest request) throws IOException {
		
		String secretCode = (String)request.getParameter("secretCode");
		String password = (String)request.getParameter("password");
		String confirmPassword = (String)request.getParameter("confirmPassword");
		String token = (String)request.getParameter("token");
		// Find the user associated with the token
		User user = userService.findByConfirmationToken(token);
		
		model.addAttribute("confirmationToken", token);
		model.addAttribute("secretCode", secretCode);
		
		//Validation Part
		if( null == token || token.isEmpty() || ( user != null && validatorUtil.isConfirmationLinkRequestedAtleastFifteenMinutesAgo(user.getRequestTime()) ) ){
			//link is more than 15 minutes old, hence expired
			userService.deleteExistingUser(user);
			redirectAttributes.addFlashAttribute("failureMessage", "Oops! Your confirmation link has been expired, please generate a new confirmation link and try.");
			return "redirect:/registerUser"; 
		}else  if( null == password || password.isEmpty()) {
			model.addAttribute("errorMessage", " * Password cannot be empty");
			model.addAttribute("isEmptyPasswordError", true);
			return "teacherConfirmPage";
		}else if(!password.equals(confirmPassword)) {
			model.addAttribute("errorMessage", " * Please check, password and confirm password must be same");
			model.addAttribute("isPasswordAndConfirmPasswordNotSameError", true);
			return "teacherConfirmPage";
		}else if(!validatorUtil.validatorPassword(password)) {
			String message = "<b>Password requirements:</b>" + "<br/><br/>" 
		                       + "1. must be at least 8 characters" + "<br/>"
					           + "2. must contains a minimum of 1 numeric character [0-9]" + "<br/>"
		                       + "3. must contains a minimum of 1 special character [@#$%^& only.]"; 
			model.addAttribute("failureMessage", message);
			model.addAttribute("isPasswordAndConfirmPasswordNotSameError", true);
			return "teacherConfirmPage";
		}else if(null == secretCode || secretCode.isEmpty() || 
				!secretCode.equals(secretCodeService.findAll().get(0).getSecretCode())){
			model.addAttribute("errorMessage", "Wrong secret code provided!");
			model.addAttribute("isWrongSecretCodeError", true);
			return "teacherConfirmPage";
		}else{
			
			// Set password
			user.setPassword(CommonUtil.encoder(password));

			// Set user to enabled
			user.setEnabled(true);
			
			//Set user role
			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRole("ROLE_TEACHER");
			user.getUserRoles().add(userRole);
			
			// Save user
			userService.saveUser(user);
			redirectAttributes.addFlashAttribute("successMessage", "Congrats you have been successfully register!");
			
			return "redirect:/login";
		}
	}
	
	
	@RequestMapping(value="/tch-search-details", method = RequestMethod.POST) 
	public void getSearchDetails(@RequestParam(Constant.semester) String semester ,@RequestParam(Constant.batch) String batch,
			@RequestParam(Constant.course) String course,HttpServletResponse response) {		
		
		JSONObject returnJson = null;
		
		try {
			
			List<Attachment> attachmentLst = attachmentService.findAttachmentForTeacher(semester,batch,course);
			if( null != attachmentLst){
				returnJson = new JSONObject();
				returnJson = commonUtil.getDetailsForPanel(attachmentLst,returnJson,Constant.markObjectType);
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
	
	
	@RequestMapping(value="/tch-save-marks-and-remarks", method = RequestMethod.POST) 
	public void saveMarks(@RequestParam(Constant.attachmentId) Long attachmentId ,@RequestParam(Constant.markObjectType) Long marks,
			@RequestParam(Constant.markPara1) String markPara1 ,@RequestParam(Constant.markPara2) String markPara2,
			@RequestParam(Constant.markPara3) String markPara3 ,@RequestParam(Constant.markPara4) String markPara4,
			@RequestParam(Constant.markPara5) String markPara5,@RequestParam(Constant.semester) String semester ,@RequestParam(Constant.batch) String batch,
			@RequestParam(Constant.course) String course, HttpServletResponse response) {		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = (User) auth.getPrincipal();
	    
		Marks marksObj = new Marks();
		marksObj.setAttachmentId(attachmentId);
		marksObj.setAuthor(user.getUsername());
		marksObj.setEmail(user.getEmail());
		marksObj.setMarkPara1(markPara1);
		marksObj.setMarkPara2(markPara2);
		marksObj.setMarkPara3(markPara3);
		marksObj.setMarkPara4(markPara4);
		marksObj.setMarkPara5(markPara5);
		//marksObj.setRemarks("testing");
		marksObj.setMarks(marks);
		marksService.save(marksObj);
		
		getSearchDetails(semester, batch, course, response);
			
		
		
	}
	
}
