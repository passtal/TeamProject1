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

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `sub_categories`;

SET FOREIGN_KEY_CHECKS = 1;


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
