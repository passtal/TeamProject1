package com.aloha.durudurub.service;

import java.util.List;

import com.aloha.durudurub.dto.UserBan;

/**
 * 신고 서비스
 */
public interface ReportService {
   // 관리자 : 신고 관리
   List<UserBan> listByTarget();
   // 7일 만료 삭제
   int deleteExpired();
   // 대시보드: 총 신고 수
   int countList();
   // 대시보드: 신고 접수
   int countNewReports();
   // 대시보드: 최신신고(신고일)
   UserBan findLastestUserBan();
}
