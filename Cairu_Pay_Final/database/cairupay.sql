-- Script SQL para criação do banco de dados CairuPay

CREATE DATABASE IF NOT EXISTS cairupay;
USE cairupay;

-- Tabela de Cliente (Pessoa)
CREATE TABLE IF NOT EXISTS cliente (
    idCliente INT AUTO_INCREMENT PRIMARY KEY,
    nomeCliente VARCHAR(255) NOT NULL,
    endereco VARCHAR(255),
    uf VARCHAR(2),
    telefone VARCHAR(20),
    documento VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100)
);

-- Tabela de Dívida
CREATE TABLE IF NOT EXISTS divida (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    idCredor INT NOT NULL,
    dataAtualizacao DATE NOT NULL,
    valorDivida DECIMAL(10, 2) NOT NULL,
    idDevedor INT NOT NULL,
    FOREIGN KEY (idCredor) REFERENCES cliente(idCliente) ON DELETE RESTRICT,
    FOREIGN KEY (idDevedor) REFERENCES cliente(idCliente) ON DELETE RESTRICT,
    CHECK (idCredor != idDevedor)
);

-- Tabela de Pagamento
CREATE TABLE IF NOT EXISTS pagamento (
    idpag INT AUTO_INCREMENT PRIMARY KEY,
    idDivida INT NOT NULL,
    dataPagamento DATE NOT NULL,
    valorPago DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (idDivida) REFERENCES divida(codigo) ON DELETE RESTRICT
);

-- Tabela de Usuário
CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cargo VARCHAR(100),
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    email VARCHAR(100)
);

-- Índices para melhor performance
CREATE INDEX idx_divida_credor ON divida(idCredor);
CREATE INDEX idx_divida_devedor ON divida(idDevedor);
CREATE INDEX idx_pagamento_divida ON pagamento(idDivida);
CREATE INDEX idx_pagamento_data ON pagamento(dataPagamento);
CREATE INDEX idx_cliente_documento ON cliente(documento);


