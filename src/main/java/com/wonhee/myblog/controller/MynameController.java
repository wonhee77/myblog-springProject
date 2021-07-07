package com.wonhee.myblog.controller;

import com.wonhee.myblog.domain.Comment;
import com.wonhee.myblog.security.UserDetailsImpl;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MynameController {

    @GetMapping( "api/myname")
    public String getMyname(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            return userDetails.getUser().getUsername();
            }
        return "";
    }
}
