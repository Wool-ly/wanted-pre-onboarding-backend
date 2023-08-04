package com.preonboarding.wanted.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    @ApiModelProperty(name = "결과", example = "로그인 되었습니다.", required = true)
    private String result;

    @ApiModelProperty(name = "이메일", example = "test@gmail.com", required = true)
    private String email;

    @ApiModelProperty(name = "Grant 타입", example = "Bearer", required = true)
    private String grantType;

    @ApiModelProperty(name = "JWT 토큰", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9", required = true)
    private String accessToken;




}
