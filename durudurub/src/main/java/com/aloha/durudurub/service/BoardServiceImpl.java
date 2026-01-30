package com.aloha.durudurub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.durudurub.dao.BoardMapper;
import com.aloha.durudurub.dto.Board;
import com.aloha.durudurub.dto.BoardImage;

/**
 * 게시판 서비스 구현체
 */
@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public List<Board> listByClub(int clubNo) {
        return boardMapper.listByClub(clubNo);
    }

    @Override
    public List<Board> listNoticeByClub(int clubNo) {
        return boardMapper.listNoticeByClub(clubNo);
    }

    @Override
    public List<Board> listNotices(int clubNo) {
        return boardMapper.listNoticeByClub(clubNo);
    }

    @Override
    public List<Board> listByWriter(int writerNo) {
        return boardMapper.listByWriter(writerNo);
    }

    @Override
    public List<Board> searchByClub(int clubNo, String keyword) {
        return boardMapper.searchByClub(clubNo, keyword);
    }

    @Override
    public Board selectByNo(int no) {
        Board board = boardMapper.selectByNo(no);
        if (board != null) {
            List<BoardImage> images = boardMapper.listImageByBoard(no);
            board.setImageList(images);
        }
        return board;
    }

    @Override
    @Transactional
    public int insert(Board board, List<BoardImage> images) {
        int result = boardMapper.insert(board);
        if (result > 0 && images != null && !images.isEmpty()) {
            int seq = 0;
            for (BoardImage image : images) {
                image.setBoardNo(board.getNo());
                image.setSeq(seq++);    // 등록하면 자동으로 번호 밀리게 ++ 로 증가처리
                boardMapper.insertImage(image);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public int update(Board board, List<BoardImage> images) {
        int result = boardMapper.update(board);
        if (result > 0 && images != null) {
            boardMapper.deleteImageByBoard(board.getNo());
            int seq = 0;
            for (BoardImage image : images) {
                image.setBoardNo(board.getNo());
                image.setSeq(seq++);
                boardMapper.insertImage(image);
            }
        }
        return result;
    }

    @Override
    public int delete(int no) {
        boardMapper.deleteImageByBoard(no);
        return boardMapper.delete(no);
    }

    @Override
    public int incrementViewCount(int no) {
        return boardMapper.incrementViewCount(no);
    }

    @Override
    public int insert(Board board) {
        return boardMapper.insert(board);
    }

    @Override
    public int update(Board board) {
        return boardMapper.update(board);
    }

}