package com.cct.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cct.beans.exceptions.*;;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler{

	public UserExceptionHandler() {
        super();
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "User not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    @ExceptionHandler(UserAlreadyExistException.class)
    protected ResponseEntity<Object> handleFound(Exception ex, WebRequest request) {
    	return handleExceptionInternal(ex, "User alread exists", new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(DataViolationException.class)
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex
          .getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
