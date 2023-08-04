CREATE USER 'wanted'@'localhost' IDENTIFIED BY 'wanted1!';
CREATE USER 'wanted'@'%' IDENTIFIED BY 'wanted1!';

GRANT ALL PRIVILEGES ON *.* TO 'wanted'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO 'wanted'@'%';

CREATE DATABASE wanted DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

