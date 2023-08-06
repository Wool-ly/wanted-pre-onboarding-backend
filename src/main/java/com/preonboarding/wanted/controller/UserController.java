package com.preonboarding.wanted.controller;

import com.preonboarding.wanted.dto.request.LoginRequest;
import com.preonboarding.wanted.dto.request.SignUpRequest;
import com.preonboarding.wanted.dto.response.LoginResponse;
import com.preonboarding.wanted.dto.response.SignUpResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Api(value = "회원관리")
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    // 과제 1. 사용자 회원가입 엔드포인트
    @ApiOperation(value = "회원가입")
    @PostMapping(value = "/signUp")
    public ResponseEntity<SignUpResponse> userSignUp(
            @Valid @RequestBody SignUpRequest requestDto) {
        SignUpResponse response = userService.userSignUp(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //과제 2. 사용자 로그인 엔드포인트
    @ApiOperation(value = "로그인")
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> userLogin(
            @Valid @RequestBody LoginRequest requestDto){
        LoginResponse response = userService.userLogin(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
