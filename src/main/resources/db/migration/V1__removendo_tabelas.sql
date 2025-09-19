-- V1_removendo_tabelas.sql
-- Drop das tabelas na ordem correta para evitar problemas de FK
DROP TABLE IF EXISTS t_cm_funcionario_funcao;
DROP TABLE IF EXISTS t_cm_funcionario;
DROP TABLE IF EXISTS t_cm_funcao;
DROP TABLE IF EXISTS t_cm_filial_departamento;
DROP TABLE IF EXISTS t_cm_departamento;
DROP TABLE IF EXISTS t_cm_filial;
DROP TABLE IF EXISTS t_cm_logradouro;
DROP TABLE IF EXISTS t_cm_bairro;
DROP TABLE IF EXISTS t_cm_cidade;
DROP TABLE IF EXISTS t_cm_estado;
DROP TABLE IF EXISTS t_cm_pais;
DROP TABLE IF EXISTS t_cm_cliente;
DROP TABLE IF EXISTS t_cm_telefone;
DROP TABLE IF EXISTS t_cm_moto;
DROP TABLE IF EXISTS t_cm_manutencao;
