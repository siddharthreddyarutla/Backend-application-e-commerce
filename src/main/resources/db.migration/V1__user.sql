CREATE TABLE IF NOT EXISTS user(
USER_ID bigint PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT 'primary key',
NAME VARCHAR(255) NOT NULL COMMENT 'name',
MOBILE_NO VARCHAR(255) NOT NULL COMMENT 'mobile number',
LOCATION VARCHAR(255) NOT NULL COMMENT 'location',
EMAIL VARCHAR(255) NOT NULL COMMENT 'email',
PASSWORD VARCHAR(255) NOT NULL COMMENT 'password',
ROLE VARCHAR(255) NOT NULL COMMENT 'role'
);