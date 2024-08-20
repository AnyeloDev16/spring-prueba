USE ProyectoJPA;
CREATE TABLE user
(
    user_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    last_name   VARCHAR(50) NOT NULL,
    birth_date  DATE NOT NULL,
    email       VARCHAR(50) NOT NULL
);