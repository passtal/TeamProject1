package com.aloha.durudurub.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.CONFLICT,"이미 신고한 사용자입니다.");
        }

        // 2) users.report_count +1
        userMapper.increaseReportCount(report.getTargetNo());

        // 3) 최신 report_count 조회
        int newCount = userMapper.selectReportCount(report.getTargetNo());

        
        // 5 이상이면 활성 차단
        // 7 이상 빨간 뱃지 - 관리자 직접 사용자 삭제! (버튼)
        boolean banned = (newCount >= 5);

        // 4) user_bans 갱신
        Map<String, Object> param = new HashMap<>();
        param.put("userNo", report.getTargetNo());
        param.put("reportCountAtBan", newCount);
        param.put("reason", "신고 접수: " + report.getReason());
        param.put("banType", "TEMPORARY");


        Date banEndDate = null;
        String isActive = "N";

        if (banned) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.DATE, 7);
            banEndDate = cal.getTime();

            isActive = "Y"; // 5회 이상 차단! - 관리자 해결!
        }
        param.put("isActive", isActive);
        param.put("banEndDate", banEndDate);

        // 갱신 호출
        reportMapper.updateUserReport(param);

        Map<String, Object> result = new HashMap<>();
        result.put("reportCount", newCount);
        result.put("banned", banned);
        return result;
    }
}
