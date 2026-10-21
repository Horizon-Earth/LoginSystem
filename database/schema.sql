CREATE DATABASE IF NOT EXISTS loginsystem
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE loginsystem;

CREATE TABLE IF NOT EXISTS usuarios (
    id             INT UNSIGNED  NOT NULL AUTO_INCREMENT,
    usuario        VARCHAR(30)   NOT NULL,
    email          VARCHAR(120)  NOT NULL,
    senha_hash     VARCHAR(255)  NOT NULL,
    criado_em      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ultimo_acesso  DATETIME      NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_usuarios_usuario UNIQUE (usuario),
    CONSTRAINT uq_usuarios_email   UNIQUE (email)
) ENGINE=InnoDB;
