-- 15. banners (광고용 배너)

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `banners`;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE `banners` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `title` VARCHAR(100) NOT NULL COMMENT '배너 제목',
    `image_url` VARCHAR(255) NOT NULL COMMENT '배너 이미지',
    `link_url` VARCHAR(500) NULL COMMENT '클릭 시 이동 URL',
    `position` VARCHAR(50) DEFAULT 'MAIN' COMMENT '배너 위치: MAIN, SIDE, POPUP',
    `is_active` CHAR(1) DEFAULT 'Y' COMMENT '활성화 여부(Y/N)',
    `start_date` DATE NULL COMMENT '노출 시작일',
    `end_date` DATE NULL COMMENT '노출 종료일',
    `seq` INT DEFAULT 0 COMMENT '정렬순서',
    `click_count` INT DEFAULT 0 COMMENT '클릭수',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`)
);

INSERT INTO `banners` (`title`, `image_url`, `link_url`, `position`, `seq`) VALUES
('두루두루 오픈 기념 이벤트', '/static/img/banner/event1.jpg', '/event/1', 'MAIN', 1),
('새로운 모임을 찾아보세요', '/static/img/banner/event2.jpg', '/clubs', 'MAIN', 2);
