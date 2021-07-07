package com.wonhee.myblog.controller;

import com.wonhee.myblog.dto.SignupRequestDto;
import com.wonhee.myblog.security.UserDetailsImpl;
import com.wonhee.myblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 로그인 에러 처리
    @GetMapping("/user/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto, Model model) {
        if (userService.registerUser(requestDto) == "") {
            userService.registerUser(requestDto);
            return "login";
        } else {
            model.addAttribute("er" +
                    "rorMessage", userService.registerUser(requestDto));
            return "signup";
        }
    }

    // 로그인 페이지 이동
    @GetMapping("/user/login")
    public String logIn() {
        return "login";
    }

    // 카카오 callback
    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(String code) {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        userService.kakaoLogin(code);

        return "redirect:/";
    }
}
