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
    private Integer subscriptionNo;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private int amount;
    private String method;
    private String status;
    private Date approvedAt;
    private Date canceledAt;
    private String cancelReason;
    private String receiptUrl;
    private Date createdAt;
    private Date updatedAt;
}
