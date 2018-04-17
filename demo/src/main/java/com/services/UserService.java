package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.User;
import com.repository.UserRepository;

@Service
public class UserService {
   
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
}
