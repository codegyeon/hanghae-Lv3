package com.lv2.spartalv2hw.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+?/`]{8,15}$")
    private String password;


    //ADMIN
    private String adminToken = "";
}

// 정규식
//"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$"

//    @NotBlank(message = "Username is required")
//    @Size(min = 4, max = 10, message = "최소 4자 이상, 10자 이하")
//    @Pattern(regexp = "^[a-z0-9]+$", message = "알파벳 소문자(a~z), 숫자(0~9)")
//    private String username;
//
//    @NotBlank(message = "Password is required")
//    @Size(min = 8, max = 15, message = "최소 8자 이상, 15자 이하")
//    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+=-{}\\[\\]|;:'\",<.>/?]+$", message = "알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자")
//    private String password;