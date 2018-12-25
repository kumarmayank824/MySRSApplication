package com.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.constant.Constant;
import com.domain.Attachment;
import com.domain.CoordinatorAttachment;
import com.domain.GraphDetail;
import com.domain.SubmissionSchedule;
import com.repository.GraphDetailRepository;
import com.services.CoordinatorService;

@Controller
public class HomeController {
   
	@Autowired
	GraphDetailRepository graphDetailRepository;
	
	@Autowired
	CoordinatorService coordinatorService;
	
	@RequestMapping(value={"/", "/home"}, method = RequestMethod.GET) 
	public String showHomePage(Model model,HttpServletRequest request,HttpServletResponse response) {		
		boolean isSubmissionAllowed;
		try {
			isSubmissionAllowed = coordinatorService.isSubmissionAllowed();
			if(isSubmissionAllowed) {
				SubmissionSchedule submissionSchedule = coordinatorService.getSubmissionSchedule();
				String marqueeMessage = "Kindly make a note of the APD documents submission schedule, started from <b>" 
				               +  submissionSchedule.getStartDate() + " </b> and ends on <b>" + submissionSchedule.getEndDate()
				               + ",</b> Please plan accordingly, Also note that there is no after or offline acceptance of documents"
				               + " once the timelines are missed.";
				model.addAttribute("marqueeMessage", marqueeMessage); 
				model.addAttribute("isSubmissionAllowed",isSubmissionAllowed); 
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "home";
	}
	
	@RequestMapping(value="/get-graph-details", method = RequestMethod.GET) 
	public void  getGraphDetails(HttpServletResponse response) {
		
       JSONObject json = null;
       List<JSONObject> jsonList = null;
       List<String> categoryList = null;
       int counter = -1;
       int totalCount = 0;
	   String[] color = new String[3]; 	
	   color[0] = "rgb(124, 181, 236)";
	   color[1] = "rgb(67, 67, 72)";
	   color[2] = "rgb(144, 237, 125)";
	   
		try {
			
			List<GraphDetail> graphDetailLst = graphDetailRepository.getGraphDetails();
			if(null != graphDetailLst){
				jsonList = new ArrayList<JSONObject>();
				categoryList = new ArrayList<String>();
				for (GraphDetail graphDetail : graphDetailLst) {
					++counter;
					json = new JSONObject();
					json.put(Constant.graphName, graphDetail.getCategory());
					json.put(Constant.graphY, graphDetail.getCount());
					totalCount += graphDetail.getCount();
					json.put(Constant.graphDrilldown, graphDetail.getCategory());
					json.put(Constant.graphColor, color[counter]);
					categoryList.add(graphDetail.getCategory());
					jsonList.add(json);
				}
			}
			json = new JSONObject();
			json.put("graphDetails", jsonList);
			json.put("totalCount", totalCount);
			json.put("categories", categoryList);
			response.getWriter().write(json.toString());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@RequestMapping(value={"/guideline-documents"}, method = RequestMethod.GET) 
	public String showGuidelineDocuments(Model model,HttpServletRequest request) {
			try {
				List<CoordinatorAttachment> attachmentLst = coordinatorService.getAllAttachment();
				if( null != attachmentLst && !attachmentLst.isEmpty()){
					model.addAttribute("coordinatorAttachmentList", attachmentLst);  
				}else {
					model.addAttribute("noAttachementFoundErrorMessage", "No document available");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}	
		return "guidelineDocuments";		
	}
}
