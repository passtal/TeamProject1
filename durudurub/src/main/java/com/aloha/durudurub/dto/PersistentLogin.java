package com.aloha.durudurub.dto;

import java.util.Date;

import lombok.Data;

/**
 * 자동 로그인 DTO
 */
@Data
public class PersistentLogin {
    
    private int no;
    private String id;
    private String userId;
    private String token;
    private Date expiryDate;
    private Date createdAt;
    private Date updatedAt;
    
}