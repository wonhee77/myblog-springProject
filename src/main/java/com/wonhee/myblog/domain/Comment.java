package com.wonhee.myblog.domain;

import com.wonhee.myblog.dto.CommentRequestDto;
import com.wonhee.myblog.dto.PostRequestDto;
import com.wonhee.myblog.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long commentId;

    @Column
    private String commentContent;

    @Column
    private Long followingCommentId;

    @Column
    private String username;

    public Comment(CommentRequestDto commentRequestDto, String username) {
        this.followingCommentId = commentRequestDto.getFollowingCommentId();
        this.commentContent = commentRequestDto.getCommentContent();
        this.username = username;
    }

    public void updateCommentByDto(CommentRequestDto commentRequestDto){
        this.commentContent = commentRequestDto.getCommentContent();
    }
}
