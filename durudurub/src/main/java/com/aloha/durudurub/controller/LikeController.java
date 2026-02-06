package com.aloha.durudurub.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.service.LikeService;
import com.aloha.durudurub.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;



/**
 * 좋아요 API 컨트롤러 (Ajax)
 */
@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/likes")
public class LikeController {

    public record LikeResponse(boolean liked, int count) {}
    
    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    /**
     * clubLike
     * @param clubNo
     * @param principal
     * @return
     */
    @PostMapping("/club/{clubNo}")
    public ResponseEntity<?> clubLike(@PathVariable("clubNo") int clubNo, Principal principal) {
        try {
            if (principal == null) {
                return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
            }
            User user = userService.selectByUserId(principal.getName());
            boolean liked = likeService.clubLike(clubNo, user.getNo());
            int count = likeService.countClubLikes(clubNo);
            return new ResponseEntity<>(new LikeResponse(liked, count), HttpStatus.OK);
        } catch (Exception e) {
            log.error("clubLike 에러입니다.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * clubLikeStatus (모임 좋아요 검증)
     * @param clubNo
     * @param principal
     * @return
     */
    @GetMapping("/club/{clubNo}")
    public ResponseEntity<?> getClubLikeStatus(@PathVariable("clubNo") int clubNo, Principal principal) {
        try {
            int count = likeService.countClubLikes(clubNo);
            boolean liked = false;

            if (principal != null) {
                User user = userService.selectByUserId(principal.getName());
                liked = likeService.isClubLiked(clubNo, user.getNo());
            }
            return new ResponseEntity<>(new LikeResponse(liked, count), HttpStatus.OK);
        } catch (Exception e) {
            log.error("clubLikeStatus 에러입니다.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * boardLike
     * @param boardNo
     * @param principal
     * @return
     */
    @PostMapping("/board/{boardNo}")
    public ResponseEntity<?> boardLike(@PathVariable("boardNo") int boardNo, Principal principal) {
        try {
            if (principal == null) {
                return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
            }
            User user = userService.selectByUserId(principal.getName());
            boolean liked = likeService.boardLike(boardNo, user.getNo());
            int count = likeService.countBoardLikes(boardNo);
            return new ResponseEntity<>(new LikeResponse(liked, count), HttpStatus.OK);
        } catch (Exception e) {
            log.error("boardLike 에러입니다.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * boardLikeStatus (게시글 좋아요 검증)
     * @param boardNo
     * @param principal
     * @return
     */
    @GetMapping("/board/{boardNo}")
    public ResponseEntity<?> getBoardLikeStatus(@PathVariable("boardNo") int boardNo, Principal principal) {
        try {
            int count = likeService.countBoardLikes(boardNo);
            boolean liked = false;

            if (principal != null) {
                User user = userService.selectByUserId(principal.getName());
                liked = likeService.isBoardLiked(boardNo, user.getNo());
            }
            return new ResponseEntity<>(new LikeResponse(liked, count), HttpStatus.OK);
        } catch (Exception e) {
            log.error("boardLikeStatus 에러입니다.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * commentLike
     * @param commentNo
     * @param principal
     * @return
     */
    @PostMapping("/comment/{commentNo}")
    public ResponseEntity<?> commentLike(@PathVariable("commentNo") int commentNo, Principal principal) {
        try {
            if (principal == null) {
                return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
            }
            User user = userService.selectByUserId(principal.getName());
            boolean liked = likeService.commentLike(commentNo, user.getNo());
            int count = likeService.countCommentLikes(commentNo);
            return new ResponseEntity<>(new LikeResponse(liked, count), HttpStatus.OK);
        } catch (Exception e) {
            log.error("commentLike 에러입니다.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * commentLikeStatus (댓글 좋아요 검증)
     * @param commentNo
     * @param principal
     * @return
     */
    @GetMapping("/comment/{commentNo}")
    public ResponseEntity<?> getCommentLikeStatus(@PathVariable("commentNo") int commentNo, Principal principal) {
        try {
            int count = likeService.countCommentLikes(commentNo);
            boolean liked = false;

            if (principal != null) {
                User user = userService.selectByUserId(principal.getName());
                liked = likeService.isCommentLiked(commentNo, user.getNo());
            }
            return new ResponseEntity<>(new LikeResponse(liked, count), HttpStatus.OK);
        } catch (Exception e) {
            log.error("commentLikeStatus 에러입니다.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}