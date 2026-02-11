package com.aloha.durudurub.dto;

import java.util.Date;

import lombok.Data;

/**
 * 결제 DTO
 */
@Data
public class Payment {

    private int no;
    private int userNo;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private int amount;
    private String currency;
    private String type;
    private String status;
    private Date requestedAt;
    private Date approvedAt;
    private Date createdAt;
    private Date updatedAt;
}
