# **프로젝트 : 두루두룹 (Duru Durub)** 👥

<p align="center">
  <img src="./durudurub/uploads/banners/durudurub-newClub.png" width="600" alt="두루두룹 대표 이미지">
</p>

> 관심사가 같은 사람들과 모여 새로운 경험을 만들어가는 **소셜 모임 플랫폼**

<br>

## 📌 시연 영상

[![시연 영상](https://img.youtube.com/vi/yVU2fAoMcvc/0.jpg)](https://www.youtube.com/watch?v=yVU2fAoMcvc)

> ⬆️ 이미지를 클릭하면 시연 영상으로 이동합니다.

<br>

---

## 📋 목차
- [1. 프로젝트 개요](#1-프로젝트-개요)
- [2. 프로젝트 구조](#2-프로젝트-구조)
- [3. 팀 구성 및 역할](#3-팀-구성-및-역할)
- [4. 기술 스택](#4-기술-스택)
- [5. 프로젝트 수행 경과](#5-프로젝트-수행-경과)
- [6. 핵심 기능 코드 리뷰](#6-핵심-기능-코드-리뷰)
- [7. 화면 UI](#7-화면-ui)
- [8. 자체 평가 의견](#8-자체-평가-의견)

---

<br>

## 1. 프로젝트 개요

### 1-1. 프로젝트 주제
- 관심사 기반 소셜 모임 플랫폼 **"두루두룹"**

### 1-2. 주제 선정 배경
- 코로나 이후 오프라인 만남에 대한 수요 증가
- 기존 소셜 플랫폼의 한계점 (카테고리 세분화 부족 등)

### 1-3. 기획 의도
- 누구나 쉽게 관심사 기반 모임을 만들고 참여할 수 있는 플랫폼
- AI 검색을 통한 맞춤형 모임 추천

### 1-4. 활용 방안
- 사용자는 카테고리별로 모임을 탐색하고, AI 검색으로 취향에 맞는 모임을 추천받을 수 있습니다.
- 프리미엄 구독을 통해 무제한 AI 검색 등 부가 기능을 이용할 수 있습니다.

### 1-5. 기대효과
- 오프라인 커뮤니티 활성화
- AI 기반 개인화 추천으로 사용자 만족도 향상

<br>

---

## 2. 프로젝트 구조

### 2-1. 주요 기능
| 구분 | 기능 |
|:---:|:---|
| 👤 사용자 | 회원가입 / 로그인 (Spring Security 세션 인증) |
| 🔍 모임 탐색 | 카테고리별 모임 목록 / 모임 상세 조회 |
| 🤖 AI 검색 | OpenAI API 기반 맞춤형 모임 검색 |
| ❤️ 즐겨찾기 | 관심 모임 좋아요 / 즐겨찾기 목록 관리 |
| 📝 게시판 | 모임 내 게시글 / 댓글 작성 및 좋아요 |
| 🗺️ 지도 | Kakao Maps API 기반 모임 위치 표시 |
| 💳 결제 | Toss Payments 연동 프리미엄 구독 |
| 🎮 미니게임 | 랜덤 미니게임 |
| 🔔 공지사항 | 공지 등록 / 조회 / 수정 / 삭제 |
| 🛡️ 관리자 | 회원 관리 / 모임 관리 / 배너 관리 / 신고 관리 |

### 2-2. 메뉴 구조도
<details>
  <summary>메뉴 구조도 펼치기</summary>
  
  <!-- 여기에 메뉴 구조도 이미지를 넣으세요 -->
  ![메뉴구조도](./여기에_메뉴구조도_이미지_경로.png)
</details>

<br>

---

## 3. 팀 구성 및 역할

| 이름 | 역할 | 담당 업무 |
|:---:|:---:|:---|
| **최영우** | 팀원 (메인 발표 및 개발) | • AI 검색 기능 (OpenAI API 연동)<br>• REST API 비동기 통신<br>• Toss Payments 결제 시스템 구축<br>• <!-- 추가 담당 업무 --> |
| **팀원2** | 팀원 | • <!-- 담당 업무 1 --><br>• <!-- 담당 업무 2 --> |
| **팀원3** | 팀원 | • <!-- 담당 업무 1 --><br>• <!-- 담당 업무 2 --> |
| **팀원4** | 팀원 | • <!-- 담당 업무 1 --><br>• <!-- 담당 업무 2 --> |
| **팀원5** | 팀장 | • <!-- 담당 업무 1 --><br>• <!-- 담당 업무 2 --> |

> 💡 인원 : **5명** &nbsp;|&nbsp; 기간 : **2026.02 ~ 2026.03**

<br>

---

## 4. 기술 스택

### Frontend
<div align="left">
  <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">
  <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
  <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white">
  <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
</div>

### Backend
<div align="left">
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
  <img src="https://img.shields.io/badge/MyBatis-000000?style=for-the-badge">
  <img src="https://img.shields.io/badge/Lombok-DC382D?style=for-the-badge">
</div>

### Database
<div align="left">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
</div>

### API / Service
<div align="left">
  <img src="https://img.shields.io/badge/OpenAI_API-412991?style=for-the-badge&logo=openai&logoColor=white">
  <img src="https://img.shields.io/badge/Toss_Payments-1e64ff?style=for-the-badge">
  <img src="https://img.shields.io/badge/Kakao_Maps_API-FEE500?style=for-the-badge&logoColor=black">
</div>

### Tools
<div align="left">
  <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white">
  <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/VS_Code-007ACC?style=for-the-badge&logo=visual-studio-code&logoColor=white">
  <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
</div>

### Architecture
```
durudurub/                          ← Spring Boot 메인 서버
├── src/main/java/.../
│   ├── config/                     ← Security, Web 설정
│   ├── controller/                 ← 뷰 & API 컨트롤러
│   ├── dao/                        ← MyBatis Mapper 인터페이스
│   ├── dto/                        ← 데이터 전송 객체
│   ├── security/                   ← Spring Security (세션 인증)
│   └── service/                    ← 비즈니스 로직
├── src/main/resources/
│   ├── mybatis/mapper/             ← SQL 매퍼 XML
│   ├── templates/                  ← Thymeleaf 템플릿 (레이아웃)
│   └── static/                     ← CSS, JS, 이미지
└── uploads/                        ← 업로드 파일 저장소

durudurub-mcp/                      ← Spring AI + MCP 서버
├── src/main/java/.../
│   └── ...                         ← Spring AI (OpenAI) 연동
```

<br>

---

## 5. 프로젝트 수행 경과

### 5-1. 요구사항 정의서
<details>
  <summary>요구사항 정의서 펼치기</summary>
  
  <!-- 여기에 요구사항 정의서 이미지를 넣으세요 -->
  ![요구사항정의서](./여기에_이미지_경로.png)
</details>

### 5-2. 기능 정의서
<details>
  <summary>기능 정의서 펼치기</summary>
  
  <!-- 여기에 기능 정의서 이미지를 넣으세요 -->
  ![기능정의서](./여기에_이미지_경로.png)
</details>

### 5-3. ERD
<details>
  <summary>ERD 펼치기</summary>
  
  <!-- 여기에 ERD 이미지를 넣으세요 -->
  ![ERD](./여기에_이미지_경로.png)
</details>

### 5-4. 테이블 정의서
<details>
  <summary>테이블 정의서 펼치기</summary>
  
  <!-- 여기에 테이블 정의서 이미지를 넣으세요 -->
  ![테이블정의서](./여기에_이미지_경로.png)
</details>

<br>

---

## 6. 핵심 기능 코드 리뷰

### 6-1. AI 검색 기능 (OpenAI API)
> 사용자의 자연어 검색어를 OpenAI API로 분석하여 맞춤형 모임을 추천합니다.

<details>
  <summary>코드 보기</summary>

```java
// AiSearchController.java 핵심 로직
// 여기에 핵심 코드 스니펫을 붙여넣으세요
```
</details>

### 6-2. Spring Security 인증 시스템
> Spring Security + 세션 기반 인증 및 인가 처리

<details>
  <summary>코드 보기</summary>

```java
// SecurityConfig.java 핵심 로직
// 여기에 핵심 코드 스니펫을 붙여넣으세요
```
</details>

### 6-3. Toss Payments 결제 연동
> 프리미엄 구독을 위한 결제 시스템

<details>
  <summary>코드 보기</summary>

```java
// PaymentController.java 핵심 로직
// 여기에 핵심 코드 스니펫을 붙여넣으세요
```
</details>

<!-- 필요한 만큼 핵심 기능을 추가하세요 -->

<br>

---

## 7. 화면 UI

### 메인 화면
<details>
  <summary>메인 화면 보기</summary>
  
  <!-- 여기에 캡처 이미지를 넣으세요 -->
  ![메인화면](./여기에_이미지_경로.png)
</details>
<br>

### 모임 탐색 (Explore)
<details>
  <summary>모임 탐색 화면 보기</summary>
  
  ![모임탐색](./여기에_이미지_경로.png)
</details>
<br>

### 모임 상세
<details>
  <summary>모임 상세 화면 보기</summary>
  
  ![모임상세](./여기에_이미지_경로.png)
</details>
<br>

### AI 검색
<details>
  <summary>AI 검색 화면 보기</summary>
  
  ![AI검색](./여기에_이미지_경로.png)
</details>
<br>

### 로그인 / 회원가입
<details>
  <summary>로그인 / 회원가입 화면 보기</summary>
  
  ![로그인](./여기에_이미지_경로.png)
</details>
<br>

### 마이페이지
<details>
  <summary>마이페이지 화면 보기</summary>
  
  ![마이페이지](./여기에_이미지_경로.png)
</details>
<br>

### 결제 (구독)
<details>
  <summary>결제 화면 보기</summary>
  
  ![결제](./여기에_이미지_경로.png)
</details>
<br>

### 관리자 페이지
<details>
  <summary>관리자 페이지 화면 보기</summary>
  
  ![관리자](./여기에_이미지_경로.png)
</details>
<br>

<!-- 필요한 만큼 화면을 추가하세요 -->

<br>

---

## 8. 자체 평가 의견

### 8-1. 개별 평가

**최영우**
> - <!-- 본인의 프로젝트 참여 소감, 잘한 점, 아쉬운 점 -->
> - <!-- 예: AI 검색 기능을 OpenAI API와 연동하여 구현한 것이 가장 큰 성과였습니다. -->
> - <!-- 예: Thymeleaf와 Spring Boot의 연동 과정에서 많은 것을 배웠습니다. -->

**팀원2**
> - <!-- 소감 -->

**팀원3**
> - <!-- 소감 -->

**팀원4**
> - <!-- 소감 -->

**팀원5**
> - <!-- 소감 -->

### 8-2. 종합 평가

**잘된 점**
- <!-- 예: 프론트엔드와 백엔드를 성공적으로 분리하여 개발 -->
- <!-- 예: REST API를 활용한 비동기 통신 구현 -->

**한계점**
- <!-- 예: 시간 부족으로 일부 기능 미구현 -->

**개선점**
- <!-- 예: CI/CD 파이프라인 도입 필요 -->
- <!-- 예: 테스트 코드 작성 부족 -->

---

<br>

<p align="center">
  <b>📫 Contact</b><br>
  ✉️ orbiyoung@gmail.com &nbsp;|&nbsp; 🏫 passtal@cau.ac.kr
</p>
