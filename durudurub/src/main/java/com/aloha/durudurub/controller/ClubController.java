package com.aloha.durudurub.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aloha.durudurub.dto.Category;
import com.aloha.durudurub.dto.Club;
import com.aloha.durudurub.dto.ClubMember;
import com.aloha.durudurub.dto.SubCategory;
import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.dto.Board;
import com.aloha.durudurub.service.BoardService;
import com.aloha.durudurub.service.CategoryService;
import com.aloha.durudurub.service.ClubService;
import com.aloha.durudurub.service.LikeService;
import com.aloha.durudurub.service.UserService;

import lombok.extern.slf4j.Slf4j;


/**
 * 모임 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/club")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private BoardService boardService;
    
    @Autowired
    private LikeService likeService;
    

    /**
     * 모임 목록 보기 (전체 모임 리스트 페이지)
     * @param categoryNo
     * @param subCategoryNo
     * @param page
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(@RequestParam(value = "category", required = false) Integer categoryNo,
                       @RequestParam(value = "sub", required = false) Integer subCategoryNo,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       Model model) {
        
        // 모든 모임을 가져온 뒤, 프론트에서 카테고리 필터링 처리
        List<Club> clubs = clubService.list();

        List<Category> categories = categoryService.list();

        model.addAttribute("clubs", clubs);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryNo", categoryNo);
        model.addAttribute("subCategoryNo", subCategoryNo);
        model.addAttribute("keyword", keyword);
        
        return "club/list";
    }

    /**
     * 모임 페이지 보기 (개별 모임 페이지)
     * @param no
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/{no}")
    public String detail(@PathVariable("no") int no,
                        Principal principal,
                        Model model) {
        try {
            // 조회수 증가
            clubService.incrementViewCount(no);

            Club club = clubService.selectByNo(no);
            
            if (club == null) {
                log.error("Club not found with no: {}", no);
                return "error/404";
            }
            
            List<ClubMember> members = clubService.listMembers(no);
            
            // 게시글 목록 조회 (공지사항 포함, 공지사항이 맨 위로)
            List<Board> boards = boardService.listByClub(no);

            model.addAttribute("club", club);
            model.addAttribute("members", members);

            if (principal != null) {
                User user = userService.selectByUserId(principal.getName());
                ClubMember myMembership = clubService.selectMember(no, user.getNo());
                model.addAttribute("myMembership", myMembership);
                model.addAttribute("isHost", club.getHostNo() == user.getNo());
                
                // 모임 좋아요 상태 설정
                club.setLiked(likeService.isClubLiked(no, user.getNo()));
                
                // 각 게시글의 좋아요 상태 설정
                if (boards != null) {
                    for (Board board : boards) {
                        board.setLiked(likeService.isBoardLiked(board.getNo(), user.getNo()));
                    }
                }
            } else {
                model.addAttribute("isHost", false);
            }
            
            model.addAttribute("boards", boards);
            return "club/detail";
        } catch (Exception e) {
            log.error("Error loading club detail for no {}: {}", no, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 세부 카테고리 조회하기 (API)
     * @param categoryNo ERROR
     * @return
     */
    @GetMapping("/api/subcategories/{categoryNo}")
    @ResponseBody
    public List<SubCategory> getSubCategories(@PathVariable("categoryNo") int categoryNo) {
        return categoryService.listBySubCategory(categoryNo);
    }
    
    /**
     * 카테고리별 모임 조회 (API)
     * @param categoryNo
     * @return
     */
    @GetMapping("/api/clubs/category/{categoryNo}")
    @ResponseBody
    public List<Club> getClubsByCategory(@PathVariable("categoryNo") int categoryNo) {
        return clubService.listByCategory(categoryNo);
    }
    
    /**
     * 소분류별 모임 조회 (API)
     * @param subCategoryNo
     * @return
     */
    @GetMapping("/api/clubs/sub/{subCategoryNo}")
    @ResponseBody
    public List<Club> getClubsBySubCategory(@PathVariable("subCategoryNo") int subCategoryNo) {
        return clubService.listBySubCategory(subCategoryNo);
    }
    
    
    /**
     * 모임 생성하기
     * @param model
     * @return
     */
    @GetMapping("/create")
    public String create(Model model) {
        List<Category> categories = categoryService.list();
        model.addAttribute("categories", categories);
        model.addAttribute("club", new Club());
        return "club/insert";
    }

    /**
     * 모임 생성 처리
     * @param club
     * @param thumbnail
     * @param principal
     * @param rttr
     * @return
     */
    @PostMapping("/create")
    public String createPro(Club club, 
                           @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
                           Principal principal, 
                           RedirectAttributes rttr) {
        User user = userService.selectByUserId(principal.getName());
        club.setHostNo(user.getNo());
        club.setStatus("RECRUITING");
        club.setCurrentMembers(1);
        
        // 파일 업로드 처리
        if (thumbnail != null && !thumbnail.isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.dir") + "/uploads/clubs/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                
                // 고유 파일명 생성
                String originalFilename = thumbnail.getOriginalFilename();
                String extension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String savedFilename = UUID.randomUUID().toString() + extension;
                
                // 파일 저장
                File savedFile = new File(uploadDir + savedFilename);
                thumbnail.transferTo(savedFile);
                
                // 웹 접근 경로 설정
                club.setThumbnailImg("/uploads/clubs/" + savedFilename);
            } catch (IOException e) {
                log.error("파일 업로드 실패", e);
            }
        }
        
        int result = clubService.insert(club);
        
        if (result > 0) {
            // 호스트 멤버 추가는 ClubServiceImpl.insert() 내부에서 처리됨
            rttr.addFlashAttribute("message", "모임이 생성되었습니다.");
            return "redirect:/club/" + club.getNo();
        }
        
        rttr.addFlashAttribute("error", "모임 생성에 실패했습니다.");
        return "redirect:/club/create";
    }

    /**
     * 모임 수정하기 (모임 수정 페이지)
     * @param no
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/{no}/edit")
    public String edit(@PathVariable("no") int no, Principal principal, Model model) {
        Club club = clubService.selectByNo(no);
        
        if (club == null) {
            return "redirect:/club";
        }
        
        User user = userService.selectByUserId(principal.getName());
        
        // 호스트만 수정 가능
        if (club.getHostNo() != user.getNo()) {
            return "redirect:/club/" + no;
        }
        
        List<Category> categories = categoryService.list();
        List<SubCategory> subCategories = null;
        if (club.getCategoryNo() > 0) {
            subCategories = categoryService.listBySubCategory(club.getCategoryNo());
        }
        
        model.addAttribute("club", club);
        model.addAttribute("categories", categories);
        model.addAttribute("subCategories", subCategories);
        
        return "club/update";
    }
    
    /**
     * 모임 수정 처리
     * @param no
     * @param club
     * @param thumbnailImg
     * @param principal
     * @param rttr
     * @return
     */
    @PostMapping("/{no}/edit")
    public String editPro(@PathVariable("no") int no, Club club, 
                         @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
                         Principal principal, RedirectAttributes rttr) {
        Club existingClub = clubService.selectByNo(no);
        User user = userService.selectByUserId(principal.getName());
        
        if (existingClub.getHostNo() != user.getNo()) {
            return "redirect:/club/" + no;
        }
        
        // 새 이미지가 업로드된 경우
        if (thumbnail != null && !thumbnail.isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.dir") + "/uploads/clubs/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                
                String originalFilename = thumbnail.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String filename = UUID.randomUUID().toString() + extension;
                
                File file = new File(uploadDir + filename);
                thumbnail.transferTo(file);
                
                club.setThumbnailImg("/uploads/clubs/" + filename);
            } catch (IOException e) {
                log.error("이미지 업로드 실패", e);
                rttr.addFlashAttribute("error", "이미지 업로드에 실패했습니다.");
                return "redirect:/club/" + no + "/edit";
            }
        } else {
            // 기존 이미지 유지
            club.setThumbnailImg(existingClub.getThumbnailImg());
        }
        
        club.setNo(no);
        int result = clubService.update(club);
        
        if (result > 0) {
            rttr.addFlashAttribute("message", "모임이 수정되었습니다.");
        } else {
            rttr.addFlashAttribute("error", "수정에 실패했습니다.");
        }
        
        return "redirect:/club/" + no;
    }
    
    /**
     * 모임 삭제하기
     * @param no
     * @param principal
     * @param rttr
     * @return
     */
    @PostMapping("/{no}/delete")
    public String delete(@PathVariable("no") int no, Principal principal, RedirectAttributes rttr) {
        Club club = clubService.selectByNo(no);
        User user = userService.selectByUserId(principal.getName());
        
        if (club.getHostNo() != user.getNo()) {
            return "redirect:/club/" + no;
        }
        clubService.delete(no);
        rttr.addFlashAttribute("message", "모임이 삭제되었습니다.");
        return "redirect:/club/list";
    }
    
    /**
     * 가입 신청하기
     * @param no
     * @param principal
     * @param rttr
     * @return
     */
    @PostMapping("/{no}/join")
    public String join(@PathVariable("no") int no, Principal principal, RedirectAttributes rttr) {
        User user = userService.selectByUserId(principal.getName());
        
        // 이미 가입/신청 여부 확인
        ClubMember existing = clubService.selectMember(no, user.getNo());
        if (existing != null) {
            rttr.addFlashAttribute("error", "이미 가입 신청을 했습니다.");
            return "redirect:/club/" + no;
        }
        
        ClubMember member = new ClubMember();
        member.setClubNo(no);
        member.setUserNo(user.getNo());
        member.setStatus("APPROVED");
        
        clubService.insertMember(member);
        clubService.incrementMemberCount(no);
        rttr.addFlashAttribute("message", "모임에 가입되었습니다.");
        
        return "redirect:/club/" + no;
    }
    
    /**
     * 가입 승인/거절 (ROLE_HOST 전용)
     * @param no
     * @param memberNo
     * @param status
     * @param principal
     * @param rttr
     * @return
     */
    @PostMapping("/{no}/member/{memberNo}/approve")
    public String approveMember(@PathVariable("no") int no,
                                @PathVariable("memberNo") int memberNo,
                                @RequestParam("status") String status,
                                Principal principal,
                                RedirectAttributes rttr) {
        Club club = clubService.selectByNo(no);
        User user = userService.selectByUserId(principal.getName());
        
        if (club.getHostNo() != user.getNo()) {
            return "redirect:/club/" + no;
        }
        
        clubService.updateMemberStatus(memberNo, status);
        
        if ("APPROVED".equals(status)) {
            clubService.incrementMemberCount(no);
            rttr.addFlashAttribute("message", "가입을 승인했습니다.");
        } else {
            rttr.addFlashAttribute("message", "가입을 거절했습니다.");
        }
        
        return "redirect:/club/" + no;
    }
    
    /**
     * 모임 탈퇴하기
     * @param no
     * @param principal
     * @param rttr
     * @return
     */
    @PostMapping("/{no}/leave")
    public String leave(@PathVariable("no") int no, Principal principal, RedirectAttributes rttr) {
        User user = userService.selectByUserId(principal.getName());
        Club club = clubService.selectByNo(no);
        
        // 호스트는 탈퇴 불가 (호스트 권한 위임 같은걸 만들어야할까요..? 보시면 Trello에 의견 주십쇼)
        if (club.getHostNo() == user.getNo()) {
            rttr.addFlashAttribute("error", "호스트는 탈퇴할 수 없습니다.");
            return "redirect:/club/" + no;
        }
        
        ClubMember member = clubService.selectMember(no, user.getNo());
        if (member != null && "APPROVED".equals(member.getStatus())) {
            clubService.decrementMemberCount(no);
        }
        clubService.deleteMember(no, user.getNo());
        
        rttr.addFlashAttribute("message", "모임에서 탈퇴했습니다.");
        return "redirect:/club/list";
    }
    
}