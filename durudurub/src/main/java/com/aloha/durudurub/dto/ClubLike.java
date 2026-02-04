package com.aloha.durudurub.dto;

import java.util.Date;

import lombok.Data;

/**
 * 모임 좋아요 DTO
 */

@Data
public class ClubLike {
    
    private int no;
    private int clubNo;
    private int userNo;
    private Date createdAt;
}