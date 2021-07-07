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

    @GetMapping("api/comments/{id}")
    public List<Comment> readComment(@PathVariable Long id) {
        List<Comment> comments = commentRepository.findByFollowingCommentIdOrderByModifiedAtDesc(id);
        return comments;
    }

    @GetMapping("api/comments/name/{id}")
    public String readCommentName(@PathVariable Long id) {
        return commentRepository.findById(id).get().getCommentContent();
    }

    @PutMapping("api/comments/{commentId}")
    public Long updatePost(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto){
        commentService.updateComment(commentId, commentRequestDto);
        return commentId;
    }


    @DeleteMapping("api/comments/{id}")
    public void deleteComment(@PathVariable Long id){
        commentRepository.deleteById(id);
    }
}
