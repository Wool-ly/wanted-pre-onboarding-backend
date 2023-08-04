package com.preonboarding.wanted.service;

import com.preonboarding.wanted.dto.request.SavePostRequest;
import com.preonboarding.wanted.dto.request.UpdatePostRequest;
import com.preonboarding.wanted.dto.response.GetPostResponse;
import com.preonboarding.wanted.dto.response.PagingPostResponse;
import com.preonboarding.wanted.dto.response.SavePostResponse;
import com.preonboarding.wanted.dto.response.UpdatePostResponse;
import com.preonboarding.wanted.entity.Post;
import com.preonboarding.wanted.entity.User;
import com.preonboarding.wanted.repository.PostRepository;
import com.preonboarding.wanted.repository.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = ((UserDetails) principal).getUsername();
        Optional<User> userEntity = userRepository.findByEmail(userEmail);

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
    }

    @Transactional(readOnly = true)
    public List<PagingPostResponse> selectPostList(Pageable paging) {

        Iterable<Post> posts = postRepository.findAll(paging);
        List<PagingPostResponse> postResponses = new ArrayList<>();

        posts.forEach(post -> postResponses.add(
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
        return postResponses;
    }

    @Transactional
    public UpdatePostResponse updatePost(Long postId, UpdatePostRequest requestDto, Principal principal) {

        Optional<Post> findPost = postRepository.findById(postId);

        if(findPost.get().getUser().getEmail().equals(principal.getName())) {

            Post post = findPost.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
            post.setTitle(requestDto.getTitle());
            post.setContent(requestDto.getContent());

            return UpdatePostResponse
                    .builder()
                    .result("게시글 수정이 완료되었습니다.")
                    .build();
        } else  {
            return UpdatePostResponse
                    .builder()
                    .result("게시글 수정 권한이 없습니다.")
                    .build();
        }

    }

    @Transactional
    public ResponseEntity<?> deletePost(Long postId, Principal principal) {

        Optional<Post> findPost = postRepository.findById(postId);

        if(findPost.get().getUser().getEmail().equals(principal.getName())) {

            System.out.println("findPost.get().getUser().getEmail() = " + findPost.get().getUser().getEmail());
            System.out.println("principal.getName() = " + principal.getName());

            Post post = findPost.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
            postRepository.delete(post);

            return ResponseEntity.ok("게시글이 정상적으로 삭제되었습니다.");
        } else  {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글 삭제 권한이 없습니다.");
        }

    }

    @Transactional(readOnly = true)
    public GetPostResponse getPost(Long postId) {

        Post entity = postRepository.findById(postId).orElseThrow(()
        -> new IllegalArgumentException("해당 게시글이 없습니다. id =" + postId));

        return new GetPostResponse(entity);
    }

}
