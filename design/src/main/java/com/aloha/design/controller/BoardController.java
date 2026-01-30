package com.aloha.design.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    /**
     * 모임 상세보기 페이지
     */
    @GetMapping("/{id}")
    public String boardDetail(@PathVariable Long id, Model model) {
        // TODO: 모임 상세 정보 조회 로직 구현
        // 예시: Board board = boardService.findById(id);
        // TODO: 현재 로그인한 사용자가 리더인지 확인
        // 예시: boolean isLeader = board.getLeader().equals(currentUser);
        
        // 임시 데이터
        model.addAttribute("boardId", id);
        model.addAttribute("title", "독서 모임 - 매주 한 권 완독하기");
        model.addAttribute("category", "책");
        model.addAttribute("leader", "책벌레");
        model.addAttribute("currentMembers", 12);
        model.addAttribute("maxMembers", 15);
        model.addAttribute("likeCount", 42);
        model.addAttribute("location", "강남구");
        model.addAttribute("isLeader", false); // 테스트용: true로 변경하면 리더 화면
        
        return "layouts/board/board-detail";
    }

    /**
     * 모임 가입 처리
     */
    @GetMapping("/{id}/join")
    public String joinBoard(@PathVariable Long id, Model model) {
        // TODO: 모임 가입 로직 구현
        return "redirect:/board/" + id;
    }

    /**
     * 게시글 작성 페이지
     */
    @GetMapping("/{id}/post/write")
    public String writePost(@PathVariable Long id, Model model) {
        // TODO: 게시글 작성 페이지 로직 구현
        model.addAttribute("boardId", id);
        return "layouts/board/post-write";
    }
}
