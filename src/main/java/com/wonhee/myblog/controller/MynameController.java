package com.wonhee.myblog.controller;

import com.wonhee.myblog.domain.Comment;
import com.wonhee.myblog.security.UserDetailsImpl;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MynameController {

    //접속한 사람 닉네임 가져오기
    @GetMapping( "api/myname")
    public String getMyname(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            return userDetails.getUser().getUsername();
            }
        return "";
    }
}
