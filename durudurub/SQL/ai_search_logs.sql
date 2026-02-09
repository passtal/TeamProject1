-- 23. ai_search_logs (AI 검색 사용 이력)
-- 무료 사용자들 체크용 (미구독 회원 3회 제한)

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `banners`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `ai_search_logs` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `user_no` INT NOT NULL COMMENT 'FK',
    `keyword` VARCHAR(200) NULL COMMENT 'AI 추출 키워드',
    `user_message` VARCHAR(500) NULL COMMENT '사용자 원본 메세지',
    `result_count` INT DEFAULT 0 COMMENT '검색 결과 수',
    PRIMARY KEY (`no`),
    FOREIGN KEY (`user_no`) REFERENCES `users`(`no`) ON DELETE CASCADE
);