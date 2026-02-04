package com.aloha.durudurub.dto;

import java.util.Date;

import lombok.Data;

/**
 * 서브 카테고리 DTO
 */
@Data
public class SubCategory {
    
    private int no;
    private int categoryNo;
    private String name;
    private int seq;
    private Date createdAt;
    private Date updatedAt;

    private Category category;      // 대분류 카테고리 표시
}
