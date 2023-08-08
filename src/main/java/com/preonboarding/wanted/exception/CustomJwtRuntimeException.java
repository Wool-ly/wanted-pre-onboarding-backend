package com.preonboarding.wanted.exception;

public class CustomJwtRuntimeException extends RuntimeException {

    public CustomJwtRuntimeException() {
        super(ErrorCode.USER_AUTHENTICATION_FAILED.getMessage());
    }

    public CustomJwtRuntimeException(Exception ex) {
        super(ex);
    }

}
