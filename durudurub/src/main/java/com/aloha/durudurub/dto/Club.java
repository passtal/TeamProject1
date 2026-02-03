package com.aloha.durudurub.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 모임 DTO
 */
@Data
public class Club {
    
    private int no;
    private int hostNo;
    private int categoryNo;
    private Integer subCategoryNo;
    private String title;
    private String description;
    private String thumbnailImg;
    private int maxMembers;
    private int currentMembers;
    private Date deadLine;
    private String location;
    private Double lat;
    private Double lng;
    private Date clubDate;
    private String status;
    private int viewCount;
    private int likeCount;
    private Date createdAt;
    private Date updatedAt;

    private User host;
    private Category category;
    private SubCategory subCategory;
    private List<ClubMember> memberList;
    private boolean isLiked;
}