package com.aloha.durudurub.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.service.UserService;



/**
 * 회원 컨트롤러
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 회원가입 페이지
     * @return
     */
    @GetMapping("/join")
    public String joinPage() {
        return "user/join";
    }

    /**
     * 이메일(user_id) 중복체크 (AJAX)
     * @param userId
     * @return
     */
    @ResponseBody
    @GetMapping("/check-userid")
    public Map<String, Object> checkUserId(@RequestParam("userId") String userId) {
        if (userId == null || userId.isBlank()) {
            return Map.of("ok", false, "message", "이메일을 입력하세요.");
        }

        boolean duplicated = userService.existsUserId(userId);
        if (duplicated) {
            return Map.of("ok", false, "message", "이미 사용 중인 이메일입니다.");
        }
        return Map.of("ok", true, "message", "사용 가능한 이메일입니다.");
    }

    /**
     * 닉네임(username) 중복체크 (AJAX)
     * @param username
     * @return
     */
    @ResponseBody
    @GetMapping("/check-username")
    public Map<String, Object> checkUsername(@RequestParam("username") String username) {
        if (username == null || username.isBlank()) {
            return Map.of("ok", false, "message", "닉네임을 입력하세요.");
        }

        boolean duplicated = userService.existsUsername(username);
        if (duplicated) {
            return Map.of("ok", false, "message", "이미 사용 중인 닉네임입니다.");
        }
        return Map.of("ok", true, "message", "사용 가능한 닉네임입니다.");
    }

    /**
     * 회원가입 처리 
     * @param user
     * @return
     */
    @PostMapping("/join")
    public String join(User user, Model model) {
        try {
            userService.signup(user);
            return "redirect:/login";  
        } catch (IllegalArgumentException e) {
            model.addAttribute("error",e.getMessage());
            model.addAttribute("user",user);
            return "user/join";
        }
    }
    

}

