package com.aloha.durudurub.dto;

import java.util.Date;

import lombok.Data;

// 관리자페이지 : 구독뱃지
@Data
public class AdminSubscription {
    private int userNo;
    private String userId;
    private String username;
    private Date createdAt;

    private String subStatus;
    private boolean isAdmin;
}
