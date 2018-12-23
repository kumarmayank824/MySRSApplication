package com.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
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

@Controller
public class TeacherController {
    
	@Autowired
	CommonUtil commonUtil;
	
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
	public ModelAndView processConfirmationForm(ModelAndView modelAndView, BindingResult bindingResult,
			@RequestParam Map requestParams, RedirectAttributes redir) {
		
		String secretCode = (String)requestParams.get("secretCode");
		
		if(!secretCode.equals(secretCodeService.findAll().get(0).getSecretCode())){
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
		
		//JSONObject returnJson = null;
		
		try {
			
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
			//returnJson = new JSONObject();
			//returnJson.put("showMarksLink",false);
			//response.getWriter().write(returnJson.toString());
			
		}  catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
