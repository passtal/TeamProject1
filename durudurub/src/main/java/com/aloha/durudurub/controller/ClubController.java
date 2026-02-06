package com.aloha.durudurub.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       Model model) {
        
        List<Club> clubs;

        if (subCategoryNo != null) {
            clubs = clubService.listBySubCategory(subCategoryNo);
        } else if (categoryNo != null) {
            clubs = clubService.listByCategory(categoryNo);
        } else {
            clubs = clubService.list();
        }
        
        // 디버깅 로그
        System.out.println("=== Club List Debug ===");
        System.out.println("clubs size: " + (clubs != null ? clubs.size() : "null"));
        if (clubs != null && !clubs.isEmpty()) {
            for (Club c : clubs) {
                System.out.println("Club: " + c.getNo() + " - " + c.getTitle());
            }
        }

        List<Category> categories = categoryService.list();

        model.addAttribute("clubs", clubs);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryNo", categoryNo);
        model.addAttribute("subCategoryNo", subCategoryNo);
        
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
            
            // 게시글 목록 조회 (최근 5개)
            List<Board> boards = boardService.listByClub(no);

            model.addAttribute("club", club);
            model.addAttribute("members", members);
            model.addAttribute("boards", boards);

            if (principal != null) {
                User user = userService.selectByUserId(principal.getName());
                ClubMember myMembership = clubService.selectMember(no, user.getNo());
                model.addAttribute("myMembership", myMembership);
                model.addAttribute("isHost", club.getHostNo() == user.getNo());
            } else {
                model.addAttribute("isHost", false);
            }
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
     * 카테고리별 모임 목록 페이지
     * @param categoryNo 카테고리 번호
     * @param model
     * @return
     */
    @GetMapping("/category")
    public String category(@RequestParam("category") int categoryNo, Model model) {
        // 카테고리 번호로 카테고리 조회
        Category category = categoryService.selectByNo(categoryNo);
        
        if (category == null) {
            return "redirect:/club/list";
        }
        
        // 카테고리에 속한 모임 조회
        List<Club> clubs = clubService.listByCategory(category.getNo());
        
        // 소분류 카테고리 조회
        List<SubCategory> subCategories = categoryService.listBySubCategory(category.getNo());
        
        model.addAttribute("categoryName", category.getName());
        model.addAttribute("category", category);
        model.addAttribute("clubs", clubs);
        model.addAttribute("subCategories", subCategories);
        
        return "club/category";
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
     * @param principal
     * @param rttr
     * @return
     */
    @PostMapping("/create")
    public String createPro(Club club, Principal principal, RedirectAttributes rttr) {
        User user = userService.selectByUserId(principal.getName());
        club.setHostNo(user.getNo());
        club.setStatus("RECRUITING");
        club.setCurrentMembers(1);
        
        int result = clubService.insert(club);
        
        if (result > 0) {
            // 호스트를 멤버로 추가 (생성 시에 모임 구성원 수가 0이면 안되기에)
            ClubMember member = new ClubMember();
            member.setClubNo(club.getNo());
            member.setUserNo(user.getNo());
            member.setStatus("APPROVED");
            clubService.insertMember(member);
            
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
        User user = userService.selectByUserId(principal.getName());
        
        // 호스트만 수정 가능
        if (club.getHostNo() != user.getNo()) {
            return "redirect:/club/" + no;
        }
        
        List<Category> categories = categoryService.list();
        List<SubCategory> subCategories = categoryService.listBySubCategory(club.getCategoryNo());
        
        model.addAttribute("club", club);
        model.addAttribute("categories", categories);
        model.addAttribute("subCategories", subCategories);
        
        return "club/update";
    }
    
    /**
     * 모임 수정 처리
     * @param no
     * @param club
     * @param principal
     * @param rttr
     * @return
     */
    @PostMapping("/{no}/edit")
    public String editPro(@PathVariable("no") int no, Club club, 
                         Principal principal, RedirectAttributes rttr) {
        Club existingClub = clubService.selectByNo(no);
        User user = userService.selectByUserId(principal.getName());
        
        if (existingClub.getHostNo() != user.getNo()) {
            return "redirect:/club/" + no;
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
        member.setStatus("PENDING");
        
        clubService.insertMember(member);
        rttr.addFlashAttribute("message", "가입 신청이 완료되었습니다.");
        
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