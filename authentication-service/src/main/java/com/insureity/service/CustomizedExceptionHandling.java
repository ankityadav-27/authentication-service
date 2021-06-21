package com.insureity.service;

import java.time.LocalDateTime;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.insureity.model.MessageResponse;

import lombok.extern.slf4j.Slf4j;

/** Service class */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomizedExceptionHandling extends ResponseEntityExceptionHandler {

	
	/**
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Object> handleUnauthorizedExceptions1(UsernameNotFoundException ex) {

		log.error("User Not Found exception!!");
		return ResponseEntity.badRequest()
				.body(new MessageResponse("User Not Found exception!! Login again...", LocalDateTime.now()));
	}

	

}
