-- ============================================================
-- V1 - Criação das tabelas
-- ============================================================

CREATE TABLE IF NOT EXISTS tb_teacher (
    teacher_id   UUID        NOT NULL DEFAULT gen_random_uuid(),
    name         VARCHAR(100) NOT NULL,
    email        VARCHAR(100) UNIQUE,
    phone        VARCHAR(20),
    gender       VARCHAR(1),
    birth_date   DATE        NOT NULL,
    creation_time TIMESTAMP,
    update_time  TIMESTAMP,
    CONSTRAINT pk_teacher PRIMARY KEY (teacher_id)
);

CREATE TABLE IF NOT EXISTS tb_course (
    course_id    UUID        NOT NULL DEFAULT gen_random_uuid(),
    name         VARCHAR(150) NOT NULL UNIQUE,
    semester     INTEGER     NOT NULL,
    creation_time TIMESTAMP,
    update_timestamp TIMESTAMP,
    teacher_id   UUID,
    CONSTRAINT pk_course PRIMARY KEY (course_id),
    CONSTRAINT fk_course_teacher FOREIGN KEY (teacher_id)
        REFERENCES tb_teacher (teacher_id)
        ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS tb_student (
    student_id   UUID        NOT NULL DEFAULT gen_random_uuid(),
    name         VARCHAR(100) NOT NULL,
    email        VARCHAR(100) UNIQUE,
    phone        VARCHAR(20),
    gender       VARCHAR(10),
    birth_date   DATE        NOT NULL,
    creation_time TIMESTAMP,
    update_time  TIMESTAMP,
    course_id    UUID,
    CONSTRAINT pk_student PRIMARY KEY (student_id),
    CONSTRAINT fk_student_course FOREIGN KEY (course_id)
        REFERENCES tb_course (course_id)
        ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS tb_roles (
    role_id BIGSERIAL   NOT NULL,
    name    VARCHAR(50) NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (role_id)
);

CREATE TABLE IF NOT EXISTS tb_users (
    user_id  UUID        NOT NULL DEFAULT gen_random_uuid(),
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS tb_users_roles (
    user_id UUID   NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_ur_user FOREIGN KEY (user_id) REFERENCES tb_users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_ur_role FOREIGN KEY (role_id) REFERENCES tb_roles (role_id) ON DELETE CASCADE
);
