package com.aloha.design.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class SignupController {

    @GetMapping("/signup")
    public String signup(Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupProcess(
            @RequestParam(required = false) MultipartFile profileImage,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String passwordConfirm,
            @RequestParam String nickname,
            @RequestParam int age,
            @RequestParam String gender,
            @RequestParam String address,
            @RequestParam(required = false) boolean termsAgree,
            @RequestParam(required = false) boolean privacyAgree,
            Model model) {
        
        // TODO: 회원가입 로직 구현
        // 예시: 유효성 검사, 비밀번호 암호화, DB 저장 등
        
        return "redirect:/login";
    }
}
