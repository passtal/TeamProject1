package com.aloha.durudurub.service;

/**
 * 좋아요 서비스
 */
public interface LikeService {
    
    // true - 좋아요 추가, false - 좋아요 삭제 (이런식으로 상태변경 넣을듯)
    
    boolean clubLike(int clubNo, int userNo);

    boolean boardLike(int boardNo, int userNo);

    boolean commentLike(int commentNo, int userNo);

    boolean isClubLiked(int clubNo, int userNo);

    boolean isBoardLiked(int boardNo, int userNo);

    boolean isCommentLiked(int commentNo, int userNo);

    int countClubLikes(int clubNo);

    int countBoardLikes(int boardNo);

    int countCommentLikes(int commentNo);

}