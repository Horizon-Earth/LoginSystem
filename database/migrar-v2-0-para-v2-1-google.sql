-- Migração do banco de dados do LoginSystem da v2.0.0 para a v2.1.0.
--
-- Adiciona suporte a contas criadas/entradas com o Google, sem afetar
-- as contas já cadastradas com usuário e senha.
--
-- Execute com um usuário do MySQL que tenha privilégio de ALTER (ex.: root):
--
--   Linux (Ubuntu): sudo mysql loginsystem < database/migrar-v2-0-para-v2-1-google.sql
--   Windows / macOS: mysql -u root -p loginsystem < database/migrar-v2-0-para-v2-1-google.sql

ALTER TABLE usuarios
    MODIFY COLUMN senha_hash VARCHAR(255) NULL,
    ADD COLUMN provedor VARCHAR(20) NOT NULL DEFAULT 'senha' AFTER senha_hash,
    ADD COLUMN google_id VARCHAR(255) NULL AFTER provedor,
    ADD COLUMN foto_url VARCHAR(500) NULL AFTER google_id,
    ADD CONSTRAINT uq_usuarios_google_id UNIQUE (google_id),
    ADD CONSTRAINT chk_usuarios_provedor CHECK (provedor IN ('senha', 'google'));

-- As contas já existentes seguem como provedor = 'senha' (valor padrão acima).
