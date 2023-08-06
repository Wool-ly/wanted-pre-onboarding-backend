package com.preonboarding.wanted.repository;

import com.preonboarding.wanted.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<List<Post>> findAllByPostId(String postId, Pageable page);
}
