package com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.SecretCode;
import com.repository.SecretCodeRepository;

@Service
public class SecretCodeService {
  
	@Autowired
	SecretCodeRepository secretCodeRepository;
	
	public List<SecretCode> findAll() {
	   return secretCodeRepository.findAll();
	}
	
}
