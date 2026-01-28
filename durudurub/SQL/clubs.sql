-- 6. clubs (모임)

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `clubs`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `clubs` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `host_no` INT NOT NULL COMMENT 'FK',    -- 이거 모임만든 유저가 자동으로 host 권한 부여 받게 해둘게요
    `category_no` INT NOT NULL COMMENT 'FK',
    `sub_category_no` INT NULL COMMENT 'FK',
    `title` VARCHAR(100) NOT NULL COMMENT '모임명',
    `description` TEXT NULL COMMENT '모임 설명',
    `thumbnail_img` VARCHAR(255) NULL COMMENT '썸네일 이미지',
    `max_members` INT DEFAULT 10 COMMENT '모집 인원수',
    `current_members` INT DEFAULT 1 COMMENT '현재 참가자 수',
    `deadline` DATE NULL COMMENT '마감일',
    `location` VARCHAR(255) NULL COMMENT '모임 장소',
    `club_date` DATETIME NULL COMMENT '모임 일시',
    `status` ENUM('RECRUITING', 'CLOSED', 'COMPLETED') DEFAULT 'RECRUITING' COMMENT '모임상태',     -- 이거 serviceImpl이랑 jsp를 어떻게 구현하느냐에 따라서 지워질수도
    `view_count` INT DEFAULT 0 COMMENT '조회수',
    `like_count` INT DEFAULT 0 COMMENT '좋아요 수',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`host_no`) REFERENCES `users`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`category_no`) REFERENCES `categories`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`sub_category_no`) REFERENCES `sub_categories`(`no`) ON DELETE SET NULL
);
