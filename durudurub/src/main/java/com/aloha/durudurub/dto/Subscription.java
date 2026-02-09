package com.aloha.durudurub.dto;

import java.util.Date;

import lombok.Data;

/**
 * 구독 DTO
 */
@Data
public class Subscription {

    private int no;
    private int userNo;
    private String status;
    private String planName;
    private Date startDate;
    private Date endDate;
    private String autoRenew;
    private Date createdAt;
    private Date updatedAt;
}
