package com.aloha.durudurub.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloha.durudurub.dto.Club;
import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.service.ClubService;
import com.aloha.durudurub.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ClubService clubService;
    
    // index (공통)
    @GetMapping("/")
    public String admin() {
        return "admin/index";
    }

    // dashboard (조각)
    @GetMapping("/fragment/dashboard")
    public String dashboard(Model model) {
        
        // 1. 통계 : 사용자(totalUsers), 모임(totalClubs), 신고(totalReports)
        int totalUsers = userService.countAll();
        int totalClubs = clubService.count();
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalClubs", totalClubs);

        // 2. 최근 활동 : 리스트(activities)
        // 최근 활동이 없으면, 리스트 조회X (카드 자체X)
        // 2-1 오늘 생성된 모임
        Club lastestClub = clubService.findLatestClub();
        String lastestClubTitle = (lastestClub != null) ? lastestClub.getTitle() : "독서 토론방";
        String lastestClubTime = (lastestClub != null && lastestClub.getCreatedAt() != null)
            ? daysAgo(lastestClub.getCreatedAt())
            : "오늘";
        model.addAttribute("latestClubTitle", lastestClubTitle);
        model.addAttribute("latestClubTime", lastestClubTime);

        // 2-2 3일 동안 신규가입
        int totalNew = userService.countNew();
        model.addAttribute("totalNew", totalNew);
        model.addAttribute("newUserTime", "어제");

         
        // 2-3 수정된 공지 (아직 기능X)
        model.addAttribute("noticeTime", "2일전");

        return "admin/fragments/dashboard";
    }
    
    private String daysAgo(Date time) {
        long days = java.time.Duration.between(time.toInstant(), java.time.Instant.now()).toDays();
        if (days < 1) return "오늘";
        if (days == 1) return "어제";
        return days + "일 전";
    }

    // 사용자 관리
    @GetMapping("/fragment/users")
    public String users(Model model) throws Exception {
        return "admin/fragments/users";
    }
    
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
