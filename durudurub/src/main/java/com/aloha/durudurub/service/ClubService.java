package com.aloha.durudurub.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

// import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.Club;
import com.aloha.durudurub.dto.ClubMember;

/**
 * 모임 서비스
 */
public interface ClubService {
    
    List<Club> list();

    List<Club> listByCategory(int categoryNo);

    List<Club> listBySubCategory(int subCategoryNo);

    List<Club> listRecent(int limit);

    Club selectByNo(int no);

    int insert(Club club);

    int update(Club club);

    int delete(int no);

    int incrementViewCount(int no);

    int updateStatus(int no, String status);

    List<ClubMember> listMembers(int clubNo);

    List<ClubMember> listApproveMembers(int clubNo);

    List<ClubMember> listPendingMembers(int clubNo);

    List<ClubMember> listByUser(int userNo);

    int joinClub(int clubNo, int userNo);

    int approveMember(int memberNo);

    int rejectMember(int memeberNo);

    int leaveClub(int clubNo, int userNo);

    boolean isMember(int clubNo, int userNo);

    ClubMember getMemberStatus(int clubNo, int userNo);

    int count();

    List<Club> listLatest(int limit);

    ClubMember selectMember(int clubNo, int userNo);

    int insertMember(ClubMember member);

    int deleteMember(int clubNo, int userNo);

    int updateMemberStatus(int memberNo, String status);

    int incrementMemberCount(int clubNo);

    int decrementMemberCount(int clubNo);

    // 마이페이지: 내모임 관리
    // 참여 중인 모임
    List<Club> joinedClubList(int userNo) throws Exception;
    // 탈퇴하기
    @Transactional  // 멤버 삭제 및 인원 수 감소
    int deleteByClubAndUser(int clubNo, int userNo);
    // 승인 대기 중인 모임
    List<Club> pendingClubList(int userNo) throws Exception;
    // 모임장별 모임 목록
    List<Club> listByHost(int hostNo) throws Exception;
}