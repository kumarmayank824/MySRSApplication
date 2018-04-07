package com.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    	
	@ExceptionHandler(value = MaxUploadSizeExceededException.class)
	public String handleFileUploadException(Long limit, Model model) {
		model.addAttribute("maxSizeError", "Bad Upload");
		return "upload";
	}

}
