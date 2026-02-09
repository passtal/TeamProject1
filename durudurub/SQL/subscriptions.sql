-- 21. subscriptions (AI 구독 정보)
-- 관리자(ROLE_ADMIN)은 구독 여부와 상관없이 전체 접근 가능
-- 구독 상태 : ACTIVE(구독중), INACTIVE(미구독), EXPIRED(만료)

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `banners`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `subscriptions` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `user_no` INT NOT NULL COMMENT 'FK',
    `status` ENUM('ACTIVE', 'INACTIVE', 'EXPIRED') DEFAULT 'INACTIVE' COMMENT '구독 상태',
    `plan_name` VARCHAR(50) DEFAULT 'AI_SEARCH' COMMENT '구독 플랜명',
    `start_date` TIMESTAMP NULL COMMENT '구독 시작일',
    `end_date` TIMESTAMP NULL COMMENT '구독 만료일',
    `auto_renew` CHAR(1) DEFAULT 'Y' COMMENT '자동 갱신 여부 (Y/N)',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`user_no`) REFERENCES `users`(`no`) ON DELETE CASCADE,
    UNIQUE KEY uk_subscription_user (`user_no`)
);