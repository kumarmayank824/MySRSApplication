package com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.Marks;
import com.repository.AttachmentRepository;
import com.repository.MarksRepository;

@Service
public class MarksService {
   
	@Autowired
	MarksRepository marksRepository;
	
	public List<Marks> findMarkedAttachmentByEmailId(String email){
		return marksRepository.findMarkedAttachmentByEmailId(email);
	}

	public Marks getMarksByAttachmentId(Long id) {
		return marksRepository.getMarksByAttachmentId(id);
	}
	
}
