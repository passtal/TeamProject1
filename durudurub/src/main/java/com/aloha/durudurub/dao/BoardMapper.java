package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.Board;
import com.aloha.durudurub.dto.BoardImage;

/**
 * 게시글 매퍼
 */
@Mapper
public interface BoardMapper {
    
    List<Board> listByClub(@Param("clubNo") int clubNo);

    List<Board> listNoticeByClub(@Param("clubNo") int clubNo);

    List<Board> listByWriter(@Param("writerNo") int clubNo);

    List<Board> searchByClub(@Param("clubNo") int clubNo, @Param("keyword") String keyword);

    Board selectByNo(@Param("no") int no);

    int insert(Board board);

    int update(Board board);

    int delete(@Param("no") int no);

    int incrementViewCount(@Param("no") int no);

    int incrementLikeCount(@Param("no") int no);

    int decrementLikeCount(@Param("no") int no);

    int incrementCommentCount(@Param("no") int no);
    
    int decrementCommentCount(@Param("no") int no);

    List<BoardImage> listImageByBoard(@Param("boardNo") int boardNo);

    int insertImage(BoardImage image);

    int deleteImage(@Param("no") int no);

    int deleteImageByBoard(@Param("boardNo") int boardNo);

}