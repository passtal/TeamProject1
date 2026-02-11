package com.aloha.durudurub.dto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import lombok.Data;

/**
 * 공지사항 DTO
 */
@Data
public class Notice {
    private int noticeNo;           // 공지사항 번호
    private int writerNo;           // 작성자 번호 (관리자 1번)
    private String title;           // 제목
    private String content;         // 내용
    private LocalDate regDate;      // 등록일
    private int views;              // 조회수
    private boolean important;      // 중요 공지 여부 - 변수명 수정!
    private List<String> category;      // 카테고리 태그 (공지, 이벤트, 업데이트, 점검) - 변수명 수정!
    private String categoryString;      // 카테고리 태그 (공지, 이벤트, 업데이트, 점검) - 변수명 수정!
    private String writerName;      // 작성자명
    
}
