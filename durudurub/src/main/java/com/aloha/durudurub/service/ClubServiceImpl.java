package com.aloha.durudurub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.durudurub.dao.ClubMapper;
import com.aloha.durudurub.dao.MemberMapper;
import com.aloha.durudurub.dto.Club;
import com.aloha.durudurub.dto.ClubMember;

/**
 * 모임 서비스 구현체
 */
@Service
public class ClubServiceImpl implements ClubService {

    @Autowired
    private ClubMapper clubMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<Club> list() {
        return clubMapper.list();
    }

    @Override
    public List<Club> listByCategory(int categoryNo) {
        return clubMapper.listByCategory(categoryNo);
    }

    @Override
    public List<Club> listBySubCategory(int subCategoryNo) {
        return clubMapper.listBySubCategory(subCategoryNo);
    }

    @Override
    public List<Club> listRecent(int limit) {
        return clubMapper.listRecent(limit);
    }

    @Override
    public Club selectByNo(int no) {
        return clubMapper.selectByNo(no);
    }

    @Override
    public int insert(Club club) {
        int result = clubMapper.insert(club);

        if (result > 0) {
            ClubMember member = new ClubMember();
            member.setClubNo(club.getNo());
            member.setUserNo(club.getHostNo());
            member.setStatus("APPROVED");
            memberMapper.insert(member);
        }
        return result;
    }

    @Override
    public int update(Club club) {
        return clubMapper.update(club);
    }

    @Override
    public int delete(int no) {
        return clubMapper.delete(no);
    }

    @Override
    public int incrementViewCount(int no) {
        return clubMapper.incrementViewCount(no);
    }

    @Override
    public int updateStatus(int no, String status) {
        return clubMapper.updateStatus(no, status);
    }

    @Override
    public List<ClubMember> listMembers(int clubNo) {
        return memberMapper.listByClub(clubNo);
    }

    @Override
    public List<ClubMember> listApproveMembers(int clubNo) {
        return memberMapper.listApproved(clubNo);
    }

    @Override
    public List<ClubMember> listPendingMembers(int clubNo) {
        return memberMapper.listPending(clubNo);
    }

    @Override
    public List<ClubMember> listByUser(int userNo) {
        return memberMapper.listByUser(userNo);
    }

    @Override
    public int joinClub(int clubNo, int userNo) {
        ClubMember member = new ClubMember();
        member.setClubNo(clubNo);
        member.setUserNo(userNo);
        member.setStatus("PENDING");
        return memberMapper.insert(member);
    }

    @Override
    public int approveMember(int memberNo) {
        ClubMember member = memberMapper.selectByNo(memberNo);
        if (member != null) {
            clubMapper.incrementCurrentMembers(member.getClubNo());
        }
        return memberMapper.updateStatus(memberNo, "APPROVED");
    }

    @Override
    public int rejectMember(int memberNo) {
        return memberMapper.updateStatus(memberNo, "REJECTED");
    }

    @Override
    @Transactional
    public int leaveClub(int clubNo, int userNo) {
        clubMapper.decrementCurrentMembers(clubNo);
        return memberMapper.deleteByClubAndUser(clubNo, userNo);
    }

    @Override
    public boolean isMember(int clubNo, int userNo) {
        return memberMapper.countByClubAndUser(clubNo, userNo);
    }

    @Override
    public ClubMember getMemberStatus(int clubNo, int userNo) {
        return memberMapper.selectByClubAndUser(clubNo, userNo);
    }

    @Override
    public int count() {
        return clubMapper.count();
    }

    @Override
    public List<Club> listLatest(int limit) {
        return clubMapper.listRecent(limit);
    }

    @Override
    public ClubMember selectMember(int clubNo, int userNo) {
        return memberMapper.selectByClubAndUser(clubNo, userNo);
    }

    @Override
    public int insertMember(ClubMember member) {
        return memberMapper.insert(member);
    }

    @Override
    public int deleteMember(int clubNo, int userNo) {
        return memberMapper.deleteByClubAndUser(clubNo, userNo);
    }

    @Override
    public int updateMemberStatus(int memberNo, String status) {
        return memberMapper.updateStatus(memberNo, status);
    }

    @Override
    public int incrementMemberCount(int clubNo) {
        return clubMapper.incrementCurrentMembers(clubNo);
    }

    @Override
    public int decrementMemberCount(int clubNo) {
        return clubMapper.decrementCommentCount(clubNo);
    }

    // 마이페이지: 내모임 관리
    // 참여중인 모임
    @Override
    public List<Club> joinedClubList(int userNo) throws Exception {
        return memberMapper.joinedClubList(userNo);
    }
    // 승인 대기 중인 모임
    @Override
    public List<Club> pendingClubList(int userNo) throws Exception {
        return memberMapper.pendingClubList(userNo);
    }
    // 모임장별 모임 목록
    @Override
    public List<Club> listByHost(int hostNo) {
        return clubMapper.listByHost(hostNo);
    }

}