package com.preonboarding.wanted.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class PagingPostResponse {

    @ApiModelProperty(name = "아이디", example = "1", required = true)
    private Long postId;

    @ApiModelProperty(name = "제목", example = "title", required = true)
    private String title;

    @ApiModelProperty(name = "내용", example = "content", required = true)
    private String content;

    @ApiModelProperty(name = "이메일", example = "test@gmail.com", required = true)
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedDt;

    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

}
