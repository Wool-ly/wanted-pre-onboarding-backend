package com.preonboarding.wanted.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@ApiModel
@NoArgsConstructor(force = true)
public class PagingPostRequest {

    @ApiModelProperty(name = "아이디", example = "1", required = true)
    private Long postId;

    @ApiModelProperty(name = "제목", example = "title", required = true)
    private String title;

    @ApiModelProperty(name = "내용", example = "content", required = true)
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedDt;
}
