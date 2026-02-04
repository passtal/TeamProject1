package com.aloha.durudurub.dto;

import lombok.Data;

/**
 * 게시글 이미지 DTO
 */
@Data
public class BoardImage {

    private int no;
    private int boardNo;
    private String imageUrl;
    private int seq;
    
}
