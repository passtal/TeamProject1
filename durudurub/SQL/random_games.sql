-- 14. random_games (랜덤 게임)
-- 게임 종류: 돌림판, 사다리, 랜덤뽑기

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `random_games`;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE `random_games` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `club_no` INT NOT NULL COMMENT 'FK',
    `game_type` ENUM('ROULETTE', 'LADDER', 'RANDOM_PICK') NOT NULL COMMENT '게임 종류: 돌림판/사다리/랜덤뽑기',
    `title` VARCHAR(100) NOT NULL COMMENT '게임 제목',
    `options` JSON NULL COMMENT '게임 옵션들',
    `result` VARCHAR(200) NULL COMMENT '결과',
    `created_by` INT NOT NULL COMMENT 'FK',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`club_no`) REFERENCES `clubs`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`created_by`) REFERENCES `users`(`no`) ON DELETE CASCADE
);
