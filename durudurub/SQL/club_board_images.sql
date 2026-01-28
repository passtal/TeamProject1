-- 9. club_board_images (게시판 이미지)

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `club_board_images`;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE `club_board_images` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `board_no` INT NOT NULL COMMENT 'FK',
    `image_url` VARCHAR(255) NOT NULL,
    `seq` INT DEFAULT 0 COMMENT '순서',
    PRIMARY KEY (`no`),
    FOREIGN KEY (`board_no`) REFERENCES `club_boards`(`no`) ON DELETE CASCADE
);
