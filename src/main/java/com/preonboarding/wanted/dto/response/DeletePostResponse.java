package com.preonboarding.wanted.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class DeletePostResponse {

    @ApiModelProperty(name = "결과", example = "게시글 삭제가 완료되었습니다.", required = true)
    private String result;

}
