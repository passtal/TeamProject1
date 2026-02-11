package com.aloha.durudurub.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aloha.durudurub.dto.Club;
import com.aloha.durudurub.dto.ClubMember;
import com.aloha.durudurub.dto.Subscription;
import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.service.ClubService;
import com.aloha.durudurub.service.LikeService;
import com.aloha.durudurub.service.SubscriptionService;
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
    private final LikeService likeService;
    
    private final SubscriptionService subscriptionService;
    
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
            int totalFavorite = likeService.countClubLikeByUser(userNo);
            
            model.addAttribute("user", user);
            model.addAttribute("totalMyClub", totalMyClub);
            model.addAttribute("totalFavorite", totalFavorite);

            if (user != null) {
                Subscription subscription = subscriptionService.selectByUserNo(user.getNo());
                model.addAttribute("subscription", subscription);
            }
        }
        return "mypage/mypage";
    }

    // 회원 정보 수정 (비동기)
    // ⭐ 사진 업로드 포함!
    @PostMapping(value={"", "/"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, String>> mypageUpdate(
        @RequestParam("username") String username,
        @RequestParam(value = "age", required = false, defaultValue = "0") int age,
        @RequestParam(value = "gender", required = false) String gender,
        @RequestParam(value = "address", required = false) String address,
        @RequestParam(value = "profileImg", required = false) MultipartFile profileImgFile,  
        Principal principal
    ) throws Exception {
        String userId = principal.getName();
        User loginUser = userService.selectByUserId(userId);

        //  1) 기본정보 업데이트
        User user = new User();
        user.setNo(loginUser.getNo());
        user.setUsername(username);
        user.setAge(age);
        user.setGender(gender);
        user.setAddress(address);
        userService.update(user);

        System.out.println("*********profileImgFile = " + (profileImgFile == null ? "null" : profileImgFile.getOriginalFilename()));
        System.out.println("********isEmpty = " + (profileImgFile == null ? "null" : profileImgFile.isEmpty()));
        System.out.println("********size = " + (profileImgFile == null ? "null" : profileImgFile.getSize()));

        //  2) 이미지 업데이트
        String imageUrl = null;
        if (profileImgFile != null && !profileImgFile.isEmpty()) {
            imageUrl = saveProfileImage(profileImgFile);
            userService.updateProfileImg(loginUser.getNo(), imageUrl);
        }

        Map<String, String> res = new HashMap<>();
        res.put("profileImgUrl", imageUrl); // 업로드 안 했으면 null
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(res);
    }
    // 사진 저장
    private static final Path UPLOAD_DIR = Paths.get(
        System.getProperty("user.dir"),
        "uploads", "profile"
    ).toAbsolutePath().normalize();
    private static final String DB_URL_PREFIX = "/uploads/profile/";

    private String saveProfileImage(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("프로필 사진은 이미지 파일만 업로드할 수 있습니다.");
        }
        long maxBytes = 5L * 1024 * 1024;
        if (file.getSize() > maxBytes) {
            throw new IllegalArgumentException("프로필 사진은 5MB 이하만 가능합니다.");
        }

        String original = file.getOriginalFilename();
        String ext = "";
        if (original != null && original.contains(".")) {
            ext = original.substring(original.lastIndexOf(".")); // ".jpg"
        }

        String savedName = UUID.randomUUID().toString().replace("-", "") + ext;
        Path target = UPLOAD_DIR.resolve(savedName).toAbsolutePath().normalize();

        try {
            Files.createDirectories(UPLOAD_DIR);

            // 디버깅 로그 (저장되는 "진짜" 경로)
            System.out.println("**********uploadDir = " + UPLOAD_DIR);
            System.out.println("**********target    = " + target);

            file.transferTo(target.toFile());

            // 저장 확인
            if (!Files.exists(target) || Files.size(target) <= 0) {
                throw new RuntimeException("파일 저장 검증 실패: " + target);
            }

            System.out.println("saved OK. size=" + Files.size(target));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("프로필 이미지 저장에 실패했습니다. target=" + target, e);
        }

        return DB_URL_PREFIX + savedName;
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
    @DeleteMapping("/api/club/{clubNo}")
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
    @GetMapping("/api/club/hostClub/{clubNo}/pending")
    @ResponseBody
    public List<ClubMember> pendingMember(
        @PathVariable("clubNo") int clubNo
    ) throws Exception {
        List<ClubMember> pendingList = clubService.listPendingMembers(clubNo);
        log.info("★★ pendingList : " + pendingList);
        
        return pendingList;
    }
    // 승인된 멤버 목록
    @GetMapping("/api/club/hostClub/{clubNo}/approved")
    @ResponseBody
    public List<ClubMember> approvedMember(
        @PathVariable("clubNo") int clubNo
    ) {
        List<ClubMember> approvedList = clubService.listApproveMembers(clubNo);
        log.info("★★ approvedList : " + approvedList);

        return approvedList;
    }
    // 모임 삭제 - 리더
    @DeleteMapping("/api/club/hostClub/{clubNo}")
    @ResponseBody
    public ResponseEntity<?> deleteClub (
        @PathVariable("clubNo") int clubNo
    ) throws Exception{
        int result = clubService.deleteClub(clubNo);

        if (result > 0) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("*****삭제 실패");
    }
    // 모임 승인 - 리더
    @PutMapping("/api/club/hostClub/{clubNo}/members/{userNo}/approved")
    @ResponseBody
    public ResponseEntity<?> approved (
        @PathVariable("clubNo") int clubNo, 
        @PathVariable("userNo") int userNo
    ) throws Exception {
        int result = clubService.approved(clubNo, userNo);

        if (result > 0) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("*****승인 실패!");
    }
    // 모임 거부 - 리더
    @DeleteMapping("/api/club/hostClub/{clubNo}/members/{userNo}/reject")
    @ResponseBody
    public ResponseEntity<?> rejectMember(
            @PathVariable("clubNo") int clubNo,
            @PathVariable("userNo") int userNo
    ) throws Exception {
        int result = clubService.rejectMember(clubNo, userNo);

        if (result > 0) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("******거부 실패");
    }
    // 모임 추방 - 리더
    @DeleteMapping("/api/club/hostClub/{clubNo}/members/{userNo}/remove")
    @ResponseBody
    public ResponseEntity<?> removeMember(
            @PathVariable("clubNo")  int clubNo,
            @PathVariable("userNo")  int userNo
    ) throws Exception {

        int result = clubService.removeMember(clubNo, userNo);

        if(result > 0) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("*****추방 실패");
    }


    // 신청 중인 모임 (조각)
    @GetMapping("/club/fragment/pendingClub")
    public String pendingClub(Model model, Principal principal) throws Exception {

        User user = userService.selectByUserId(principal.getName());
        int userNo = user.getNo();

        List<Club> pendingClub = clubService.myClubList(userNo, "PENDING"); // 네 서비스 메서드에 맞게
        model.addAttribute("pendingClub", pendingClub);

        return "mypage/fragments/pendingClub";
    }
    // 신청 취소
    // 신청 중인 모임 - 신청 취소
    @DeleteMapping("api/club/pending/{clubNo}")
    @ResponseBody
    public ResponseEntity<?> cancelPending(
        @PathVariable("clubNo") int clubNo,
        Principal principal
    ) throws Exception {

        int userNo = userService.selectByUserId(principal.getName()).getNo();
        int result = clubService.cancelPending(clubNo, userNo);

        if (result > 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
    //------------------------------
    // 즐겨찾기
    @GetMapping("/favorites")
    public String favorites(
        Model model,
        Principal principal
    ) throws Exception{

        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.selectByUserId(principal.getName());
        int userNo = user.getNo();

        List<Club> favoriteClubs= likeService.favoriteList(userNo);

        model.addAttribute("favoriteClubs", favoriteClubs);
        log.info("****************favoriteClubs: {}", favoriteClubs);

        return "mypage/favorites";
    }
}
