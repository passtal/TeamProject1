-- 기초 데이터 DML

-- 대분류 카테고리
-- 대분류 카테고리
INSERT INTO `categories` (`name`, `description`, `icon`, `seq`) VALUES
('자기계발', '나를 성장시키는 모임', '/img/icons/category-self-development.png', 1),
('스포츠', '함께 땀 흘리는 모임', '/img/icons/category-sports.png', 2),
('푸드', '맛있는 음식을 나누는 모임', '/img/icons/category-food.png', 3),
('게임', '게임을 즐기는 모임', '/img/icons/category-game.png', 4),
('동네친구', '동네에서 친구 만들기', '/img/icons/category-neighborhood.png', 5),
('여행', '함께 떠나는 여행', '/img/icons/category-travel.png', 6),
('예술', '예술을 사랑하는 모임', '/img/icons/category-art.png', 7),
('기타', '그 외 다양한 취미활동!', '/img/icons/category-etc.png', 8);

-- 소분류 카테고리
-- 자기계발 (category_no = 1)
INSERT INTO `sub_categories` (`category_no`, `name`, `seq`) VALUES
(1, '독서', 1),
(1, '스피치', 2),
(1, '면접', 3),
(1, '회화', 4);

-- 스포츠 (category_no = 2)
INSERT INTO `sub_categories` (`category_no`, `name`, `seq`) VALUES
(2, '러닝', 1),
(2, '테니스', 2),
(2, '풋살', 3),
(2, '등산', 4);

-- 푸드 (category_no = 3)
INSERT INTO `sub_categories` (`category_no`, `name`, `seq`) VALUES
(3, '맛집', 1),
(3, '한식', 2),
(3, '베이킹', 3),
(3, '쿠킹교실', 4);

-- 게임 (category_no = 4)
INSERT INTO `sub_categories` (`category_no`, `name`, `seq`) VALUES
(4, '보드게임', 1),
(4, '홀덤', 2),
(4, '포켓볼', 3),
(4, 'e-sport', 4);

-- 동네친구 (category_no = 5)
INSERT INTO `sub_categories` (`category_no`, `name`, `seq`) VALUES
(5, '경도', 1),
(5, '술래잡기', 2),
(5, '술자리', 3),
(5, '카풀', 4);

-- 여행 (category_no = 6)
INSERT INTO `sub_categories` (`category_no`, `name`, `seq`) VALUES
(6, '국내', 1),
(6, '해외', 2),
(6, '당일치기', 3),
(6, '패키지', 4);

-- 예술 (category_no = 7)
INSERT INTO `sub_categories` (`category_no`, `name`, `seq`) VALUES
(7, '미술', 1),
(7, '음악', 2),
(7, '연극', 3),
(7, '뮤지컬', 4);

-- 기타 (category_no = 8)
INSERT INTO `sub_categories` (`category_no`, `name`, `seq`) VALUES
(8, '반려동물 산책모임', 1),
(8, '봉사활동 모임', 2),
(8, '우리동네 플리마켓', 3),
(8, '뭐든지 자랑하는 방', 4);

-- 신고 카테고리 초기 데이터
INSERT INTO `report_categories` (`name`, `description`, `seq`) VALUES
('스팸 및 홍보', '광고성 게시물 또는 스팸', 1),
('학대나 괴롭힘', '다른 사용자를 괴롭히거나 학대하는 행위', 2),
('해로운 허위 정보', '거짓 정보 유포', 3),
('개인정보 노출 및 침해', '개인정보 무단 공개', 4),
('욕설 및 비방', '욕설, 비하, 명예훼손', 5),
('음란물 및 선정성', '부적절한 성적 콘텐츠', 6),
('기타', '기타 신고 사유', 7);

-- 관리자 계정 (테스트용)
INSERT INTO `users` (`id`, `user_id`, `password`, `username`) VALUES
(UUID(), 'admin@durudurub.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '관리자안됨'); -- 비번 123456 입니다

-- 테스트 일반 유저 (비밀번호: 123456)
INSERT INTO `users` (`id`, `user_id`, `password`, `username`, `address`) VALUES
(UUID(), 'user1@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '테스트유저1', '서울시 강남구'),
(UUID(), 'user2@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '테스트유저2', '서울시 서초구'),
(UUID(), 'premium@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '프리미엄유저', '서울시 마포구');

SELECT * FROM `users`;

INSERT INTO `user_auth` (`user_no`, `auth`) VALUES
(1, 'ROLE_ADMIN');

INSERT INTO `user_auth` (`user_no`, `auth`) VALUES
(2, 'ROLE_USER'),
(3, 'ROLE_USER'),
(4, 'ROLE_USER');

SELECT * FROM `user_auth`;

-- 샘플 모임 데이터
INSERT INTO `clubs` (`host_no`, `category_no`, `sub_category_no`, `title`, `description`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(2, 1, 1, '매주 토요일 독서 모임', '다양한 책을 읽고 이야기 나눠요!', 10, '서울시 강남구 스터디카페', '37.49790000', '127.02760000', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(2, 2, 5, '주말 러닝 크루', '함께 달리며 건강 챙겨요!', 20, '올림픽공원', '37.51330000', '127.10010000', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(3, 3, 9, '맛집 탐방단', '숨은 맛집을 찾아 떠나요', 8, '서울 전역', '37.53450000', '37.53450000', DATE_ADD(NOW(), INTERVAL 5 DAY), 'RECRUITING'),
(4, 4, 13, '보드게임 모임', '다양한 보드게임을 즐겨요', 6, '홍대 보드게임카페', '37.55630000', '126.92370000', DATE_ADD(NOW(), INTERVAL 2 DAY), 'RECRUITING');
INSERT INTO `clubs` (`host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(2, 8, 8, '더그 러브 모임', '더그를 찾아서','/uploads/clubs/find-dug.jpg', 20, '고잉메리홈', '37.55630000', '126.92370000', DATE_ADD(NOW(), INTERVAL 2 DAY), 'RECRUITING');

-- 모임 멤버 추가
INSERT INTO `club_members` (`club_no`, `user_no`, `status`) VALUES
(1, 2, 'APPROVED'),
(1, 3, 'APPROVED'),
(2, 2, 'APPROVED'),
(3, 3, 'APPROVED'),
(4, 4, 'APPROVED'),
(5, 2, 'APPROVED');

-- 샘플 배너
-- /uploads/banners 파일로 변경!
INSERT INTO `banners` (`title`, `image_url`, `link_url`, `position`, `seq`) VALUES
('두루두루 오픈 기념', '/uploads/banners/durudurub-open.png', '/clubs', 'MAIN', 1),
('케이크 먹고 싶어요', '/uploads/banners/cafe_banner.png', '/clubs', 'MAIN', 2),
('모임을 만들어보세요!', '/uploads/banners/durudurub-newClub.png', '/clubs', 'MAIN', 3),
('스테이크 사주세요', '/uploads/banners/restaurant_banner.png', '/clubs', 'MAIN', 4),
('작은 핸드폰 원하세요?', '/uploads/banners/smartphone_banner.png', '/clubs', 'MAIN', 5),
('프리미엄 50%', '/uploads/banners/durudurub-premium.png', '/clubs', 'MAIN', 6),
('로또 당첨되면 바로 뜨자', '/uploads/banners/travel_air_banner.png', '/clubs', 'MAIN', 7);


-- 샘플 공지사항
INSERT INTO `notices` (`writer_no`, `category`, `title`, `content`, `is_important`) VALUES
(1, '서비스 안내', '두루두루 서비스 오픈 안내', '안녕하세요! 두루두루 소모임 플랫폼이 오픈했습니다. 다양한 모임에 참여해보세요!', 'Y'),
(1, '업데이트', '프리미엄 구독 서비스 안내', '프리미엄 구독 시 AI 검색 무제한, 광고 없는 서비스를 이용하실 수 있습니다.', 'N'),
(1, '이벤트', '오픈 기념 이벤트 안내', '첫 달 프리미엄 구독 50% 할인 이벤트를 진행합니다!', 'Y');


-- 샘플 게시글
INSERT INTO `club_boards` (`club_no`, `writer_no`, `title`, `content`, `is_notice`) VALUES
(1, 2, '이번 주 읽을 책 추천합니다', '이번 주는 "데미안"을 함께 읽어보면 어떨까요?', 'N'),
(1, 3, '지난 모임 후기', '정말 좋은 시간이었습니다! 다음에도 참여할게요.', 'N'),
(2, 2, '러닝 코스 변경 안내', '이번 주는 올림픽공원 대신 한강공원에서 진행합니다.', 'Y');

-- 샘플 차단 데이터
INSERT INTO `user_bans`
(`user_no`, `reason`, `report_count_at_ban`, `ban_type`, `ban_end_date`, `is_active`)
VALUES
(1, '욕설 및 비방 신고 누적', 4, 'TEMPORARY', DATE_ADD(NOW(), INTERVAL 7 DAY), 'Y'),
(2, '광고 및 스팸 행위 반복', 5, 'TEMPORARY', DATE_ADD(NOW(), INTERVAL 7 DAY), 'Y'),
(3, '불법 콘텐츠 게시', 7, 'PERMANENT', NULL, 'Y');

