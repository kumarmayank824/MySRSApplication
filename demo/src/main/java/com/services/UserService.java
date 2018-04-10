package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.User;
import com.repository.RegistrationRepository;

@Service
public class UserService {
   
    private RegistrationRepository registrationRepository;
	
    @Autowired
    public UserService(RegistrationRepository registrationRepository) { 
      this.registrationRepository = registrationRepository;
    }
    
	public User findByEmail(String email) {
		return registrationRepository.findByEmail(email);
	}
	
	public User findByConfirmationToken(Object object) {
		return registrationRepository.findByConfirmationToken(object);
	}
	
	public void saveUser(User user) {
		registrationRepository.save(user);
	}
}
