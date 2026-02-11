package com.aloha.durudurub.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.Notice;

public interface NoticeService {
    // 공지사항 목록
    List<Notice> getNoticeList();
    // 공지사항 상세보기
    Notice getNotice(@Param("noticeNo") int noticeNo);
    // 공지사항 등록
    int createdNotice(Notice notice, int loginUserNo);
    // 공지사항 수정
    int updatedNotice(Notice notice);
    // 공지사항 삭제
    int deletedNotice(@Param("noticeNo") int noticeNo);
    // 조회수
    Notice getNoticeAndIncrease(@Param("noticeNo") int noticeNo);
}
