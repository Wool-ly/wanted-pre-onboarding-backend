package com.preonboarding.wanted.service;

import com.preonboarding.wanted.dto.request.SavePostRequest;
import com.preonboarding.wanted.dto.request.UpdatePostRequest;
import com.preonboarding.wanted.dto.response.DeletePostResponse;
import com.preonboarding.wanted.dto.response.GetPostResponse;
import com.preonboarding.wanted.dto.response.PagingPostResponse;
import com.preonboarding.wanted.dto.response.SavePostResponse;
import com.preonboarding.wanted.dto.response.UpdatePostResponse;
import com.preonboarding.wanted.entity.Post;
import com.preonboarding.wanted.entity.User;
import com.preonboarding.wanted.exception.CustomException;
import com.preonboarding.wanted.exception.ErrorCode;
import com.preonboarding.wanted.repository.PostRepository;
import com.preonboarding.wanted.repository.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public SavePostResponse savePost(SavePostRequest requestDto) {

        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            if (!(principal instanceof UserDetails)) {
                return SavePostResponse.builder()
                        .result("인증되지 않은 사용자입니다.")
                        .build();
            }
            String userEmail = ((UserDetails) principal).getUsername();
            Optional<User> userEntity = userRepository.findByEmail(userEmail);

            if (userEntity.isEmpty()) {
                return SavePostResponse.builder()
                        .result("사용자 정보를 찾을 수 없습니다.")
                        .build();
            }

            User user = new User();
            user.setUserId(userEntity.get().getUserId());

            Post post = new Post();
            post.setTitle(requestDto.getTitle());
            post.setContent(requestDto.getContent());
            post.setUser(user);

            postRepository.save(post);

            return SavePostResponse
                    .builder()
                    .result("게시글 작성이 완료되었습니다.")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return SavePostResponse.builder()
                    .result("게시글 작성 중 오류가 발생했습니다.")
                    .build();
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Page<PagingPostResponse>> getPostList(Pageable paging) {

        try {
            Page<Post> postsPage = postRepository.findAll(paging);
            List<PagingPostResponse> postResponses = new ArrayList<>();

            postsPage.getContent().forEach(post -> postResponses.add(
                    PagingPostResponse
                            .builder()
                            .postId(post.getPostId())
                            .email(post.getUser().getEmail())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .createdDt(post.getCreatedDt())
                            .updatedDt(post.getUpdatedDt())
                            .build())
            );

            if (postResponses.isEmpty()) {
                // content가 비어있는 경우 메시지만 반환
                return ResponseEntity.ok(new PageImpl<>(Collections.emptyList(), paging, 0));
            }

            return ResponseEntity.ok(
                    new PageImpl<>(postResponses, paging, postsPage.getTotalElements()));
        } catch (CustomException e) {
            log.error("게시글 목록을 조회할 때 오류가 발생했습니다.", e);
            throw new CustomException(ErrorCode.POST_LIST_FETCH_ERROR);
        }
    }

    @Transactional
    public UpdatePostResponse updatePost(Long postId, UpdatePostRequest requestDto,
            Principal principal) {
        try {
            Optional<Post> findPost = postRepository.findById(postId);

            if (findPost.isEmpty()) {
                throw new CustomException(ErrorCode.POST_NOT_FOUND);
            }

            Post post = findPost.get();

            if (!post.getUser().getEmail().equals(principal.getName())) {
                throw new CustomException(ErrorCode.UNAUTHORIZED_UPDATE);
            }

            post.setTitle(requestDto.getTitle());
            post.setContent(requestDto.getContent());

            return UpdatePostResponse.builder()
                    .result("게시글 수정이 완료되었습니다.")
                    .build();
        } catch (CustomException e) {
            return UpdatePostResponse
                    .builder()
                    .result(e.getErrorCode().getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return UpdatePostResponse.builder()
                    .result("게시글 수정 중 오류가 발생했습니다.")
                    .build();
        }
    }

    @Transactional
    public DeletePostResponse deletePost(Long postId, Principal principal) {
        try {
            Optional<Post> findPost = postRepository.findById(postId);

            if (findPost.isEmpty()) {
                throw new CustomException(ErrorCode.POST_NOT_FOUND);
            }

            Post post = findPost.get();

            if (!post.getUser().getEmail().equals(principal.getName())) {
                throw new CustomException(ErrorCode.UNAUTHORIZED_DELETE);
            }

            postRepository.delete(post);

            return DeletePostResponse.builder()
                    .result("게시글 삭제가 완료되었습니다.")
                    .build();
        } catch (CustomException e) {
            return DeletePostResponse
                    .builder()
                    .result(e.getErrorCode().getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return DeletePostResponse
                    .builder()
                    .result("게시글 삭제 중 오류가 발생했습니다.")
                    .build();
        }
    }

    @Transactional(readOnly = true)
    public GetPostResponse getPost(Long postId) {
        try {
            Post entity = postRepository.findById(postId)
                    .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

            return new GetPostResponse(entity);
        } catch (CustomException e) {
            return GetPostResponse
                    .builder()
                    .result(e.getErrorCode().getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return GetPostResponse
                    .builder()
                    .result("게시글 조회 중 오류가 발생했습니다.")
                    .build();
        }
    }

}
