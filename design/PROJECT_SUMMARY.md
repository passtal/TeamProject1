# 두루두룹 메인 화면 개발 완료

## 📋 프로젝트 개요
Spring Boot + Thymeleaf를 사용하여 "두루두룹" 모임 사이트의 메인 화면을 Bootstrap 기반으로 개발했습니다.
Figma 디자인을 그대로 반영하여 완성도 높은 웹 페이지를 구현했습니다.

---

## 📁 프로젝트 구조

```
src/main/
├── java/com/aloha/design/
│   └── controller/
│       └── IndexController.java          # 메인 화면 요청 처리
├── resources/
│   ├── templates/
│   │   ├── index.html                   # 메인 페이지
│   │   ├── fragments/
│   │   │   ├── navbar.html              # 네비게이션 바 컴포넌트
│   │   │   └── footer.html              # 푸터 컴포넌트
│   │   └── layouts/
│   │       └── base.html                # 기본 레이아웃 (향후 사용)
│   ├── static/
│   │   ├── css/
│   │   │   ├── reset.css                # CSS 초기화
│   │   │   └── style.css                # 커스텀 스타일
│   │   └── img/
│   │       ├── hero-image.png           # 히어로 섹션 이미지
│   │       ├── ad-banner.png            # 광고 배너 이미지
│   │       └── icons/                   # 아이콘 저장 폴더
│   └── application.properties           # Thymeleaf 설정
```

---

## 🎨 구현된 주요 섹션

### 1. 네비게이션 바 (Navbar)
- 두루두룹 로고 및 브랜드명
- 모임 검색 기능
- 미니게임 버튼
- 알림 아이콘

### 2. 히어로 섹션 (Hero Section)
- 큰 타이틀: "함께 하면 더 즐거운 순간"
- 설명 텍스트
- CTA 버튼: "모임 둘러보기"
- 이미지: 함께하는 즐거운 순간

### 3. 광고 배너 섹션 (Ad Banner)
- 배너 이미지
- 프리미엄 원두 커피 홍보
- "신규 회원 20% 할인" 메시지
- 페이지네이션 닷
- 자세히 보기 버튼

### 4. 카테고리 섹션 (Category Section)
- 8가지 카테고리 버튼:
  - 자기계발
  - 스포츠
  - 푸드
  - 게임
  - 동네친구
  - 여행
  - 예술
  - 반려동물
- 각 카테고리별 컬러풀한 아이콘
- 호버 효과로 상호작용성 강화

### 5. 푸터 (Footer)
- 회사 소개
- 소셜 미디어 링크
- 서비스 링크 (모임 찾기, 만들기, 가이드, FAQ)
- 회사 정보 (소개, 공지사항, 이용약관, 개인정보)
- 회사 연락처 및 주소

---

## 🛠 기술 스택

### Backend
- **Spring Boot 3.5.10**
- **Thymeleaf**: 템플릿 엔진
- **Thymeleaf Layout Dialect**: 레이아웃 관리

### Frontend
- **Bootstrap 5.3.0**: UI 프레임워크
- **HTML5**: 마크업
- **CSS3**: 스타일링
- **SVG**: 아이콘 그래픽

### DevTools
- **Gradle**: 빌드 도구
- **Spring Boot DevTools**: 개발 편의성

---

## ✨ 주요 특징

### 1. 반응형 디자인
- Mobile 우선 접근법
- 모든 화면 크기에 최적화:
  - 데스크톱 (1200px+)
  - 태블릿 (768px-1199px)
  - 모바일 (<768px)

### 2. Thymeleaf 프래그먼트
- `navbar.html`: 재사용 가능한 네비게이션
- `footer.html`: 재사용 가능한 푸터
- 다른 페이지에서 쉽게 포함 가능

### 3. 색상 시스템
```css
--primary-color: #00A651 (녹색)
--dark-color: #101828 (어두운 회색)
--text-muted: #4A5565 (중간 회색)
--light-gray: #F3F4F6 (밝은 회색)
--border-color: #E5E7EB (테두리)
--success-color: #00A651 (성공)
```

### 4. 인터랙션
- 카테고리 버튼 호버 효과 (4px 위로 이동)
- 부드러운 색상 전환
- 그림자 효과로 깊이감 표현

### 5. Figma 이미지
- 자동 다운로드: hero-image.png (1168x1000)
- 자동 다운로드: ad-banner.png (2432x640)

---

## 🚀 실행 방법

### 1. 프로젝트 빌드
```bash
./gradlew.bat build
```

### 2. 애플리케이션 실행
```bash
./gradlew.bat bootRun
```

### 3. 브라우저 접속
```
http://localhost:8080/
```

---

## 📋 Thymeleaf 설정 (application.properties)
```properties
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.cache=false
spring.thymeleaf.mode=HTML
spring.thymeleaf.encoding=UTF-8
```

---

## 🎯 향후 확장 계획

1. **동적 카테고리 데이터**: 데이터베이스에서 카테고리 로드
2. **사용자 인증**: 로그인/회원가입 기능
3. **모임 목록 페이지**: 카테고리별 모임 표시
4. **모임 상세 페이지**: 개별 모임 정보
5. **검색 기능**: 모임 검색 API
6. **장바구니/위시리스트**: 관심 모임 저장
7. **API 연동**: 백엔드 API와 통신

---

## 📝 파일 설명

### controller/IndexController.java
```java
@GetMapping("/")
public String index(Model model) {
    // 카테고리, 광고 데이터 전달
    return "index";
}
```

### templates/index.html
- 메인 페이지 구조
- 모든 섹션 포함
- Bootstrap 클래스 활용

### templates/fragments/navbar.html
- 네비게이션 바 컴포넌트
- 로고, 검색, 버튼 포함
- 다른 페이지에서 재사용 가능

### templates/fragments/footer.html
- 푸터 컴포넌트
- 회사 정보, 링크 포함
- 모든 페이지에 공통 사용

### static/css/reset.css
- 브라우저 기본 스타일 제거
- 일관된 기본값 설정

### static/css/style.css
- 커스텀 스타일
- 색상, 레이아웃, 반응형 디자인
- 호버 효과 및 애니메이션

---

## 🔍 주요 CSS 클래스

### 컴포넌트
- `.navbar`: 네비게이션 바
- `.hero-section`: 히어로 섹션
- `.ad-section`: 광고 배너
- `.category-section`: 카테고리 섹션
- `.footer`: 푸터

### 유틸리티
- `.text-dark`: 어두운 텍스트
- `.text-muted`: 옅은 텍스트
- `.btn-success`: 초록색 버튼
- `.rounded-4`: 큰 모서리 둥글게
- `.shadow-lg`: 큰 그림자

---

## 💡 개발 팁

1. **핫 리로드**: Spring Boot DevTools로 저장 시 자동 새로고침
2. **Thymeleaf 문법**:
   - `th:href="@{/path}"`: 경로 링크
   - `th:src="@{/image.png}"`: 이미지 경로
   - `th:replace="~{fragments/navbar :: navbar}"`: 프래그먼트 포함
3. **Bootstrap**: 공식 문서 참고 (https://getbootstrap.com)

---

## 📧 연락처
- 이메일: contact@durudurub.com
- 고객센터: 1588-0000 (평일 10:00 - 18:00)
- 주소: 서울특별시 강남구 테헤란로 123, 4층

---

**완성일**: 2026년 1월 30일
**상태**: ✅ 완성
