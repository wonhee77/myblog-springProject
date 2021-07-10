package com.wonhee.myblog.controller;

import com.wonhee.myblog.domain.Comment;
import com.wonhee.myblog.domain.CommentRepository;
import com.wonhee.myblog.dto.CommentRequestDto;
import com.wonhee.myblog.dto.PostRequestDto;
import com.wonhee.myblog.security.UserDetailsImpl;
import com.wonhee.myblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    //댓글 저장
    @PostMapping("/api/comments")
    public String createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username;
        if (userDetails != null) {
            username = userDetails.getUser().getUsername();
            Comment comment = new Comment(commentRequestDto, username);
            commentRepository.save(comment);
            return "success";
        }
        return "fail";
    }

    //모든 댓글 읽기
    @GetMapping("api/comments/{id}")
    public List<Comment> readComment(@PathVariable Long id) {
        List<Comment> comments = commentRepository.findByFollowingCommentIdOrderByModifiedAtDesc(id);
        return comments;
    }

    //댓글 내용 가져오기
    @GetMapping("api/comments/name/{id}")
    public String readCommentName(@PathVariable Long id) {
        return commentRepository.findById(id).get().getCommentContent();
    }

    //댓글 수정
    @PutMapping("api/comments/{commentId}")
    public Long updatePost(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto){
        commentService.updateComment(commentId, commentRequestDto);
        return commentId;
    }

    //댓글 지우기
    @DeleteMapping("api/comments/{id}")
    public void deleteComment(@PathVariable Long id){
        commentRepository.deleteById(id);
    }
}
