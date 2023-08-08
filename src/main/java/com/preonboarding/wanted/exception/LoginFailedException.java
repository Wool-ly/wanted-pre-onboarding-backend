package com.preonboarding.wanted.exception;

public class LoginFailedException extends Throwable {

    public LoginFailedException() {
        super(ErrorCode.LOGIN_FAILED.getMessage());
    }

    private LoginFailedException(String msg) {
        super(msg);
    }
}