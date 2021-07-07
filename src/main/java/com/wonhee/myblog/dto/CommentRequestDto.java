package com.wonhee.myblog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    String commentContent;
    long followingCommentId;

}
