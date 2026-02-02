package com.aloha.durudurub.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

/**
 * 공지사항 DTO
 */
@Data
public class Notice {
    private int noticeNo;           // 공지사항 번호
    private String title;           // 제목
    private String content;         // 내용
    private LocalDate regDate;      // 등록일
    private int views;              // 조회수
    private boolean important;      // 중요 공지 여부
    private List<String> tags;      // 카테고리 태그 (공지, 이벤트, 업데이트, 점검)
    private String writerName;      // 작성자명
    
    public Notice() {
    }
    
    public Notice(int noticeNo, String title, String content, LocalDate regDate, 
                  int views, boolean important, List<String> tags) {
        this.noticeNo = noticeNo;
        this.title = title;
        this.content = content;
        this.regDate = regDate;
        this.views = views;
        this.important = important;
        this.tags = tags;
    }
}
