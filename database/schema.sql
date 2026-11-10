CREATE DATABASE IF NOT EXISTS loginsystem
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE loginsystem;

CREATE TABLE IF NOT EXISTS usuarios (
    id             INT UNSIGNED  NOT NULL AUTO_INCREMENT,
    usuario        VARCHAR(30)   NOT NULL,
    email          VARCHAR(120)  NOT NULL,
    senha_hash     VARCHAR(255)  NULL,          -- NULL para contas criadas apenas com o Google
    provedor       VARCHAR(20)   NOT NULL DEFAULT 'senha',  -- 'senha' ou 'google'
    google_id      VARCHAR(255)  NULL,          -- ID da conta no Google (sub do ID token)
    foto_url       VARCHAR(500)  NULL,          -- foto de perfil do Google
    criado_em      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ultimo_acesso  DATETIME      NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_usuarios_usuario UNIQUE (usuario),
    CONSTRAINT uq_usuarios_email   UNIQUE (email),
    CONSTRAINT uq_usuarios_google_id UNIQUE (google_id),
    CONSTRAINT chk_usuarios_provedor CHECK (provedor IN ('senha', 'google'))
) ENGINE=InnoDB;
