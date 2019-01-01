package com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.constant.Constant;
import com.domain.Attachment;
import com.domain.Marks;
import com.domain.User;
import com.repository.MarksRepository;
import com.repository.SubmissionScheduleRepository;
import com.repository.UserRepository;
import com.util.CommonUtil;

@Service
public class UserService {
    
	@Autowired
	MarksRepository marksRepository;
	
	@Autowired
	AttachmentService attachmentService;
	
	@Autowired
	SubmissionScheduleRepository submissionScheduleRepository;
	
	@Autowired
	CommonUtil commonUtil;
	
    private UserRepository userRepository;
	
    @Autowired
    public UserService(UserRepository registrationRepository) { 
      this.userRepository = registrationRepository;
    }
    
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User findByConfirmationToken(Object object) {
		return userRepository.findByConfirmationToken(object);
	}
	
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	@Transactional
	public void updateUserDetail(User newUser, User oldUser) {
		
		int userId = oldUser.getUserId();
		String username = newUser.getUsername() != null ? newUser.getUsername() : oldUser.getUsername();
		String semester = newUser.getSemester() != null ? newUser.getSemester() : oldUser.getSemester();
		String batch = newUser.getBatch() != null ? newUser.getBatch() : oldUser.getBatch();
		String course = newUser.getCourse() != null ? newUser.getCourse() : oldUser.getCourse();
		userRepository.updateUserDetail(
					username,
					semester,
					batch,
					course,
					userId
				);
	}
	
	public Model setUserProfileExtraParameter(Model model, User user) {
		if(user.getSignInType().equalsIgnoreCase(Constant.teacher)) {
    		List<Marks> marksLst = marksRepository.findMarkedAttachmentByEmailId(user.getEmail());
    		if( null != marksLst && !marksLst.isEmpty() ) {
    			model.addAttribute("noOfAttachmentMarked", marksLst.size());
	    	}else {
    			model.addAttribute("noOfAttachmentMarked", 0);
    		}
    	}else if(user.getSignInType().equalsIgnoreCase(Constant.student)) {
    		List<Attachment> attachmentLst = attachmentService.findAttachmentByEmailId(user.getEmail());
	    	if( null != attachmentLst && !attachmentLst.isEmpty() ) {
	    		attachmentService.updateUserNameForAttachment(user);
    			model.addAttribute("noOfTotalUpload", attachmentLst.size());
	    	}else {
    			model.addAttribute("noOfTotalUpload", 0);
    		}	
    	}
		return model;
	}

	public void deleteExistingUser(User userExists) {
		 userRepository.delete(userExists);
		
	}
    
	@Transactional
	public void disableUserAccount(int flag,String email) {
		userRepository.disableUserAccount(flag,email);
		
	}

	public String getCoordinatorEmailLst() {
		return userRepository.getCoordinatorEmailLst();
	}
}
