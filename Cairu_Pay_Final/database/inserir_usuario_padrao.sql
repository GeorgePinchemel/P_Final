-- Script para inserir usuário padrão no sistema
-- Execute este script após criar o banco de dados

USE cairupay;

-- Inserir usuário padrão (login: admin, senha: admin)
INSERT INTO usuario (nome, cargo, login, senha, email) 
VALUES ('Administrador', 'Administrador', 'admin', 'admin', 'admin@cairupay.com');

-- Você pode adicionar mais usuários aqui se necessário
-- INSERT INTO usuario (nome, cargo, login, senha, email) 
-- VALUES ('Nome do Usuário', 'Cargo', 'login', 'senha', 'email@exemplo.com');

