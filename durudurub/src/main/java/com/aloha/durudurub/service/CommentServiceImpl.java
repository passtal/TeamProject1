package com.aloha.durudurub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.durudurub.dao.BoardMapper;
import com.aloha.durudurub.dao.CommentMapper;
import com.aloha.durudurub.dto.Comment;

/**
 * 댓글 서비스 구현체
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public List<Comment> listByBoard(int boardNo) {
        return commentMapper.listByBoard(boardNo);
    }

    @Override
    public List<Comment> listByWriter(int writerNo) {
        return commentMapper.listByWriter(writerNo);
    }

    @Override
    public Comment selectByNo(int no) {
        return commentMapper.selectByNo(no);
    }

    @Override
    @Transactional
    public int insert(Comment comment) {
        int result = commentMapper.insert(comment);
        if (result > 0) {
            boardMapper.incrementCommentCount(comment.getBoardNo());
        }
        return result;
    }

    @Override
    public int update(Comment comment) {
        return commentMapper.update(comment);
    }

    @Override
    public int delete(int no) {
        Comment comment = commentMapper.selectByNo(no);
        int result = commentMapper.delete(no);
        if (result > 0 && comment != null) {
            boardMapper.decrementCommentCount(comment.getBoardNo());
        }
        return result;
    }

    @Override
    public int insertCommentLike(int commentNo, int userNo) {
        return commentMapper.insertCommentLike(commentNo, userNo);
    }

    @Override
    public int deleteCommentLike(int commentNo, int userNo) {
        return commentMapper.deleteCommentLike(commentNo, userNo);
    }

    @Override
    public int countCommentLike(int commentNo, int userNo) {
        return commentMapper.countCommentLike(commentNo, userNo);
    }

    @Override
    public int incrementLikeCount(int no) {
        return commentMapper.incrementLikeCount(no);
    }

    @Override
    public int decrementLikeCount(int no) {
        return commentMapper.decrementLikeCount(no);
    }
}
