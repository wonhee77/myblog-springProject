package com.wonhee.myblog.service;

import com.wonhee.myblog.domain.UserRepository;
import com.wonhee.myblog.dto.SignupRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
class UserServiceTest {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserServiceTest(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @Test
    public String joinTest(){
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("원희");
        signupRequestDto.setPassword("1234");
        signupRequestDto.setConfirmPassword("1234");
        return userService.registerUser(signupRequestDto);
    }

}