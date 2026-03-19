package com.security.springsecurity.exception.handler;

import com.security.springsecurity.exception.ErrorResponse;
import com.security.springsecurity.exception.UserAlreadyExits;
import com.security.springsecurity.exception.UserDoesNotExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserDoesNotExists.class)
    public ResponseEntity<ErrorResponse> handleUserDoesNotExists(UserDoesNotExists userDoesNotExists) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, userDoesNotExists.getMessage());
    }


    @ExceptionHandler(UserAlreadyExits.class)
    public ResponseEntity<ErrorResponse> UserAlreadyExits(UserAlreadyExits userAlreadyExits) {
        return buildErrorResponse(HttpStatus.CONFLICT, userAlreadyExits.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException badCredentialsException) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, badCredentialsException.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        ErrorResponse error = new ErrorResponse(status, message);
        return new ResponseEntity<>(error, status);
    }

}
