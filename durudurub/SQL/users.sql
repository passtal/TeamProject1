SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `id` VARCHAR(64) NOT NULL COMMENT 'UK (UUID)',
    `user_id` VARCHAR(100) NOT NULL COMMENT '아이디(이메일)',
    `password` VARCHAR(100) NOT NULL COMMENT '비밀번호(BCrypt)',
    `username` VARCHAR(100) NOT NULL COMMENT '닉네임',
    `profile_img` VARCHAR(300) NULL DEFAULT '/static/img/default-profile.png',
    `age` INT DEFAULT 0 COMMENT '나이',
    `gender` VARCHAR(20) DEFAULT '공개안함',
    `address` VARCHAR(255) NULL COMMENT '주거지',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    UNIQUE KEY uk_user_uuid (`id`),
    UNIQUE KEY uk_user_login (`user_id`),
    UNIQUE KEY uk_user_username (`username`)
    -- UK 는 특별한 일 없으면 건들지 말아주세요 (특히 users 테이블)
);

SET FOREIGN_KEY_CHECKS = 1;