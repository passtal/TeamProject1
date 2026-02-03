package com.aloha.durudurub.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
     * 회원가입 (AJAX) - multipart (프로필 사진 포함)
     * /api/users/join
     */
    @PostMapping(value="/join", consumes = "multipart/form-data")
    public ResponseEntity<?> join(
            @RequestParam("userId") String userId,
            @RequestParam("password") String password,
            @RequestParam("username") String username,
            @RequestParam(value = "age", required = false, defaultValue = "0") int age,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "address", required = false) String address,
            @RequestPart(value = "profileImgFile", required = false) MultipartFile profileImgFile
    ) {
        try {
            // ✅ 기존 User DTO로 묶어서 서비스로 넘김 (폴더 추가 없이)
            User user = new User();
            user.setUserId(userId);
            user.setPassword(password);
            user.setUsername(username);
            user.setAge(age);
            user.setGender(gender);
            user.setAddress(address);

            // ✅ 서비스에 파일까지 넘기는 메서드가 필요
            // 1) userService.insert(user, profileImgFile) 로 오버로딩 추천
            int userNo = userService.insert(user, profileImgFile);

            if (userNo <= 0) {
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("회원가입 실패", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}