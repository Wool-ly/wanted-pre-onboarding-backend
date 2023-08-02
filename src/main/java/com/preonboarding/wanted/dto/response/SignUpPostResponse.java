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
public class SignUpPostResponse {

    @ApiModelProperty(name = "결과", example = "회원가입이 완료되었습니다.", required = true)
    private String result;

    @ApiModelProperty(name = "이메일", example = "test@gmail.com", required = true)
    private String email;

}
