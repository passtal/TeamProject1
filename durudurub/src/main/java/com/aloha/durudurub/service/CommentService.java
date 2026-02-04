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

}