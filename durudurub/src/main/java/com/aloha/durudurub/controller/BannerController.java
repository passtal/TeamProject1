package com.aloha.durudurub.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aloha.durudurub.dto.Banner;
import com.aloha.durudurub.service.UserService;
import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.service.BannerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 배너 관리 컨트롤러 (관리자 전용)
 */

@Slf4j
@Controller
@RequestMapping("/admin/adminpage/banner")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;
    private final UserService userService;

    /* 관리자 권한 필요함! */
    private boolean isAdmin(Principal principal) {
        if (principal == null) return false;
        try {
            User user = userService.selectByUserId(principal.getName());
            if (user == null) return false;
            return "admin@durudurub.com".equals(user.getUserId());
        } catch (Exception e) {
            log.error("관리자 조회 실패!");
            return false;
        }
    }

    // 배너 목록 - 동기(HTML)
    @GetMapping("/list")
    public String list(Principal principal, Model model) throws Exception {
        // 관리자 권한 - FORBIDDEN (403, 권한 없음 )
        if (!isAdmin(principal)) {
            return "/error/403";
        }
        List<Banner> list = bannerService.bannerList();
        model.addAttribute("list", list);
        return "/adminpage/banner";
    }
}
