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
(UUID(), 'premium@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '프리미엄유저', '서울시 마포구'),
(UUID(), 'user3@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '테스트유저3', '서울시 마포구'),
(UUID(), 'user4@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '테스트유저4', '서울시 마포구'),
(UUID(), 'user5@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '테스트유저5', '서울시 마포구'),
(UUID(), 'user6@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '테스트유저6', '서울시 마포구'),
(UUID(), 'user7@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '테스트유저7', '서울시 마포구');

SELECT * FROM `users`;

INSERT INTO `user_auth` (`user_no`, `auth`) VALUES
(1, 'ROLE_ADMIN');

INSERT INTO `user_auth` (`user_no`, `auth`) VALUES
(2, 'ROLE_USER'),
(3, 'ROLE_USER'),
(4, 'ROLE_USER'),
(5, 'ROLE_USER'),
(6, 'ROLE_USER'),
(7, 'ROLE_USER'),
(8, 'ROLE_USER');

SELECT * FROM `user_auth`;

-- 샘플 모임 데이터
INSERT INTO `clubs` (`host_no`, `category_no`, `sub_category_no`, `title`, `description`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(3, 3, 9, '맛집 탐방단', '숨은 맛집을 찾아 떠나요', 8, '서울 전역', '37.53450000', '37.53450000', DATE_ADD(NOW(), INTERVAL 5 DAY), 'RECRUITING'),
(4, 4, 13, '보드게임 모임', '다양한 보드게임을 즐겨요', 6, '홍대 보드게임카페', '37.55630000', '126.92370000', DATE_ADD(NOW(), INTERVAL 2 DAY), 'RECRUITING');
INSERT INTO `clubs` (`host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(2, 8, 8, '더그 러브 모임', '더그를 찾아서','/uploads/clubs/find-dug.jpg', 20, '고잉메리홈', '37.55630000', '126.92370000', DATE_ADD(NOW(), INTERVAL 2 DAY), 'RECRUITING');

-- 1. 자기 계발
-- -1. 독서
INSERT INTO `clubs` (`host_no`, `category_no`, `sub_category_no`, `title`, `description`,  `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(5, 1, 1, '매주 토요일 독서 모임', '다양한 책을 읽고 이야기 나눠요!', '/uploads/clubs/self-development/bookclub1.png', 10, '서울시 강남구 스터디카페', '37.49790000', '127.02760000', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(2, 1, 1, '베르베르 베르사유 궁전에서 책 읽자', '베르사유 궁전가서 낭만을 즐기고 싶은 자여, 베르나르 베르베르 책이나 읽자', '/uploads/clubs/self-development/bookclub2.png', 10, '서울시 마포구 카페', '37.558458', '126.908235', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(3, 1, 1, '숨어 있는 뮤지컬 원작 책 찾기', '뮤지컬을 좋아하는 사람들이 모인 원작 책 공유 모임!', '/uploads/clubs/self-development/bookclub3.png', 10, '인천광역시 부평동 카페', '37.487380', '126.626480', DATE_ADD(NOW(), INTERVAL 5 DAY), 'RECRUITING'),
(4, 1, 1, '1권만 읽은 놈이 제일 무섭다', '꾸준하게 책 읽을 사람 모집합니다~', '/uploads/clubs/self-development/bookclub4.png', 5, '롯데 잠실', '37.55630000', '126.92370000', DATE_ADD(NOW(), INTERVAL 2 DAY), 'RECRUITING');
-- -2. 스피치
INSERT INTO `clubs` (`host_no`, `category_no`, `sub_category_no`, `title`, `description`,  `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(6, 1, 2, '프레젠테이션 두려움 떨치기', '회사 발표 때마다 떨린다면 일로 오세요!', '/uploads/clubs/self-development/speech1.png', 10, '여의도 공유오피스', '37.531416', '126.932766', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(7, 1, 2, '오늘도 말하는 방법 잊지 말아요', '자신감을 갖고, 목소리를 내보세요', '/uploads/clubs/self-development/speech2.png', 10, '부천 스터디카페', '37.506865', '126.788388', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(8, 1, 2, '시장통 과일가게 사장님 따라하기', '시장통 사장님이 알려주는 큰 소리 내는 법', '/uploads/clubs/self-development/speech3.png', 10, '제주 올레시장 광장', '33.248878', '126.564094', DATE_ADD(NOW(), INTERVAL 5 DAY), 'RECRUITING'),
(9, 1, 2, '스피치는 자신감!', '스피치는 자신감!', '/uploads/clubs/self-development/speech4.png', 6, '부평 스터디카페', '37.499737', '126.721040', DATE_ADD(NOW(), INTERVAL 2 DAY), 'RECRUITING');
-- -3. 면접
INSERT INTO `clubs` (`host_no`, `category_no`, `sub_category_no`, `title`, `description`,  `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(5, 1, 3, '면접 끝판왕 되기', '면접 연습 같이할 사람 구해요~', '/uploads/clubs/self-development/interview1.png', 10, '노량진 학원', '37.515733', '126.942157', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(2, 1, 3, '오늘 면접 주인공은 나다', '이번주 면접 보시는 분들 같이 연습해요', '/uploads/clubs/self-development/interview2.png', 10, '노량진 스터디카페', '37.515733', '126.942157', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(3, 1, 3, '매번 면접에서 떨어져요. 다 여기로!', '같이 면접 공포증 없앨 분들 모집합니다', '/uploads/clubs/self-development/interview3.png', 10, '대치동 스터디카페', '37.499862', '127.063479', DATE_ADD(NOW(), INTERVAL 5 DAY), 'RECRUITING'),
(4, 1, 3, '사회초년생 삐약이들만의 면접 팁 공유', '20대만 모일 수 있는 면접 모임', '/uploads/clubs/self-development/interview4.png', 10, '안산 스터디카페', '37.327307', '126.819799', DATE_ADD(NOW(), INTERVAL 2 DAY), 'RECRUITING');
-- -4. 회화
INSERT INTO `clubs` (`host_no`, `category_no`, `sub_category_no`, `title`, `description`,  `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(6, 1, 4, '영어 회화 올리고 싶은 사람', '우리 모임에서는 영어로만 얘기할 수 있습니다', '/uploads/clubs/self-development/conversation1.png', 10, '이태원 카페', '37.538917', '126.992519', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(7, 1, 4, '중국 여행 좋아하는 사람들 모임', '중국어 연습도 하고, 같이 여행도 가요!', '/uploads/clubs/self-development/conversation2.jpg', 15, '노원구 카페', '37.640386', '127.075086', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(8, 1, 4, '영어 배우고 싶은 5060 모여라!', '이태원에서 영어 알려드려요! 재능 기부~', '/uploads/clubs/self-development/conversation3.jpg', 8, '이태원 카페', '37.538917', '126.992519', DATE_ADD(NOW(), INTERVAL 5 DAY), 'RECRUITING'),
(9, 1, 4, '무역 영어 필요한 사람', '전공 무역학과 나온 사람들 모이세요', '/uploads/clubs/self-development/conversation4.png', 10, '인천 카페', '37.450278', '126.653488', DATE_ADD(NOW(), INTERVAL 2 DAY), 'RECRUITING');

-- 2. 스포츠
-- -1.러닝
INSERT INTO `clubs` (`host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(2, 2, 1, '주말 러닝 크루', '함께 달리며 건강 챙겨요!', '/uploads/clubs/sports/running1.jpg', 8, '올림픽공원', '37.51330000', '127.10010000', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(3, 2, 1, '매주 저녁8시 한강 러닝 모집', '매주 토요일 저녁 8시마다 한강에서 런닝하실분~', '/uploads/clubs/sports/running2.jpg', 10, '여의도 한강', '37.526940', '126.934765', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(4, 2, 1, '러닝으로 다이어트 도전!', '오늘도 다이어트 실패하신 분들 오세요', '/uploads/clubs/sports/running3.jpg', 10, '인천 송도', '37.390232', '126.640082', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING');
-- -2. 테니스
INSERT INTO `clubs` (`host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(5, 2, 2, '서울 망원동 테니스 같이할 사람', '망원동 야외 테니스장에서 같이 테니스 하실 분~', '/uploads/clubs/sports/tennis1.jpg', 10, '망원동 테니스장', '37.554743', '126.898335', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(6, 2, 2, '테니스 대결할 사람', '이번주 토요일 안산 테니스장 8시에 오삼. 진 사람 치킨사기', '/uploads/clubs/sports/tennis2.jfif', 4, '안산 야외테니스', '37.307036', '126.811365', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(7, 2, 2, '테니스 사랑하는 사람들 (테사사)', '테니스를 사랑하는가? 그럼 공튀기면서 입장하기', '/uploads/clubs/sports/tennis3.jpg', 15, '평택 테니스장', '37.095984', '127.072944', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING');
-- -3. 풋살
INSERT INTO `clubs` (`host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(8, 2, 3, '풋살 동호회 만듭니다', '풋살 좋아하시면 누구든지 환영합니다', '/uploads/clubs/sports/foot1.jpg', 10, '수원 풋살장', '37.296292', '127.041254', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(9, 2, 3, '평택 풋살인들 모여라', '평택 풋살장 8시에 달마다 경기합시다', '/uploads/clubs/sports/foot2.jpg', 20, '평택 풋살장', '37.010238', '127.118662', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(1, 2, 3, '이번주 목요일 풋살할 사람', '나이X 경력X 직업X 이름도 안물어봅니다', '/uploads/clubs/sports/foot3.jfif', 15, '부산 풋살장', '35.187120', '129.166182', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING');
-- -4. 등산
INSERT INTO `clubs` (`host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(2, 2, 4, '반달가슴곰 보러 지리산 탐방대', '반달가슴곰 무섭지만 보고싶어서 지리산 갈건데 같이 갈 사람', '/uploads/clubs/sports/hiking1.jpg', 5, '지리산', '35.337784', '127.730641', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(3, 2, 4, '등산이 좋아요', '산 좋아하는 사람들은 누구든 환영', '/uploads/clubs/sports/hiking2.jfif', 10, '문학산', '37.432428', '126.679583', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(4, 2, 4, '주식은 떨어졌지만, 등산은 한다', '제 주식은 떨어짔지만, 등산은 해봅니다. 동지들이여 모여라', '/uploads/clubs/sports/hiking3.jfif', 5, '설악산', '38.120326', '128.465644', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING');

-- 3. 푸드
-- -1. 맛집
INSERT INTO `clubs` (`host_no`, `category_no`, `sub_category_no`, `title`, `description`, `thumbnail_img`, `max_members`, `location`, `lat`, `lng`, `club_date`, `status`) VALUES
(5, 3, 1, '오늘도 날라간다. 피리부는 호빵맨', '1달에 한번씩 전국으로 빵집 투어할 사람 모집합니다', '/uploads/clubs/food/best1.jpg', 4, '대전 성심당', '36.329079', '127.427190', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING'),
(6, 3, 1, '우리 동네 맛집 마스터하기', '인천 부평동 사는 사람들, 같이 맛집 찾아내여~~', '/uploads/clubs/food/best2.jfif', 4, '부평역', '37.490552', '126.723651', DATE_ADD(NOW(), INTERVAL 7 DAY), 'RECRUITING'),
(7, 3, 1, '먹고 싶지만, 돈이 없어요', '그지 깽깽들끼리 모여서 맛집 n/1 할 사람!', '/uploads/clubs/food/best3.jfif', 3, '서울 종로구', '37.596338', '126.977808', DATE_ADD(NOW(), INTERVAL 3 DAY), 'RECRUITING');
-- -2. 한식
-- -3. 베이킹

-- 4. 자기 계발
-- 5. 자기 계발
-- 6. 자기 계발
-- 7. 자기 계발
-- 8. 자기 계발

-- 모임 멤버 추가 (꼭 하기!!)
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
('두루두루 오픈 기념', '/uploads/banners/durudurub-open.png', '/notice', 'MAIN', 1),
('케이크 먹고 싶어요', '/uploads/banners/cafe_banner.png', '/club/list', 'MAIN', 2),
('모임을 만들어보세요!', '/uploads/banners/durudurub-newClub.png', '/club/create', 'MAIN', 3),
('스테이크 사주세요', '/uploads/banners/restaurant_banner.png', '/club/list', 'MAIN', 4),
('작은 핸드폰 원하세요?', '/uploads/banners/smartphone_banner.png', '/club/list', 'MAIN', 5),
('프리미엄 50%', '/uploads/banners/durudurub-premium.png', '/users/mypage', 'MAIN', 6),
('로또 당첨되면 바로 뜨자', '/uploads/banners/travel_air_banner.png', '/club/list', 'MAIN', 7);


-- 샘플 공지사항
INSERT INTO `notices`
(`writer_no`, `category`, `title`, `content`, `is_important`, `view_counts`)
VALUES
(1, '공지', '두루두룹 서비스 정식 오픈 안내',
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
(1, '이벤트', '신규 회원 환영 이벤트 - 첫 모임 참여 시 포인트 지급!',
 '🎁 신규 회원 환영 이벤트 안내

두루두룹에 가입하신 모든 신규 회원분들께 특별한 혜택을 드립니다!

이벤트 기간: 2026년 1월 22일 ~ 2월 28일

혜택 내용:
1️⃣ 회원가입 완료 시 500 포인트 지급
2️⃣ 첫 모임 참여 승인 시 1,000 포인트 추가 지급
3️⃣ 첫 게시글 작성 시 300 포인트 추가 지급

※ 포인트는 향후 프리미엄 기능 이용 시 사용 가능합니다.

여러분의 활발한 참여를 기다립니다! 💚', 'Y', 856),
(1, '업데이트', '모임 게시글 사진 업로드 기능 추가 업데이트',
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
(1, '점검', '서버 정기 점검 안내 (1월 27일 새벽 2시~4시)',
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
(1, '공지', '부적절한 게시글 및 모임 신고 기능 안내',
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

