-- 11. club_board_likes (게시글 좋아요 기록)

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `club_board_likes`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `club_board_likes` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `board_no` INT NOT NULL COMMENT 'FK',
    `user_no` INT NOT NULL COMMENT 'FK',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`board_no`) REFERENCES `club_boards`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`user_no`) REFERENCES `users`(`no`) ON DELETE CASCADE
);
