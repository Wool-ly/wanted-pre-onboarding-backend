package com.preonboarding.wanted.dto.response;

import com.preonboarding.wanted.entity.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class PagingPostResponse {

    @ApiModelProperty(name = "아이디", example = "1", required = true)
    private Long postId;

    @ApiModelProperty(name = "제목", example = "title", required = true)
    private String title;

    @ApiModelProperty(name = "내용", example = "content", required = true)
    private List<Post> content;

    @ApiModelProperty(name = "이메일", example = "test@gmail.com", required = true)
    private String email;

    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;

    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
