package com.preonboarding.wanted.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@ApiModel
@NoArgsConstructor(force = true)
public class SavePostRequest {

    @NotBlank(message = "제목은 필수 입력값입니다.")
    @ApiModelProperty(name = "제목", example = "title", required = true)
    private String title;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    @ApiModelProperty(name = "내용", example = "content", required = true)
    private String content;

}
