package com.ohalo.user.exceptions;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ohalo.user.rest.responses.AppFailureResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class, RuntimeException.class,
			Exception.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		ex.printStackTrace();
		AppFailureResponse bodyOfResponse = new AppFailureResponse(ex.getMessage(),
				"A problem was encountered while processing your request");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	@ExceptionHandler(value = { EntityNotFoundException.class, NoSuchElementException.class })
	protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
		ex.printStackTrace();
		AppFailureResponse bodyOfResponse = new AppFailureResponse(ex.getMessage(), "Record not found");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String errorMessage = ex.getBindingResult().getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().orElse(ex.getMessage());
		AppFailureResponse bodyOfResponse = new AppFailureResponse(errorMessage, errorMessage);
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
}
