package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.BoardLike;
import com.aloha.durudurub.dto.Club;
import com.aloha.durudurub.dto.ClubLike;
import com.aloha.durudurub.dto.CommentLike;

/**
 * 좋아요 매퍼
 */
@Mapper
public interface LikeMapper {
    
    int insertClubLike(ClubLike like);
    
    int deleteClubLike(@Param("clubNo") int clubNo, @Param("userNo") int userNo);
    
    int countClubLike(@Param("clubNo") int clubNo, @Param("userNo") int userNo);
    
    int countClubLikeByClub(@Param("clubNo") int clubNo);
    
    List<ClubLike> listClubLikeByUser(@Param("userNo") int userNo);
    
    int insertBoardLike(BoardLike like);
    
    int deleteBoardLike(@Param("boardNo") int boardNo, @Param("userNo") int userNo);
    
    int countBoardLike(@Param("boardNo") int boardNo, @Param("userNo") int userNo);
    
    int countBoardLikeByBoard(@Param("boardNo") int boardNo);
    
    int insertCommentLike(CommentLike like);
    
    int deleteCommentLike(@Param("commentNo") int commentNo, @Param("userNo") int userNo);
    
    int countCommentLike(@Param("commentNo") int commentNo, @Param("userNo") int userNo);
    
    int countCommentLikeByComment(@Param("commentNo") int commentNo);
    
    int countClubLikes(@Param("clubNo") int clubNo);
    
    int countBoardLikes(@Param("boardNo") int boardNo);
    
    int countCommentLikes(@Param("commentNo") int commentNo);

    // 즐겨찾기 모임리스트
    List<Club> favoriteList(@Param("userNo") int userNo) throws Exception;
    // 즐겨찾기 수
    int countClubLikeByUser(@Param("userNo") int userNo) throws Exception;

}