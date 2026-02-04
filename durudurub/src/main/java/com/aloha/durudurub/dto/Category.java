package com.aloha.durudurub.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 카테고리 DTO
 */

@Data
public class Category {
    
    private int no;
    private String name;
    private String description;
    private String icon;
    private int seq;
    private Date createdAt;
    private Date updatedAt;

    private List<SubCategory> subCategoryList;      // 하위카테고리 (DML로 넣었던거 보여준다는 느낌)
    
}