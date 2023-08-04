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
import com.preonboarding.wanted.repository.PostRepository;
import com.preonboarding.wanted.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Transactional
    public PagingPostResponse selectPostList(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();

        List<Post> content= listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PagingPostResponse postResponse = new PagingPostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    private Post mapToDto(Post post) {
        Post postDto = new Post();
        postDto.setPostId(post.getPostId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());

        return postDto;
    }

    @Transactional
    public UpdatePostResponse updatePost(Long postId, UpdatePostRequest requestDto) {

        Optional<Post> byId = postRepository.findById(postId);
        Post post = byId.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        return UpdatePostResponse
                .builder()
                .result("게시글 수정이 완료되었습니다.")
                .build();
    }

    @Transactional
    public DeletePostResponse deletePost(Long postId) {

        Optional<Post> byId = postRepository.findById(postId);
        Post post = byId.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        postRepository.delete(post);

        return DeletePostResponse
                .builder()
                .result("게시글 삭제가 완료되었습니다.")
                .build();
    }

    public GetPostResponse getPost(Long postId) {

        Post entity = postRepository.findById(postId).orElseThrow(()
        -> new IllegalArgumentException("해당 게시글이 없습니다. id =" + postId));

        return new GetPostResponse(entity);
    }

}
