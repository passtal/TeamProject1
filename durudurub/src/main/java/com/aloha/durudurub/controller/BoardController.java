package com.aloha.durudurub.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aloha.durudurub.dto.Board;
import com.aloha.durudurub.dto.BoardImage;
import com.aloha.durudurub.dto.Club;
import com.aloha.durudurub.dto.ClubMember;
import com.aloha.durudurub.dto.Comment;
import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.service.BoardService;
import com.aloha.durudurub.service.ClubService;
import com.aloha.durudurub.service.CommentService;
import com.aloha.durudurub.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;



/**
 * 게시판 컨트롤러
 * 모임 내 게시판 - /club/{clubNo}/board
 */
@Slf4j
@Controller
@RequestMapping("/club/{clubNo}/board")
public class BoardController {
    
    @Autowired
    private BoardService boardService;
    
    @Autowired
    private ClubService clubService;
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private UserService userService;


    /**
     * 게시글 목록(list)
     * GET /club/{clubNo}/board
     */
    @GetMapping("")
    public String list(@PathVariable("clubNo") int clubNo,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        Principal principal,
                        Model model) {
        if (!isMember(clubNo, principal)) {
            return "redirect:/club/" + clubNo;
        }

        Club club = clubService.selectByNo(clubNo);
        List<Board> boards = boardService.listByClub(clubNo);
        List<Board> notices = boardService.listNotices(clubNo);

        model.addAttribute("club", club);
        model.addAttribute("boards", boards);
        model.addAttribute("notices", notices);

        return "board/list";
    }

    /**
     * 게시글 상세보기(detail)
     * GET /club/{clubNo}/board/{no}
     */
    @GetMapping("/{no}")
    public String detail(@PathVariable("clubNo") int clubNo,
                        @PathVariable("no") int no,
                        Principal principal,
                        Model model) {
        if (!isMember(clubNo, principal)) {
            return "redirect:/club/" + clubNo;
        }
        
        boardService.incrementViewCount(no);

        Club club = clubService.selectByNo(clubNo);
        Board board = boardService.selectByNo(no);
        List<Comment> comments = commentService.listByBoard(no);

        User currentUser = userService.selectByUserId(principal.getName());

        model.addAttribute("club", club);
        model.addAttribute("board", board);
        model.addAttribute("comments", comments);
        model.addAttribute("isWriter", board.getWriterNo() == currentUser.getNo());
        model.addAttribute("isHost", club.getHostNo() == currentUser.getNo());

        return "board/detail";
    }

    /**
     * 게시글 작성 페이지
     * GET /club/{clubNo}/board/write
     */
    @GetMapping("/write")
    public String write(@PathVariable("clubNo") int clubNo,
                        Principal principal,
                        Model model) {
        if (!isMember(clubNo, principal)) {
            return "redirect:/club/" + clubNo;
        }

        Club club = clubService.selectByNo(clubNo);
        User user = userService.selectByUserId(principal.getName());
        boolean isHost = club.getHostNo() == user.getNo();

        model.addAttribute("club", club);
        model.addAttribute("board", new Board());
        model.addAttribute("isHost", isHost);

        return "board/insert";
    }

    
    /**
     * 글쓰기 처리
     * POST /club/{clubNo}/board/write
     */    
    @PostMapping("/write")
    public String writePro(@PathVariable("clubNo") int clubNo,
                            Board board,
                            @RequestParam(value = "isNotice", defaultValue = "N") String isNotice,
                            @RequestParam(value = "images", required = false) List<MultipartFile> images,
                            Principal principal,
                            RedirectAttributes rttr) {
        if (!isMember(clubNo, principal)) {
            return "redirect:/club/" + clubNo;
        }
        
        User user = userService.selectByUserId(principal.getName());
        Club club = clubService.selectByNo(clubNo);

        board.setClubNo(clubNo);
        board.setWriterNo(user.getNo());

        if (club.getHostNo() == user.getNo()) {
            board.setIsNotice(isNotice);
        } else {
            board.setIsNotice("N");
        }
        
        // 이미지 파일 저장 처리
        List<BoardImage> boardImages = saveImages(images);
        
        int result = boardService.insert(board, boardImages);

        if (result > 0) {
            rttr.addFlashAttribute("message", "게시글이 작성되었습니다.");
            return "redirect:/club/" + clubNo + "/board/" + board.getNo();
        }

        rttr.addFlashAttribute("error", "요청이 실패했습니다.");
        return "redirect:/club/" + clubNo + "/board/write";
    }

    /**
     * 게시글 수정 페이지
     * GET /club/{clubNo}/board/{no}/edit
     */
    @GetMapping("/{no}/edit")
    public String edit(@PathVariable("clubNo") int clubNo,
                      @PathVariable("no") int no,
                      Principal principal,
                      Model model) {
        if (!isMember(clubNo, principal)) {
            return "redirect:/club/" + clubNo;
        }
        
        Board board = boardService.selectByNo(no);
        User user = userService.selectByUserId(principal.getName());
        Club club = clubService.selectByNo(clubNo);
        
        // 작성자 또는 호스트만 수정 가능
        if (board.getWriterNo() != user.getNo() && club.getHostNo() != user.getNo()) {
            return "redirect:/club/" + clubNo + "/board/" + no;
        }
        
        model.addAttribute("club", club);
        model.addAttribute("board", board);
        model.addAttribute("isHost", club.getHostNo() == user.getNo());
        
        return "board/update";
    }

    /**
     * 게시글 수정 처리
     * POST /club/{clubNo}/board/{no}/edit
     */
    @PostMapping("/{no}/edit")
    public String editPro(@PathVariable("clubNo") int clubNo,
                         @PathVariable("no") int no,
                         Board board,
                         @RequestParam(value = "isNotice", defaultValue = "N") String isNotice,
                         @RequestParam(value = "images", required = false) List<MultipartFile> images,
                         Principal principal,
                         RedirectAttributes rttr) {
        Board existingBoard = boardService.selectByNo(no);
        User user = userService.selectByUserId(principal.getName());
        Club club = clubService.selectByNo(clubNo);
        
        if (existingBoard.getWriterNo() != user.getNo() && club.getHostNo() != user.getNo()) {
            return "redirect:/club/" + clubNo + "/board/" + no;
        }
        
        board.setNo(no);
        if (club.getHostNo() == user.getNo()) {
            board.setIsNotice(isNotice);
        }
        
        // 새 이미지가 있으면 저장 후 기존 이미지 교체
        List<BoardImage> boardImages = saveImages(images);
        
        int result;
        if (boardImages != null && !boardImages.isEmpty()) {
            result = boardService.update(board, boardImages);
        } else {
            result = boardService.update(board);
        }
        
        if (result > 0) {
            rttr.addFlashAttribute("message", "게시글이 수정되었습니다.");
        } else {
            rttr.addFlashAttribute("error", "수정에 실패했습니다.");
        }
        
        return "redirect:/club/" + clubNo + "/board/" + no;
    }
    
    /**
     * 게시글 삭제
     * POST /club/{clubNo}/board/{no}/delete
     */
    @PostMapping("/{no}/delete")
    public String delete(@PathVariable("clubNo") int clubNo,
                        @PathVariable("no") int no,
                        Principal principal,
                        RedirectAttributes rttr) {
        Board board = boardService.selectByNo(no);
        User user = userService.selectByUserId(principal.getName());
        Club club = clubService.selectByNo(clubNo);
        
        if (board.getWriterNo() != user.getNo() && club.getHostNo() != user.getNo()) {
            return "redirect:/club/" + clubNo + "/board/" + no;
        }
        
        boardService.delete(no);
        rttr.addFlashAttribute("message", "게시글이 삭제되었습니다.");
        
        return "redirect:/club/" + clubNo + "/board";
    }
    
    /**
     * 이미지 파일 저장 유틸
     */
    private List<BoardImage> saveImages(List<MultipartFile> images) {
        List<BoardImage> boardImages = new ArrayList<>();
        if (images == null || images.isEmpty()) return boardImages;
        
        String uploadDir = System.getProperty("user.dir") + "/uploads/boards/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        
        for (MultipartFile file : images) {
            if (file.isEmpty()) continue;
            try {
                String originalName = file.getOriginalFilename();
                String ext = "";
                if (originalName != null && originalName.contains(".")) {
                    ext = originalName.substring(originalName.lastIndexOf("."));
                }
                String savedName = UUID.randomUUID().toString() + ext;
                File dest = new File(uploadDir + savedName);
                file.transferTo(dest);
                
                BoardImage boardImage = new BoardImage();
                boardImage.setImageUrl("/uploads/boards/" + savedName);
                boardImages.add(boardImage);
            } catch (IOException e) {
                log.error("게시글 이미지 업로드 실패", e);
            }
        }
        return boardImages;
    }

    /**
     * 멤버 여부 확인 (승인된 멤버 또는 호스트만 접근 가능)
     */
    private boolean isMember(int clubNo, Principal principal) {
        if (principal == null) return false;
        
        User user = userService.selectByUserId(principal.getName());
        
        // 호스트는 항상 접근 가능
        Club club = clubService.selectByNo(clubNo);
        if (club != null && club.getHostNo() == user.getNo()) {
            return true;
        }
        
        ClubMember member = clubService.selectMember(clubNo, user.getNo());
        
        return member != null && "APPROVED".equals(member.getStatus());
    }
}