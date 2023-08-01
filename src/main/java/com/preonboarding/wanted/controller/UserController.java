package com.preonboarding.wanted.controller;

import com.preonboarding.wanted.dto.request.SignUpPostRequest;
import com.preonboarding.wanted.dto.response.SignUpPostResponse;
import com.preonboarding.wanted.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Api(value = "회원관리")
public class UserController {

    private UserService userService;

    // 과제 1. 사용자 회원가입 엔드포인트
    @ApiOperation(value = "회원가입")
    @PostMapping(value = "/api/v1/users/signUp")
    public ResponseEntity<SignUpPostResponse> signUp(
            @RequestBody @Validated SignUpPostRequest requestDto) {

        SignUpPostResponse response = userService.signUp(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
