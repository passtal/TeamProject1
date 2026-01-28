-- 16. user_bans (회원 차단 이력)
-- 신고 누적으로 인한 차단 기록 관리
-- 차단 기준: report_count >= 5 (또는 관리자 판단)

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user_bans`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `user_bans` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `user_no` INT NOT NULL COMMENT 'FK', -- 차단된 유저
    `reason` VARCHAR(200) NOT NULL COMMENT '차단 사유',
    `report_count_at_ban` INT DEFAULT 0 COMMENT '차단 시점 신고 누적 횟수',
    `ban_type` ENUM('TEMPORARY', 'PERMANENT') DEFAULT 'TEMPORARY' COMMENT '차단 유형: 임시/영구',
    `ban_end_date` DATE NULL COMMENT '차단 해제 예정일',    -- 영구 차단일 시, NULL로 표기함
    `is_active` CHAR(1) DEFAULT 'Y' COMMENT '현재 차단 상태(Y/N)',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '차단 일시',
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`user_no`) REFERENCES `users`(`no`) ON DELETE CASCADE
);
