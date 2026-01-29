package com.aloha.durudurub.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    
    /**
     * 현재 로그인한 사용자 정보 조회
     * @param principal
     * @return
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        try {
            if (principal == null) {
                return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
            }
            User user = userService.selectByUserId(principal.getName());
            if (user == null) {
                return new ResponseEntity<>("NOT_FOUND", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            log.error("사용자 정보 조회에 실패했습니다.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * 회원 정보 조회 (번호로)
     * @param no
     * @return
     */
    @GetMapping("/{no}")
    public ResponseEntity<?> getByNo(@PathVariable("no") int no) {
        try {
            User user = userService.selectByNo(no);
            if (user == null) {
                return new ResponseEntity<>("NOT_FOUND", HttpStatus.NOT_FOUND);
            }
            // 비밀번호 제거
            user.setPassword(null);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            log.error("회원 정보 조회에 실패했습니다.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * 회원 가입
     * @param user
     * @return
     */
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody User user) {
        try {
            // 아이디 중복 체크
            if (userService.existsByUserId(user.getUserId())) {
                return new ResponseEntity<>("DUPLICATE_ID", HttpStatus.BAD_REQUEST);
            }
            // 닉네임 중복 체크
            if (userService.existsByUsername(user.getUsername())) {
                return new ResponseEntity<>("DUPLICATE_USERNAME", HttpStatus.BAD_REQUEST);
            }
            
            int result = userService.insert(user);
            if (result == 0) {
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("회원 가입에 실패했습니다.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * 회원 정보 수정
     * @param user
     * @param principal
     * @return
     */
    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody User user, Principal principal) {
        try {
            User currentUser = userService.selectByUserId(principal.getName());
            user.setNo(currentUser.getNo());
            
            int result = userService.update(user);
            if (result == 0) {
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            log.error("회원 정보 수정 실패", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * 회원 탈퇴
     * @param principal
     * @return
     */
    @DeleteMapping("")
    public ResponseEntity<?> delete(Principal principal) {
        try {
            User currentUser = userService.selectByUserId(principal.getName());
            int result = userService.delete(currentUser.getNo());
            if (result == 0) {
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            log.error("회원 탈퇴 실패", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * 비밀번호 변경
     * @param user
     * @param principal
     * @return
     */
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody User user, Principal principal) {
        try {
            User currentUser = userService.selectByUserId(principal.getName());
            
            int result = userService.updatePassword(currentUser.getNo(), user.getPassword());
            if (result == 0) {
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            log.error("비밀번호 변경 실패", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * 프로필 이미지 변경
     * @param user
     * @param principal
     * @return
     */
    @PutMapping("/profile-img")
    public ResponseEntity<?> updateProfileImg(@RequestBody User user, Principal principal) {
        try {
            User currentUser = userService.selectByUserId(principal.getName());
            
            int result = userService.updateProfileImg(currentUser.getNo(), user.getProfileImg());
            if (result == 0) {
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            log.error("프로필 이미지 변경 실패", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * 아이디 중복 체크
     * @param userId
     * @return
     */
    @GetMapping("/check-id")
    public ResponseEntity<?> checkId(@RequestParam("userId") String userId) {
        try {
            boolean exists = userService.existsByUserId(userId);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception e) {
            log.error("아이디 중복 체크 실패", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * 닉네임 중복 체크
     * @param username
     * @return
     */
    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestParam("username") String username) {
        try {
            boolean exists = userService.existsByUsername(username);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception e) {
            log.error("닉네임 중복 체크 실패", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}