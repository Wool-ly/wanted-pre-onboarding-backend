package com.preonboarding.wanted.exception;

public class CustomAuthenticationException extends RuntimeException {

    public CustomAuthenticationException() {
        super(ErrorCode.USER_AUTHENTICATION_FAILED.getMessage());
    }

    public CustomAuthenticationException(Exception ex) {
        super(ex);
    }
}