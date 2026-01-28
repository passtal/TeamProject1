-- 7. club_members (모임 참가자)

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `club_members`;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE `club_members` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `club_no` INT NOT NULL COMMENT 'FK',
    `user_no` INT NOT NULL COMMENT 'FK',
    `status` ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING' COMMENT '참가상태',
    `joined_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,       -- current_timestamp 메서드 호출하는 column 명은 절대 수정하지 말 것
    PRIMARY KEY (`no`),
    FOREIGN KEY (`club_no`) REFERENCES `clubs`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`user_no`) REFERENCES `users`(`no`) ON DELETE CASCADE,
    UNIQUE KEY uk_club_user (`club_no`, `user_no`)      -- 이거 있어야 같은 모임에 중복가입안됨
);
