//package com.wonhee.myblog.domain;
//
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
//import com.wonhee.myblog.dto.PostRequestDto;
//import com.wonhee.myblog.util.Timestamped;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//
//@Getter
//@NoArgsConstructor
//public class PostPlusMyname extends Timestamped {
//
//    String title;
//
//    String content;
//
//    String username;
//
//    String myname;
//
//    LocalDateTime createdAt;
//
//
//    public PostPlusMyname(Post post, String myname){
//        this.content = post.getContent();
//        this.title = post.getTitle();
//        this.username = post.getUsername();
//        this.myname = myname;
//        this.createdAt = post.getCreatedAt();
//    }
//
//}
