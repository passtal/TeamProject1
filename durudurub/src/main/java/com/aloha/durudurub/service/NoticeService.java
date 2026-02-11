package com.aloha.durudurub.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.Notice;

public interface NoticeService {
    // 공지사항 목록
    List<Notice> getNoticeList();
    // 공지사항 상세보기
    Notice getnotice(@Param("noticeNo") int noticeNo);
    // 공지사항 등록
    int createNotice(Notice notice, int loginUserNo);
    // 공지사항 수정
    int updateNotice(Notice notice, int loginUserNo);
    // 공지사항 삭제
    int deleteNotice(@Param("noticeNo") int noticeNo, int loginUserNo);
}
