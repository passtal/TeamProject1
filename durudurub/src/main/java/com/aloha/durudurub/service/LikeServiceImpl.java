package com.aloha.durudurub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.durudurub.dao.BoardMapper;
import com.aloha.durudurub.dao.ClubMapper;
import com.aloha.durudurub.dao.CommentMapper;
import com.aloha.durudurub.dao.LikeMapper;
import com.aloha.durudurub.dto.ClubLike;

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
            clubMapper.decrementLikeCount(userNo);
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
            likeMapper.deleteClubLike(boardNo, userNo);
            boardMapper.decrementLikeCount(userNo);
            return false;
        } else {
            ClubLike like = new ClubLike();
            like.setClubNo(boardNo);
            like.setUserNo(userNo);
            likeMapper.insertClubLike(like);
            boardMapper.incrementLikeCount(boardNo);
            return true;
        }
    }

    @Override
    @Transactional
    public boolean commentLike(int commentNo, int userNo) {
        boolean isLiked = likeMapper.countBoardLike(commentNo, userNo) > 0;

        if (isLiked) {
            likeMapper.deleteClubLike(commentNo, userNo);
            commentMapper.decrementLikeCount(userNo);
            return false;
        } else {
            ClubLike like = new ClubLike();
            like.setClubNo(commentNo);
            like.setUserNo(userNo);
            likeMapper.insertClubLike(like);
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