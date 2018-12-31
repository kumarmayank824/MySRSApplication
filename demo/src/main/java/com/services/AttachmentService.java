package com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.domain.Attachment;
import com.domain.User;
import com.repository.AttachmentRepository;

@Service
public class AttachmentService {
    
	@Autowired
	AttachmentRepository attachmentRepository;
	
	public Attachment findOne(Long attachmentId) {
		return attachmentRepository.findOne(attachmentId);
	}
	
	public List<Attachment> findAttachmentByEmailId(String email) {
		return attachmentRepository.findAttachmentByEmailId(email);
	}
	
	@Transactional
	public void updateUserNameForAttachment(User user) {
		attachmentRepository.updateUserNameForAttachment(user.getUsername(), user.getEmail());
	}

	public Attachment saveAttachment(Attachment attachment) {
		return attachmentRepository.save(attachment);
	}

	public List<Attachment> getAllAttachment() {
		return attachmentRepository.findAll();
	}

	public List<Attachment> findAttachmentForTeacher(String semester, String batch, String course) {
		return attachmentRepository.findAttachmentForTeacher(semester,batch,course);
	}
}
