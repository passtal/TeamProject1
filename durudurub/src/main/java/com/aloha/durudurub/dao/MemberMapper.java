package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import com.aloha.durudurub.dto.Club;
import com.aloha.durudurub.dto.ClubMember;

/**
 * 모임 멤버 Mapper
 */
@Mapper
public interface MemberMapper {
    
    List<ClubMember> listByClub(@Param("clubNo") int clubNo);
    
    List<ClubMember> listByUser(@Param("userNo") int userNo);
    
    List<ClubMember> listApproved(@Param("clubNo") int clubNo);
    
    List<ClubMember> listPending(@Param("clubNo") int clubNo);
    
    ClubMember selectByNo(@Param("no") int no);
    
    ClubMember selectByClubAndUser(@Param("clubNo") int clubNo, @Param("userNo") int userNo);
    
    int insert(ClubMember member);
    
    int updateStatus(@Param("no") int no, @Param("status") String status);
    
    int delete(@Param("no") int no);
    
    int deleteByClubAndUser(@Param("clubNo") int clubNo, @Param("userNo") int userNo);
    
    boolean countByClubAndUser(@Param("clubNo") int clubNo, @Param("userNo") int userNo);

    // 내모임관리
    // 가입 신청 취소
    int cancelPending(@Param("clubNo") int clubNo, @Param("userNo") int userNo);
}