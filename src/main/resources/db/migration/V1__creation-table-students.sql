CREATE TABLE tb_student(
                           id INT PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           phone INT,
                           gender CHAR(1),
                           creation_time TIMESTAMP NOT NULL,
                           birthdate DATE  NOT NULL
);