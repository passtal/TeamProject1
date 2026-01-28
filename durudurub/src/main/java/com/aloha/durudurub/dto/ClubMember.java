package com.aloha.durudurub.dto;

import java.util.Date;

import lombok.Data;

/**
 * 모임 참가자 DTO
 */

@Data
public class ClubMember {
    
    private int no;
    private int clubNo;
    private int userNo;
    private String status;
    private Date joinedAt;

    private User user;
    private Club club;
    
}
