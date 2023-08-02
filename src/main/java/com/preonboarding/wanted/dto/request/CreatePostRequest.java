package com.preonboarding.wanted.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@ApiModel
@NoArgsConstructor(force = true)
public class CreatePostRequest {

    @NotBlank(message = "제목은 필수 입력값입니다.")
    @ApiModelProperty(name = "제목", example = "title", required = true)
    private String title;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    @ApiModelProperty(name = "내용", example = "content", required = true)
    private String content;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다. '@'를 포함하여 입력해주세요.")
    @ApiModelProperty(name = "이메일", example = "test@gmail.com", required = true)
    private String email;

}
