package com.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.constant.Constant;
import com.services.CoordinatorService;
import com.services.UserService;

@Controller
public class CoordinatorController {
   
	@Autowired
	UserService userService;
	
	@Autowired
	CoordinatorService coordinatorService;
	
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
}
