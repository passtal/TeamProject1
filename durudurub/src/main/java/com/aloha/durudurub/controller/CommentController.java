package com.aloha.durudurub.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aloha.durudurub.dto.Comment;
import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.service.CommentService;
import com.aloha.durudurub.service.UserService;

import lombok.extern.slf4j.Slf4j;


/**
 * 댓글 API 컨트롤러 (Ajax)
 */
@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;


    /**
     * 댓글 목록 조회
     * @param no
     * @return
     */
    @GetMapping("/board/{boardNo}")
    public ResponseEntity<?> getByBoard(@PathVariable("boardNo") int boardNo) {
        try {
            List<Comment> comments = commentService.listByBoard(boardNo);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("댓글 목록 조회에 실패했습니다.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 개별 댓글 조회 (getOne 방식 사용)
     * @param no
     * @return
     */
    @GetMapping("/{no}")
    public ResponseEntity<?> getOne(@PathVariable("no") int no) {
        try {
            Comment comment = commentService.selectByNo(no);
            if (comment == null) {
                return new ResponseEntity<>("NOT_FOUND", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (Exception e) {
            log.error("댓글 조회에 실패했습니다.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * 댓글 작성하기
     * @param comment
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Comment comment, Principal principal) {
        try {
            User user = userService.selectByUserId(principal.getName());
            comment.setWriterNo(user.getNo());

            int result = commentService.insert(comment);
            if (result == 0) {
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("댓글 작성 실패", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 댓글 수정하기
     * @param comment
     * @return
     */
    @PutMapping()
    public ResponseEntity<?> update(@RequestBody Comment comment, Principal principal) {
        try {
            Comment existing = commentService.selectByNo(comment.getNo());
            User user = userService.selectByUserId(principal.getName());

            if (existing.getWriterNo() != user.getNo()) {
                return new ResponseEntity<>("FORBIDDEN", HttpStatus.FORBIDDEN);
            }

            int result = commentService.update(comment);
            if (result == 0) {
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            log.error("댓글 수정에 실패했습니다.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 댓글 삭제하기
     * @param no
     * @return
     */
    @DeleteMapping("/{no}")
    public ResponseEntity<?> destory(@PathVariable("no") int no, Principal principal) {
        try {
            Comment comment = commentService.selectByNo(no);
            User user = userService.selectByUserId(principal.getName());

            if (comment.getWriterNo() != user.getNo()) {
                return new ResponseEntity<>("FORBIDDEN", HttpStatus.FORBIDDEN);
            }

            int result = commentService.delete(no);
            if (result == 0) {
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            log.error("댓글 삭제에 실패했습니다.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 댓글 좋아요 토글
     * @param commentNo
     * @param principal
     * @return
     */
    @PostMapping("/{commentNo}/like")
    public ResponseEntity<?> toggleLike(@PathVariable("commentNo") int commentNo, Principal principal) {
        try {
            if (principal == null) {
                return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
            }

            User user = userService.selectByUserId(principal.getName());
            int userNo = user.getNo();

            // 좋아요 여부 확인
            int likeCount = commentService.countCommentLike(commentNo, userNo);
            
            boolean isLiked;
            if (likeCount > 0) {
                // 좋아요 취소
                commentService.deleteCommentLike(commentNo, userNo);
                commentService.decrementLikeCount(commentNo);
                isLiked = false;
            } else {
                // 좋아요 추가
                commentService.insertCommentLike(commentNo, userNo);
                commentService.incrementLikeCount(commentNo);
                isLiked = true;
            }

            // 현재 좋아요 수 조회
            Comment comment = commentService.selectByNo(commentNo);
            
            return ResponseEntity.ok(new java.util.HashMap<String, Object>() {{
                put("liked", isLiked);
                put("count", comment.getLikeCount());
            }});
        } catch (Exception e) {
            log.error("댓글 좋아요 토글 실패", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}