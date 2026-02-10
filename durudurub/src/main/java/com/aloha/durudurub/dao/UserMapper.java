package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.AdminSubscription;
import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.dto.UserBan;

/**
 * 회원 매퍼
 */
@Mapper
public interface UserMapper {
    
    List<User> list();
    
    User selectByNo(@Param("no") int no);
    
    User selectByUserId(@Param("userId") String userId);
    
    User selectByUsername(@Param("username") String username);
    
    int insert(User user);
    
    int update(User user);
    
    int delete(@Param("no") int no);
    
    int updatePassword(@Param("no") int no, @Param("password") String password);
    
    int updateProfileImg(@Param("no") int no, @Param("profileImg") String profileImg);
    
    int countByUserId(@Param("userId") String userId);
    
    int countByUsername(@Param("username") String username);

    // 관리자페이지
    // 전체 이용자 수
    int countAll();
    // 최신 가입자 수
    int countNew();
    // 최신 가입일 계산
    User findLastestUser();
    // 사용자 리스트(구독 여부)
    List<AdminSubscription> userList();

    // 신고하기
    int increaseReportCount(@Param("targetNo") int targetNo);
    int selectReportCount(@Param("targetNo") int targetNo);
    // 차단 기록
    int countActiveBan(@Param("userNo") int userNo);
    int insertUserBan(UserBan userBan);
}
