package com.security.springsecurity.exception;

public class UserAlreadyExits extends RuntimeException {

    public UserAlreadyExits(String message) {
        super(message);
    }

}
