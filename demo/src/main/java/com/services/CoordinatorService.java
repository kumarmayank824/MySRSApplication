package com.services;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.constant.Constant;
import com.domain.SubmissionSchedule;
import com.repository.SubmissionScheduleRepository;
import com.repository.UserRepository;
import com.util.CommonUtil;

@Service
public class CoordinatorService {
  
	@Autowired
	SubmissionScheduleRepository submissionScheduleRepository;
	
	@Autowired
	SubmissionSchedule submissionSchedule;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommonUtil commonUtil;
	
	public String getSecretCode() {
		return userRepository.getSecretCode();
	}
	
	public void saveSubmissionSchedule(String coordinatorDaterange) {
		String[] dateArray = coordinatorDaterange.split(Constant.submissionDateSeparator);
		String startDate = dateArray[0].trim();
		String endDate = dateArray[1].trim();
		submissionSchedule.setStartDate(startDate);
		submissionSchedule.setEndDate(endDate);
		submissionScheduleRepository.deleteAll();
		submissionScheduleRepository.save(submissionSchedule);
	}
	
	public boolean isSubmissionAllowed() throws ParseException {
		boolean isSubmissionAllowed = false;
		SubmissionSchedule submissionSchedule = getSubmissionSchedule();
		if( null != submissionSchedule ) {
			Date startDate = commonUtil.dateFormatterReturnDateObj(submissionSchedule.getStartDate(),Constant.dateFormat2);
			Date endDate = commonUtil.dateFormatterReturnDateObj(submissionSchedule.getEndDate(),Constant.dateFormat2);
			Date currentDate = new Date();
			isSubmissionAllowed = currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <= 0;
		}
		return isSubmissionAllowed;
	}
	
	public SubmissionSchedule getSubmissionSchedule() {
		SubmissionSchedule submissionSchedule = null;
		List<SubmissionSchedule>  submissionScheduleList = submissionScheduleRepository.findAll();
		if( null != submissionScheduleList && !submissionScheduleList.isEmpty() 
				&& null != submissionScheduleList.get(0)) {
		     submissionSchedule = submissionScheduleList.get(0);	
		}
		return submissionSchedule;
	}
}
