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
    public List<Club> search(String keyword) {
        return clubMapper.search(keyword);
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
        return clubMapper.decrementCurrentMembers(clubNo);
    }


    // 마이페이지: 내모임 관리
    // 모임리스트 (승인, 리더, 대기)
    @Override
    public List<Club> myClubList(int userNo, String status) throws Exception {
        return clubMapper.myClubList(userNo, status);
    }
    // 모임리스트 수
    @Override
    public int countByStatus(int userNo, String status) throws Exception {
        return clubMapper.countByStatus(userNo, status);
    }
    // 전체 모임개수
    @Override
    public int countByUser(int userNo) throws Exception {
        return clubMapper.countByUser(userNo);
    }
    // 탈퇴하기 - 참여
    @Override
    public int deleteByClubAndUser(int clubNo, int userNo) {
        return memberMapper.deleteByClubAndUser(clubNo, userNo);
    }

    // 모임리스트 - 리더
    @Override
    public List<Club> listByHost(int hostNo) {
        return clubMapper.listByHost(hostNo);
    }
    // 모임 삭제 - 리더
    @Override
    @Transactional
    public int deleteClub(int clubNo) throws Exception {
        // 1. 자식 삭제
        memberMapper.deleteMembersByClubNo(clubNo);
        // 2. 부모 삭제
        return clubMapper.deleteClub(clubNo);
    }
    // 모임 승인 - 리더
    @Override
    @Transactional
    public int approved(int clubNo, int userNo) throws Exception {
        int result = memberMapper.approved(clubNo, userNo);
        if (result > 0) {
            clubMapper.incrementCurrentMembers(clubNo);
        }
        return result;
    }
    // 모임 거부 - 리더
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int rejectMember(int clubNo, int userNo) throws Exception {
        return memberMapper.rejectMember(clubNo, userNo);
    }
    // 모임 추방 - 리더
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeMember(int clubNo, int userNo) throws Exception {
        int result = memberMapper.removeMember(clubNo, userNo);
        if (result > 0) {
            clubMapper.decrementCurrentMembers(clubNo);
        }
        return result;
    }

    // 승인 대기
    // 가입 신청 취소
    @Override
    public int cancelPending(int clubNo, int userNo) {
        return memberMapper.cancelPending(clubNo, userNo);
    }

    // 관리자페이지
    // 1. 대시보드
    @Override
    public Club findLatestClub() {
        return clubMapper.findLatestClub();
    }
}

    



    
