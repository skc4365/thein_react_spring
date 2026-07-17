select current_database();

drop table if exists users;

CREATE TABLE users (
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,   -- BCrypt 암호화된 값 저장
    name        VARCHAR(50)  NOT NULL,
    role        VARCHAR(20)  NOT NULL DEFAULT 'ROLE_USER',
    created_at  TIMESTAMP    NOT NULL DEFAULT now()
);

-- 세션저장용으로 수정했음, 참고만 하세용~
--CREATE TABLE refresh_tokens (
--    id          BIGSERIAL PRIMARY KEY,
--    user_id     BIGINT      NOT NULL REFERENCES users(id),
--    token       VARCHAR(500) NOT NULL,
--    expiry_date TIMESTAMP    NOT NULL
--);

select * from users;
select * from hello07;

-- 일반 사용자 계정 (username: user01 / password: 1234)
INSERT INTO users (username, password, name, role)
VALUES ('user01', '$2b$10$c3HUtFGxtkg6bLmNuTeGLu5G7B.GcF14DihWmvzie7v/SczjvCAyC', '홍길동', 'ROLE_USER');

-- 관리자 계정 (username: admin / password: 1234)
INSERT INTO users (username, password, name, role)
VALUES ('admin', '$2b$10$c3HUtFGxtkg6bLmNuTeGLu5G7B.GcF14DihWmvzie7v/SczjvCAyC', '관리자', 'ROLE_ADMIN');


