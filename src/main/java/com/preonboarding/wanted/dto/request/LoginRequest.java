package com.preonboarding.wanted.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@ApiModel
@NoArgsConstructor(force = true)
public class LoginRequest {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다. '@'를 포함하여 입력해주세요.")
    @ApiModelProperty(name = "이메일", example = "test@gmail.com", required = true)
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^.{8,}$", message = "비밀번호는 8자 이상이어야 합니다.")
    @ApiModelProperty(name = "비밀번호", example = "password123!", required = true)
    private String password;

}
