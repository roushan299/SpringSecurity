package com.security.springsecurity.exception;

public class UserDoesNotExists extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserDoesNotExists(String message) {
        super(message);
    }

}
