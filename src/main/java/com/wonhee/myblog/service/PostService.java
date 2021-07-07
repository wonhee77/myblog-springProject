package com.wonhee.myblog.service;

import com.wonhee.myblog.domain.Post;
import com.wonhee.myblog.dto.PostRequestDto;
import com.wonhee.myblog.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    public List<Post> getPosts(){
        return repository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional
    public Long update(Long id, PostRequestDto postRequestDto){
        Post post = repository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디를 확인해주세요.")
        );
        post.updateByDto(postRequestDto);
        return id;
    }

    @Transactional
    public Long increaseViews(Long id){
        Post post = repository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디를 확인해주세요.")
        );
        int views = post.getViews();
        post.setViews(++views);
        return id;
    }
}
