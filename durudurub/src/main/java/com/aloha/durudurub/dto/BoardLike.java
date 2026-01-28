package com.aloha.durudurub.dto;

import java.util.Date;

import lombok.Data;

/**
 * 게시글 좋아요 DTO
 */
@Data
public class BoardLike {

    private int no;
    private int boardNo;
    private int userNo;
    private Date createdAt;

}
