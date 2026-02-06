package com.aloha.durudurub.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.service.ClubService;
import com.aloha.durudurub.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 관리자 페이지 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ClubService clubService;
    
    /**
     * 관리자 대시보드
     */
    @GetMapping("/")
    public String admin(Model model) {
        
        // 1. 통계 : 사용자(totalUsers), 모임(totalClubs), 신고(totalReports)
        int totalUsers = userService.countAll();
        int totalClubs = clubService.count();
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalClubs", totalClubs);
        // 2. 최근 활동 : 리스트(activities)
        
        return "admin/dashboard";
    }
    /**
     * 관리자 대시보드
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        
        // 1. 통계 : 사용자(totalUsers), 모임(totalClubs), 신고(totalReports)
        int totalUsers = userService.countAll();
        int totalClubs = clubService.count();
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalClubs", totalClubs);
        // 2. 최근 활동 : 리스트(activities)
        
        return "admin/dashboard";
    }
    
    /**
     * 사용자 관리 페이지
     * 1. 사용자 리스트 조회
     */
    @GetMapping("/users")
    public String users(
        @RequestParam("userId") String userId,
        Model model
    ) throws Exception {

        User user = userService.selectByUserId(userId);
        List<User> userList = userService.list();
        log.info("★★ pendingList : {}", userList);

        model.addAttribute("user", user);
        model.addAttribute("userList", userList);

        return "admin/users";
    }
    @GetMapping("/users/list")
    @ResponseBody
    public List<User> userList() {
        return userService.list();
    }
    // 2. 사용자 상세 조회

    // 3. 사용자 삭제
    
    /**
     * 모임 관리 페이지
     */
    @GetMapping("/clubs")
    @ResponseBody
    public String clubs(Model model) {
        // TODO: 모임 관리 페이지 구현
        return "admin/clubs";
    }
    
    /**
     * 신고 관리 페이지
     */
    @GetMapping("/reports")
    @ResponseBody
    public String reports(Model model) {
        // TODO: 신고 관리 페이지 구현
        return "admin/reports";
    }
    
    /**
     * 배너 관리 페이지
     */
    @GetMapping("/banners")
    @ResponseBody
    public String banners(Model model) {
        // TODO: 배너 관리 페이지 구현
        return "admin/banner/list";
    }
    
    /**
     * 카테고리 관리 페이지
     */
    @GetMapping("/categories")
    @ResponseBody
    public String categories(Model model) {
        // TODO: 카테고리 관리 페이지 구현
        return "admin/categories";
    }
}
