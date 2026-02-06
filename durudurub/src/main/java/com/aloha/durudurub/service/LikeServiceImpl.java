package com.aloha.durudurub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.durudurub.dao.BoardMapper;
import com.aloha.durudurub.dao.ClubMapper;
import com.aloha.durudurub.dao.CommentMapper;
import com.aloha.durudurub.dao.LikeMapper;
import com.aloha.durudurub.dto.BoardLike;
import com.aloha.durudurub.dto.ClubLike;
import com.aloha.durudurub.dto.CommentLike;

/**
 * 좋아요 서비스 구현체
 */
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private ClubMapper clubMapper;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Transactional
    public boolean clubLike(int clubNo, int userNo) {
        boolean isLiked = likeMapper.countClubLike(clubNo, userNo) > 0;

        if (isLiked) {
            // 좋아요 취소하는 경우 ↓
            likeMapper.deleteClubLike(clubNo, userNo);
            clubMapper.decrementLikeCount(clubNo);
            return false;
        } else {
            // false인 상태에서 좋아요를 누르는 경우 ↓
            ClubLike like = new ClubLike();
            like.setClubNo(clubNo);
            like.setUserNo(userNo);
            likeMapper.insertClubLike(like);
            clubMapper.incrementLikeCount(clubNo);
            return true;
        }
    }

    @Override
    @Transactional
    public boolean boardLike(int boardNo, int userNo) {
        boolean isLiked = likeMapper.countBoardLike(boardNo, userNo) > 0;

        if (isLiked) {
            // 좋아요 취소
            likeMapper.deleteBoardLike(boardNo, userNo);
            boardMapper.decrementLikeCount(boardNo);
            return false;
        } else {
            // 좋아요 추가
            BoardLike like = new BoardLike();
            like.setBoardNo(boardNo);
            like.setUserNo(userNo);
            likeMapper.insertBoardLike(like);
            boardMapper.incrementLikeCount(boardNo);
            return true;
        }
    }

    @Override
    @Transactional
    public boolean commentLike(int commentNo, int userNo) {
        boolean isLiked = likeMapper.countCommentLike(commentNo, userNo) > 0;

        if (isLiked) {
            // 좋아요 취소
            likeMapper.deleteCommentLike(commentNo, userNo);
            commentMapper.decrementLikeCount(commentNo);
            return false;
        } else {
            // 좋아요 추가
            CommentLike like = new CommentLike();
            like.setCommentNo(commentNo);
            like.setUserNo(userNo);
            likeMapper.insertCommentLike(like);
            commentMapper.incrementLikeCount(commentNo);
            return true;
        }
    }

    @Override
    public boolean isClubLiked(int clubNo, int userNo) {
        return likeMapper.countClubLike(clubNo, userNo) > 0;
    }

    @Override
    public boolean isBoardLiked(int boardNo, int userNo) {
        return likeMapper.countBoardLike(boardNo, userNo) > 0;
    }

    @Override
    public boolean isCommentLiked(int commentNo, int userNo) {
        return likeMapper.countCommentLike(commentNo, userNo) > 0;
    }

    @Override
    public int countClubLikes(int clubNo) {
        return likeMapper.countClubLikes(clubNo);
    }

    @Override
    public int countBoardLikes(int boardNo) {
        return likeMapper.countBoardLikes(boardNo);
    }

    @Override
    public int countCommentLikes(int commentNo) {
        return likeMapper.countCommentLikes(commentNo);
    }
    
}