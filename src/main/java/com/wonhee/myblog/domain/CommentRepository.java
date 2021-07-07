package com.wonhee.myblog.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
//    List<Comment> findAllByOrderByFollowingCommentIdDesc(Long followingCommentId);
    List<Comment> findByFollowingCommentIdOrderByModifiedAtDesc(Long followingCommentId);

}