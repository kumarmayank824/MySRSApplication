package com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.domain.CustomUserDetails;
import com.domain.User;
import com.repository.UserRepository;
import com.repository.UserRolesRepository;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
	private final UserRepository userRepository;
	private final UserRolesRepository userRolesRepository;
	
	@Autowired
    public CustomUserDetailsService(UserRepository userRepository,UserRolesRepository userRolesRepository) {
        this.userRepository = userRepository;
        this.userRolesRepository=userRolesRepository;
    }
	
        
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if(null == user){
			throw new UsernameNotFoundException("Authentication Failed");
		}else{
			List<String> userRoles = userRolesRepository.findRoleByEmail(email);
			return new CustomUserDetails(user,userRoles);
		}
	}
		
}
