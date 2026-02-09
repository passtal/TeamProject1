package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aloha.durudurub.dto.Report;
import com.aloha.durudurub.dto.UserBan;

/**
 * 신고 매퍼
 */
@Mapper
public interface ReportMapper {
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
   // 신고하기
   int insertUserReport(Report report);
}
