package com.preonboarding.wanted.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@ApiModel
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class LoginPostRequest {

    @NonNull
    @ApiModelProperty(name = "이메일", example = "test@gmail.com", required = true)
    private String email;

    @NonNull
    @ApiModelProperty(name = "비밀번호", example = "password123!", required = true)
    private String password;

}
