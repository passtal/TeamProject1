package com.aloha.durudurub.service;

import java.util.List;

import com.aloha.durudurub.dto.Comment;

/**
 * 댓글 서비스
 */
public interface CommentService {
    
    List<Comment> listByBoard(int boardNo);

    List<Comment> listByWriter(int writerNo);

    Comment selectByNo(int no);

    int insert(Comment comment);

    int update(Comment comment);

    int delete(int no);

    // 댓글 좋아요 관련
    int insertCommentLike(int commentNo, int userNo);

    int deleteCommentLike(int commentNo, int userNo);

    int countCommentLike(int commentNo, int userNo);

    int incrementLikeCount(int no);

    int decrementLikeCount(int no);

}