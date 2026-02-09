package com.aloha.durudurub.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.aloha.durudurub.dto.Club;
import com.aloha.durudurub.dto.Subscription;
import com.aloha.durudurub.service.ClubService;
import com.aloha.durudurub.service.SubscriptionService;
import com.aloha.durudurub.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/users/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final UserService userService;
    private final ClubService clubService;
    private final SubscriptionService subscriptionService;
    
    // 마이페이지 메인
    @GetMapping()
    public String mypagePage(Model model, Principal principal) throws Exception {
        if (principal != null) {
            var user = userService.selectByUserId(principal.getName());
            model.addAttribute("user", user);

            if (user != null) {
                Subscription subscription = subscriptionService.selectByUserNo(user.getNo());
                model.addAttribute("subscription", subscription);
            }
        }
        return "mypage/mypage";
    }
    
    // 회원 탈퇴 모달
    @DeleteMapping("/modal")
    @ResponseBody
    public ResponseEntity<Void> deleteUser(Principal principal) throws Exception {
        // if (principal == null) return ResponseEntity.status(401).build();

        int userNo = userService.selectByUserId(principal.getName()).getNo();
        userService.delete(userNo);
        
        return ResponseEntity.noContent().build();
    }

    // 내모임 관리 페이지
    // @GetMapping("/club-management")
    // public String clubManagementPage(Model model, Principal principal) throws Exception {
    //     if (principal != null) {
    //         model.addAttribute("user", userService.selectByUserId(principal.getName()));
    //     }
    //     return "mypage/club-management";
    // }
    
   // club index (기본) - 동기, 페이지 이동
   @GetMapping("/club")
    public String clubPage(
        @RequestParam(name="type", defaultValue = "APPROVED") String type,
        Model model,
        Principal principal
    ) throws Exception {
        // if (principal != null) {
        //     model.addAttribute("user", userService.selectByUserId(principal.getName()));
        // }
        model.addAttribute("type", type);
        return "mypage/mypage-club";
    }

    // 리스트 조회 (타입별 조회) - 비동기, 내부 페이지만 변경
    @GetMapping("/club/list")
    // @GetMapping("/list")
    public String clubListFragment(
        @RequestParam(name="type", defaultValue = "APPROVED") String type,
        Model model,
        Principal principal
    ) throws Exception {

        // 임시 코드
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int userNo = userService.selectByUserId(principal.getName()).getNo();

        List<Club> myclubList = switch (type) {
            case "APPROVED" -> clubService.joinedClubList(userNo);
            case "PENDING"  -> clubService.pendingClubList(userNo);
            case "HOST"     -> clubService.listByHost(userNo);
            default         -> java.util.Collections.emptyList();
        };

        model.addAttribute("myclubList", myclubList);
        model.addAttribute("type", type);

        // 카운트도 같이 fragment에 필요하면 같이 넣기
        model.addAttribute("joinedClubCount", clubService.joinedClubList(userNo).size());
        model.addAttribute("pendingClubCount", clubService.pendingClubList(userNo).size());
        model.addAttribute("hostClubCount", clubService.listByHost(userNo).size());

        // HTML fragments 정의 필요!
        return "mypage/mypage-club :: myclubListFragment";
    }

    // // 탈퇴 및 신청취소
    // @DeleteMapping("/{clubNo}")
    // @ResponseBody
    // public org.springframework.http.ResponseEntity<Void> delete(
    //     @PathVariable int clubNo,
    //     @RequestParam(defaultValue = "APPROVED") String type,
    //     Principal principal
    // ) throws Exception {

    //     int userNo = userService.selectByUserId(principal.getName()).getNo();

    //     // 삭제
    //     try {
    //         boolean result = clubService.deleteByClubAndUser(no);
    //         if (!result) {
    //             // build : 응답 바디 필요 없을 때 사용하는 메서드
    //             return ResponseEntity.badRequest().build();
    //         }
    //         return ResponseEntity.noContent().build();
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //     }

    //     return org.springframework.http.ResponseEntity.ok().build();
    // }
}
