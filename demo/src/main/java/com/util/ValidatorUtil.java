package com.util;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ValidatorUtil {
   
	private static final String PASSWORD_PATTERN = 
            "((?=.*\\d)(?=.*[!@#$%^&*]).{8,20})";
	
	private static final String EMAIL_REGEX = 
			"^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	private static final String WIPRO_EMAIL_REGEX = 
			"^[\\w-\\+]+(\\.[\\w]+)*@[\\\\wipro-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	

	public boolean validateWiproEmail(String email) {
		return Pattern.compile(WIPRO_EMAIL_REGEX,Pattern.CASE_INSENSITIVE).matcher(email).matches();
	}
	
	public boolean validatorPassword(String password){
		return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
	}
	
	
	public static boolean isConfirmationLinkRequestedAtleastTenMinutesAgo(Date date) throws IOException {
	    Instant instant = Instant.ofEpochMilli(date.getTime());
	    Instant tenMinutesAgo = Instant.now().minus(Duration.ofMinutes(1));
	    return instant.isBefore(tenMinutesAgo);
	}
}
