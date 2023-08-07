# 원티드 프리온보딩 백엔드 인턴십 - 선발 과제

## 1. 지원자의 성명
안녕하세요. 원티드 프리온보딩 백엔드 인턴십 지원자 양슬기 입니다.
<br>
<br>
## 2. 애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)
<br>



<br>
<br>


## 2-1. docker compose + AWS 클라우드 배포

배포 서버 URL
http://3.37.214.129:8080/

시스템 흐름도
![docker-workflow-steps](https://github.com/Wool-ly/wanted-pre-onboarding-backend/assets/78457967/09eee86e-7c66-44f7-acba-fa31e70eec16)

출처 : https://devfoxstar.github.io/java/springboot-docker-ec2-deploy/

## 3. 데이터베이스 테이블 구조

ERD : https://www.erdcloud.com/d/LrrABe7xF5vR6TSDF

![image](https://github.com/Wool-ly/wanted-pre-onboarding-backend/assets/78457967/2b5c1741-0ed4-415d-ba70-f4c2217b30f2)


<br>
<br>

## 4. 구현한 API의 동작을 촬영한 데모 영상 링크
<br>
<br>

## 5. 구현 방법 및 이유에 대한 간략한 설명
<br>
<br>

## 6. API 명세(request/response 포함) 

포스트맨 API 명세서
https://documenter.getpostman.com/view/23934112/2s9XxyQDCH

### 과제 1. 사용자 회원가입 엔드포인트

[POST] /api/v1/users/signUp
```
(request) body - email, password
(response) body - result
```

### 과제 2. 사용자 로그인 엔드포인트

[POST] /api/v1/users/login

```
(request) body - email , password
(response) body - result, email, grantType, accessToken
```
### 과제 3. 새로운 게시글을 생성하는 엔드포인트

[POST] /api/v1/posts
```
(request) header - Authorization : Bearer + token
      	  body - title, content
(response) body - result
```

### 과제 4. 게시글 목록을 조회하는 엔드포인트

[GET] /api/v1/posts?page={page}&pageSize={pageSize}
```
(request) param - page, pageSize
(response) body - postId, title, content, email, createdDt, updatedDt, pageNo, pageSize, totalElements, totalPages, last
```

### 과제 5. 특정 게시글을 조회하는 엔드포인트

[GET] /api/v1/posts/{id}
```
(request) param - id (postId)
(response) body - postId, title, content, email, createdDt, updatedDt
```

### 과제 6. 특정 게시글을 수정하는 엔드포인트

[PATCH] /api/v1/posts/{id}
```
(request) header - Authorization : Bearer + token 
	  param - id (postId)
	  body - title, content
(response) body - result
```

** 만약, 게시글 작성자가 아닌데 수정을 시도하는 경우 "게시글 수정 권한이 없습니다."

### 과제 7. 특정 게시글을 삭제하는 엔드포인트

[DELETE] /api/v1/posts/{id}
```
(request) header - Authorization : Bearer + token 
	  param - id (postId)
(response) body - result
```

** 만약, 게시글 작성자가 아닌데 삭제를 시도하는 경우 "게시글 삭제 권한이 없습니다."

<br>
<br>
