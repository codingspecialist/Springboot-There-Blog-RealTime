# 스프링부트 블로그 프로젝트 - 수업중 레포 (실시간 코드 반영)

## 1. 기술스택
- JDK 11
- Springboot 2.X 버전
- JPA
- H2 인메모리 디비 - 방언 MySQL
- JSP
- Security
- AJAX
- JSoup
- 부트스트랩

## 2. 요구사항
> HTTP 메서드를 POST와 GET만 사용한다.

#### 요구사항 1단계
- 회원가입
- 로그인 (시큐리티)
- 글쓰기 (섬머노트)
- 게시글 목록보기 (Lazy전략 - N+1 -> 복습)
- 게시글 상세보기 (데이터 뿌리기)
- 썸네일 등록하기 (Jsoup)

#### 요구사항 2단계
- 아이디 중복확인
- 비밀번호 동일체크
- 로그인 아이디 기억하기
- 프론트 유효성 검사 (onsubmit)
- 백엔드 유효성 검사 (AOP 등록)
- 글로벌 Exception 처리
- Script 응답 설정

#### 요구사항 3단계
- 회원 프로필 사진 등록
- 회원정보 보기
- 회원정보 수정
- 에러 로그 테이블 관리

#### 요구사항 4단계
- 페이징
- 검색

#### 요구사항 5단계  (개인 프로젝트)
- Love 테이블 생성
- Reply 테이블 생성
- 연관관계 설정
- 댓글쓰기
- 댓글삭제
- 좋아요 하기
- 좋아요 보기
- 남은 기능 추가하기 (각자 알아서)
- 보완하고 싶은것 추가하기 (각자 알아서)