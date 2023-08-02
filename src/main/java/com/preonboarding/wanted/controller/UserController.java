package com.preonboarding.wanted.controller;

import com.preonboarding.wanted.dto.request.LoginPostRequest;
import com.preonboarding.wanted.dto.request.SignUpPostRequest;
import com.preonboarding.wanted.dto.response.SignUpPostResponse;
import com.preonboarding.wanted.entity.UserRole;
import com.preonboarding.wanted.entity.User;
import com.preonboarding.wanted.security.JwtTokenProvider;
import com.preonboarding.wanted.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
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

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // 과제 1. 사용자 회원가입 엔드포인트
    @ApiOperation(value = "회원가입")
    @PostMapping(value = "/api/v1/users/signUp")
    public ResponseEntity<SignUpPostResponse> userSignUp(
            @RequestBody @Validated SignUpPostRequest requestDto) {

        SignUpPostResponse response = userService.userSignUp(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    // 과제 2. 사용자 로그인 엔드포인트
    @ApiOperation(value = "로그인")
    @PostMapping(value = "/api/v1/users/login")
    public String userLogin(LoginPostRequest requestDto, HttpServletResponse response){

        User user = userService.userLogin(requestDto);
        UserRole role = user.getRole();

        String token = jwtTokenProvider.createToken(user.getEmail(), role);
        response.setHeader("JWT", token);
        return token;
    }


}
