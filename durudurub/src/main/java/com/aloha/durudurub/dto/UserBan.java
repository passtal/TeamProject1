package com.aloha.durudurub.dto;

import java.util.Date;

import lombok.Data;

/**
 * 회원 정지 DTO
 */

@Data
public class UserBan {
    
    private int no;
    private int userNo;
    private String reason;
    private int reportCountAtBan;
    private String banType;
    private Date banEndDate;
    private String isActive;
    private Date createdAt;
    private Date updatedAt;

    private User user;

}