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

API 배포 서버 URL : http://3.37.214.129:8080/ (230903 배포 중단)

포스트맨 API 명세서 : https://documenter.getpostman.com/view/23934112/2s9XxyQDCH

<br>
1) 포스트맨 API 명세서 및 아래 첨부한 구현 데모 영상을 참고하여 Postman을 통해 과제별 엔드포인트를 호출합니다.

<br>
2) 각 엔드포인트 별 성공/실패에 따른 Response 시나리오는 포스트맨 API 명세서에서 자세히 확인할 수 있습니다.
  <br>
   <br>
   
   ![image](https://github.com/Wool-ly/wanted-pre-onboarding-backend/assets/78457967/b474026c-2e80-401f-a98d-0a6a0171bea3)
   
   <br>

   **ex) 회원가입에 성공한 경우**
   ```
	{
	  "result": "회원 가입이 완료되었습니다."
	}
   ```

   **ex) 회원가입 시 이메일 유효성 검사에 실패한 경우**
   ```
	{
	  "status": 400,
	  "code": "CM_001",
	  "message": "입력값이 올바르지 않습니다.",
	  "errors": [
	    {
	      "field": "email",
	      "value": "test",
	      "reason": "이메일 형식에 맞지 않습니다. '@'를 포함하여 입력해주세요."
	    }
	  ]
	}
   ```
   **ex) 회원가입 시 비밀번 유효성 검사에 실패한 경우**
   ```
	{
	  "status": 400,
	  "code": "CM_001",
	  "message": "입력값이 올바르지 않습니다.",
	  "errors": [
	    {
	      "field": "password",
	      "value": "test123",
	      "reason": "비밀번호는 8자 이상이어야 합니다."
	    }
	  ]
	}
   ```


## 2-1. docker compose + AWS 클라우드 배포 환경 구조

#### 1) API 배포 서버 URL : http://3.37.214.129:8080/

#### 2) 시스템 흐름도
![image](https://github.com/Wool-ly/wanted-pre-onboarding-backend/assets/78457967/925d9f16-bf82-4ee2-8428-cbbae97adaec)

#### 3) Dockerfile

```
FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} wanted-0.0.1-SNAPSHOT.jar
ENV    PROFILE dev
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "wanted-0.0.1-SNAPSHOT.jar"]
```

#### 4) docker-compose.yml

```
version: "3.8"
services:
  wantedDB:
    container_name: wantedDB
    command: --default-authentication-plugin=mysql_native_password
    image: mysql:8.0.34
    restart: always
    volumes:
      - ./db/conf.d:/etc/mysql/conf.d
      - ./db/data:/var/lib/mysql
      - ./initdb.d:/docker-entrypoint-initdb.d
    environment:
      - MYSQL_ROOT_PASSWORD=root1234!
      - MYSQL_DATABASE=wanted
      - MYSQL_USER=wanted
      - MYSQL_PASSWORD=wanted1!
    ports:
      - "3306:3306"
    networks:
      - wanted-network
    healthcheck:
      test: [ "CMD", "mysqladmin" ,"ping", "-h", "localhost" ]
      timeout: 20s
      retries: 10
  wantedServer:
    container_name: wantedServer
    ports:
      - "8080:8080"
    image: woollly/wanted_backend_server:1.0
    volumes:
      - wanted_images:/app/wanted/images
    networks:
      - wanted-network
    depends_on:
      wantedDB:
        condition: service_healthy
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://wantedDB:3306/wanted?serverTimezone=Asia/Seoul&useSSL=false&allowPublicKeyRetrieval=true
networks:
  wanted-network:
    driver: bridge
volumes:
  mysql_volume:
    driver: local
  wanted_images:
    driver: local
```


#### 5) init.sql
```
CREATE TABLE wanted.users (
   user_id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   email VARCHAR(100) NOT NULL,
   password VARCHAR(100) NOT NULL,
   role VARCHAR(100) NOT NULL,
   created_dt DATETIME NOT NULL,
   updated_dt DATETIME NULL DEFAULT NULL
) DEFAULT CHARSET=utf8mb4 ENGINE=InnoDB;

CREATE TABLE wanted.posts (
   post_id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   title VARCHAR(100) NOT NULL COMMENT '제목',
   content VARCHAR(2000) NOT NULL COMMENT '내용',
   user_id BIGINT(20) NOT NULL,
   created_dt DATETIME NOT NULL,
   updated_dt DATETIME NULL DEFAULT NULL
) DEFAULT CHARSET=utf8mb4 ENGINE=InnoDB;

ALTER TABLE wanted.posts
ADD CONSTRAINT fk_user_id
FOREIGN KEY (user_id) REFERENCES users(user_id);

```
- Docker-Compose 에서 init.sql을 실행하여 wanted 데이터베이스에 users 테이블과 posts 테이블을 생성합니다.
- ``./initdb.d:/docker-entrypoint-initdb.d``


참고 : https://devfoxstar.github.io/java/springboot-docker-ec2-deploy/

## 3. 데이터베이스 테이블 구조

ERD : https://www.erdcloud.com/d/LrrABe7xF5vR6TSDF

![image](https://github.com/Wool-ly/wanted-pre-onboarding-backend/assets/78457967/2b5c1741-0ed4-415d-ba70-f4c2217b30f2)


<br>
<br>

## 4. 구현한 API의 동작을 촬영한 데모 영상 링크

유튜브 재생목록 링크 : https://www.youtube.com/playlist?list=PLtGWIYp0BHaKhWW3oK28wWTBfuzB0q8MM

#### #과제 1. 사용자 회원가입 엔드포인트
![1  signUp](https://github.com/Wool-ly/wanted-pre-onboarding-backend/assets/78457967/9bbd2f75-032c-4ded-9b5c-b83cc984a051)

#### #과제 2. 사용자 로그인 엔드포인트
![2  login](https://github.com/Wool-ly/wanted-pre-onboarding-backend/assets/78457967/80372e36-a416-4bdf-a5da-f886f00d9970)


#### #과제 3. 새로운 게시글을 생성하는 엔드포인트
![3](https://github.com/Wool-ly/wanted-pre-onboarding-backend/assets/78457967/a6e04ce7-a41a-4f1a-9c69-2082d3d3be15)


#### #과제 4. 게시글 목록을 조회하는 엔드포인트
![4  getPostList](https://github.com/Wool-ly/wanted-pre-onboarding-backend/assets/78457967/c5357baa-7700-49ba-96c9-550bc2dc982e)


#### #과제 5. 특정 게시글을 조회하는 엔드포인트
![5  getPost](https://github.com/Wool-ly/wanted-pre-onboarding-backend/assets/78457967/8bf6d216-e0da-4842-bdc9-4bfe566889e8)


#### #과제 6. 특정 게시글을 수정하는 엔드포인트
![6  updatePost](https://github.com/Wool-ly/wanted-pre-onboarding-backend/assets/78457967/4eef4e00-d464-4205-9521-df723a45c095)


#### #과제 7. 특정 게시글을 삭제하는 엔드포인트
![7  deletePost](https://github.com/Wool-ly/wanted-pre-onboarding-backend/assets/78457967/e2a6b31d-98a8-4985-bf77-c0f22b592dc9)



#### #과제 엔드포인트 호출 후 DB 저장 확인 
![DB 저장_wanted_aws_docker](https://github.com/Wool-ly/wanted-pre-onboarding-backend/assets/78457967/c3faf34c-3b9b-482a-8346-f27219db7d7f)


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

2-3) 이메일과 비밀번호가 유효한 경우, 서버는 사용자 인증을 거친 후에 JWT 토큰을 발급합니다.

2-4) 로그인 요청에 성공한 경우, 사용자에게 "로그인에 성공하였습니다." result 메시지, email, grantType, accessToken을 전달합니다.

<br>

### 과제 3. 새로운 게시글을 생성하는 엔드포인트

3-1) 사용자가 게시글 제목(title), 게시글 내용(content)을 입력하여 **POST** 요청을 보냅니다.
- 이때 application/json 전송 방식으로 보냅니다.
- 게시글 작성자를 식별하기 위해 이전에 로그인 요청 시 발급받은 grantType(Bearer), accessToken을 header의 Authorization에 담아서 보냅니다.

3-2) 새로운 게시글을 데이터베이스 Post 테이블에 저장합니다.
- SecurityContextHolder를 통해 현재 로그인한 사용자 정보(이메일)를 가져오고, 이 정보를 통해 데이터베이스에서 일치하는 이메일을 가진 회원을 조회합니다.
- 데이터베이스에서 이 이메일 정보를 통해 최종적으로 회원 아이디를 조회하여 user_id 컬럼에 값을 세팅합니다.

3-3) 사용자에게 "게시글 작성이 완료되었습니다." result 메시지를 응답으로 전달합니다.

<br>

### 과제 4. 게시글 목록을 조회하는 엔드포인트

4-1) 사용자에서 페이지 번호(page), 페이지당 게시글 수(pageSize)를 요청 파라미터로 입력하여 **GET** 요청을 보냅니다.

4-2) 서버에서는 데이터베이스에서 인터페이스인 'Pageable'을 활용하여 페이징 정보(paging)에 맞는 모든 게시글 목록을 조회합니다.
- Spring Framework에서 페이징 처리를 위해 사용되는 인터페이스인 'Pageable'을 활용했습니다.
- 페이지 번호(page)는 0부터, 페이지당 게시글 수(pageSize)는 10, 정렬은 게시글 생성일자(createdDt)를 기준으로 내림차순으로 정렬합니다.

4-3) 사용자에게 게시글 ID(postId), 게시글 제목(title), 게시글 내용(content), 작성자 이메일(email),  게시글 생성일자(createdDt), 게시글 수정 일자(updatedDt)와  Pageable 인터페이스의 페이지 번호(pageNumber), 페이지당 게시글 수 (pageSize), 전체 데이터 개수(totalElements), 총 페이지 수(totalPages), 마지막 페이지 여부(last) 등을 포함하여 응답으로 전달합니다.


### 과제 5. 특정 게시글을 조회하는 엔드포인트

5-1) 사용자에서 특정 게시글 아이디(postId)를 요청 파라미터로 입력하여 **GET** 요청을 보냅니다.

5-2) 서버에서는 사용자로부터 받은 특정 ID(postId)에 해당하는 게시글의 상세 정보를 데이터베이스에서 조회합니다.

5-3) 사용자에게 게시글 ID(postId), 게시글 제목(title), 게시글 내용(content), 작성자 이메일(email), 게시글 생성일자(createdDt), 게시글 수정 일자(updatedDt)를 응답으로 전달합니다.



### 과제 6. 특정 게시글을 수정하는 엔드포인트

6-1) 사용자에서 특정 게시글 아이디(postId)를 요청 파라미터로 입력하여 **PATCH** 요청을 보냅니다.
- 이때 application/json 전송 방식으로 보냅니다.
- PUT 메서드의 경우 어떠한 데이터를 변경하고자할 때 수정된 값만 일부분 보낼 경우에 보내지 않은 다른 데이터는 null로 변경되는 등 자원의 변경이 일어나므로,
변경하고자 하는 부분만 반영되며 기존의 다른 데이터는 유지되는 PATCH 메서드를 사용했습니다.
- 게시글 작성자를 식별하기 위해 이전에 로그인 요청 시 발급받은 grantType(Bearer), accessToken을 header의 Authorization에 담아서 보냅니다.
 
6-2) 서버에서는 사용자로부터 받은 특정 게시글 아이디(postId)와 grantType(Bearer), accessToken을 통해 해당 게시글을 작성한 사용자인지 검증합니다.

6-3) 만약, 해당 게시글을 작성한 사용자가 맞다면 게시글을 데이터베이스에서 수정합니다.
- 만약, 해당 게시글을 작성한 사용자가 아닌데 수정을 시도한다면 "게시글 수정 권한이 없습니다." result 메시지를 응답으로 전달합니다.

6-4) 사용자에게 "게시글 수정이 완료되었습니다." result 메시지를 응답으로 전달합니다.

### 과제 7. 특정 게시글을 삭제하는 엔드포인트

7-1) 사용자에서 특정 게시글 아이디(postId)를 요청 파라미터로 입력하여 **DELETE** 요청을 보냅니다.
- 이때 application/json 전송 방식으로 보냅니다.
- 게시글 작성자를 식별하기 위해 이전에 로그인 요청 시 발급받은 grantType(Bearer), accessToken을 header의 Authorization에 담아서 보냅니다.
 
7-2) 서버에서는 사용자로부터 받은 특정 게시글 아이디(postId)와 grantType(Bearer), accessToken을 통해 해당 게시글을 작성한 사용자인지 검증합니다.

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
(request) header - Authorization : Bearer + accessToken
      	  body - title, content
(response) body - result
```

### 과제 4. 게시글 목록을 조회하는 엔드포인트

**[GET] /api/v1/posts?page={page}&pageSize={pageSize}**
```
(request) param - page, pageSize
(response) body - postId, title, content, email, createdDt, updatedDt & Pageable(pageNumber, pageSize, totalElements, totalPages, last and so on) 
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
(request) header - Authorization : Bearer + accessToken
	  param - id (postId)
	  body - title, content
(response) body - result
```

** 만약, 게시글 작성자가 아닌데 수정을 시도하는 경우 "게시글 수정 권한이 없습니다." result 메시지를 응답

### 과제 7. 특정 게시글을 삭제하는 엔드포인트

**[DELETE] /api/v1/posts/{id}**
```
(request) header - Authorization : Bearer + accessToken
	  param - id (postId)
(response) body - result
```

** 만약, 게시글 작성자가 아닌데 삭제를 시도하는 경우 "게시글 삭제 권한이 없습니다." result 메시지를 응답

<br>
