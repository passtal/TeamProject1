package com.aloha.durudurub.dto;

import java.util.Date;

import lombok.Data;

/**
 * 신고 DTO
 */

@Data
public class Report {
    
    private int no;
    private int clubNo;
    private int reporterNo;
    private int targetNo;
    private String reason;
    private Date createdAt;

    private User reporter;
    private User target;
    private Club club;

}