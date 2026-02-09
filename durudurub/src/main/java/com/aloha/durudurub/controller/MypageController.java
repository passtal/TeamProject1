package com.aloha.durudurub.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloha.durudurub.dto.Club;
import com.aloha.durudurub.dto.ClubMember;
import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.service.ClubService;
import com.aloha.durudurub.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/users/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final UserService userService;
    private final ClubService clubService;
    
    // 마이페이지 메인
    // user_id 조회
    @GetMapping("")
    public String mypagePage(
        Model model, 
        Principal principal
    ) throws Exception {
        if (principal != null) {

            User user = userService.selectByUserId(principal.getName());
            int userNo = user.getNo();

            int totalMyClub = clubService.countByUser(userNo);

            model.addAttribute("user", user);
            model.addAttribute("totalMyClub", totalMyClub);
        }
        return "mypage/mypage";
    }

    // 회원 정보 수정 (비동기)
    // ⭐ 사진 업로드 완성 시 수정 필요!
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Void> mypageUpdate(
        @RequestParam("username") String username,
        @RequestParam(value = "age", required = false, defaultValue = "0") int age,
        @RequestParam(value = "gender", required = false) String gender,
        @RequestParam(value = "address", required = false) String address,
        Principal principal
    ) throws Exception {
        String userId = principal.getName();
        User loginUser = userService.selectByUserId(userId);
        
        User user = new User();
        // 조건문 : no 조회
        user.setNo(loginUser.getNo());
        user.setUsername(username);
        user.setAge(age);
        user.setGender(gender);
        user.setAddress(address);

        userService.update(user);

        return ResponseEntity.ok().build();
    }

    // 회원 탈퇴 모달
    @DeleteMapping("/modal")
    @ResponseBody
    public ResponseEntity<Void> deleteUser(
        Principal principal,
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws Exception {
        if (principal != null) {
            int userNo = userService.selectByUserId(principal.getName()).getNo();
            userService.delete(userNo);
        }
        new SecurityContextLogoutHandler().logout(request, response, authentication);

        return ResponseEntity.noContent().build();
    }
// 완료 ------------------------------------------------------------------------

    
    // 내모임 관리
    // club index (공통)
    @GetMapping("/club")
    public String clubPage(
        Model model,
        Principal principal
    ) throws Exception {

        User user = userService.selectByUserId(principal.getName());
        int userNo = user.getNo();

        List<Club> hostClub = clubService.listByHost(userNo);
        int countByHost = hostClub.size();
        
        int countByApproved = clubService.countByStatus(userNo, "APPROVED");
        int countByPending = clubService.countByStatus(userNo, "PENDING");

        model.addAttribute("countByApproved", countByApproved);
        model.addAttribute("countByHost", countByHost);
        model.addAttribute("countByPending", countByPending);

        return "mypage/mypage-club";
    }


    // 가입 중인 모임 (조각)
    @GetMapping("/club/fragment/approvedClub")
    public String approvedClub(
        Model model,
        Principal principal
    ) throws Exception {

        User user = userService.selectByUserId(principal.getName());
        int userNo = user.getNo();

        List<Club> approvedClub = clubService.myClubList(userNo, "APPROVED");
        
        log.info("*********approvedClub: {}", approvedClub);
        model.addAttribute("approvedClub", approvedClub);

        return "mypage/fragments/approvedClub";
    }
    // 가입 중인 모임 - 탈퇴
    @DeleteMapping("/club/api/{clubNo}")
    @ResponseBody
    public int deleteApprovedClub (
        @PathVariable("clubNo") int clubNo,
        Principal principal
    ) {
        User user = userService.selectByUserId(principal.getName());
        int userNo = user.getNo(); 

        return clubService.leaveClub(clubNo, userNo);
    }


    // 리더인 모임 (조각)
    @GetMapping("/club/fragment/hostClub")
    public String hostClub(
        Model model,
        Principal principal
    ) throws Exception{

        User host = userService.selectByUserId(principal.getName());
        int hostNo = host.getNo();

        List<Club> hostClub = clubService.listByHost(hostNo);

        log.info("*******hostClub: {}", hostClub);
        model.addAttribute("hostClub", hostClub);

        return "mypage/fragments/hostClub";
    }
    // 승인 대기 목록 (json)
    @GetMapping("/club/hostClub/{clubNo}/pending")
    @ResponseBody
    public List<ClubMember> pendingMember(
        @PathVariable("clubNo") int clubNo
    ) throws Exception {
        List<ClubMember> pendingList = clubService.listPendingMembers(clubNo);
        log.info("★★ pendingList : " + pendingList);

        return pendingList;
    }
    // 승인된 멤버 목록
    @GetMapping("/club/hostClub/{clubNo}/approved")
    @ResponseBody
    public List<ClubMember> approvedMember(
        @PathVariable("clubNo") int clubNo
    ) {
        List<ClubMember> approvedList = clubService.listApproveMembers(clubNo);
        log.info("★★ approvedList : " + approvedList);

        return approvedList;
    }
    

    // 신청 중인 모임
    // 신청 취소
    @DeleteMapping("/club/pending/{clubNo}")
    @ResponseBody
    public ResponseEntity<Void> cancelPending(
        @PathVariable("clubNo") int clubNo,
        Principal principal
    ) {
        int userNo = userService.selectByUserId(principal.getName()).getNo();

        try {
            int result = clubService.cancelPending(clubNo, userNo);
            if (result == 0) {
                return ResponseEntity.badRequest().build();
            }
            System.out.println(SecurityContextHolder.getContext().getAuthentication());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
