package com.aloha.durudurub.dto;

import java.util.Date;

import lombok.Data;

/**
 * 댓글 DTO
 */
@Data
public class Comment {
    
    private int no;
    private int boardNo;
    private int writerNo;
    private String content;
    private int likeCount;
    private Date createdAt;
    private Date updatedAt;

    private User writer;        // 작성자 정보 드러내기
    private boolean isLiked;    // 좋아요 눌렀는지 여부 (인스타처럼 하트 두번 누르면 취소되게 할 예정)
}
