package com.wonhee.myblog.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String username;
    private String content;


    public PostRequestDto(String title, String username, String content) {
        this.title = title;
        this.username = username;
        this.content = content;
    }
}
