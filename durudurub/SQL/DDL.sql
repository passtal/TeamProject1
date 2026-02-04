-- =====================================================
-- 두루두룹 - 소모임 플랫폼 (With ... Spring Security, JQuery Ajax, Java, Lombok, Thymeleaf, MySQL)
-- =====================================================

CREATE DATABASE IF NOT EXISTS durudurub;

USE durudurub;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS
    users,
    user_auth,
    user_bans,
    persistence_logins,
    categories,
    sub_categories,
    clubs,
    club_members,
    club_boards,
    club_board_images,
    club_comments,
    club_board_likes,
    club_comment_likes,
    club_likes,
    random_games,
    banners,
    club_member_reports
    ;

SET FOREIGN_KEY_CHECKS = 1;


-- 1. users (회원)

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
    `report_count` INT DEFAULT 0 COMMENT '신고 누적 횟수',
    `is_banned` CHAR(1) DEFAULT 'N' COMMENT '차단 여부(Y/N)',
    `banned_at` TIMESTAMP NULL COMMENT '차단 일시',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    UNIQUE KEY uk_user_uuid (`id`),
    UNIQUE KEY uk_user_login (`user_id`),
    UNIQUE KEY uk_user_username (`username`)
    -- UK 는 특별한 일 없으면 건들지 말아주세요 (특히 users 테이블)
);


-- 2. user_auth (회원 권한부여)

CREATE TABLE `user_auth` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `user_no` INT NOT NULL COMMENT 'FK',
    `auth` VARCHAR(50) NOT NULL COMMENT 'ROLE_USER, ROLE_HOST, ROLE_ADMIN',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`user_no`) REFERENCES `users`(`no`) ON DELETE CASCADE
);


-- 3. persistence_logins (자동 로그인)

CREATE TABLE `persistence_logins` (
    `no`            INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '번호',
    `id`            VARCHAR(255) NOT NULL COMMENT 'ID (UUID)',
    `user_id`       VARCHAR(100) NOT NULL COMMENT '회원 아이디',
    `token`         VARCHAR(255) NOT NULL COMMENT '인증 토큰',
    `expiry_date`   TIMESTAMP NOT NULL COMMENT '만료시간',
    `created_at`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일자',
    `updated_at`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일자'
);


-- 4. categories (대분류 카테고리)

CREATE TABLE `categories` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `name` VARCHAR(50) NOT NULL COMMENT '카테고리명',
    `description` VARCHAR(200) NULL COMMENT '카테고리 설명',
    `icon` VARCHAR(100) NULL COMMENT '카테고리 아이콘',
    `seq` INT DEFAULT 0 COMMENT '정렬순서',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    UNIQUE KEY uk_category_name (`name`)
);


-- 5. sub_categories (소분류 카테고리)

-- 가용 가능한 카테고리 종류 모음

-- 자기계발: 독서, 스피치, 면접, 회화, 자격증 ...
-- 스포츠: 러닝, 테니스, 풋살, 등산 ...
-- 푸드: 맛집 탐방, 베이킹, 쿠킹교실 ...
-- 게임: 보드게임, 홀덤, e-sport ...
-- 동네친구: 경도, 카풀, 술모임 ...
-- 여행: 국내, 해외, 당일치기, 패키지 ...
-- 예술: 미술, 음악, 연극, 뮤지컬 ...
-- 반려동물: 간식나눔, 산책, 애견카페 ...


CREATE TABLE `sub_categories` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `category_no` INT NOT NULL COMMENT 'FK: 대분류',
    `name` VARCHAR(50) NOT NULL COMMENT '소분류명',
    `seq` INT DEFAULT 0 COMMENT '정렬순서',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`category_no`) REFERENCES `categories`(`no`) ON DELETE CASCADE
);



-- 6. clubs (모임)

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
    `lat` DECIMAL(10, 8) NULL COMMENT '위도',
    `lng` DECIMAL(11, 8) NULL COMMENT '경도',
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



-- 7. club_members (모임 참가자)


CREATE TABLE `club_members` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `club_no` INT NOT NULL COMMENT 'FK',
    `user_no` INT NOT NULL COMMENT 'FK',
    `status` ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING' COMMENT '참가상태',
    `joined_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,       -- current_timestamp 메서드 호출하는 column 명은 절대 수정하지 말 것
    PRIMARY KEY (`no`),
    FOREIGN KEY (`club_no`) REFERENCES `clubs`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`user_no`) REFERENCES `users`(`no`) ON DELETE CASCADE,
    UNIQUE KEY uk_club_user (`club_no`, `user_no`)      -- 이거 있어야 같은 모임에 중복가입안됨
);



-- 8. club_boards (모임 게시판)


CREATE TABLE `club_boards` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `club_no` INT NOT NULL COMMENT 'FK',
    `writer_no` INT NOT NULL COMMENT 'FK',
    `title` VARCHAR(200) NOT NULL COMMENT '제목',
    `content` TEXT NOT NULL COMMENT '내용',
    `view_count` INT DEFAULT 0 COMMENT '조회수',
    `like_count` INT DEFAULT 0 COMMENT '좋아요 수',
    `comment_count` INT DEFAULT 0 COMMENT '댓글 수',
    `is_notice` CHAR(1) DEFAULT 'N' COMMENT '공지 여부(Y/N)',       -- 이거 좀 고민인게, 공지를 따로 테이블을 만들어서 올리면 admin 기능 구현할때도 편할 거 같긴 함 (공지사항 및 신고.건의 게시판을 만든다는 가정하에)
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`club_no`) REFERENCES `clubs`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`writer_no`) REFERENCES `users`(`no`) ON DELETE CASCADE
);



-- 9. club_board_images (게시판 이미지)


CREATE TABLE `club_board_images` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `board_no` INT NOT NULL COMMENT 'FK',
    `image_url` VARCHAR(255) NOT NULL,
    `seq` INT DEFAULT 0 COMMENT '순서',
    PRIMARY KEY (`no`),
    FOREIGN KEY (`board_no`) REFERENCES `club_boards`(`no`) ON DELETE CASCADE
);


-- 10. club_comments (댓글)

CREATE TABLE `club_comments` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `board_no` INT NOT NULL COMMENT 'FK',
    `writer_no` INT NOT NULL COMMENT 'FK',
    `content` VARCHAR(500) NOT NULL COMMENT '댓글 내용',
    `like_count` INT DEFAULT 0 COMMENT '좋아요 수',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`board_no`) REFERENCES `club_boards`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`writer_no`) REFERENCES `users`(`no`) ON DELETE CASCADE
);


-- 11. club_board_likes (게시글 좋아요 기록)

CREATE TABLE `club_board_likes` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `board_no` INT NOT NULL COMMENT 'FK',
    `user_no` INT NOT NULL COMMENT 'FK',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`board_no`) REFERENCES `club_boards`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`user_no`) REFERENCES `users`(`no`) ON DELETE CASCADE
);



-- 12. club_commment_likes (댓글 좋아요 기록)


CREATE TABLE `club_comment_likes` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `comment_no` INT NOT NULL COMMENT 'FK: 댓글',
    `user_no` INT NOT NULL COMMENT 'FK: 회원',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`comment_no`) REFERENCES `club_comments`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`user_no`) REFERENCES `users`(`no`) ON DELETE CASCADE
);



-- 13. club_likes (모임 좋아요 기록)


CREATE TABLE `club_likes` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `club_no` INT NOT NULL COMMENT 'FK',
    `user_no` INT NOT NULL COMMENT 'FK',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`club_no`) REFERENCES `clubs`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`user_no`) REFERENCES `users`(`no`) ON DELETE CASCADE
);


-- 14. random_games (랜덤 게임)
-- 게임 종류: 돌림판, 사다리, 랜덤뽑기
-- 1회성 게임용 (결과 저장 X)

CREATE TABLE `random_games` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `game_type` ENUM('ROULETTE', 'LADDER', 'RANDOM_PICK') NOT NULL COMMENT '게임 종류: 돌림판/사다리/랜덤뽑기',
    `title` VARCHAR(100) NOT NULL COMMENT '게임 제목',
    `options` JSON NULL COMMENT '게임 옵션들',
    PRIMARY KEY (`no`)
);



-- 15. banners (광고용 배너)

CREATE TABLE `banners` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `title` VARCHAR(100) NOT NULL COMMENT '배너 제목',
    `image_url` VARCHAR(255) NOT NULL COMMENT '배너 이미지',
    `link_url` VARCHAR(500) NULL COMMENT '클릭 시 이동 URL',
    `position` VARCHAR(50) DEFAULT 'MAIN' COMMENT '배너 위치: MAIN, SIDE, POPUP',
    `description` VARCHAR(200) NULL COMMENT '배너 텍스트',
    `is_active` CHAR(1) DEFAULT 'Y' COMMENT '활성화 여부(Y/N)',
    `start_date` DATE NULL COMMENT '노출 시작일',
    `end_date` DATE NULL COMMENT '노출 종료일',
    `seq` INT DEFAULT 0 COMMENT '정렬순서',
    `click_count` INT DEFAULT 0 COMMENT '클릭수',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`)
);


-- 16. user_bans (회원 차단 이력)
-- 신고 누적으로 인한 차단 기록 관리
-- 차단 기준: report_count >= 5 (또는 관리자 판단)
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


-- 17. club_member_reports (모임 멤버 신고 내역)
CREATE TABLE `club_member_reports` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `club_no` INT NOT NULL COMMENT 'FK',    -- 신고가 발생한 해당 모임
    `reporter_no` INT NOT NULL COMMENT 'FK',    -- 신고한 회원의 no
    `target_no` INT NOT NULL COMMENT 'FK',      -- 신고당한 회원의 no
    `reason` VARCHAR(100) NULL COMMENT '신고 사유 (욕설, 비매너 등)',
    `processed_at` TIMESTAMP NULL COMMENT '처리 일시',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`club_no`) REFERENCES `clubs`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`reporter_no`) REFERENCES `users`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`target_no`) REFERENCES `users`(`no`) ON DELETE CASCADE,
    UNIQUE KEY uk_report_history (`club_no`, `reporter_no`, `target_no`)    -- 이거 없으면 중복신고로 망합니다 지우지마세요
);

-- 18. report_categories (신고 카테고리)

CREATE TABLE `report_categories` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `name` VARCHAR(50) NOT NULL COMMENT '신고 카테고리',
    `description` VARCHAR(200) NULL COMMENT '카테고리 설명',
    `seq` INT DEFAULT 0 COMMENT '정렬순서',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`)
);

-- 19. notices (공지사항)

CREATE TABLE `notices` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `writer_no` INT NOT NULL COMMENT 'FK',      -- ADMIN
    `category` VARCHAR(50) NULL COMMENT '공지 카테고리',
    `title` VARCHAR(200) NOT NULL COMMENT '제목',
    `content` TEXT NOT NULL COMMENT '내용',
    `is_important` CHAR(1) DEFAULT 'N' COMMENT '중요 공지 여부',
    `view_counts` INT DEFAULT 0 COMMENT '조회수',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`writer_no`) REFERENCES `users`(`no`) ON DELETE CASCADE
);