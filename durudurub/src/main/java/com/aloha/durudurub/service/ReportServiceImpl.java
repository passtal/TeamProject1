package com.aloha.durudurub.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.durudurub.dao.ReportMapper;
import com.aloha.durudurub.dto.UserBan;

import lombok.RequiredArgsConstructor;

/**
 * 신고 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportMapper reportMapper;

    // 관리자 : 신고 관리
    @Override
    public List<UserBan> listByTarget() {
        return reportMapper.listByTarget();
    }
    // 7일 만료 삭제
    @Override
    @Transactional
    public int deleteExpired() {
        return reportMapper.deleteExpired();
    }
    // 대시보드 : 총 신고 수
    @Override
    public int countList() {
        return reportMapper.countList();
    }
    // 대시보드: 신고 접수 활동
    @Override
    public int countNewReports() {
        return reportMapper.countNewReports();
    }
    // 대시보드: 최신신고(신고일)
    @Override
    public UserBan findLastestUserBan() {
        return reportMapper.findLastestUserBan();
    }
}
