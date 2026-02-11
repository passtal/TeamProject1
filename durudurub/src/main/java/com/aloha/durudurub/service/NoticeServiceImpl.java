package com.aloha.durudurub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aloha.durudurub.dao.AuthMapper;
import com.aloha.durudurub.dao.NoticeMapper;
import com.aloha.durudurub.dto.Auth;
import com.aloha.durudurub.dto.Notice;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeMapper noticeMapper;
    private final AuthMapper authMapper;

    @Override
    public List<Notice> getNoticeList() {
        return noticeMapper.noticeList();
    }

    @Override
    public Notice getnotice(int noticeNo) {
        return noticeMapper.noticeSelect(noticeNo);
    }
    // ---- 관리자권한
    // 등록
    @Override
    public int createNotice(Notice notice, int loginUserNo) {
        // 관리자 번호 1번
        notice.setWriterNo(loginUserNo);
        return noticeMapper.noticeInsert(notice);
    }
    // 수정
    @Override
    public int updateNotice(Notice notice, int loginUserNo) {
        // 관리자 번호 1번
        notice.setWriterNo(loginUserNo);
        return noticeMapper.noticeUpdate(notice);
    }
    // 삭제
    @Override
    public int deleteNotice(int noticeNo, int loginUserNo) {
        return noticeMapper.noticeDelete(noticeNo);
    }
    
}
