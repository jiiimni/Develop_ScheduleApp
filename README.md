# 🗓 Develop Schedule API

스파르타 내일배움캠프 CH3 숙련 Spring 일정 관리 앱 Develop 과제  
Spring Boot 기반 일정 관리 REST API 프로젝트입니다.  
CRUD → 연관관계 → 세션 인증까지 단계적으로 확장하며 설계한 과제입니다.

---

# 📌 1. Tech Stack

## 💻 Backend

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate

## 🗄 Database

* MySQL 8.x

## 🔐 Authentication

* HttpSession (Cookie 기반 인증)

## 📦 Build Tool

* Gradle

## 🧪 API Test Tool

* Postman

---

# 📌 2. 프로젝트 목표

* RESTful API 설계 이해
* JPA 연관관계 매핑 실습
* DTO 분리 설계 경험
* Session 기반 인증 구현
* 단계적 리팩토링 경험 (Lv1 → Lv4)

---

# 📌 3. ERD 구조

```
User (1)  ----  (N) Schedule
```

### User

* id (PK)
* username
* email (unique)
* password
* created_at
* updated_at

### Schedule

* id (PK)
* title
* content
* user_id (FK)
* created_at
* updated_at

---

# 📌 4. 프로젝트 구조

```
controller
 ┣ AuthController
 ┣ UserController
 ┗ ScheduleController

service
 ┣ UserService
 ┗ ScheduleService

repository
 ┣ UserRepository
 ┗ ScheduleRepository

entity
 ┣ User
 ┣ Schedule
 ┗ BaseTimeEntity

dto
 ┣ Request / Response DTO 분리
```

---

# 📌 5. 주요 기능

## ✅ Lv1 - 일정 CRUD

* 일정 생성
* 일정 전체 조회
* 일정 단건 조회
* 일정 수정
* 일정 삭제
* JPA Auditing 적용

---

## ✅ Lv2 - User CRUD + 연관관계

* 유저 생성 / 조회 / 수정
* Schedule → User 연관관계 적용
* username 문자열 제거
* @ManyToOne 매핑

---

## ✅ Lv3 - 회원가입 기능 확장

* password 필드 추가
* 이메일 unique 제약조건 적용
* Validation 적용

---

## ✅ Lv4 - 로그인 (Session 인증)

* 이메일 + 비밀번호 로그인
* HttpSession 저장
* 로그인한 사용자만 일정 생성 가능
* 로그아웃 기능 구현

---

# 📌 6. API Summary

## 🔹 User

| Method | URL             | Description |
| ------ | --------------- | ----------- |
| POST   | /api/users      | 회원가입        |
| GET    | /api/users      | 유저 전체 조회    |
| PATCH  | /api/users/{id} | 유저 수정       |

---

## 🔹 Auth

| Method | URL              | Description |
| ------ | ---------------- | ----------- |
| POST   | /api/auth/login  | 로그인         |
| POST   | /api/auth/logout | 로그아웃        |

---

## 🔹 Schedule

| Method | URL                 | Description    |
| ------ | ------------------- | -------------- |
| POST   | /api/schedules      | 일정 생성 (로그인 필요) |
| GET    | /api/schedules      | 전체 조회          |
| GET    | /api/schedules/{id} | 단건 조회          |
| PATCH  | /api/schedules/{id} | 수정             |
| DELETE | /api/schedules/{id} | 삭제             |

---

# 📌 7. 인증 흐름

1️⃣ 회원가입  
2️⃣ 로그인 → JSESSIONID 발급  
3️⃣ 세션 유지 상태에서 일정 생성 가능  
4️⃣ 로그아웃 후 일정 생성 시 예외 발생  

---

# 📌 8. 실행 방법

### 1️⃣ DB 생성

```sql
CREATE DATABASE develop_schedule;
```

### 2️⃣ application.yml 설정

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/develop_schedule
    username: root
    password: 12345678
```

### 3️⃣ 실행

```bash
./gradlew bootRun
```

---

# 📌 9. 설계 의도

* Entity와 DTO를 분리하여 확장성과 유지보수성 확보
* Service Layer에서 비즈니스 로직 처리
* Controller는 HTTP 입출력만 담당
* 연관관계 매핑을 통해 데이터 무결성 확보
* Session 인증을 통해 로그인 상태 관리

---

# 📌 10. 개선 예정 (도전 기능)

* 전역 예외 처리 (@RestControllerAdvice)
* Validation 메시지 커스터마이징
* 비밀번호 암호화 (BCrypt)
* 로그인 인증 인터셉터 적용

---

# ✨ 회고

이번 프로젝트를 통해 단순 CRUD 구현을 넘어
설계 → 확장 → 인증 적용까지의 전체 흐름을 경험할 수 있었다.

특히 연관관계와 세션 인증은 단순 기능 구현이 아니라
“구조를 어떻게 설계하느냐”의 문제라는 것을 배웠다.
