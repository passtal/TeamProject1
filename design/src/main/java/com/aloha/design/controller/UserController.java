package com.aloha.design.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {

    /**
     * 회원가입 페이지
     */
    @GetMapping("/join")
    public String join(Model model) {
        return "join";
    }

    /**
     * 회원가입 처리
     */
    @PostMapping("/join")
    public String joinProcess(
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

    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    /**
     * 로그인 처리
     */
    @PostMapping("/login")
    public String loginProcess(
            @RequestParam String email,
            @RequestParam String password,
            Model model) {
        
        // TODO: 로그인 로직 구현
        
        return "redirect:/";
    }

    /**
     * 로그아웃 처리
     */
    @GetMapping("/logout")
    public String logout() {
        // TODO: 로그아웃 로직 구현
        return "redirect:/";
    }

    /**
     * 비밀번호 찾기 페이지
     */
    @GetMapping("/find-password")
    public String findPassword(Model model) {
        return "find-password";
    }

    /**
     * 비밀번호 찾기 처리
     */
    @PostMapping("/find-password")
    public String findPasswordProcess(
            @RequestParam String email,
            Model model) {
        
        // TODO: 비밀번호 찾기 로직 구현
        // 예시: 이메일 검증, 임시 토큰 생성, 이메일 발송 등
        
        // 성공 시 이메일 정보와 메시지를 모델에 추가
        model.addAttribute("email", email);
        model.addAttribute("message", "비밀번호 재설정 링크가 이메일로 전송되었습니다.");
        return "find-password";
    }
}
