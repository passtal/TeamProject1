package com.aloha.durudurub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 관리자 페이지 컨트롤러
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    /**
     * 관리자 대시보드
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // TODO: 실제 통계 데이터 가져오기
        // 임시 데이터 설정
        model.addAttribute("totalUsers", "1,247");
        model.addAttribute("totalClubs", "89");
        model.addAttribute("totalReports", "23");
        
        // 임시 활동 데이터
        java.util.List<String> activities = java.util.Arrays.asList("1", "2", "3");
        model.addAttribute("activities", activities);
        
        return "admin/dashboard";
    }
    
    /**
     * 사용자 관리 페이지
     */
    @GetMapping("/users")
    public String users(Model model) {
        // TODO: 사용자 관리 페이지 구현
        return "admin/users";
    }
    
    /**
     * 모임 관리 페이지
     */
    @GetMapping("/clubs")
    public String clubs(Model model) {
        // TODO: 모임 관리 페이지 구현
        return "admin/clubs";
    }
    
    /**
     * 신고 관리 페이지
     */
    @GetMapping("/reports")
    public String reports(Model model) {
        // TODO: 신고 관리 페이지 구현
        return "admin/reports";
    }
    
    /**
     * 배너 관리 페이지
     */
    @GetMapping("/banners")
    public String banners(Model model) {
        // TODO: 배너 관리 페이지 구현
        return "admin/banner/list";
    }
    
    /**
     * 카테고리 관리 페이지
     */
    @GetMapping("/categories")
    public String categories(Model model) {
        // TODO: 카테고리 관리 페이지 구현
        return "admin/categories";
    }
}
