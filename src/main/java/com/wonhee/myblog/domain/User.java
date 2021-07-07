package com.wonhee.myblog.domain;

import com.wonhee.myblog.dto.SignupRequestDto;
import com.wonhee.myblog.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private Long kakaoId;


    public User(SignupRequestDto requestDto) {
        String name = requestDto.getUsername();
        String password = requestDto.getPassword();
        this.username = name;
        this.password = password;
    }

    public User(String username, String password, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.kakaoId = kakaoId;
    }
}
