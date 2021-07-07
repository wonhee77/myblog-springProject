package com.wonhee.myblog.domain;

import com.wonhee.myblog.dto.PostRequestDto;
import com.wonhee.myblog.util.Timestamped;
import com.wonhee.myblog.util.TimestampedCreatedOnly;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends TimestampedCreatedOnly {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    String content;

    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    int views;

    public Post(PostRequestDto postRequestDto, String username){
        this.content = postRequestDto.getContent();
        this.title = postRequestDto.getTitle();
        this.username = username;
    }

    public void updateByDto(PostRequestDto postRequestDto){
        this.content = postRequestDto.getContent();
        this.title = postRequestDto.getTitle();
    }

}
