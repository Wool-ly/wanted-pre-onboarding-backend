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
public class UpdatePostResponse {

    @ApiModelProperty(name = "결과", example = "게시글 수정이 완료되었습니다.", required = true)
    private String result;

}
