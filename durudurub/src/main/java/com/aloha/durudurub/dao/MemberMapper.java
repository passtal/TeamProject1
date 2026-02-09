package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    // 마이페이지: 내모임 관리
    // 참여 중인 모임 리스트
    List<Club> joinedClubList(@Param("userNo") int userNo) throws Exception;
    List<Club> pendingClubList(@Param("userNo") int userNo) throws Exception;

    // 마이페이지: 카운트 및 목록
    int countByUser(@Param("userNo") int userNo);
    int countByStatus(@Param("userNo") int userNo, @Param("status") String status);
    List<Club> myClubList(@Param("userNo") int userNo, @Param("status") String status);
}