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

    List<Club> listByHost(@Param("HostNo") int hostNo);

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

    // AI 검색용
    List<Club> search(@Param("keyword") String keyword);

}