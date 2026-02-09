package com.aloha.durudurub.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiSearchLog {
    private int no;
    private int userNo;
    private String keyword;
    private String userMessage;
    private int resultCount;
    private Date searchedAt;
}
