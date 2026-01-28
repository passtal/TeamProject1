package com.aloha.durudurub.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aloha.durudurub.dto.Board;
import com.aloha.durudurub.dto.Club;
import com.aloha.durudurub.dto.Comment;
import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.service.BoardService;
import com.aloha.durudurub.service.ClubService;
import com.aloha.durudurub.service.CommentService;
import com.aloha.durudurub.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



/**
 * 게시판 컨트롤러
 */
@Controller
@RequestMapping("/board")
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
     * @param param
     * @return
     */
    @GetMapping("/board/list")
    public String list(@PathVariable ("clubNo") int clubNo,
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
     * @param param
     * @return
     */
    @GetMapping("/board/{no}")
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
        model.addAttribute("isWriter", board.getWriter());
        model.addAttribute("isHost", club.getHost());

        return "board/detail";
    }

    /**
     * 게시글 작성 페이지
     * @param param
     * @return
     */
    @GetMapping("/write")
    public String write(@PathVariable("clubNo") int clubNo,
                        Principal principal,
                        Model model) {
        if (!isMember(clubNo, principal)) {
            return "redirect:/club/" + clubNo;
        }

        Club club = clubService.selectByNo(clubNo);
        User user = userService.selectByUserId(principal);
        boolean isHost = club.getHostNo() == user.getNo();

        model.addAttribute("club", club);
        model.addAttribute("board", new Board());
        model.addAttribute("isHost", isHost);

        return "board/write";
    }

    
    /**
     * 글쓰기 처리
     * @param entity
     * @return
     */    
    @PostMapping("/write")
    public String writePro(@PathVariable("clubNo") int clubNo,
                            Board board,
                            @RequestParam(value = "isNotice", defaultValue = "N") String isNotice,
                            Principal principal,
                            RedirectAttributes rttr) {
        if (!isMember(clubNo, principal)) {
            return "redirect:/club/" + clubNo;
        }
        
        User user = userService.selectByUserId(principal);
        Club club = clubService.selectByNo(clubNo);

        board.setClubNo(clubNo);
        board.setWriterNo(user.getNo());

        if (club.getHostNo() == user.getNo()) {
            board.setIsNotice(isNotice);
        } else {
            board.setIsNotice("N");
        }
        
        int result = boardService.insert(board);

        if (result > 0) {
            rttr.addAttribute("message", "게시글이 작성되었습니다.");
            return "redirect:/club/" + clubNo + "/board/" + board.getNo();
        }

        rttr.addAttribute("error", "요청이 실패했습니다.");
        return "redirect:/club/" + clubNo + "board/write";
    }

    /**
     * TODO - 수정페이지, 수정기능 페이지, 삭제페이지 (기능필없음)
     */
    
    
}