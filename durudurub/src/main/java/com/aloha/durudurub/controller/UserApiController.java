package com.aloha.durudurub.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 회원 API 컨트롤러 (Ajax)
 */
@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserApiController {

    @Autowired
    private UserService userService;

    /**
     * 이메일(user_id) 중복 체크
     * /api/users/check-userid?userId=...
     */
    @GetMapping("/check-userid")
    public ResponseEntity<?> checkUserId(@RequestParam("userId") String userId) {
        try {
            if (userId == null || userId.isBlank()) {
                return new ResponseEntity<>(Map.of("ok", false, "message", "이메일을 입력하세요."), HttpStatus.BAD_REQUEST);
            }

            boolean duplicated = userService.existsByUserId(userId);
            if (duplicated) {
                return new ResponseEntity<>(Map.of("ok", false, "message", "이미 사용 중인 이메일입니다."), HttpStatus.OK);
            }
            return new ResponseEntity<>(Map.of("ok", true, "message", "사용 가능한 이메일입니다."), HttpStatus.OK);

        } catch (Exception e) {
            log.error("이메일 중복 체크 실패", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 닉네임(username) 중복 체크
     * /api/users/check-username?username=...
     */
    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestParam("username") String username) {
        try {
            if (username == null || username.isBlank()) {
                return new ResponseEntity<>(Map.of("ok", false, "message", "닉네임을 입력하세요."), HttpStatus.BAD_REQUEST);
            }

            boolean duplicated = userService.existsByUsername(username);
            if (duplicated) {
                return new ResponseEntity<>(Map.of("ok", false, "message", "이미 사용 중인 닉네임입니다."), HttpStatus.OK);
            }
            return new ResponseEntity<>(Map.of("ok", true, "message", "사용 가능한 닉네임입니다."), HttpStatus.OK);

        } catch (Exception e) {
            log.error("닉네임 중복 체크 실패", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원가입 (AJAX)
     * - JSON으로 User 받음
     * - 성공 시 "SUCCESS" 반환
     * /api/users/join
     */
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody User user) {
        try {
            int userNo = userService.insert(user); // 성공 시 userNo 리턴하도록 구현되어 있어야 함

            if (userNo <= 0) {
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // 유효성/중복 등 사용자 입력 문제는 400으로 메시지 반환
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("회원가입 실패", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
