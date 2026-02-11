package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.Notice;

@Mapper
public interface NoticeMapper {
    // 공지사항 목록
    List<Notice> noticeList();
    // 공지사항 상세보기
    Notice noticeSelect(@Param("noticeNo") int noticeNo);
    // 공지사항 등록
    int noticeInsert(Notice notice);
    // 공지사항 수정
    int noticeUpdate(Notice notice);
    // 공지사항 삭제
    int noticeDelete(@Param("noticeNo") int noticeNo);
    // 조회수
    int increaseViews(@Param("noticeNo") int noticeNo);
}
