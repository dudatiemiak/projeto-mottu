-- V2_criando_tabelas.sql

CREATE TABLE t_cm_pais (
    id_pais IDENTITY PRIMARY KEY,
    nm_pais VARCHAR(100) NOT NULL
);

CREATE TABLE t_cm_estado (
    id_estado IDENTITY PRIMARY KEY,
    nm_estado VARCHAR(50) NOT NULL,
    id_pais BIGINT NOT NULL,
    CONSTRAINT fk_estado_pais FOREIGN KEY (id_pais) REFERENCES t_cm_pais(id_pais)
);

CREATE TABLE t_cm_cidade (
    id_cidade IDENTITY PRIMARY KEY,
    nm_cidade VARCHAR(100) NOT NULL,
    id_estado BIGINT NOT NULL,
    CONSTRAINT fk_cidade_estado FOREIGN KEY (id_estado) REFERENCES t_cm_estado(id_estado)
);

CREATE TABLE t_cm_bairro (
    id_bairro IDENTITY PRIMARY KEY,
    nm_bairro VARCHAR(100) NOT NULL,
    id_cidade BIGINT NOT NULL,
    CONSTRAINT fk_bairro_cidade FOREIGN KEY (id_cidade) REFERENCES t_cm_cidade(id_cidade)
);

CREATE TABLE t_cm_logradouro (
    id_logradouro IDENTITY PRIMARY KEY,
    nm_logradouro VARCHAR(100) NOT NULL,
    nr_logradouro INT NOT NULL,
    nm_complemento VARCHAR(100),
    id_bairro BIGINT NOT NULL,
    CONSTRAINT fk_logradouro_bairro FOREIGN KEY (id_bairro) REFERENCES t_cm_bairro(id_bairro)
);

CREATE TABLE t_cm_filial (
    id_filial IDENTITY PRIMARY KEY,
    nm_filial VARCHAR(100) NOT NULL,
    id_logradouro BIGINT NOT NULL,
    CONSTRAINT fk_filial_logradouro FOREIGN KEY (id_logradouro) REFERENCES t_cm_logradouro(id_logradouro)
);

CREATE TABLE t_cm_departamento (
    id_departamento IDENTITY PRIMARY KEY,
    nm_departamento VARCHAR(100) NOT NULL,
    ds_departamento VARCHAR(255)
);

CREATE TABLE t_cm_filial_departamento (
    id_filial_departamento IDENTITY PRIMARY KEY,
    id_filial BIGINT NOT NULL,
    id_departamento BIGINT NOT NULL,
    dt_entrada DATE NOT NULL,
    dt_saida DATE,
    CONSTRAINT fk_fd_filial FOREIGN KEY (id_filial) REFERENCES t_cm_filial(id_filial),
    CONSTRAINT fk_fd_departamento FOREIGN KEY (id_departamento) REFERENCES t_cm_departamento(id_departamento)
);

CREATE TABLE t_cm_funcao (
    id_funcao IDENTITY PRIMARY KEY,
    nm_funcao VARCHAR(50) NOT NULL
);

CREATE TABLE t_cm_funcionario (
    id_funcionario IDENTITY PRIMARY KEY,
    id_filial BIGINT NOT NULL,
    nm_funcionario VARCHAR(100) NOT NULL,
    nm_email_corporativo VARCHAR(100) NOT NULL,
    nm_senha VARCHAR(225) NOT NULL,
    nm_cargo VARCHAR(50) NOT NULL,
    CONSTRAINT fk_funcionario_filial FOREIGN KEY (id_filial) REFERENCES t_cm_filial(id_filial)
);

CREATE TABLE t_cm_funcionario_funcao (
    id_funcionario BIGINT NOT NULL,
    id_funcao BIGINT NOT NULL,
    PRIMARY KEY (id_funcionario, id_funcao),
    CONSTRAINT fk_ff_funcionario FOREIGN KEY (id_funcionario) REFERENCES t_cm_funcionario(id_funcionario),
    CONSTRAINT fk_ff_funcao FOREIGN KEY (id_funcao) REFERENCES t_cm_funcao(id_funcao)
);

CREATE TABLE t_cm_cliente (
    id_cliente IDENTITY PRIMARY KEY,
    nm_cliente VARCHAR(100) NOT NULL,
    nr_cpf VARCHAR(11) NOT NULL,
    nm_email VARCHAR(100) NOT NULL,
    id_logradouro BIGINT NOT NULL,
    CONSTRAINT fk_cliente_logradouro FOREIGN KEY (id_logradouro) REFERENCES t_cm_logradouro(id_logradouro)
);

CREATE TABLE t_cm_telefone (
    id_telefone IDENTITY PRIMARY KEY,
    nr_telefone VARCHAR(20) NOT NULL,
    nr_ddi VARCHAR(5) NOT NULL,
    nr_ddd VARCHAR(3) NOT NULL,
    id_cliente BIGINT NOT NULL,
    CONSTRAINT fk_telefone_cliente FOREIGN KEY (id_cliente) REFERENCES t_cm_cliente(id_cliente)
);

CREATE TABLE t_cm_moto (
    id_moto IDENTITY PRIMARY KEY,
    nm_placa VARCHAR(10) NOT NULL,
    nm_modelo VARCHAR(50) NOT NULL,
    st_moto VARCHAR(20) NOT NULL,
    km_rodado DOUBLE NOT NULL,
    id_cliente BIGINT NOT NULL,
    id_filial_departamento BIGINT NOT NULL,
    CONSTRAINT fk_moto_cliente FOREIGN KEY (id_cliente) REFERENCES t_cm_cliente(id_cliente),
    CONSTRAINT fk_moto_filial_departamento FOREIGN KEY (id_filial_departamento) REFERENCES t_cm_filial_departamento(id_filial_departamento)
);

CREATE TABLE t_cm_manutencao (
    id_manutencao IDENTITY PRIMARY KEY,
    id_moto BIGINT NOT NULL,
    dt_entrada DATE NOT NULL,
    dt_saida DATE,
    ds_manutencao VARCHAR(255),
    CONSTRAINT fk_manutencao_moto FOREIGN KEY (id_moto) REFERENCES t_cm_moto(id_moto)
);
