package com.aloha.durudurub.dto;

import java.util.Date;

import lombok.Data;

/**
 * 배너 DTO
 */
@Data
public class Banner {
    
    private int no;
    private String title;
    private String imageUrl;
    private String linkUrl;
    private String isActive;
    private Date startDate;
    private Date endDate;
    private int seq;
    private int clickCount;
    private Date createdAt;
    private Date updatedAt;

}