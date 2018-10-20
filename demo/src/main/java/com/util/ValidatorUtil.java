package com.util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ValidatorUtil {
   
	private static final String PASSWORD_PATTERN = 
            "((?=.*\\d)(?=.*[!@#$%^&*]).{8,20})";
	
	public boolean validatorPassword(String password){
		return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
	}
}
