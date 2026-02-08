package com.aloha.durudurub.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
    private String position;
    private String description;
    private String isActive;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private int seq;
    private int clickCount;
    private Date createdAt;
    private Date updatedAt;

}