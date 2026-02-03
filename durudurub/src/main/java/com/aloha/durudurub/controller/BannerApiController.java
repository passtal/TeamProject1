package com.aloha.durudurub.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aloha.durudurub.dto.Banner;
import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.service.BannerService;
import com.aloha.durudurub.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/adminpage/banner/modal")
@RequiredArgsConstructor
public class BannerApiController {
    // URL 수정 필수!
    
    private final BannerService bannerService;
    private final UserService userService;
    
    /* 관리자 권한 필요함! */
    private boolean isAdmin(Principal principal) {
        if (principal == null) return false;
        try {
            // 현재 로그인한 사용자 아이디 가져오기
            User user = userService.selectByUserId(principal.getName());
            if (user == null) return false;
            return "admin@durudurub.com".equals(user.getUserId());
        } catch (Exception e) {
            log.error("관리자 조회 실패!");
            return false;
        }
    }

    // 배너 조회 - 모달 (비동기)
    @GetMapping("/{no}")
    public ResponseEntity<Banner> getOne(@PathVariable("no") Integer no, Principal principal) {
        // 관리자 권한 - FORBIDDEN (403, 권한 없음 )
        if (!isAdmin(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // 조회
        try {
            Banner banner = bannerService.bannerSelect(no);
            if (banner == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(banner);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    // 배너 등록 - 모달 (비동기)
    @PostMapping("/bann")
    public ResponseEntity<Banner> create(@RequestBody Banner banner, Principal principal) {
        // 관리자 권한 - FORBIDDEN (403, 권한 없음 )
        if (!isAdmin(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // 등록
        try {
            boolean result = bannerService.bannerInsert(banner);
            if (!result) {
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(banner);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    // 배너 수정 - 모달 (비동기)
    @PutMapping()
    public ResponseEntity<Banner> update(@RequestBody Banner banner, Principal principal) {
        // 관리자 권한 - FORBIDDEN (403, 권한 없음 )
        if (!isAdmin(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // 수정
        try {
            boolean result = bannerService.bannerUpdate(banner);
            if (!result) {
                // body() : HTTP 응답 바디에 실제 데이터를 넣을 때 사용하는 메서드
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(banner);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    // 배너 삭제 - 모달 (비동기)
    @DeleteMapping("/{no}")
    public ResponseEntity<Void> destroy(@PathVariable("no") Integer no, Principal principal) {
        // 관리자 권한 - FORBIDDEN (403, 권한 없음 )
        if (!isAdmin(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // 삭제
        try {
            boolean result = bannerService.bannerDelete(no);
            if (!result) {
                // build : 응답 바디 필요 없을 때 사용하는 메서드
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}