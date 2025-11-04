-- Pais
INSERT INTO t_cm_pais (nm_pais) VALUES ('Brasil');
INSERT INTO t_cm_pais (nm_pais) VALUES ('Argentina');
INSERT INTO t_cm_pais (nm_pais) VALUES ('Chile');
INSERT INTO t_cm_pais (nm_pais) VALUES ('Peru');
INSERT INTO t_cm_pais (nm_pais) VALUES ('Uruguai');
-- Estado
INSERT INTO t_cm_estado (nm_estado, id_pais) VALUES ('SP', 1);
INSERT INTO t_cm_estado (nm_estado, id_pais) VALUES ('RJ', 1);
INSERT INTO t_cm_estado (nm_estado, id_pais) VALUES ('BA', 1);
INSERT INTO t_cm_estado (nm_estado, id_pais) VALUES ('MG', 1);
INSERT INTO t_cm_estado (nm_estado, id_pais) VALUES ('RS', 1);
-- Cidade
INSERT INTO t_cm_cidade (nm_cidade, id_estado) VALUES ('São Paulo', 1);
INSERT INTO t_cm_cidade (nm_cidade, id_estado) VALUES ('Rio de Janeiro', 2);
INSERT INTO t_cm_cidade (nm_cidade, id_estado) VALUES ('Salvador', 3);
INSERT INTO t_cm_cidade (nm_cidade, id_estado) VALUES ('Belo Horizonte', 4);
INSERT INTO t_cm_cidade (nm_cidade, id_estado) VALUES ('Porto Alegre', 5);
-- Bairro
INSERT INTO t_cm_bairro (nm_bairro, id_cidade) VALUES ('Centro', 1);
INSERT INTO t_cm_bairro (nm_bairro, id_cidade) VALUES ('Jardins', 1);
INSERT INTO t_cm_bairro (nm_bairro, id_cidade) VALUES ('Copacabana', 2);
INSERT INTO t_cm_bairro (nm_bairro, id_cidade) VALUES ('Pituba', 3);
INSERT INTO t_cm_bairro (nm_bairro, id_cidade) VALUES ('Savassi', 4);
-- Logradouro
INSERT INTO t_cm_logradouro (nm_logradouro, nr_logradouro, nm_complemento, id_bairro) VALUES ('Rua A', 100, 'Apto 1', 1);
INSERT INTO t_cm_logradouro (nm_logradouro, nr_logradouro, nm_complemento, id_bairro) VALUES ('Av. Paulista', 1500, 'Conj. 5', 2);
INSERT INTO t_cm_logradouro (nm_logradouro, nr_logradouro, nm_complemento, id_bairro) VALUES ('Rua Atlântica', 200, NULL, 3);
INSERT INTO t_cm_logradouro (nm_logradouro, nr_logradouro, nm_complemento, id_bairro) VALUES ('Rua Bahia', 50, 'Casa', 4);
INSERT INTO t_cm_logradouro (nm_logradouro, nr_logradouro, nm_complemento, id_bairro) VALUES ('Av. Cristóvão Colombo', 3000, NULL, 5);
-- Filial
INSERT INTO t_cm_filial (nm_filial, id_logradouro) VALUES ('Filial Centro', 1);
INSERT INTO t_cm_filial (nm_filial, id_logradouro) VALUES ('Filial Jardins', 2);
INSERT INTO t_cm_filial (nm_filial, id_logradouro) VALUES ('Filial Copacabana', 3);
INSERT INTO t_cm_filial (nm_filial, id_logradouro) VALUES ('Filial Pituba', 4);
INSERT INTO t_cm_filial (nm_filial, id_logradouro) VALUES ('Filial Savassi', 5);
-- Departamento
INSERT INTO t_cm_departamento (nm_departamento, ds_departamento) VALUES ('Logística', 'Departamento responsável pela logística da empresa');
INSERT INTO t_cm_departamento (nm_departamento, ds_departamento) VALUES ('RH', 'Departamento de Recursos Humanos');
INSERT INTO t_cm_departamento (nm_departamento, ds_departamento) VALUES ('Financeiro', 'Departamento de controle financeiro');
INSERT INTO t_cm_departamento (nm_departamento, ds_departamento) VALUES ('TI', 'Departamento de tecnologia da informação');
INSERT INTO t_cm_departamento (nm_departamento, ds_departamento) VALUES ('Comercial', 'Departamento de vendas e atendimento');
-- FilialDepartamento
INSERT INTO t_cm_filial_departamento (id_filial, id_departamento, dt_entrada, dt_saida) VALUES (1, 1, '2023-01-01', NULL);
INSERT INTO t_cm_filial_departamento (id_filial, id_departamento, dt_entrada, dt_saida) VALUES (2, 2, '2023-02-01', NULL);
INSERT INTO t_cm_filial_departamento (id_filial, id_departamento, dt_entrada, dt_saida) VALUES (3, 3, '2023-03-01', NULL);
INSERT INTO t_cm_filial_departamento (id_filial, id_departamento, dt_entrada, dt_saida) VALUES (4, 4, '2023-04-01', NULL);
INSERT INTO t_cm_filial_departamento (id_filial, id_departamento, dt_entrada, dt_saida) VALUES (5, 5, '2023-05-01', NULL);
-- Cliente
INSERT INTO t_cm_cliente (nm_cliente, nr_cpf, nm_email, id_logradouro) VALUES ('João Silva', '12345678901', 'joao@email.com', 1);
INSERT INTO t_cm_cliente (nm_cliente, nr_cpf, nm_email, id_logradouro) VALUES ('Maria Souza', '23456789012', 'maria@email.com', 2);
INSERT INTO t_cm_cliente (nm_cliente, nr_cpf, nm_email, id_logradouro) VALUES ('Carlos Lima', '34567890123', 'carlos@email.com', 3);
INSERT INTO t_cm_cliente (nm_cliente, nr_cpf, nm_email, id_logradouro) VALUES ('Ana Costa', '45678901234', 'ana@email.com', 4);
INSERT INTO t_cm_cliente (nm_cliente, nr_cpf, nm_email, id_logradouro) VALUES ('Lucas Rocha', '56789012345', 'lucas@email.com', 5);
-- Telefone
INSERT INTO t_cm_telefone (nr_telefone, nr_ddi, nr_ddd, id_cliente) VALUES ('912345678', '+55', '11', 1);
INSERT INTO t_cm_telefone (nr_telefone, nr_ddi, nr_ddd, id_cliente) VALUES ('998877665', '+55', '21', 2);
INSERT INTO t_cm_telefone (nr_telefone, nr_ddi, nr_ddd, id_cliente) VALUES ('987654321', '+55', '31', 3);
INSERT INTO t_cm_telefone (nr_telefone, nr_ddi, nr_ddd, id_cliente) VALUES ('977112233', '+55', '71', 4);
INSERT INTO t_cm_telefone (nr_telefone, nr_ddi, nr_ddd, id_cliente) VALUES ('966554433', '+55', '51', 5);
-- Moto
INSERT INTO t_cm_moto (nm_placa, id_modelo, st_moto, km_rodado, id_cliente, id_filial_departamento) VALUES ('ABC1234', 1, 'FUNCIONANDO', 1200.5, 1, 1);
INSERT INTO t_cm_moto (nm_placa, id_modelo, st_moto, km_rodado, id_cliente, id_filial_departamento) VALUES ('DEF5678', 2, 'MANUTENCAO', 800.0, 2, 2);
INSERT INTO t_cm_moto (nm_placa, id_modelo, st_moto, km_rodado, id_cliente, id_filial_departamento) VALUES ('GHI9012', 3, 'PATIO', 3000.8, 3, 3);
INSERT INTO t_cm_moto (nm_placa, id_modelo, st_moto, km_rodado, id_cliente, id_filial_departamento) VALUES ('JKL3456', 1, 'FUNCIONANDO', 150.3, 4, 4);
INSERT INTO t_cm_moto (nm_placa, id_modelo, st_moto, km_rodado, id_cliente, id_filial_departamento) VALUES ('MNO7890', 2, 'MANUTENCAO', 2200.0, 5, 5);
-- Manutencao
INSERT INTO t_cm_manutencao (id_moto, dt_entrada, dt_saida, ds_manutencao) VALUES (3, '2024-03-20', NULL, 'Correção elétrica');
--Funcao
INSERT INTO t_cm_funcao (nm_funcao) VALUES ('ADMIN');
INSERT INTO t_cm_funcao (nm_funcao) VALUES ('OPERACIONAL');
INSERT INTO t_cm_funcao (nm_funcao) VALUES ('ATENDIMENTO');
INSERT INTO t_cm_funcao (nm_funcao) VALUES ('ANALISTA');
-- Funcionario
INSERT INTO t_cm_funcionario (id_filial, nm_funcionario, nm_email_corporativo, nm_senha, nm_cargo) VALUES (1, 'Eduarda Tiemi', 'duda@empresa.com', '$2a$12$G0dtDbKrJ8SQEQR1T/tCaeq.Qr1iQLmXTiAms9C4LIb816l/PaeGK', 'Gerente de Filial');
INSERT INTO t_cm_funcionario_funcao (id_funcionario, id_funcao) VALUES ((SELECT id_funcionario FROM t_cm_funcionario WHERE nm_email_corporativo = 'duda@empresa.com'), (SELECT id_funcao FROM t_cm_funcao WHERE nm_funcao = 'ADMIN'));

