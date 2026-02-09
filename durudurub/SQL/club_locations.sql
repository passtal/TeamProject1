-- 20. club_locations (모임 장소 좌표 - 지도 참조용)

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `banners`;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE `club_location` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `location` VARCHAR(255) COMMENT '장소',
    `lat` DECIMAL(10, 8) NULL COMMENT '위도',
    `lng` DECIMAL(11, 8) NULL COMMENT '경도',
    PRIMARY KEY (`no`)
    -- FOREIGN KEY (`location`) REFERENCES `clubs`(`location`) ON DELETE CASCADE
);