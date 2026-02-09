package com.aloha.durudurub.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aloha.durudurub.dto.Banner;
import com.aloha.durudurub.dto.Category;
import com.aloha.durudurub.service.BannerService;
import com.aloha.durudurub.service.CategoryService;

/**
 * 메인 페이지 컨트롤러
 */
@Controller
public class HomeController {
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BannerService bannerService;
    
    @Autowired
    private BannerService bannerService;
    
    @GetMapping("/")
    public String index(Model model) {
        try {
            // 카테고리 목록 조회
            List<Category> categories = categoryService.list();
            if (categories == null) {
                categories = new ArrayList<>();
            }
            model.addAttribute("categories", categories);
            
            // 활성화된 배너 목록 조회 (수정)
            List<Banner> banners = bannerService.getMainBanner();
            model.addAttribute("banners", banners);
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("categories", new ArrayList<>());
            model.addAttribute("banners", new ArrayList<>());
        }
        return "index";
    }

    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    /**
     * 회원가입 페이지
     */
    @GetMapping("/join")
    public String signup() {
        return "user/join";
    }

    /**
     * 비밀번호 찾기 페이지
     */
    @GetMapping("/user/find-password")
    public String findPassword() {
        return "user/find-password";
    }
}