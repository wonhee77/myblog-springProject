package com.wonhee.myblog.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String Password;
    private String ConfirmPassword;
}
