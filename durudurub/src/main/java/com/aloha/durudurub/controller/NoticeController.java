package com.aloha.durudurub.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aloha.durudurub.dto.Notice;

/**
 * 공지사항 컨트롤러
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {
    
    /**
     * 공지사항 목록 페이지
     */
    @GetMapping
    public String list(Model model) {
        // 테스트용 더미 데이터
        List<Notice> noticeList = new ArrayList<>();
        
        // 중요 공지 1
        Notice notice1 = new Notice();
        notice1.setNoticeNo(1);
        notice1.setTitle("두루두룹 서비스 정식 오픈 안내");
        notice1.setContent("두루두룹 서비스가 정식으로 오픈되었습니다.");
        notice1.setRegDate(LocalDate.of(2026, 1, 20));
        notice1.setViews(1245);
        notice1.setImportant(true);
        notice1.setTags(List.of("공지", "중요"));
        noticeList.add(notice1);
        
        // 중요 공지 2
        Notice notice2 = new Notice();
        notice2.setNoticeNo(2);
        notice2.setTitle("신규 회원 환영 이벤트 - 첫 모임 참여 시 포인트 지급!");
        notice2.setContent("첫 모임 참여 시 포인트를 지급합니다.");
        notice2.setRegDate(LocalDate.of(2026, 1, 22));
        notice2.setViews(856);
        notice2.setImportant(true);
        notice2.setTags(List.of("이벤트", "중요"));
        noticeList.add(notice2);
        
        // 일반 공지 1
        Notice notice3 = new Notice();
        notice3.setNoticeNo(3);
        notice3.setTitle("모임 게시글 사진 업로드 기능 추가 업데이트");
        notice3.setContent("사진 업로드 기능이 추가되었습니다.");
        notice3.setRegDate(LocalDate.of(2026, 1, 25));
        notice3.setViews(432);
        notice3.setImportant(false);
        notice3.setTags(List.of("업데이트"));
        noticeList.add(notice3);
        
        // 일반 공지 2
        Notice notice4 = new Notice();
        notice4.setNoticeNo(4);
        notice4.setTitle("서버 정기 점검 안내 (1월 27일 새벽 2시~4시)");
        notice4.setContent("서버 정기 점검이 예정되어 있습니다.");
        notice4.setRegDate(LocalDate.of(2026, 1, 26));
        notice4.setViews(678);
        notice4.setImportant(false);
        notice4.setTags(List.of("점검"));
        noticeList.add(notice4);
        
        // 일반 공지 3
        Notice notice5 = new Notice();
        notice5.setNoticeNo(5);
        notice5.setTitle("부적절한 게시글 및 모임 신고 기능 안내");
        notice5.setContent("신고 기능을 이용해주세요.");
        notice5.setRegDate(LocalDate.of(2026, 1, 24));
        notice5.setViews(523);
        notice5.setImportant(false);
        notice5.setTags(List.of("공지"));
        noticeList.add(notice5);
        
        model.addAttribute("noticeList", noticeList);
        
        return "notice/list";
    }
    
    /**
     * 공지사항 상세 페이지
     */
    @GetMapping("/{noticeNo}")
    public String detail(@PathVariable int noticeNo, Model model) {
        // 테스트용 더미 데이터
        Notice notice = new Notice();
        notice.setNoticeNo(noticeNo);
        notice.setTitle("두루두룹 서비스 정식 오픈 안내");
        notice.setContent("두루두룹 서비스가 정식으로 오픈되었습니다.\n\n많은 이용 부탁드립니다.");
        notice.setRegDate(LocalDate.of(2026, 1, 20));
        notice.setViews(1245);
        notice.setImportant(true);
        notice.setTags(List.of("공지", "중요"));
        notice.setWriterName("관리자");
        
        model.addAttribute("notice", notice);
        
        return "notice/detail";
    }
    
    /**
     * 공지사항 작성 페이지
     */
    @GetMapping("/insert")
    public String insertForm() {
        return "notice/insert";
    }
    
    /**
     * 공지사항 수정 페이지
     */
    @GetMapping("/update/{noticeNo}")
    public String updateForm(@PathVariable int noticeNo, Model model) {
        // 테스트용 더미 데이터 (실제로는 DB에서 조회)
        Notice notice = new Notice();
        notice.setNoticeNo(noticeNo);
        notice.setTitle("두루두룹 서비스 정식 오픈 안내");
        notice.setContent("두루두룹 서비스가 정식으로 오픈되었습니다.\n\n많은 이용 부탁드립니다.");
        notice.setRegDate(LocalDate.of(2026, 1, 20));
        notice.setViews(1245);
        notice.setImportant(true);
        notice.setTags(List.of("공지"));
        notice.setWriterName("관리자");
        
        model.addAttribute("notice", notice);
        
        return "notice/update";
    }
}
