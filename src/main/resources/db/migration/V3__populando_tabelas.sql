-- V3_populando_tabelas.sql

-- Países
INSERT INTO t_cm_pais (nm_pais) VALUES ('Brasil');
INSERT INTO t_cm_pais (nm_pais) VALUES ('Argentina');
INSERT INTO t_cm_pais (nm_pais) VALUES ('Chile');
INSERT INTO t_cm_pais (nm_pais) VALUES ('Peru');
INSERT INTO t_cm_pais (nm_pais) VALUES ('Uruguai');

-- Estados
INSERT INTO t_cm_estado (nm_estado, id_pais) VALUES ('SP', 1);
INSERT INTO t_cm_estado (nm_estado, id_pais) VALUES ('RJ', 1);
INSERT INTO t_cm_estado (nm_estado, id_pais) VALUES ('BA', 1);
INSERT INTO t_cm_estado (nm_estado, id_pais) VALUES ('MG', 1);
INSERT INTO t_cm_estado (nm_estado, id_pais) VALUES ('RS', 1);

-- Cidades
INSERT INTO t_cm_cidade (nm_cidade, id_estado) VALUES ('São Paulo', 1);
INSERT INTO t_cm_cidade (nm_cidade, id_estado) VALUES ('Rio de Janeiro', 2);
INSERT INTO t_cm_cidade (nm_cidade, id_estado) VALUES ('Salvador', 3);
INSERT INTO t_cm_cidade (nm_cidade, id_estado) VALUES ('Belo Horizonte', 4);
INSERT INTO t_cm_cidade (nm_cidade, id_estado) VALUES ('Porto Alegre', 5);

-- Bairros
INSERT INTO t_cm_bairro (nm_bairro, id_cidade) VALUES ('Centro', 1);
INSERT INTO t_cm_bairro (nm_bairro, id_cidade) VALUES ('Jardins', 1);
INSERT INTO t_cm_bairro (nm_bairro, id_cidade) VALUES ('Copacabana', 2);
INSERT INTO t_cm_bairro (nm_bairro, id_cidade) VALUES ('Pituba', 3);
INSERT INTO t_cm_bairro (nm_bairro, id_cidade) VALUES ('Savassi', 4);

-- Logradouros
INSERT INTO t_cm_logradouro (nm_logradouro, nr_logradouro, nm_complemento, id_bairro) VALUES ('Rua A', 100, 'Apto 1', 1);
INSERT INTO t_cm_logradouro (nm_logradouro, nr_logradouro, nm_complemento, id_bairro) VALUES ('Av. Paulista', 1500, 'Conj. 5', 2);
INSERT INTO t_cm_logradouro (nm_logradouro, nr_logradouro, nm_complemento, id_bairro) VALUES ('Rua Atlântica', 200, NULL, 3);
INSERT INTO t_cm_logradouro (nm_logradouro, nr_logradouro, nm_complemento, id_bairro) VALUES ('Rua Bahia', 50, 'Casa', 4);
INSERT INTO t_cm_logradouro (nm_logradouro, nr_logradouro, nm_complemento, id_bairro) VALUES ('Av. Cristóvão Colombo', 3000, NULL, 5);

-- Filiais
INSERT INTO t_cm_filial (nm_filial, id_logradouro) VALUES ('Filial Centro', 1);
INSERT INTO t_cm_filial (nm_filial, id_logradouro) VALUES ('Filial Jardins', 2);
INSERT INTO t_cm_filial (nm_filial, id_logradouro) VALUES ('Filial Copacabana', 3);
INSERT INTO t_cm_filial (nm_filial, id_logradouro) VALUES ('Filial Pituba', 4);
INSERT INTO t_cm_filial (nm_filial, id_logradouro) VALUES ('Filial Savassi', 5);

-- Departamentos
INSERT INTO t_cm_departamento (nm_departamento, ds_departamento) VALUES ('Logística', 'Departamento responsável pela logística da empresa');
INSERT INTO t_cm_departamento (nm_departamento, ds_departamento) VALUES ('RH', 'Departamento de Recursos Humanos');
INSERT INTO t_cm_departamento (nm_departamento, ds_departamento) VALUES ('Financeiro', 'Departamento de controle financeiro');
INSERT INTO t_cm_departamento (nm_departamento, ds_departamento) VALUES ('TI', 'Departamento de tecnologia da informação');
INSERT INTO t_cm_departamento (nm_departamento, ds_departamento) VALUES ('Comercial', 'Departamento de vendas e atendimento');

-- FilialDepartamento
INSERT INTO t_cm_filial_departamento (id_filial, id_departamento, dt_entrada, dt_saida) VALUES (1, 1, TO_DATE('2023-01-01','YYYY-MM-DD'), NULL);
INSERT INTO t_cm_filial_departamento (id_filial, id_departamento, dt_entrada, dt_saida) VALUES (2, 2, TO_DATE('2023-02-01','YYYY-MM-DD'), NULL);
INSERT INTO t_cm_filial_departamento (id_filial, id_departamento, dt_entrada, dt_saida) VALUES (3, 3, TO_DATE('2023-03-01','YYYY-MM-DD'), NULL);
INSERT INTO t_cm_filial_departamento (id_filial, id_departamento, dt_entrada, dt_saida) VALUES (4, 4, TO_DATE('2023-04-01','YYYY-MM-DD'), NULL);
INSERT INTO t_cm_filial_departamento (id_filial, id_departamento, dt_entrada, dt_saida) VALUES (5, 5, TO_DATE('2023-05-01','YYYY-MM-DD'), NULL);

-- Clientes
INSERT INTO t_cm_cliente (nm_cliente, nr_cpf, nm_email, id_logradouro) VALUES ('João Silva', '12345678901', 'joao@email.com', 1);
INSERT INTO t_cm_cliente (nm_cliente, nr_cpf, nm_email, id_logradouro) VALUES ('Maria Souza', '23456789012', 'maria@email.com', 2);
INSERT INTO t_cm_cliente (nm_cliente, nr_cpf, nm_email, id_logradouro) VALUES ('Carlos Lima', '34567890123', 'carlos@email.com', 3);
INSERT INTO t_cm_cliente (nm_cliente, nr_cpf, nm_email, id_logradouro) VALUES ('Ana Costa', '45678901234', 'ana@email.com', 4);
INSERT INTO t_cm_cliente (nm_cliente, nr_cpf, nm_email, id_logradouro) VALUES ('Lucas Rocha', '56789012345', 'lucas@email.com', 5);

-- Telefones
INSERT INTO t_cm_telefone (nr_telefone, nr_ddi, nr_ddd, id_cliente) VALUES ('912345678', '+55', '11', 1);
INSERT INTO t_cm_telefone (nr_telefone, nr_ddi, nr_ddd, id_cliente) VALUES ('998877665', '+55', '21', 2);
INSERT INTO t_cm_telefone (nr_telefone, nr_ddi, nr_ddd, id_cliente) VALUES ('987654321', '+55', '31', 3);
INSERT INTO t_cm_telefone (nr_telefone, nr_ddi, nr_ddd, id_cliente) VALUES ('977112233', '+55', '71', 4);
INSERT INTO t_cm_telefone (nr_telefone, nr_ddi, nr_ddd, id_cliente) VALUES ('966554433', '+55', '51', 5);

-- Motos
INSERT INTO t_cm_moto (nm_placa, nm_modelo, st_moto, km_rodado, id_cliente, id_filial_departamento) VALUES ('ABC1234', 'SPORT', 'FUNCIONANDO', 1200.5, 1, 1);
INSERT INTO t_cm_moto (nm_placa, nm_modelo, st_moto, km_rodado, id_cliente, id_filial_departamento) VALUES ('DEF5678', 'E', 'MANUTENCAO', 800.0, 2, 2);
INSERT INTO t_cm_moto (nm_placa, nm_modelo, st_moto, km_rodado, id_cliente, id_filial_departamento) VALUES ('GHI9012', 'POP', 'PATIO', 3000.8, 3, 3);
INSERT INTO t_cm_moto (nm_placa, nm_modelo, st_moto, km_rodado, id_cliente, id_filial_departamento) VALUES ('JKL3456', 'SPORT', 'FUNCIONANDO', 150.3, 4, 4);
INSERT INTO t_cm_moto (nm_placa, nm_modelo, st_moto, km_rodado, id_cliente, id_filial_departamento) VALUES ('MNO7890', 'E', 'MANUTENCAO', 2200.0, 5, 5);

-- Manutenções
INSERT INTO t_cm_manutencao (id_moto, dt_entrada, dt_saida, ds_manutencao) VALUES (1, TO_DATE('2024-01-01','YYYY-MM-DD'), TO_DATE('2024-01-05','YYYY-MM-DD'), 'Troca de óleo e revisão geral');
INSERT INTO t_cm_manutencao (id_moto, dt_entrada, dt_saida, ds_manutencao) VALUES (2, TO_DATE('2024-02-10','YYYY-MM-DD'), TO_DATE('2024-02-15','YYYY-MM-DD'), 'Troca de pneu traseiro');
INSERT INTO t_cm_manutencao (id_moto, dt_entrada, dt_saida, ds_manutencao) VALUES (3, TO_DATE('2024-03-20','YYYY-MM-DD'), NULL, 'Correção elétrica');
INSERT INTO t_cm_manutencao (id_moto, dt_entrada, dt_saida, ds_manutencao) VALUES (4, TO_DATE('2024-04-12','YYYY-MM-DD'), TO_DATE('2024-04-14','YYYY-MM-DD'), 'Alinhamento e balanceamento');
INSERT INTO t_cm_manutencao (id_moto, dt_entrada, dt_saida, ds_manutencao) VALUES (5, TO_DATE('2024-05-01','YYYY-MM-DD'), NULL, 'Troca de pastilha de freio');

-- Funções
INSERT INTO t_cm_funcao (nm_funcao) VALUES ('ADMIN');
INSERT INTO t_cm_funcao (nm_funcao) VALUES ('OPERACIONAL');
INSERT INTO t_cm_funcao (nm_funcao) VALUES ('ATENDIMENTO');
INSERT INTO t_cm_funcao (nm_funcao) VALUES ('ANALISTA');

-- Funcionário admin (senha: 123456)
INSERT INTO t_cm_funcionario (id_filial, nm_funcionario, nm_email_corporativo, nm_senha, nm_cargo) VALUES (1, 'Eduarda Tiemi', 'duda@empresa.com', '$2a$12$G0dtDbKrJ8SQEQR1T/tCaeq.Qr1iQLmXTiAms9C4LIb816l/PaeGK', 'Gerente de Filial');
INSERT INTO t_cm_funcionario_funcao (id_funcionario, id_funcao) VALUES ((SELECT id_funcionario FROM t_cm_funcionario WHERE nm_email_corporativo = 'duda@empresa.com'), (SELECT id_funcao FROM t_cm_funcao WHERE nm_funcao = 'ADMIN'));
