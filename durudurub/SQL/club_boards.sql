-- 8. club_boards (모임 게시판)

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `club_boards`;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE `club_boards` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `club_no` INT NOT NULL COMMENT 'FK',
    `writer_no` INT NOT NULL COMMENT 'FK',
    `title` VARCHAR(200) NOT NULL COMMENT '제목',
    `content` TEXT NOT NULL COMMENT '내용',
    `view_count` INT DEFAULT 0 COMMENT '조회수',
    `like_count` INT DEFAULT 0 COMMENT '좋아요 수',
    `comment_count` INT DEFAULT 0 COMMENT '댓글 수',
    `is_notice` CHAR(1) DEFAULT 'N' COMMENT '공지 여부(Y/N)',       -- 이거 좀 고민인게, 공지를 따로 테이블을 만들어서 올리면 admin 기능 구현할때도 편할 거 같긴 함 (공지사항 및 신고.건의 게시판을 만든다는 가정하에)
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`club_no`) REFERENCES `clubs`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`writer_no`) REFERENCES `users`(`no`) ON DELETE CASCADE
);
