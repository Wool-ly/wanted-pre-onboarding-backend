package com.preonboarding.wanted.security;

import com.preonboarding.wanted.exception.ErrorCode;
import com.preonboarding.wanted.exception.ErrorResponse;
import com.preonboarding.wanted.exception.ErrorResponse.FieldError;
import io.jsonwebtoken.JwtException;
import java.util.List;

public class TokenNotValidateException extends JwtException {

    public TokenNotValidateException(String message) {
        super(message);
    }

    public TokenNotValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}
