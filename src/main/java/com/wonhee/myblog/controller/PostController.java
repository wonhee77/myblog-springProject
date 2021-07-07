package com.wonhee.myblog.controller;

import com.wonhee.myblog.domain.Post;
import com.wonhee.myblog.dto.PostRequestDto;
import com.wonhee.myblog.domain.PostRepository;
import com.wonhee.myblog.security.UserDetailsImpl;
import com.wonhee.myblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostRepository repository;
    private final PostService postService;

    @GetMapping("api/posts")
    public List<Post> readPosts(){
        return postService.getPosts();
    }

    @PostMapping("api/posts")
    public Post creatPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUser().getUsername();
        Post post = new Post(postRequestDto, username);
        repository.save(post);
        return post;
    }

    @GetMapping("api/posts/{id}")
    public Post readPost(@PathVariable Long id){
        Post post = repository.findById(id).orElseThrow(
                ()-> new NullPointerException("id를 찾을 수 없습니다.")
        );
        postService.increaseViews(id);
        return post;
    }

    @PutMapping("api/posts/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto){
        postService.update(id, postRequestDto);
        return id;
    }

    @DeleteMapping("api/posts/{id}")
    public Long deletePost(@PathVariable Long id){
        repository.deleteById(id);
        return id;
    }

}