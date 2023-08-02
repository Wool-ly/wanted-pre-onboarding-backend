package com.preonboarding.wanted.controller;

import com.preonboarding.wanted.dto.request.LoginPostRequest;
import com.preonboarding.wanted.dto.request.SignUpPostRequest;
import com.preonboarding.wanted.dto.response.LoginPostResponse;
import com.preonboarding.wanted.dto.response.SignUpPostResponse;
import com.preonboarding.wanted.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Api(value = "회원관리")
public class UserController {

    private final UserService userService;

    // 과제 1. 사용자 회원가입 엔드포인트
    @ApiOperation(value = "회원가입")
    @PostMapping(value = "/api/v1/users/signUp")
    public ResponseEntity<SignUpPostResponse> userSignUp(
            @Valid @RequestBody SignUpPostRequest requestDto) {
        SignUpPostResponse response = userService.userSignUp(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //과제 2. 사용자 로그인 엔드포인트
    @ApiOperation(value = "로그인")
    @PostMapping(value = "/api/v1/users/login")
    public ResponseEntity<LoginPostResponse> userLogin(
            @Valid @RequestBody LoginPostRequest requestDto){
        LoginPostResponse response = userService.userLogin(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
