package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.Comment;

/**
 * 댓글 매퍼
 */
@Mapper
public interface CommentMapper {
    
    List<Comment> listByBoard(@Param("boardNo") int boardNo);

    List<Comment> listByWriter(@Param("writerNo") int writerNo);

    Comment selectByNo(@Param("no") int no);

    int insert(Comment comment);

    int update(Comment comment);

    int delete(@Param("no") int no);

    int incrementLikeCount(@Param("no") int no);

    int decrementLikeCount(@Param("no") int no);
    
    int incrementCommentCount(@Param("no") int no);

    int decrementCommentCount(@Param("no") int no);

    int countByBoard(@Param("boardNo") int boardNo);    // 게시글에 달린 댓글 갯수 취합하는 역할의 매핑 (필요없어질 수도 있음)

}