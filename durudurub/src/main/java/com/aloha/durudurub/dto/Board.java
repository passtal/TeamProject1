package com.aloha.durudurub.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 게시글 DTO
 */
@Data
public class Board {
    
    private int no;
    private int clubNo;
    private int writerNo;
    private String title;
    private String content;
    private int viewCount;
    private int likeCount;
    private int commentCount;
    private String isNotice;
    private Date createdAt;
    private Date updatedAt;

    private User writer;    // 작성자 누군지 보이기
    private Club club;      // 무슨 모임인지 보이기
    private List<BoardImage> imageList;     // 게시판 이미지나오게하는 기능에 참조
    private List<Comment> commentList;      // 댓글 나오게하는 기능에 참조
    private boolean liked;        // 좋아요 눌렀는지 여부 (인스타처럼 하트 두번 누르면 취소되게 할 예정)
    
}