package com.preonboarding.wanted.controller;

import com.preonboarding.wanted.dto.request.CreatePostRequest;
import com.preonboarding.wanted.dto.response.CreatePostResponse;
import com.preonboarding.wanted.entity.Post;
import com.preonboarding.wanted.service.PostService;
import com.preonboarding.wanted.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Api(value = "게시판 관리")
@RequestMapping("/api/v1/posts")
public class PostController {

    private final UserService userService;
    private final PostService postService;

    // 과제 3. 새로운 게시글을 생성하는 엔드포인트
    @ApiOperation(value = "게시글 작성")
    @PostMapping(value = "/write")
    public ResponseEntity<CreatePostResponse> writePost(
            @Valid @RequestBody CreatePostRequest requestDto) {
        CreatePostResponse response = postService.createPost(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 과제 4. 게시글 목록을 조회하는 엔드포인트
    @ApiOperation(value = "게시글 목록 조회")
    @GetMapping
    public List<Post> postList() {
        return postService.selectPostList();
    }

}
