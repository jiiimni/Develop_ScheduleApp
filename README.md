# 🗓 Develop Schedule API

Spring Boot 기반 일정 관리 REST API 프로젝트입니다.
CRUD → 연관관계 → Session 인증 → 전역 예외 처리 → 비밀번호 암호화까지 단계적으로 확장하며 설계한 과제입니다.

---

# 📌 1. Tech Stack

### 💻 Backend

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate

### 🗄 Database

* MySQL 8.x

### 🔐 Authentication

* HttpSession (Cookie 기반 인증)

### 🔐 Security

* BCrypt 비밀번호 암호화

### 📦 Build

* Gradle

### 🧪 Test

* Postman

---

# 📌 2. 프로젝트 목표

* RESTful API 설계 이해
* JPA 연관관계 매핑 실습
* DTO 기반 계층 분리 설계
* Session 인증 흐름 구현
* 전역 예외 처리 및 상태 코드 분리
* 비밀번호 암호화 적용

---

# 📌 3. ERD

```
User (1)  ----  (N) Schedule
```

### User

* id (PK)
* username
* email (unique)
* password (BCrypt 암호화 저장)
* created_at
* updated_at

### Schedule

* id (PK)
* title
* content
* user_id (FK)
* created_at
* updated_at

<img width="428" height="373" alt="ERD" src="https://github.com/user-attachments/assets/ec1481d8-b1b5-45e8-b4a2-d05fec7777d0" />

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

exception
 ┣ CustomException
 ┣ ErrorResponse
 ┗ GlobalExceptionHandler

config
 ┗ PasswordEncoder
```

---

# 📌 5. 주요 기능

## ✅ Lv1 – 일정 CRUD

* 일정 생성 / 조회 / 수정 / 삭제
* JPA Auditing 적용

## ✅ Lv2 – User 연관관계

* Schedule → User `@ManyToOne`
* username 직접 저장 제거
* FK 기반 설계

## ✅ Lv3 – 회원가입 확장

* password 필드 추가
* 이메일 unique 제약
* DTO Validation 적용

## ✅ Lv4 – Session 로그인

* 이메일 + 비밀번호 로그인
* HttpSession에 userId 저장
* 로그인 사용자만 일정 생성 가능

## ✅ Lv5 – 전역 예외 처리

* `@RestControllerAdvice` 적용
* ErrorResponse 구조 통일
* 상태 코드 명확히 분리

| 상황            | 상태 코드 |
| ------------- | ----- |
| Validation 실패 | 400   |
| 로그인 필요        | 401   |
| 리소스 없음        | 404   |
| 이메일 중복        | 409   |

## ✅ Lv6 – 비밀번호 암호화

* BCrypt 적용
* 회원가입 시 암호화 저장
* 로그인 시 `matches()`로 검증
* 평문 저장 제거

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

# 📘 API Specification

## 📌 공통 정보

* Base URL: `http://localhost:8080`
* Content-Type: `application/json`
* Authentication: Session (`JSESSIONID`)
* Date Format: `yyyy-MM-dd'T'HH:mm:ss`

---

# 🔐 Authentication API

## 1️⃣ 로그인

### POST `/api/auth/login`

```json
{
  "email": "jimin@test.com",
  "password": "12345678"
}
```

### ✅ 200 OK

```
Set-Cookie: JSESSIONID=xxxx
```

### ❌ 401 Unauthorized

```json
{
  "message": "이메일 또는 비밀번호가 올바르지 않습니다."
}
```

---

# 👤 User API

## 회원가입

### POST `/api/users`

```json
{
  "username": "jimin",
  "email": "jimin@test.com",
  "password": "12345678"
}
```

### ❌ 409 Conflict

```json
{
  "message": "이미 존재하는 이메일입니다."
}
```

---

# 📅 Schedule API

## 일정 생성

### POST `/api/schedules`

### Authentication

로그인 필요 (Session)

### ❌ 401 Unauthorized

```json
{
  "message": "로그인이 필요합니다."
}
```

---

# 📌 7. 인증 흐름

1️⃣ 회원가입
2️⃣ 로그인 → JSESSIONID 발급
3️⃣ 세션 유지 상태에서 일정 생성 가능
4️⃣ 로그아웃 후 접근 시 401

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

# 📌 9. 설계 포인트

* Entity / DTO 분리로 계층 구조 명확화
* Service에서 비즈니스 로직 처리
* Controller는 HTTP 입출력만 담당
* 전역 예외 처리로 응답 구조 통일
* 상태 코드 분리로 API 명확성 강화
* BCrypt 적용으로 보안 강화

---

# ✨ 프로젝트

CRUD → 연관관계 → 인증 → 예외 처리 → 보안 적용까지
단계적으로 확장하며 설계한 API 프로젝트입니다.

[과제 필수 기능 Velog]  
https://velog.io/@jiiim_ni/%EB%82%B4%EC%9D%BC%EB%B0%B0%EC%9B%80%EC%BA%A0%ED%94%84-Spring-3%EA%B8%B0-CH3-%EC%88%99%EB%A0%A8-Spring-%EC%9D%BC%EC%A0%95-%EA%B4%80%EB%A6%AC-%EC%95%B1-Develop-%ED%95%84%EC%88%98-%EA%B8%B0%EB%8A%A5  
[과제 도전 기능 Velog]  
https://velog.io/@jiiim_ni/%EB%82%B4%EC%9D%BC%EB%B0%B0%EC%9B%80%EC%BA%A0%ED%94%84-Spring-3%EA%B8%B0-CH3-%EC%88%99%EB%A0%A8-Spring-%EC%9D%BC%EC%A0%95-%EA%B4%80%EB%A6%AC-%EC%95%B1-Develop-%EB%8F%84%EC%A0%84-%EA%B8%B0%EB%8A%A5  

---
