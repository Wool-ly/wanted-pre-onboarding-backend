package com.preonboarding.wanted.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.preonboarding.wanted.entity.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class GetPostResponse {

    @JsonInclude(Include.NON_NULL)
    @ApiModelProperty(name = "아이디", example = "1", required = true)
    private Long postId;

    @JsonInclude(Include.NON_NULL)
    @ApiModelProperty(name = "제목", example = "title", required = true)
    private String title;

    @JsonInclude(Include.NON_NULL)
    @ApiModelProperty(name = "내용", example = "content", required = true)
    private String content;

    @JsonInclude(Include.NON_NULL)
    @ApiModelProperty(name = "이메일", example = "test@gmail.com", required = true)
    private String email;

    @JsonInclude(Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDt;

    @JsonInclude(Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedDt;

    @JsonInclude(Include.NON_NULL)
    @ApiModelProperty(name = "결과", required = true)
    private String result;


    public GetPostResponse(Post entity) {
        this.postId = entity.getPostId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.email = entity.getUser().getEmail();
        this.createdDt = entity.getCreatedDt();
        this.updatedDt = entity.getUpdatedDt();
    }
}
