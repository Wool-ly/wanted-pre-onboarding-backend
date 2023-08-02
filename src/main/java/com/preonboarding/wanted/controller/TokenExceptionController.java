package com.preonboarding.wanted.controller;

import com.preonboarding.wanted.exception.CustomException;
import com.preonboarding.wanted.exception.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenExceptionController {

    @GetMapping("/exception/entrypoint")
    public void entryPoint() {
        throw new CustomException(ErrorCode.SERVER_ERROR);
    }

    @GetMapping("/exception/access")
    public void denied() {
        throw new CustomException(ErrorCode.NO_ADMIN);
    }

}
