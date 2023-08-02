package com.preonboarding.wanted.service;

import com.preonboarding.wanted.dto.request.CreatePostRequest;
import com.preonboarding.wanted.dto.response.CreatePostResponse;
import com.preonboarding.wanted.dto.response.ReadGetResponse;
import com.preonboarding.wanted.entity.Post;
import com.preonboarding.wanted.entity.User;
import com.preonboarding.wanted.repository.PostRepository;
import com.preonboarding.wanted.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public CreatePostResponse createPost(CreatePostRequest requestDto) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;

        String username = ((UserDetails) principal).getUsername();
        Optional<User> email = userRepository.findByEmail(username);

        User user = new User();
        user.setUserId(email.get().getUserId());

        Post post = new Post();
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());
        post.setUser(user);

        postRepository.save(post);

        return CreatePostResponse
                .builder()
                .result("게시글 작성이 완료되었습니다.")
                .build();
    }

    public List<Post> selectPostList() {
        return postRepository.findAll();
    }
}
