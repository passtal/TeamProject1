package com.aloha.durudurub.dto;

import java.util.Date;

import lombok.Data;

/**
 * 댓글 좋아요 DTO
 */
@Data
public class CommentLike {
    
    private int no;
    private int commentNo;
    private int userNo;
    private Date createdAt;
    
}