-- 17. club_member_reports (모임 멤버 신고 내역)

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `club_member_reports`;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE `club_member_reports` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `club_no` INT NOT NULL COMMENT 'FK',    -- 신고가 발생한 해당 모임
    `reporter_no` INT NOT NULL COMMENT 'FK',    -- 신고한 회원의 no
    `target_no` INT NOT NULL COMMENT 'FK',      -- 신고당한 회원의 no
    `reason` VARCHAR(100) NULL COMMENT '신고 사유 (욕설, 비매너 등)',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`club_no`) REFERENCES `clubs`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`reporter_no`) REFERENCES `users`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`target_no`) REFERENCES `users`(`no`) ON DELETE CASCADE,
    UNIQUE KEY uk_report_history (`club_no`, `reporter_no`, `target_no`)    -- 이거 없으면 중복신고로 망합니다 지우지마세요
);
