package com.aloha.durudurub.service;

import java.util.List;

import com.aloha.durudurub.dto.Board;
import com.aloha.durudurub.dto.BoardImage;

/**
 * 게시글 서비스
 */
public interface BoardService {

    List<Board> listByClub(int clubNo);
    
    List<Board> listNoticeByClub(int clubNo);

    List<Board> listNotices(int clubNo);

    List<Board> listRegularByClub(int clubNo);

    List<Board> listByWriter(int writerNo);

    // 검색 기능은 일단 기능구현을 해놔야 ai활용 검색할때 안되더라도 보험이 되지 않을까해서..
    List<Board> searchByClub(int clubNo, String keyword);

    Board selectByNo(int no);

    int insert(Board board);
    
    int insert(Board board, List<BoardImage> images);

    int update(Board board);

    int update(Board board, List<BoardImage> images);

    int delete(int no);

    int incrementViewCount(int no);

}