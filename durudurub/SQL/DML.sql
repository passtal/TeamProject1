-- =====================================================
-- 기초 데이터 DML
-- INSERT IGNORE + 명시적 PK 사용으로 기존 데이터를 건드리지 않습니다.
-- 이미 존재하는 데이터는 건너뛰고, 없는 데이터만 추가됩니다.
-- =====================================================

-- 대분류 카테고리 (UNIQUE KEY on name → INSERT IGNORE로 중복 방지)
INSERT IGNORE INTO `categories` (`no`, `name`, `description`, `icon`, `seq`) VALUES
(1, '자기계발', '나를 성장시키는 모임', '/img/icons/category-self-development.png', 1),
(2, '스포츠', '함께 땀 흘리는 모임', '/img/icons/category-sports.png', 2),
(3, '푸드', '맛있는 음식을 나누는 모임', '/img/icons/category-food.png', 3),
(4, '게임', '게임을 즐기는 모임', '/img/icons/category-game.png', 4),
(5, '동네친구', '동네에서 친구 만들기', '/img/icons/category-neighborhood.png', 5),
(6, '여행', '함께 떠나는 여행', '/img/icons/category-travel.png', 6),
(7, '예술', '예술을 사랑하는 모임', '/img/icons/category-art.png', 7),
(8, '기타', '그 외 다양한 취미활동!', '/img/icons/category-etc.png', 8);

-- 소분류 카테고리 (명시적 PK로 중복 방지)
-- 자기계발 (category_no = 1)
INSERT IGNORE INTO `sub_categories` (`no`, `category_no`, `name`, `seq`) VALUES
(1, 1, '독서', 1),
(2, 1, '스피치', 2),
(3, 1, '면접', 3),
(4, 1, '회화', 4);

-- 스포츠 (category_no = 2)
INSERT IGNORE INTO `sub_categories` (`no`, `category_no`, `name`, `seq`) VALUES
(5, 2, '러닝', 1),
(6, 2, '테니스', 2),
(7, 2, '풋살', 3),
(8, 2, '등산', 4);

-- 푸드 (category_no = 3)
INSERT IGNORE INTO `sub_categories` (`no`, `category_no`, `name`, `seq`) VALUES
(9, 3, '맛집', 1),
(10, 3, '한식', 2),
(11, 3, '베이킹', 3),
(12, 3, '쿠킹교실', 4);

-- 게임 (category_no = 4)
INSERT IGNORE INTO `sub_categories` (`no`, `category_no`, `name`, `seq`) VALUES
(13, 4, '보드게임', 1),
(14, 4, '홀덤', 2),
(15, 4, '포켓볼', 3),
(16, 4, 'e-sport', 4);

-- 동네친구 (category_no = 5)
INSERT IGNORE INTO `sub_categories` (`no`, `category_no`, `name`, `seq`) VALUES
(17, 5, '경도', 1),
(18, 5, '술래잡기', 2),
(19, 5, '술자리', 3),
(20, 5, '카풀', 4);

-- 여행 (category_no = 6)
INSERT IGNORE INTO `sub_categories` (`no`, `category_no`, `name`, `seq`) VALUES
(21, 6, '국내', 1),
(22, 6, '해외', 2),
(23, 6, '당일치기', 3),
(24, 6, '패키지', 4);

-- 예술 (category_no = 7)
INSERT IGNORE INTO `sub_categories` (`no`, `category_no`, `name`, `seq`) VALUES
(25, 7, '미술', 1),
(26, 7, '음악', 2),
(27, 7, '연극', 3),
(28, 7, '뮤지컬', 4);

-- 기타 (category_no = 8)
INSERT IGNORE INTO `sub_categories` (`no`, `category_no`, `name`, `seq`) VALUES
(29, 8, '반려동물 산책모임', 1),
(30, 8, '봉사활동 모임', 2),
(31, 8, '우리동네 플리마켓', 3),
(32, 8, '뭐든지 자랑하는 방', 4);

-- 신고 카테고리 초기 데이터 (명시적 PK)
INSERT IGNORE INTO `report_categories` (`no`, `name`, `description`, `seq`) VALUES
(1, '스팸 및 홍보', '광고성 게시물 또는 스팸', 1),
(2, '학대나 괴롭힘', '다른 사용자를 괴롭히거나 학대하는 행위', 2),
(3, '해로운 허위 정보', '거짓 정보 유포', 3),
(4, '개인정보 노출 및 침해', '개인정보 무단 공개', 4),
(5, '욕설 및 비방', '욕설, 비하, 명예훼손', 5),
(6, '음란물 및 선정성', '부적절한 성적 콘텐츠', 6),
(7, '기타', '기타 신고 사유', 7);

-- 관리자 계정 (UNIQUE KEY on user_id, username → INSERT IGNORE로 중복 방지)
INSERT IGNORE INTO `users` (`no`, `id`, `user_id`, `password`, `username`) VALUES
(1, UUID(), 'admin@durudurub.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '관리자안됨'); -- 비번 123456 입니다

-- 테스트 일반 유저 (비밀번호: 123456) (UNIQUE KEY on user_id, username)
INSERT IGNORE INTO `users` (`no`, `id`, `user_id`, `password`, `username`, `address`) VALUES
(2, UUID(), 'user1@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '테스트유저1', '서울시 강남구'),
(3, UUID(), 'user2@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '테스트유저2', '서울시 서초구'),
(4, UUID(), 'premium@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '프리미엄유저', '서울시 마포구'),
(5, UUID(), 'user3@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '테스트유저3', '서울시 마포구'),
(6, UUID(), 'user4@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '테스트유저4', '서울시 마포구'),
(7, UUID(), 'user5@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '테스트유저5', '서울시 마포구'),
(8, UUID(), 'user6@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '테스트유저6', '서울시 마포구'),
(9, UUID(), 'user7@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '테스트유저7', '서울시 마포구'),
(10, UUID(), 'user8@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '테스트유저8', '서울시 마포구');

-- 유저 권한 (명시적 PK)
INSERT IGNORE INTO `user_auth` (`no`, `user_no`, `auth`) VALUES
(1, 1, 'ROLE_ADMIN');

INSERT IGNORE INTO `user_auth` (`no`, `user_no`, `auth`) VALUES
(2, 2, 'ROLE_USER'),
(3, 3, 'ROLE_USER'),
(4, 4, 'ROLE_USER'),
(5, 5, 'ROLE_USER'),
(6, 6, 'ROLE_USER'),
(7, 7, 'ROLE_USER'),
(8, 8, 'ROLE_USER'),
(9, 9, 'ROLE_USER');


-- =====================================================
-- 샘플 모임 데이터 (명시적 PK로 중복 방지)
-- =====================================================

-- 1. 자기 계발
-- -1. 독서
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(1, 5, 1, 1, '매주 토요일 독서 모임', '다양한 책을 읽고 이야기 나눠요!', '/uploads/clubs/self-development/bookclub1.png', 10, '서울시 강남구 스터디카페', '37.49790000', '127.02760000', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(2, 2, 1, 1, '베르베르 베르사유 궁전에서 책 읽자', '베르사유 궁전가서 낭만을 즐기고 싶은 자여, 베르나르 베르베르 책이나 읽자', '/uploads/clubs/self-development/bookclub2.png', 10, '서울시 마포구 카페', '37.558458', '126.908235', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(3, 3, 1, 1, '숨어 있는 뮤지컬 원작 책 찾기', '뮤지컬을 좋아하는 사람들이 모인 원작 책 공유 모임!', '/uploads/clubs/self-development/bookclub3.png', 10, '인천광역시 부평동 카페', '37.487380', '126.626480', DATE_ADD(NOW(), INTERVAL 5 DAY), 'RECRUITING'),
(4, 4, 1, 1, '1권만 읽은 놈이 제일 무섭다', '꾸준하게 책 읽을 사람 모집합니다~', '/uploads/clubs/self-development/bookclub4.png', 5, '롯데 잠실', '37.55630000', '126.92370000', DATE_ADD(NOW(), INTERVAL 2 DAY), 'RECRUITING');
-- -2. 스피치
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(5, 6, 1, 2, '프레젠테이션 두려움 떨치기', '회사 발표 때마다 떨린다면 일로 오세요!', '/uploads/clubs/self-development/speech1.png', 10, '여의도 공유오피스', '37.531416', '126.932766', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(6, 7, 1, 2, '오늘도 말하는 방법 잊지 말아요', '자신감을 갖고, 목소리를 내보세요', '/uploads/clubs/self-development/speech2.png', 10, '부천 스터디카페', '37.506865', '126.788388', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(7, 8, 1, 2, '시장통 과일가게 사장님 따라하기', '시장통 사장님이 알려주는 큰 소리 내는 법', '/uploads/clubs/self-development/speech3.png', 10, '제주 올레시장 광장', '33.248878', '126.564094', DATE_ADD(NOW(), INTERVAL 5 DAY), 'RECRUITING'),
(8, 9, 1, 2, '스피치는 자신감!', '스피치는 자신감!', '/uploads/clubs/self-development/speech4.png', 6, '부평 스터디카페', '37.499737', '126.721040', DATE_ADD(NOW(), INTERVAL 2 DAY), 'RECRUITING');
-- -3. 면접
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(9, 5, 1, 3, '면접 끝판왕 되기', '면접 연습 같이할 사람 구해요~', '/uploads/clubs/self-development/interview1.png', 10, '노량진 학원', '37.515733', '126.942157', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(10, 2, 1, 3, '오늘 면접 주인공은 나다', '이번주 면접 보시는 분들 같이 연습해요', '/uploads/clubs/self-development/interview2.png', 10, '노량진 스터디카페', '37.515733', '126.942157', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(11, 3, 1, 3, '매번 면접에서 떨어져요. 다 여기로!', '같이 면접 공포증 없앨 분들 모집합니다', '/uploads/clubs/self-development/interview3.png', 10, '대치동 스터디카페', '37.499862', '127.063479', DATE_ADD(NOW(), INTERVAL 5 DAY), 'RECRUITING'),
(12, 4, 1, 3, '사회초년생 삐약이들만의 면접 팁 공유', '20대만 모일 수 있는 면접 모임', '/uploads/clubs/self-development/interview4.png', 10, '안산 스터디카페', '37.327307', '126.819799', DATE_ADD(NOW(), INTERVAL 2 DAY), 'RECRUITING');
-- -4. 회화
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(13, 6, 1, 4, '영어 회화 올리고 싶은 사람', '우리 모임에서는 영어로만 얘기할 수 있습니다', '/uploads/clubs/self-development/conversation1.png', 10, '이태원 카페', '37.538917', '126.992519', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(14, 7, 1, 4, '중국 여행 좋아하는 사람들 모임', '중국어 연습도 하고, 같이 여행도 가요!', '/uploads/clubs/self-development/conversation2.jpg', 15, '노원구 카페', '37.640386', '127.075086', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(15, 8, 1, 4, '영어 배우고 싶은 5060 모여라!', '이태원에서 영어 알려드려요! 재능 기부~', '/uploads/clubs/self-development/conversation3.jpg', 8, '이태원 카페', '37.538917', '126.992519', DATE_ADD(NOW(), INTERVAL 5 DAY), 'RECRUITING'),
(16, 9, 1, 4, '무역 영어 필요한 사람', '전공 무역학과 나온 사람들 모이세요', '/uploads/clubs/self-development/conversation4.png', 10, '인천 카페', '37.450278', '126.653488', DATE_ADD(NOW(), INTERVAL 2 DAY), 'RECRUITING');

-- 2. 스포츠
-- -1. 러닝
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(17, 2, 2, 5, '주말 러닝 크루', '함께 달리며 건강 챙겨요!', '/uploads/clubs/sports/running1.jpg', 8, '올림픽공원', '37.51330000', '127.10010000', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(18, 3, 2, 5, '매주 저녁8시 한강 러닝 모집', '매주 토요일 저녁 8시마다 한강에서 런닝하실분~', '/uploads/clubs/sports/running2.jpg', 10, '여의도 한강', '37.526940', '126.934765', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(19, 4, 2, 5, '러닝으로 다이어트 도전!', '오늘도 다이어트 실패하신 분들 오세요', '/uploads/clubs/sports/running3.jpg', 10, '인천 송도', '37.390232', '126.640082', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING');
-- -2. 테니스
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(20, 5, 2, 6, '서울 망원동 테니스 같이할 사람', '망원동 야외 테니스장에서 같이 테니스 하실 분~', '/uploads/clubs/sports/tennis1.jpg', 10, '망원동 테니스장', '37.554743', '126.898335', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(21, 6, 2, 6, '테니스 대결할 사람', '이번주 토요일 안산 테니스장 8시에 오삼. 진 사람 치킨사기', '/uploads/clubs/sports/tennis2.jfif', 4, '안산 야외테니스', '37.307036', '126.811365', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(22, 7, 2, 6, '테니스 사랑하는 사람들 (테사사)', '테니스를 사랑하는가? 그럼 공튀기면서 입장하기', '/uploads/clubs/sports/tennis3.jpg', 15, '평택 테니스장', '37.095984', '127.072944', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING');
-- -3. 풋살
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(23, 8, 2, 7, '풋살 동호회 만듭니다', '풋살 좋아하시면 누구든지 환영합니다', '/uploads/clubs/sports/foot1.jpg', 10, '수원 풋살장', '37.296292', '127.041254', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(24, 9, 2, 7, '평택 풋살인들 모여라', '평택 풋살장 8시에 달마다 경기합시다', '/uploads/clubs/sports/foot2.jpg', 20, '평택 풋살장', '37.010238', '127.118662', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(25, 1, 2, 7, '이번주 목요일 풋살할 사람', '나이X 경력X 직업X 이름도 안물어봅니다', '/uploads/clubs/sports/foot3.jfif', 15, '부산 풋살장', '35.187120', '129.166182', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING');
-- -4. 등산
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(26, 2, 2, 8, '반달가슴곰 보러 지리산 탐방대', '반달가슴곰 무섭지만 보고싶어서 지리산 갈건데 같이 갈 사람', '/uploads/clubs/sports/hiking1.jpg', 5, '지리산', '35.337784', '127.730641', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(27, 3, 2, 8, '등산이 좋아요', '산 좋아하는 사람들은 누구든 환영', '/uploads/clubs/sports/hiking2.jfif', 10, '문학산', '37.432428', '126.679583', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(28, 4, 2, 8, '주식은 떨어졌지만, 등산은 한다', '제 주식은 떨어짔지만, 등산은 해봅니다. 동지들이여 모여라', '/uploads/clubs/sports/hiking3.jfif', 5, '설악산', '38.120326', '128.465644', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING');

-- 3. 푸드
-- -1. 맛집
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(29, 5, 3, 9, '오늘도 날라간다. 피리부는 호빵맨', '1달에 한번씩 전국으로 빵집 투어할 사람 모집합니다', '/uploads/clubs/food/best1.jpeg', 4, '대전 성심당', '36.329079', '127.427190', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(30, 6, 3, 9, '우리 동네 맛집 마스터하기', '인천 부평동 사는 사람들, 같이 맛집 찾아내여~~', '/uploads/clubs/food/best2.jfif', 4, '부평역', '37.490552', '126.723651', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(31, 3, 3, 9, '기분 좋아지는 맛집 탐방', '스트레스도 날리고, 기분도 좋아지는 맛집 탐방해요!', '/uploads/clubs/food/best4.jpeg', 4, '부평역', '37.490552', '126.723651', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(32, 7, 3, 9, '먹고 싶지만, 돈이 없어요', '그지 깽깽들끼리 모여서 맛집 n/1 할 사람!', '/uploads/clubs/food/best3.jfif', 3, '서울 종로구', '37.596338', '126.977808', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING');
-- -2. 한식
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(33, 8, 3, 10, '한국인 입맛 사로잡는 한식집 투어', '한국인들만 참여 가능합니다.', '/uploads/clubs/food/koreafood1.jpg', 4, '목포', '34.806106', '126.387621', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(34, 9, 3, 10, '한식파 모두 모여라', '한식을 사랑한다면 누그든 참여 가능!', '/uploads/clubs/food/koreafood2.jpeg', 4, '광주', '35.160978', '126.859565', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(35, 1, 3, 10, '야채는 싫지만 한식은 좋아요', '야채 가득한 한식은 싫지만, 한식파인 사람들 모임', '/uploads/clubs/food/koreafood3.png', 4, '안동', '36.585912', '128.726030', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(36, 2, 3, 10, '한식, 분식, 일식, 양식, 매운거안매운거', '중에서 한식만 투어할 예정임', '/uploads/clubs/food/koreafood4.png', 3, '충주', '37.011232', '127.924030', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING');
-- -3. 베이킹
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(37, 4, 3, 11, '같이 빵 만들어요', '쿠킹룸 대여해서 같이 빵 만드는 모임', '/uploads/clubs/food/baking1.png', 4, '안양 베이킹', '37.029231', '127.269500', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(38, 5, 3, 11, '빵 만드는 교실 오픈', '베이킹에 관심 있으신 분들 무료로 알려드려요!', '/uploads/clubs/food/baking2.png', 4, '성남 베이킹', '37.398384', '127.113494', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(39, 6, 3, 11, '제빵왕 김탁구를 꿈꾸며', '분캐로 김탁구가 되고 싶다면 참여ㄱㄱ', '/uploads/clubs/food/baking3.jpeg', 4, '남양주 베이킹', '37.623439', '127.170886', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(40, 7, 3, 11, '예비 카페 사장 모집', '같이 빵 만드면서 디저트 연습해요!', '/uploads/clubs/food/baking4.jpeg', 3, '안성 베이킹', '37.029231', '127.276367', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING');

-- 4. 게임
-- -1. 보드게임
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(41, 8, 4, 13, '이번주 다빈치할 사람', '다빈치 게임 좋아하는 사람들 누구든 환영! 홍대역 2시!', '/uploads/clubs/game/boardgame1.jpeg', 6, '부천 보드카페', '37.487992', '126.781366', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(42, 9, 4, 13, '시간 날때 보드게임하는 모임', '시간 날때마다 모여서 보드게임하는 모임입니당. 단톡방 팔 예정', '/uploads/clubs/game/boardgame2.png', 8, '홍대역', '37.557757', '126.924435', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(43, 1, 4, 13, '동체 시력 최강자 가르는 할리갈리 모임', '할리갈리 최강자 뽑습니다', '/uploads/clubs/game/boardgame3.jpeg', 5, '구월동 보드카페', '37.445233', '126.701340', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');
-- -2. 홀덤
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(44, 2, 4, 14, '포커 게임 모임', '우리는 모여서 게임하는 모임입니다. 참고해주세요', '/uploads/clubs/game/poker1.png', 6, '인천 부평동', '37.490610', '126.723151', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(45, 3, 4, 14, '블라인드 포커 게임하기', '단톡방 파서 시간 날때마다 게임할 예정입니다. 가볍게 즐겨주세요~', '/uploads/clubs/game/poker2.png', 6, '시흥시', '37.362504', '126.742839', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(46, 4, 4, 14, '합법적 도박 온라인 포커 게임할 사람', '포커 깔아주시면 id 서로 공유합시다~', '/uploads/clubs/game/poker3.png', 6, '안양시', '37.389757', '126.934375', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');
-- -3. 포켓볼
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(47, 5, 4, 15, '포켓볼 모임', '안양 포켓볼 인원 모집합니다', '/uploads/clubs/game/pocketball1.png', 6, '안양시', '37.389757', '126.934375', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(48, 6, 4, 15, '5060만 가능한 포켓볼 모임', '아재들끼리 포켓볼도 치고, 한잔 하고 즐깁시다~', '/uploads/clubs/game/pocketball2.png', 6, '성남시', '37.398111', '127.113494', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(49, 7, 4, 15, '토요일 포켓볼게임 할 사람', '이번주 토요일 8시 소사역 집합!', '/uploads/clubs/game/pocketball3.png', 6, '소사역', '37.482959', '126.795557', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');
-- -4. e-sport
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(50, 8, 4, 16, '오바 와치아웃 빠대 매니아', '오버워치 빠대만 즐기는 사람들 4명 선착순', '/uploads/clubs/game/esport1.jpeg', 5, '안양시', '37.389757', '126.934375', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(51, 9, 4, 16, '홍진호가 왜 2인자임을 아는 모임', '요즘 애들은 가라. 스타크레프트1 조인할 사람 모집', '/uploads/clubs/game/esport2.jpeg', 6, '성남시', '37.398111', '127.113494', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(52, 1, 4, 16, '추억의 옛날 메이플스토리', '옛날 메이플 같이 하면서 메소 모아요~!', '/uploads/clubs/game/esport3.jpeg', 5, '소사역', '37.482959', '126.795557', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');

-- 5. 동네 친구
-- -1. 경도
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(53, 2, 5, 17, '경찰과 도둑 놀이', '경찰과 도둑 하고 싶은 30대 모여라!', '/uploads/clubs/join/police1.jpeg', 20, '과천 공원', '37.432023', '126.994948', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(54, 3, 5, 17, '다음주 수요일 저녁 경도 예정', '다음주 수요일 올림픽공원에서 5시에 경도하고 치킨 먹을 예정', '/uploads/clubs/join/police2.png', 15, '올림픽공원', '37.520900', '127.121580', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(55, 4, 5, 17, '경도할 사람 여기로!', '이번주 금요일에 한강공원에서 경도할 사람 참여ㄱㄱ', '/uploads/clubs/join/police3.png', 15, '한강공원', '37.518917', '127.087333', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');
-- -2. 술래잡기
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(56, 5, 5, 18, '철수와 영희 술래잡기', '철수와 영희 분장 필수! 잠실 호수공원 8시', '/uploads/clubs/join/join1.jpeg', 10, '잠실 호수공원', '37.511480', '127.105539', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(57, 6, 5, 18, '술래잡기 할 사람~!', '어린이공원에서 술래잡기 할 사람! 시간, 날짜 추후 공지!', '/uploads/clubs/join/join2.png', 15, '서울 어린이공원', '37.549550', '127.081813', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(58, 7, 5, 18, '토요일날 술래잡기 모집', '토요일에 술래잡기할 20,30대 모이세요~~', '/uploads/clubs/join/join3.jpeg', 15, '서울 한강공원', '37.532735', '127.069931', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');
-- -3. 술자리
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(59, 8, 5, 19, '오늘 삼겹살 먹을 사람', '오늘 용산역 기찻길 근처에서 삼겹살 먹을 예정입니다', '/uploads/clubs/join/alcohol1.jpeg', 5, '용산역', '37.528940', '126.965493', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(60, 9, 5, 19, '혼자 있는데 혼술 싫은 모임', '혼술하기 싫은 사람들끼리 모여서 오순도순 술마셔요~', '/uploads/clubs/join/alcohol2.jpeg', 4, '이태원', '37.539224', '126.992090', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(61, 1, 5, 19, '칵테일 좋아하는 사람 손!', '칵테일 맛집도 가고, 서로 공유도 해요!', '/uploads/clubs/join/alcohol3.jpg', 5, '이태원', '37.539224', '126.992090', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');

-- 6. 여행
-- -1. 국내
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(62, 2, 6, 21, '1달간 전국일주', '3월 2일부터 1달간 배낭 하나로 전국 일주 예정', '/uploads/clubs/travel/korea1.png', 10, '서울역', '37.554150', '126.970820', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(63, 3, 6, 21, '제주도 여행 같이 갈 청춘!', '20대라면 참여 가능한 제주도 청춘 여행!', '/uploads/clubs/travel/korea2.jpg', 8, '서울역', '37.554150', '126.970820', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(64, 4, 6, 21, '차 한대로 바다 여행', '차 한대 빌려서 같이 바다 보러갈 사람 모집합니다~', '/uploads/clubs/travel/korea3.jpg', 4, '서울역', '37.554150', '126.970820', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');
-- -2. 해외
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(65, 5, 6, 22, '2030 청춘 해외여행 같이 가요!', '2030대라면 참여 가능/목적지 아직 안 정함', '/uploads/clubs/travel/travel1.jpg', 10, '인천공항', '37.458853', '126.441989', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(66, 6, 6, 22, '몽골 은하수보러 조인할 사람', '3월 몽골 여행 갈 사람 구합니다', '/uploads/clubs/travel/travel2.jpeg', 8, '인천공항', '37.458853', '126.441989', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(67, 7, 6, 22, '배낭메고 순례길 걸을 분!', '스페인 순례길 같이 걸을 사람 구합니다', '/uploads/clubs/travel/travel3.jpeg', 6, '인천공항', '37.458853', '126.441989', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');
-- -3. 패키지
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(68, 8, 6, 23, '유럽 패키지 여행 모임', '유럽 패키지 여행 갈 예정인데, 인원 수 채우고 싶어요!', '/uploads/clubs/travel/package1.jpeg', 8, '인천공항', '37.458853', '126.441989', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(69, 9, 6, 23, '크리스마스 패키지 여행', '혼자 가기 무서워서, 크리스마스 같이 갈 사람 구합니다~', '/uploads/clubs/travel/package2.jpeg', 10, '인천공항', '37.458853', '126.441989', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(70, 9, 6, 23, '3월 일본 패키지 여행', '3월 말에 벚꽃 보러 일본 여행 2박3일 갈 사람~~', '/uploads/clubs/travel/package3.jpeg', 6, '인천공항', '37.458853', '126.441989', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');

-- 7. 예술
-- -1. 미술 (3개)
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(71, 2, 7, 25, '주말에 전시회 같이 볼 사람', '전시 보고 근처 카페에서 감상 공유해요! 토요일 2시.', '/uploads/clubs/art/art1.jpg', 6, '서울시립미술관', '37.564213', '126.975614', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(72, 3, 7, 25, '드로잉 카페 스케치 모임', '아이패드/스케치북 아무거나 OK! 2시간만 같이 그려요.', '/uploads/clubs/art/art2.jpg', 8, '홍대 드로잉카페', '37.556540', '126.923846', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(73, 4, 7, 25, '유화/아크릴 취미반 번개', '초보 환영! 재료는 각자, 서로 팁 공유하면서 그려요.', '/uploads/clubs/art/art3.jpg', 5, '성수 작업실', '37.544581', '127.055982', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');
-- -2. 음악 (3개)
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(74, 5, 7, 26, '퇴근 후 통기타 합주 모임', '코드 몇 개만 알아도 OK! 같이 연습하고 2~3곡 맞춰봐요.', '/uploads/clubs/art/music1.jpg', 6, '신촌 연습실', '37.556939', '126.937124', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(75, 6, 7, 26, '재즈/팝 보컬 스터디', '한 곡 정해서 파트 나누고 피드백! 녹음까지 해봐요.', '/uploads/clubs/art/music2.jpg', 8, '합정 스튜디오', '37.549623', '126.914032', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(76, 6, 7, 26, '클래식 감상 + 추천곡 교환', '좋아하는 곡 소개하고 같이 듣는 모임. 라이트하게 진행!', '/uploads/clubs/art/music3.jpg', 10, '광화문', '37.571675', '126.976857', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');
-- -3. 연극 (3개)
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(77, 7, 7, 27, '대학로 연극 같이 보러가요', '공연 보고 근처에서 후기 수다! 예매는 각자 진행해요.', '/uploads/clubs/art/play1.jpeg', 6, '대학로', '37.582604', '127.003150', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(78, 8, 7, 27, '연극 대본 리딩 모임', '짧은 대본으로 역할 나눠 리딩! 연기 경험 없어도 환영.', '/uploads/clubs/art/play2.jpeg', 8, '건대입구', '37.540484', '127.069293', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(79, 9, 7, 27, '즉흥연기(임프로) 체험 번개', '몸풀기 게임부터! 웃고 떠드는 편한 분위기예요.', '/uploads/clubs/art/play3.png', 10, '잠실', '37.513272', '127.100159', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');
-- -4. 뮤지컬 (3개)
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(80, 5, 7, 28, '뮤지컬 넘버 같이 듣고 떠들기', '최애 넘버 공유하고 플레이리스트 만들어요! 가볍게 수다.', '/uploads/clubs/art/musical1.jpg', 10, '여의도', '37.521930', '126.924556', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(81, 2, 7, 28, '뮤지컬 관람 + 커튼콜 후기 모임', '관람 후 감상평 공유! 사진/사인/MD 정보도 나눠요.', '/uploads/clubs/art/musical2.jpg', 6, '블루스퀘어', '37.538658', '126.999682', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(82, 3, 7, 28, '뮤지컬 노래 연습(듀엣/합창)', '파트 나눠서 맞춰보고 영상/녹음으로 피드백! 초보 환영.', '/uploads/clubs/art/musical3.png', 8, '강남 연습실', '37.498095', '127.027610', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');

-- 8. 기타
-- -1. 반려동물 산책 (3개)
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(83, 4, 8, 29, '한강 강아지 산책 파티', '강아지랑 같이 산책하고 사진도 찍어요! 리드줄 필수~', '/uploads/clubs/etc/find-dug.jpg', 8, '여의도 한강공원', '37.528314', '126.934945', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(84, 5, 8, 29, '저녁 산책 메이트 구해요', '퇴근 후 1시간 산책! 반려견 없으셔도 참여 가능(산책만 같이)!', '/uploads/clubs/etc/petwalk2.jpg', 6, '서울숲', '37.544379', '127.037442', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(85, 6, 8, 29, '주말 애견운동장 번개', '애견운동장 가서 뛰뛰! 중소형견 위주로 안전하게 놀아요.', '/uploads/clubs/etc/petwalk3.jpg', 10, '일산 호수공원', '37.658359', '126.768402', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');
-- -2. 봉사활동 (3개)
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(86, 7, 8, 30, '주말 플로깅(쓰레기 줍기) 봉사', '걷고 줍고 건강챙기고! 장갑/집게 있으면 가져오세요.', '/uploads/clubs/etc/volunteer1.png', 15, '잠실 한강공원', '37.517020', '127.104385', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(87, 8, 8, 30, '유기동물 보호소 청소 도우미', '보호소 환경정리 + 간단한 산책 보조. 따뜻한 마음만 오세요!', '/uploads/clubs/etc/volunteer2.png', 10, '인천 계양구', '37.545025', '126.737623', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(88, 9, 8, 30, '무료급식소 배식 봉사', '배식/정리/설거지 도와요. 처음 오시는 분도 환영!', '/uploads/clubs/etc/volunteer3.jpeg', 12, '서울역 인근', '37.554150', '126.970820', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');
-- -3. 플리마켓(아나바다) (3개)
INSERT IGNORE INTO `clubs` (`no`, `host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(89, 2, 8, 31, '우리끼리 미니 플리마켓 열자', '안 쓰는 물건 교환/판매! 자리세 없이 n/1로 준비해요.', '/uploads/clubs/etc/fleamarket1.jpeg', 10, '홍대 걷고싶은거리', '37.556340', '126.922341', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(90, 2, 8, 31, '아나바다: 옷장 정리 같이 해요', '옷/잡화 위주로 교환! 상태 좋은 것만 가져오기 약속~', '/uploads/clubs/etc/fleamarket2.jpeg', 8, '성수동', '37.544581', '127.055982', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(91, 3, 8, 31, '중고거래 말고 플리로 한방에!', '책/굿즈/생활용품 한 번에 정리. 구경만 와도 OK!', '/uploads/clubs/etc/fleamarket3.jpeg', 12, '부천 중앙공원', '37.503414', '126.766031', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING');


-- =====================================================
-- 클럽 멤버 (UNIQUE KEY on club_no, user_no → INSERT IGNORE로 중복 방지)
-- =====================================================
INSERT IGNORE INTO club_members (club_no, user_no, status) VALUES
(1, 5, 'APPROVED'),
(2, 2, 'APPROVED'),
(3, 3, 'APPROVED'),
(4, 4, 'APPROVED'),
(5, 6, 'APPROVED'),
(6, 7, 'APPROVED'),
(7, 8, 'APPROVED'),
(8, 9, 'APPROVED'),
(9, 5, 'APPROVED'),
(10, 2, 'APPROVED'),
(11, 3, 'APPROVED'),
(12, 4, 'APPROVED'),
(13, 6, 'APPROVED'),
(14, 7, 'APPROVED'),
(15, 8, 'APPROVED'),
(16, 9, 'APPROVED'),
(17, 2, 'APPROVED'),
(18, 3, 'APPROVED'),
(19, 4, 'APPROVED'),
(20, 5, 'APPROVED'),
(21, 6, 'APPROVED'),
(22, 7, 'APPROVED'),
(23, 8, 'APPROVED'),
(24, 9, 'APPROVED'),
(25, 1, 'APPROVED'),
(26, 2, 'APPROVED'),
(27, 3, 'APPROVED'),
(28, 4, 'APPROVED'),
(29, 5, 'APPROVED'),
(30, 6, 'APPROVED'),
(31, 3, 'APPROVED'),
(32, 7, 'APPROVED'),
(33, 8, 'APPROVED'),
(34, 9, 'APPROVED'),
(35, 1, 'APPROVED'),
(36, 2, 'APPROVED'),
(37, 4, 'APPROVED'),
(38, 5, 'APPROVED'),
(39, 6, 'APPROVED'),
(40, 7, 'APPROVED'),
(41, 8, 'APPROVED'),
(42, 9, 'APPROVED'),
(43, 1, 'APPROVED'),
(44, 2, 'APPROVED'),
(45, 3, 'APPROVED'),
(46, 4, 'APPROVED'),
(47, 5, 'APPROVED'),
(48, 6, 'APPROVED'),
(49, 7, 'APPROVED'),
(50, 8, 'APPROVED'),
(51, 9, 'APPROVED'),
(52, 1, 'APPROVED'),
(53, 2, 'APPROVED'),
(54, 3, 'APPROVED'),
(55, 4, 'APPROVED'),
(56, 5, 'APPROVED'),
(57, 6, 'APPROVED'),
(58, 7, 'APPROVED'),
(59, 8, 'APPROVED'),
(60, 9, 'APPROVED'),
(61, 1, 'APPROVED'),
(62, 2, 'APPROVED'),
(63, 3, 'APPROVED'),
(64, 4, 'APPROVED'),
(65, 5, 'APPROVED'),
(66, 6, 'APPROVED'),
(67, 7, 'APPROVED'),
(68, 8, 'APPROVED'),
(69, 9, 'APPROVED'),
(70, 9, 'APPROVED'),
(71, 2, 'APPROVED'),
(72, 3, 'APPROVED'),
(73, 4, 'APPROVED'),
(74, 5, 'APPROVED'),
(75, 6, 'APPROVED'),
(76, 6, 'APPROVED'),
(77, 7, 'APPROVED'),
(78, 8, 'APPROVED'),
(79, 9, 'APPROVED'),
(80, 5, 'APPROVED'),
(81, 2, 'APPROVED'),
(82, 3, 'APPROVED'),
(83, 4, 'APPROVED'),
(84, 5, 'APPROVED'),
(85, 6, 'APPROVED'),
(86, 7, 'APPROVED'),
(87, 8, 'APPROVED'),
(88, 9, 'APPROVED'),
(89, 2, 'APPROVED'),
(90, 2, 'APPROVED'),
(91, 3, 'APPROVED');


-- 샘플 배너 (명시적 PK)
INSERT IGNORE INTO `banners` (`no`, `title`, `image_url`, `link_url`, `position`, `seq`) VALUES
(1, '두루두루 오픈 기념', '/uploads/banners/durudurub-open.png', '/notice', 'MAIN', 1),
(2, '케이크 먹고 싶어요', '/uploads/banners/cafe_banner.png', '/club/list', 'MAIN', 2),
(3, '모임을 만들어보세요!', '/uploads/banners/durudurub-newClub.png', '/club/create', 'MAIN', 3),
(4, '스테이크 사주세요', '/uploads/banners/restaurant_banner.png', '/club/list', 'MAIN', 4),
(5, '작은 핸드폰 원하세요?', '/uploads/banners/smartphone_banner.png', '/club/list', 'MAIN', 5),
(6, '프리미엄 50%', '/uploads/banners/durudurub-premium.png', '/users/mypage', 'MAIN', 6),
(7, '로또 당첨되면 바로 뜨자', '/uploads/banners/travel_air_banner.png', '/club/list', 'MAIN', 7);


-- 샘플 공지사항 (명시적 PK)
INSERT IGNORE INTO `notices`
(`no`, `writer_no`, `category`, `title`, `content`, `is_important`, `view_counts`)
VALUES
(1, 1, '공지', '두루두룹 서비스 정식 오픈 안내',
 '안녕하세요, 두루두룹입니다! 
 
드디어 소모임 커뮤니티 서비스 ''두루두룹''이 정식으로 오픈되었습니다. 🎉

두루두룹은 같은 관심사를 가진 사람들이 모여 다양한 활동을 함께 즐길 수 있는 플랫폼입니다. 운동, 문화/예술, 게임, 푸드 등 다양한 카테고리의 모임을 만들고 참여할 수 있습니다.

주요 기능:
• 다양한 카테고리의 모임 생성 및 참여
• 모임별 게시글 작성 및 댓글 소통
• 관심있는 모임 즐겨찾기
• 리더의 승인을 통한 안전한 모임 운영

많은 관심과 이용 부탁드립니다.
감사합니다!', 'Y', 1245),
(2, 1, '이벤트', '신규 회원 환영 이벤트 - 첫 모임 참여 시 포인트 지급!',
 '🎁 신규 회원 환영 이벤트 안내

두루두룹에 가입하신 모든 신규 회원분들께 특별한 혜택을 드립니다!

이벤트 기간: 2026년 1월 22일 ~ 2월 28일

혜택 내용:
1️⃣ 회원가입 완료 시 500 포인트 지급
2️⃣ 첫 모임 참여 승인 시 1,000 포인트 추가 지급
3️⃣ 첫 게시글 작성 시 300 포인트 추가 지급

※ 포인트는 향후 프리미엄 기능 이용 시 사용 가능합니다.

여러분의 활발한 참여를 기다립니다! 💚', 'Y', 856),
(3, 1, '업데이트', '모임 게시글 사진 업로드 기능 추가 업데이트',
 '📢 새로운 기능이 추가되었습니다!

이번 업데이트에서 모임 게시글에 사진을 업로드할 수 있는 기능이 추가되었습니다.

주요 업데이트 내용:
✅ 게시글 작성 시 최대 10장의 사진 업로드 가능
✅ 사진 미리보 및 삭제 기능
✅ 게시글에 댓글 기능 추가
✅ 댓글 실시간 알림 기능

모임 활동 사진을 공유하고, 멤버들과 더욱 활발하게 소통해보세요!

더 나은 서비스를 위해 항상 노력하겠습니다.
감사합니다.', 'N', 432),
(4, 1, '점검', '서버 정기 점검 안내 (1월 27일 새벽 2시~4시)',
 '🔧 서버 정기 점검 안내

안정적인 서비스 제공을 위해 정기 점검을 실시합니다.

점검 일시: 2026년 1월 27일 (월) 02:00 ~ 04:00 (약 2시간)

점검 내용:
• 서버 안정화 작업
• 데이터베이스 최적화
• 보안 업데이트 적용

점검 시간 동안 서비스 이용이 일시적으로 중단됩니다.
양해 부탁드리며, 점검이 조기 완료될 경우 별도 공지 없이 서비스가 재개됩니다.

이용에 불편을 드려 죄송합니다.
감사합니다.', 'N', 678),
(5, 1, '공지', '부적절한 게시글 및 모임 신고 기능 안내',
 '📌 건전한 커뮤니티 문화를 위한 신고 기능 안내

두루두룹은 모든 회원이 안전하고 즐겁게 이용할 수 있는 커뮤니티를 만들기 위해 노력하고 있습니다.

신고 대상:
• 욕설, 비방, 차별적 표현이 포함된 게시글
• 허위 정보나 사기성 모임
• 불법적이거나 부적절한 내용
• 개인정보 무단 노출

신고 방법:
각 게시글 및 모임 상세 페이지의 우측 상단 [신고하기] 버튼을 클릭해주세요.

신고 처리:
접수된 신고는 24시간 내 검토되며, 운영 정책에 따라 조치됩니다.

여러분의 적극적인 참여로 더 나은 커뮤니티를 만들어갑시다.
감사합니다.', 'N', 523);


-- 샘플 게시글 (명시적 PK)
INSERT IGNORE INTO `club_boards` (`no`, `club_no`, `writer_no`, `title`, `content`, `is_notice`) VALUES
(1, 1, 2, '이번 주 읽을 책 추천합니다', '이번 주는 "데미안"을 함께 읽어보면 어떨까요?', 'N'),
(2, 1, 3, '지난 모임 후기', '정말 좋은 시간이었습니다! 다음에도 참여할게요.', 'N'),
(3, 2, 2, '러닝 코스 변경 안내', '이번 주는 올림픽공원 대신 한강공원에서 진행합니다.', 'Y');

-- 샘플 차단 데이터 (UNIQUE KEY on user_no → INSERT IGNORE로 중복 방지)
INSERT IGNORE INTO `user_bans`
(`user_no`, `reason`, `report_count_at_ban`, `ban_type`, `ban_end_date`, `is_active`)
VALUES
(1, '욕설 및 비방 신고 누적', 4, 'TEMPORARY', DATE_ADD(NOW(), INTERVAL 7 DAY), 'Y'),
(2, '광고 및 스팸 행위 반복', 5, 'TEMPORARY', DATE_ADD(NOW(), INTERVAL 7 DAY), 'Y'),
(3, '불법 콘텐츠 게시', 7, 'PERMANENT', NULL, 'Y');

