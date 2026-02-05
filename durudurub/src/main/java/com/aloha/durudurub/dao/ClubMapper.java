package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.Club;

/**
 * 모임 매퍼
 */
@Mapper
public interface ClubMapper {

    List<Club> list();

    List<Club> listByCategory(@Param("categoryNo") int categoryNo);

    List<Club> listBySubCategory(@Param("subCategoryNo") int subCategoryNo);

    List<Club> listByHost(@Param("hostNo") int hostNo);

    List<Club> listRecent(@Param("limit") int limit);

    Club selectByNo(@Param("no") int no);

    int insert(Club club);
    
    int update(Club club);

    int delete(@Param("no") int no);

    int incrementViewCount(@Param("no") int no);

    int incrementLikeCount(@Param("no") int no);

    int decrementLikeCount(@Param("no") int no);

    int incrementCurrentMembers(@Param("no") int no);

    int decrementCurrentMembers(@Param("no") int no);

    int updateStatus(@Param("no") int no, @Param("status") String status);

    int count();

    List<Club> listUpcoming(@Param("limit") int limit);

    int decrementCommentCount(int clubNo);


    // 마이페이지: 내모임 관리
    // 참여 중인 모임 리스트
    List<Club> joinedClubList(@Param("userNo") int userNo) throws Exception;
    // 승인 대기 중인 모임 리스트
    List<Club> pendingClubList(@Param("userNo") int userNo) throws Exception;
}