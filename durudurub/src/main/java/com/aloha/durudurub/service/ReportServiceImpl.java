package com.aloha.durudurub.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.durudurub.dao.ReportMapper;
import com.aloha.durudurub.dao.UserMapper;
import com.aloha.durudurub.dto.Report;
import com.aloha.durudurub.dto.UserBan;

import lombok.RequiredArgsConstructor;

/**
 * 신고 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportMapper reportMapper;
    private final UserMapper userMapper;

    private static final int BAN_THRESHOLD = 5;
    private static final int TEMP_BAN_DAYS = 7;

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
    // 신고하기
    @Override
    public int insertUserReport(Report report) {
        return reportMapper.insertUserReport(report);
    }

    @Transactional
    @Override
    public Map<String, Object> reportUser(Report report) throws Exception {

        // 1) 신고 INSERT (중복이면 UNIQUE 때문에 DuplicateKeyException)
        try {
            reportMapper.insertUserReport(report);
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("이미 신고한 사용자입니다.");
        }

        // 2) users.report_count +1
        userMapper.increaseReportCount(report.getTargetNo());

        // 3) 최신 report_count 조회
        int newCount = userMapper.selectReportCount(report.getTargetNo());

        // 4) user_bans에 무조건 기록 남기기
        UserBan ban = new UserBan();
        ban.setUserNo(report.getTargetNo());
        ban.setReportCountAtBan(newCount);
        ban.setReason("신고 접수: " + report.getReason());

        // 5 이상이면 활성 차단, 아니면 비활성 기록
        if (newCount >= 5) {
            ban.setBanType("TEMPORARY");
            ban.setIsActive("Y");

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.DATE, 7);
            ban.setBanEndDate(cal.getTime());
        } else {
            ban.setBanType("TEMPORARY"); // 또는 null/기본값
            ban.setIsActive("N");        // 아직 차단 X
            ban.setBanEndDate(null);
        }

        userMapper.insertUserBan(ban);

        boolean banned = (newCount >= 5);

        Map<String, Object> result = new HashMap<>();
        result.put("reportCount", newCount);
        result.put("banned", banned);
        return result;
    }
}
