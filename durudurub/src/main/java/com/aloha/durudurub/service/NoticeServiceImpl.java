package com.aloha.durudurub.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.durudurub.dao.NoticeMapper;
import com.aloha.durudurub.dto.Notice;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeMapper noticeMapper;

    @Override
    public List<Notice> getNoticeList() {
        return noticeMapper.noticeList();
    }

    @Override
    public Notice getNotice(int noticeNo) {
        return noticeMapper.noticeSelect(noticeNo);
    }
    // ---- 관리자권한
    // 등록
    @Override
    public int createdNotice(Notice notice, int loginUserNo) {
        // 관리자 번호 1번
        notice.setWriterNo(loginUserNo);
        noticeMapper.noticeInsert(notice);
        return notice.getNoticeNo();
    }
    // 수정
    @Override
    public int updatedNotice(Notice notice) {
        return noticeMapper.noticeUpdate(notice);
    }
    // 삭제
    @Override
    public int deletedNotice(int noticeNo) {
        return noticeMapper.noticeDelete(noticeNo);
    }
    // 조회수
    @Override
    @Transactional
    public Notice getNoticeAndIncrease(int noticeNo) {
        noticeMapper.increaseViews(noticeNo);
        return noticeMapper.noticeSelect(noticeNo);
    }
    
}
