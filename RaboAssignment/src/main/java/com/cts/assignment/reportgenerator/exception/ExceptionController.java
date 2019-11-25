package com.cts.assignment.reportgenerator.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(UnknownFileException.class)
	public ResponseEntity<Object> exception(UnknownFileException exception) {
		return new ResponseEntity<>("Unknown File Type", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionDetails> handleAllExceptions(Exception ex) {
		ExceptionDetails errorDetails = new ExceptionDetails(new Date(), ex.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}