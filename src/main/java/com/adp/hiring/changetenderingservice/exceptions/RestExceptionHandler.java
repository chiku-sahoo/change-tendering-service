package com.adp.hiring.changetenderingservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.adp.hiring.changetenderingservice.models.ErrorResponse;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(InvalidBillValueException.class)
	public ResponseEntity<ErrorResponse> handleInvalidBill(RuntimeException e) {
		var errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(InsufficientChangeException.class)
	public ResponseEntity<ErrorResponse> handleInsufficientChangeException(RuntimeException e) {
		var errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage());
		return ResponseEntity.unprocessableEntity().body(errorResponse);
	}

	@ExceptionHandler(InvalidDenominationException.class)
	public ResponseEntity<ErrorResponse> handleInvalidDenominationException(RuntimeException e) {
		var errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.badRequest().body(errorResponse);
	}

}
