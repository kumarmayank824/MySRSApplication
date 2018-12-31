package com.util;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ValidatorUtil {
     
	
	/*^               # start-of-string
	(?=.*[0-9])       # a digit must occur at least once
	(?=.*[a-z])       # a lower case letter must occur at least once
	(?=.*[A-Z])       # an upper case letter must occur at least once
	(?=.*[@#$%^&+=])  # a special character must occur at least once
	(?=\S+$)          # no whitespace allowed in the entire string
	.{8,}             # anything, at least eight places though
	$                 # end-of-string*/
	private static final String PASSWORD_PATTERN = 
            "^(?=.*[0-9])(?=.*[@#$%^&])(?=\\S+$).{8,}$";
	
	
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
	
	
	public static boolean isConfirmationLinkRequestedAtleastFifteenMinutesAgo(Date date) throws IOException {
	    Instant instant = Instant.ofEpochMilli(date.getTime());
	    Instant fifteenMinutesAgo = Instant.now().minus(Duration.ofMinutes(120));
	    return instant.isBefore(fifteenMinutesAgo);
	}

	public Map<String,Object> validateStudentParamter(String semester, String batch, String course) {
		
		String errorMessage = "";
		String missingFieldMessage = ""; 
	    String wrongValueMessage = "";
		String initialMissingFieldMessage = "<b>Neccessary information missing:</b><br/><br/>";
		String initialWrongValueMessage = "<b>Please provide correct values for following field(s):</b><br/><br/>";
		String [] semesterLst = new String[] {"V","VI","VII"};
		String [] batchLst = new String[] {"Batch 1","Batch 2","Batch 3","Batch 4","Batch 5",
				                                   "Batch 6","Batch 7","Batch 8","Batch 9","Batch 10"};
		String [] courseLst = new String[] {"M.Tech (IT)","M.Tech (VLSI)","M.Tech (EDS)"};
		
		Map<String,Object> errorMap = new HashMap<>();
		errorMap.put("isSemsterError", false);
		errorMap.put("isBatachError", false);
		errorMap.put("isCourseError", false);
		if (null == semester || semester.isEmpty()) {
			missingFieldMessage += "Semester<br/>";
			errorMap.put("isSemsterError", true);
		}else {
			if (!Arrays.asList(semesterLst).contains(semester) ) {
				wrongValueMessage += "Semester<br/>";
				errorMap.put("isSemsterError", true);
			}
		}
		if (null == batch || batch.isEmpty()) {
			missingFieldMessage += "Batch<br/>";
			errorMap.put("isBatachError", true);
		}else {
			if (!Arrays.asList(batchLst).contains(batch) ) {
				wrongValueMessage += "Batch<br/>";
				errorMap.put("isBatachError", true);
			}
		}
		if (null == course || course.isEmpty()) {
			missingFieldMessage += "Course<br/>";
			errorMap.put("isCourseError", true);
		}else {
			if (!Arrays.asList(courseLst).contains(course) ) {
				wrongValueMessage += "Course<br/>";
				errorMap.put("isCourseError", true);
			}
		}
		
		if( !missingFieldMessage.isEmpty() ) {
			errorMessage +=  initialMissingFieldMessage + missingFieldMessage;
		}
		if( !wrongValueMessage.isEmpty() ) {
			errorMessage += initialWrongValueMessage + wrongValueMessage;
		}
		errorMap.put("errorMessage", errorMessage);
		
		return errorMap;
	}
}
