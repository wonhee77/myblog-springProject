package com.wonhee.myblog.service;

import com.sparta.springcore.security.kakao.KakaoUserInfo;
import com.wonhee.myblog.domain.User;
import com.wonhee.myblog.domain.UserRepository;
import com.wonhee.myblog.dto.SignupRequestDto;
import com.wonhee.myblog.security.kakao.KakaoOAuth2;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KakaoOAuth2 kakaoOAuth2;
    private final AuthenticationManager authenticationManager;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, KakaoOAuth2 kakaoOAuth2, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.kakaoOAuth2 = kakaoOAuth2;
        this.authenticationManager = authenticationManager;
    }


    public void kakaoLogin(String authorizedCode) {
        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(authorizedCode);
        Long kakaoId = userInfo.getId();
        String nickname = userInfo.getNickname();

        // 우리 DB 에서 회원 Id 와 패스워드
        // 회원 Id = 카카오 nickname
        String username = nickname;
        // 패스워드 = 카카오 Id + ADMIN TOKEN
        String password = kakaoId + ADMIN_TOKEN;

        // DB 에 중복된 Kakao Id 가 있는지 확인
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);

        // 카카오 정보로 회원가입
        if (kakaoUser == null) {
            // 패스워드 인코딩
            String encodedPassword = passwordEncoder.encode(password);

            kakaoUser = new User(nickname, encodedPassword, kakaoId);
            userRepository.save(kakaoUser);
        }

        // 로그인 처리
        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }







    public String registerUser(SignupRequestDto requestDto) {
        String errorMessage = "";
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String confirmPassword = requestDto.getConfirmPassword();

        Optional<User> foundUsername = userRepository.findByUsername(username);

        String pattern = "^[a-zA-Z0-9]*$";

        if (foundUsername.isPresent()){
            return "중복된 닉네임입니다.";
        }

        if (username.length() < 3) {
            return "닉네임을 3자 이상 입력해주세요.";
        } else if (!Pattern.matches(pattern,username)) {
            return "알파벳 대소문자(a~z, A~Z), 숫자(0~9)로만 입력해주세요.";
        }
        else if (!password.equals(confirmPassword)) {
            System.out.println("password = " + password);
            System.out.println("confirmPassword = " + confirmPassword);
            return "비밀번호가 일치하지 않습니다.";
        }
        else if (password.contains(username)) {
            return "비밀번호에 닉네임을 포함할 수 없습니다.";
        } else if (password.length() < 3) {
            return "비밀번호를 4자 이상으로 만들어주세요.";
        }

        String securedPassword = passwordEncoder.encode(password);
        requestDto.setPassword(securedPassword);

        User user = new User(requestDto);
        userRepository.save(user);

        return errorMessage;
    }
}






