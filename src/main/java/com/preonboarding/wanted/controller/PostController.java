package com.preonboarding.wanted.controller;

import com.preonboarding.wanted.dto.request.SavePostRequest;
import com.preonboarding.wanted.dto.request.UpdatePostRequest;
import com.preonboarding.wanted.dto.response.DeletePostResponse;
import com.preonboarding.wanted.dto.response.GetPostResponse;
import com.preonboarding.wanted.dto.response.PagingPostResponse;
import com.preonboarding.wanted.dto.response.SavePostResponse;
import com.preonboarding.wanted.dto.response.UpdatePostResponse;
import com.preonboarding.wanted.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Api(value = "게시판 관리")
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    // 과제 3. 새로운 게시글을 생성하는 엔드포인트
    @ApiOperation(value = "게시글 작성")
    @PostMapping
    public ResponseEntity<SavePostResponse> savePost(
        @Valid @RequestBody SavePostRequest requestDto) {
        SavePostResponse response = postService.savePost(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 과제 4. 게시글 목록을 조회하는 엔드포인트
    @ApiOperation(value = "게시글 목록 조회")
    @GetMapping
    public ResponseEntity<List<PagingPostResponse>> selectPostList(@RequestParam(defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(defaultValue = "0", required = false) Integer page) throws Exception {

        Pageable paging  = PageRequest.of(page, pageSize);

        List<PagingPostResponse> PagingPostResponse = postService.selectPostList(paging);

        return new ResponseEntity<>(PagingPostResponse, HttpStatus.CREATED);
    }


    // 과제 5. 특정 게시글을 조회하는 엔드포인트
    @ApiOperation(value = "게시글 상세 조회")
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetPostResponse> getPost(
            @PathVariable("id") Long postId) {
        GetPostResponse response = postService.getPost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 과제 6. 특정 게시글을 수정하는 엔드포인트
    @ApiOperation(value = "게시글 수정")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<UpdatePostResponse> updatePost(
            @PathVariable("id") Long postId,
            @Valid @RequestBody UpdatePostRequest requestDto) {
        UpdatePostResponse response = postService.updatePost(postId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 과제 7. 특정 게시글을 삭제하는 엔드포인트
    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DeletePostResponse> deletePost(
            @PathVariable("id") Long postId) {
        DeletePostResponse response = postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
