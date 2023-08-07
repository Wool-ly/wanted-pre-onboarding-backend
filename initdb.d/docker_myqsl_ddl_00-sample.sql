-- CREATE DATABASE test_db CHARACTER SET utf8;
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

