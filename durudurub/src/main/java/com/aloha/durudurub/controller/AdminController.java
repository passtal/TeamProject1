package com.aloha.durudurub.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aloha.durudurub.dto.AdminSubscription;
import com.aloha.durudurub.dto.Banner;
import com.aloha.durudurub.dto.Club;
import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.dto.UserBan;
import com.aloha.durudurub.service.BannerService;
import com.aloha.durudurub.service.ClubService;
import com.aloha.durudurub.service.ReportService;
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
    private final ReportService reportService;
    private final BannerService bannerService;
    
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
        int totalReports = reportService.countList();
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalClubs", totalClubs);
        model.addAttribute("totalReports", totalReports);

        // 2. 최근 활동 : 리스트(activities)
        // 최근 활동이 없으면, 리스트 조회X (카드 자체X)
        // 2-1 오늘 생성된 모임
        Club lastestClub = clubService.findLatestClub();
        String lastestClubTitle = (lastestClub != null) ? lastestClub.getTitle() : null;
        String lastestClubTime = (lastestClub != null && lastestClub.getCreatedAt() != null)
            ? daysAgo(lastestClub.getCreatedAt())
            : null;
        if (lastestClub == null) {
            String lastestClubMsg = "새로 가입된 모임이 없습니다";
            model.addAttribute("lastestClubMsg", lastestClubMsg);
        }
        model.addAttribute("lastestClubTitle", lastestClubTitle);
        model.addAttribute("lastestClubTime", lastestClubTime);

        // 2-2 신규가입
        int totalNew = userService.countNew();
        User lastestUser = userService.findLastestUser();
        String lastestUserTime = (lastestUser != null && lastestUser.getCreatedAt() != null)
            ? daysAgo(lastestUser.getCreatedAt())
            : null;
        if (totalNew == 0) {
            String lastestUserMsg = "새로 가입한 사용자가 없습니다";
            model.addAttribute("lastestUserMsg", lastestUserMsg);
        }
        model.addAttribute("totalNew", totalNew);
        model.addAttribute("lastestUserTime", lastestUserTime);

         
        // 2-3 신고 접수 (아직 기능X)
        int totalNewReports = reportService.countNewReports();
        UserBan lastestUserBan = reportService.findLastestUserBan();
        String lastestReportsTime = (lastestUserBan != null && lastestUserBan.getCreatedAt() != null)
            ? daysAgo(lastestUserBan.getCreatedAt())
            : null;
        if (totalNewReports == 0) {
            String lastestReportMsg = "새로 접수된 신고가 없습니다";
            model.addAttribute("lastestReportMsg", lastestReportMsg);
        }
        model.addAttribute("totalNewReports", totalNewReports);
        model.addAttribute("lastestReportsTime", lastestReportsTime);

        return "admin/fragments/dashboard";
    }
    
    private String daysAgo(Date time) {
        ZoneId zoneId = ZoneId.of("Asia/Seoul");

        LocalDate today = LocalDate.now(zoneId);
        LocalDate target = time.toInstant().atZone(zoneId).toLocalDate();

        long days = ChronoUnit.DAYS.between(target, today);

        if (days <= 0) return "오늘";
        if (days == 1) return "1일전";
        if (days == 2) return "2일전";
        return days + "일 전";
    }

    // 사용자 관리 (조각)
    @GetMapping("/fragment/users")
    public String users() {
        return "admin/fragments/users";
    }
    // 사용자 관리 (json)
    @GetMapping("/api/users")
    @ResponseBody
    public List<AdminSubscription> usersData() {
        return userService.userList();
    }
    // 사용자 삭제
    @DeleteMapping("/api/users/{userNo}")
    @ResponseBody
    public int deleteUser(@PathVariable int userNo) {
        return userService.delete(userNo);
    }
    

    // 모임 관리 (조각)
    @GetMapping("/fragment/clubs")
    public String clubs() {
        return "admin/fragments/clubs";
    }
    // 모임 관리 (json)
    @GetMapping("/api/clubs")
    @ResponseBody
    public List<Club> clubsData() {
        return clubService.list();
    }
    // 모임 삭제
    @DeleteMapping("/api/clubs/{clubNo}")
    @ResponseBody
    public int deleteClub(@PathVariable int clubNo) {
        return clubService.delete(clubNo);
    }
    
    
    // 신고 관리 (조각)
    @GetMapping("/fragment/reports")
    public String reports() {
        return "admin/fragments/reports";
    }
    // 신고 관리 (json)
    @GetMapping("/api/reports")
    @ResponseBody
    public List<UserBan> reportsData() {
        reportService.deleteExpired();
        return reportService.listByTarget();
    }
    // 신고 - 4회 이상 버튼 누르면 (유저 삭제)
    @DeleteMapping("/api/reports/{userNo}")
    @ResponseBody
    public int deleteReports(@PathVariable int userNo) {
        return userService.delete(userNo);
    }


    // 배너 관리 (조각)
    @GetMapping("/fragment/banners")
    public String banners() {
        return "admin/fragments/banner";
    }
    // 배너 관리 (리스트)
    @GetMapping("/api/banners")
    @ResponseBody
    public List<Banner> bannerData() throws Exception {
        return bannerService.bannerList();
    }
    // 배너 추가 (모달)
    @PostMapping("/api/banners/insert")
    @ResponseBody
    public int createdBanner(
        Banner banner,
        @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws Exception {
        System.out.println("imageFile = " + (imageFile == null ? "null" : imageFile.getOriginalFilename()));
        System.out.println(">>>> isActive = [" + banner.getIsActive() + "]");
        return bannerService.bannerInsert(banner, imageFile);
    }
    // 배너 수정 (모달)
    @PutMapping("/api/banners/update/{no}")
    @ResponseBody
    public int updatedBanner(
        Banner banner,
        @PathVariable("no") Integer no,
        @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws Exception {
        System.out.println("imageFile = " + (imageFile == null ? "null" : imageFile.getOriginalFilename()));
        banner.setNo(no);
        return bannerService.bannerUpdate(banner, imageFile);
    }
    // 배너 삭제 (모달)
    @DeleteMapping("/api/banners/{no}")
    @ResponseBody
    public int deleteBanner (
        @PathVariable int no
    ) throws Exception {
        return bannerService.bannerDelete(no);
    }
    // 배너 위치 뱃지
    @PatchMapping("/api/banners/{no}/active")
    @ResponseBody
    public int updateBannerActive (
        @PathVariable("no") Integer no,
        @RequestBody Map<String, String> body
    ) throws Exception {
        // Y or N
        String isActive = body.get("isActive");
        return bannerService.updateBannerActive(no, isActive);
    }
    // 배너 활성화 뱃지
    @PatchMapping("/api/banners/{no}/position")
    @ResponseBody
    public int updateBannerPosition (
        @PathVariable("no") Integer no,
        @RequestBody Map<String, String> body
    )throws Exception {
        // MAIN, POPUP
        String position = body.get("position");
        return bannerService.updateBannerPosition(no, position);
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
