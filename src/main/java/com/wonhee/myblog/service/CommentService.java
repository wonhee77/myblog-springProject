package com.wonhee.myblog.service;

import com.wonhee.myblog.domain.Comment;
import com.wonhee.myblog.domain.CommentRepository;
import com.wonhee.myblog.domain.Post;
import com.wonhee.myblog.dto.CommentRequestDto;
import com.wonhee.myblog.dto.PostRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Long updateComment(Long id, CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("댓글 확인 필요")
        );
        comment.updateCommentByDto(commentRequestDto);
        return id;
    }
}
