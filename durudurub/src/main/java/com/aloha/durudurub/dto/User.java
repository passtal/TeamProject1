package com.aloha.durudurub.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 회원 DTO
 */
@Data
public class User {
    
    private int no;
    private String id;
    private String userId;
    private String username;
    private String password;
    private String address;
    private String gender;
    private String profileImg;
    private int age;
    private int reportCount;
    private Date createdAt;
    private Date updatedAt;

    private List<Auth> authList;     // 권한 목록
}
