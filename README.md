# 원티드 프리온보딩 백엔드 인턴십 - 선발 과제

## 1. 지원자의 성명
안녕하세요. 원티드 프리온보딩 백엔드 인턴십 지원자 양슬기 입니다.
<br>
<br>
## 2. 애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)
<br>

### [도커 환경에서 git clone하는 경우]
도커 환경이 준비되어 있다는 가정하에 작성하겠습니다.
<br>
<br>
**1) git clone https://github.com/Wool-ly/wanted-pre-onboarding-backend.git**
<br>
   - 원격지의 프로젝트 repository를 가져옵니다.
<br>

**2) docker pull woollly/wanted_backend_server:1.0**
<br>
   - 제가 docker hub에 업로드해둔 이미지를 pull을 통해 다운받습니다.
<br>

**3) cd wanted-pre-onboarding-backend**
<br>     
   - git clone으로 내려받은 wanted-pre-onboarding-backend 디렉토리 경로로 이동합니다.
<br>

**4) docker-compose up -d**
<br>
   - docker-compose.yml에 애플리케이션 실행을 위한 Springboot Server 컨테이너와 MySQL DB 컨테이너를 정의하였습니다.
   - docker-compose 명령어를 통해 컨테이너들 생성하여 백그라운드에서 실행합니다.
<br>

### [현재 배포 중인인 AWS 서버를 통해 Postman으로 URL을 호출하는 경우]

API 배포 서버 URL : http://3.37.214.129:8080/

포스트맨 API 명세서 : https://documenter.getpostman.com/view/23934112/2s9XxyQDCH

1) 포스트맨 API 명세서를 참고하여 Postman을 통해 과제별 엔드포인트를 호출합니다.
2) 실제 호출 URL 예시
   <br>
   회원가입 : [POST] http://3.37.214.129:8080/api/v1/users/signUp
   <br>
   로그인 : [POST] http://3.37.214.129:8080/api/v1/users/login
   <br>
   게시글 생성 : [POST] http://3.37.214.129:8080/api/v1/posts
   <br>
   게시글 목록 조회 : [GET] http://3.37.214.129:8080/api/v1/posts?page=0&pageSize=10
   <br>
   특정 게시글 상세 조회 :  [GET] http://3.37.214.129:8080/api/v1/posts/1
   <br>
   게시글 수정 : [PATCH] http://3.37.214.129:8080/api/v1/posts/1
   <br>
   게시글 삭제 : [DELETE] http://3.37.214.129:8080/api/v1/posts/1
<br>
<br>


## 2-1. docker compose + AWS 클라우드 배포 환경 구조

1) API 배포 서버 URL : http://3.37.214.129:8080/

2) 시스템 흐름도
![image](https://github.com/Wool-ly/wanted-pre-onboarding-backend/assets/78457967/925d9f16-bf82-4ee2-8428-cbbae97adaec)



참고 : https://devfoxstar.github.io/java/springboot-docker-ec2-deploy/

## 3. 데이터베이스 테이블 구조

ERD : https://www.erdcloud.com/d/LrrABe7xF5vR6TSDF

![image](https://github.com/Wool-ly/wanted-pre-onboarding-backend/assets/78457967/2b5c1741-0ed4-415d-ba70-f4c2217b30f2)


<br>
<br>

## 4. 구현한 API의 동작을 촬영한 데모 영상 링크
<br>
<br>

## 5. 구현 방법 및 이유에 대한 간략한 설명

### 과제 1. 사용자 회원가입 엔드포인트

1-1) 클라이언트가 이메일(email)과 비밀번호(password)를 입력하여 **POST** 요청을 보냅니다.
- 이때 application/json 전송 방식으로 보냅니다.

1-2) 서버에서는 클라이언트로부터 입력 받은 이메일(email)과 비밀번호(password)를 검증합니다.
- 비밀번호 암호화 : SpringSecurity를 적용하여 PasswordEncoder 인터페이스를 활용하여 단방향 암호화를 사용했습니다.

1-3) 이메일과 비밀번호가 유효한 경우, 새로운 사용자를 MySQL 데이터베이스에 저장합니다.
- 이메일에 @이 포함되지 않을 경우 CM_001 코드, 400 Status를 리턴하며 이메일 형식에 맞지 않는다는 에러 메시지를 출력합니다. (javax validation의 @Email 어노테이션 활용)
- 비밀번호가 8자 이상이 아닐 경우 CM_001 코드, 400 Status를 리턴하며 비밀번호는 8자 이상이어야 한다는 에러 메시지를 출력합니다. (javax validation의 @Pattern어노테이션 활용)

1-4) 이메일과 비밀번호가 유효하고, 중복되는 이메일이 없는 경우 새로운 사용자를 데이터베이스 User 테이블에 저장합니다.

1-5) 회원가입이 성공한 경우, 사용자에게 "회원 가입이 완료되었습니다." result 메시지를 응답으로 전달합니다.

<br>

### 과제 2. 사용자 로그인 엔드포인트

2-1) 클라이언트가 이메일(email)과 비밀번호(password)를 입력하여 **POST** 요청을 보냅니다.
- 이때 application/json 전송 방식으로 보냅니다.

2-2) 서버는 클라이언트로부터 입력받은 이메일(email)과 비밀번호(password)를 검증합니다.
- 이때 application/json 전송 방식으로 보내며, 이메일과 비밀번호에 대한 유효성 검사를 진행합니다.
- 이메일에 @이 포함되지 않을 경우 CM_001 코드, 400 Status를 리턴하며 이메일 형식에 맞지 않는다는 에러 메시지를 출력합니다. (javax validation의 @Email 어노테이션 활용)
- 비밀번호가 8자 이상이 아닐 경우 CM_001 코드, 400 Status를 리턴하며 비밀번호는 8자 이상이어야 한다는 에러 메시지를 출력합니다. (javax validation의 @Pattern어노테이션 활용)

2-3) 이메일과 비밀번호가 유효한 경우, 서버는 사용자 인증을 거친 후에 JWT 토큰을 발급합니다. (Bearer 토큰도 함께)

2-4) 로그인 요청에 성공한 경우, 사용자에게 "로그인에 성공하였습니다." result 메시지,  Bearer, accessToken을 전달합니다.

<br>

### 과제 3. 새로운 게시글을 생성하는 엔드포인트

3-1) 사용자가 게시글 제목(title), 게시글 내용(content)을 입력하여 **POST** 요청을 보냅니다.
- 이때 application/json 전송 방식으로 보냅니다.
- 게시글 작성자를 식별하기 위해 이전에 로그인 요청 시 발급받은 Bearer, accessToken을 header의 Authorization에 담아서 보냅니다.

3-2) 새로운 게시글을 데이터베이스 Post 테이블에 저장합니다.
- SecurityContextHolder를 통해 현재 로그인한 사용자 정보(이메일)를 가져오고, 이 정보를 통해 데이터베이스에서 일치하는 이메일을 가진 회원을 조회합니다.
- 데이터베이스에서 이 이메일 정보를 통해 최종적으로 회원 아이디를 조회하여 user_id 컬럼에 값을 세팅합니다.

3-3) 사용자에게 "게시글 작성이 완료되었습니다." result 메시지를 응답으로 전달합니다.

<br>

### 과제 4. 게시글 목록을 조회하는 엔드포인트

4-1) 사용자에서 페이지 번호(page), 페이지당 게시글 수(pageSize)를 요청 파라미터로 입력하여 **GET** 요청을 보냅니다.

4-2) 서버에서는 데이터베이스에서 페이징 정보(paging)에 맞는 모든 게시글 목록을 조회합니다.
- Spring Framework에서 페이징 처리를 위해 사용되는 인터페이스인 'Pageable'을 활용했습니다.

4-3) 사용자에게 게시글 ID(postId), 작성자 이메일(email), 게시글 제목(title), 게시글 내용(content), 게시글 생성일자(createdDt), 게시글 수정 일자(updatedDt)와  Pageable 인터페이스의 페이지 번호(pageNo), 페이지당 게시글 수 (pageSize), 전체 데이터 개수(totalElements), 총 페이지 수(totalPages), 마지막 페이지 여부(last)를 응답으로 전달합니다.


### 과제 5. 특정 게시글을 조회하는 엔드포인트

5-1) 사용자에서 특정 게시글 아이디(postId)를 요청 파라미터로 입력하여 **GET** 요청을 보냅니다.

5-2) 서버에서는 사용자로부터 받은 특정 ID(postId)에 해당하는 게시글의 상세 정보를 데이터베이스에서 조회합니다.

5-3) 사용자에게 게시글 ID(postId), 게시글 제목(title), 게시글 내용(content), 작성자 이메일(email), 게시글 생성일자(createdDt), 게시글 수정 일자(updatedDt)를 응답으로 전달합니다.



### 과제 6. 특정 게시글을 수정하는 엔드포인트

6-1) 사용자에서 특정 게시글 아이디(postId)를 요청 파라미터로 입력하여 **PATCH** 요청을 보냅니다.
- 이때 application/json 전송 방식으로 보냅니다.
- PUT 메서드의 경우 어떠한 데이터를 변경하고자할 때 수정된 값만 일부분 보낼 경우에 보내지 않은 다른 데이터는 null로 변경되는 등 자원의 변경이 일어나므로,
변경하고자 하는 부분만 반영되며 기존의 다른 데이터는 유지되는 PATCH 메서드를 사용했습니다.
- 게시글 작성자를 식별하기 위해 이전에 로그인 요청 시 발급받은 Bearer, accessToken을 header의 Authorization에 담아서 보냅니다.
 
6-2) 서버에서는 사용자로부터 받은 특정 게시글 아이디(postId)와 Bearer, accessToken을 통해 해당 게시글을 작성한 사용자인지 검증합니다.

6-3) 만약, 해당 게시글을 작성한 사용자가 맞다면 게시글을 데이터베이스에서 수정합니다.
- 만약, 해당 게시글을 작성한 사용자가 아닌데 수정을 시도한다면 "게시글 수정 권한이 없습니다." result 메시지를 응답으로 전달합니다.

6-4) 사용자에게 "게시글 수정이 완료되었습니다." result 메시지를 응답으로 전달합니다.

### 과제 7. 특정 게시글을 삭제하는 엔드포인트

7-1) 사용자에서 특정 게시글 아이디(postId)를 요청 파라미터로 입력하여 **DELETE** 요청을 보냅니다.
- 이때 application/json 전송 방식으로 보냅니다.
- 게시글 작성자를 식별하기 위해 이전에 로그인 요청 시 발급받은 Bearer, accessToken을 header의 Authorization에 담아서 보냅니다.
 
7-2) 서버에서는 사용자로부터 받은 특정 게시글 아이디(postId)와 Bearer, accessToken을 통해 해당 게시글을 작성한 사용자인지 검증합니다.

7-3) 만약, 해당 게시글을 작성한 사용자가 맞다면 게시글을 데이터베이스에서 삭제합니다.
- 만약, 해당 게시글을 작성한 사용자가 아닌데 삭제를 시도한다면 "게시글 삭제 권한이 없습니다." result 메시지를 응답으로 전달합니다.

7-4) 사용자에게 "게시글 삭제가 완료되었습니다." result 메시지를 응답으로 전달합니다.

<br>

## 6. API 명세(request/response 포함) 

포스트맨 API 명세서
https://documenter.getpostman.com/view/23934112/2s9XxyQDCH

### 과제 1. 사용자 회원가입 엔드포인트

**[POST] /api/v1/users/signUp**
```
(request) body - email, password
(response) body - result
```

### 과제 2. 사용자 로그인 엔드포인트

**[POST] /api/v1/users/login**

```
(request) body - email , password
(response) body - result, email, grantType, accessToken
```
### 과제 3. 새로운 게시글을 생성하는 엔드포인트

**[POST] /api/v1/posts**
```
(request) header - Authorization : Bearer + token
      	  body - title, content
(response) body - result
```

### 과제 4. 게시글 목록을 조회하는 엔드포인트

**[GET] /api/v1/posts?page={page}&pageSize={pageSize}**
```
(request) param - page, pageSize
(response) body - postId, title, content, email, createdDt, updatedDt, pageNo, pageSize, totalElements, totalPages, last
```

### 과제 5. 특정 게시글을 조회하는 엔드포인트

**[GET] /api/v1/posts/{id}**
```
(request) param - id (postId)
(response) body - postId, title, content, email, createdDt, updatedDt
```

### 과제 6. 특정 게시글을 수정하는 엔드포인트

**[PATCH] /api/v1/posts/{id}**
```
(request) header - Authorization : Bearer + token 
	  param - id (postId)
	  body - title, content
(response) body - result
```

** 만약, 게시글 작성자가 아닌데 수정을 시도하는 경우 "게시글 수정 권한이 없습니다." result 메시지를 응답

### 과제 7. 특정 게시글을 삭제하는 엔드포인트

**[DELETE] /api/v1/posts/{id}**
```
(request) header - Authorization : Bearer + token 
	  param - id (postId)
(response) body - result
```

** 만약, 게시글 작성자가 아닌데 삭제를 시도하는 경우 "게시글 삭제 권한이 없습니다." result 메시지를 응답

<br>
