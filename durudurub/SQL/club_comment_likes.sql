-- 12. club_commment_likes (댓글 좋아요 기록)

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `club_comment_likes`;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE `club_comment_likes` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `comment_no` INT NOT NULL COMMENT 'FK: 댓글',
    `user_no` INT NOT NULL COMMENT 'FK: 회원',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`comment_no`) REFERENCES `club_comments`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`user_no`) REFERENCES `users`(`no`) ON DELETE CASCADE
);
