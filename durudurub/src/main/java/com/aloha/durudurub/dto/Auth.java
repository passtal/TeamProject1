package com.aloha.durudurub.dto;

import java.util.Date;

import lombok.Data;

/**
 * 회원 권한 DTO
 */

@Data
public class Auth {
    private int no;
    private int userNo;
    private String auth;
    private Date createdAt;
    private Date updatedAt;
}