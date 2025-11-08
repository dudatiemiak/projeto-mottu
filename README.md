
# 🏍️ Projeto Mottu — Gerenciamento Inteligente de Pátios

**Challenge 2025 – Java Advanced (FIAP)**

Aplicação web e API REST em Java 17 + Spring Boot 3 para gestão de pátios (motos, manutenções, clientes, filiais). Inclui autenticação com Spring Security, telas Thymeleaf e documentação via Swagger.

---

## Índice

- [Sobre](#sobre)
- [Tecnologias](#tecnologias)
- [Instalação / Execução](#instalação--execução)
- [Configuração do banco de dados](#configuração-do-banco-de-dados)
- [Acesso (web)](#acesso-web)
- [Atualizações recentes](#atualizações-recentes)
- [Autores](#autores)

---

## Sobre

Projeto desenvolvido como atividade acadêmica (disciplina Java Advanced). Fornece telas server-side com Thymeleaf, API REST e integrações básicas para gestão do fluxo de pátio.

## Tecnologias

- Java 17, Spring Boot 3.x
- Spring MVC, Spring Data JPA, Bean Validation
- Spring Security (form login)
- Thymeleaf templates
- Oracle Database (configurável via `application.properties`)
- Swagger (springdoc), Lombok

---

## Instalação / Execução

1. Clonar o repositório

```powershell
git clone https://github.com/dudatiemiak/projeto-mottu.git
cd projeto-mottu
```

2. Rodar em modo desenvolvimento

```powershell
mvn clean install
mvn spring-boot:run
```

Aplicação disponível em: `http://localhost:8080`

3. Empacotar e executar JAR

```powershell
mvn clean package -DskipTests
java -jar target/projeto-mottu-0.0.1-SNAPSHOT.jar
```

---

## Configuração do banco de dados

Por padrão o projeto foi adaptado para Oracle (substituiu uso de H2 em entregas anteriores). Configure o datasource no `src/main/resources/application.properties` com URL, usuário e senha apropriados ao seu ambiente.

Observação: não inclua credenciais no repositório — use variáveis de ambiente ou profiles.

---

## Acesso (web)

- Login: `http://localhost:8080/login`
- URLs úteis (após login):
  - `/manutencao/lista` — manutenções
  - `/moto/lista` — motos
  - `/cliente/lista` — clientes

Perfis: `ADMIN`, `OPERACIONAL`, `ATENDIMENTO`, `ANALISTA` (conforme configuração de dados)

---

## Atualizações recentes

- Internacionalização (PT/EN/ES): mensagens de interface, placeholders e validação.
- Validações: mensagens de Bean Validation convertidas para chaves i18n e integradas ao MessageSource.
- Templates: correções de exibição (modelos, fragmentos) e remoção de código inseguro em fragments.
- Estilização: ajustes de contraste e paleta da marca (verde #09c44a / preto) aplicados inline conforme solicitação.

---

## Autores

- Eduarda Tiemi Akamini Machado — RM 554756
- Felipe Pizzinato Bigatto — RM 555141
- Gustavo de Oliveira Turci Sandrini — RM 557505

---

*Versão: entrega 2025 — atividade acadêmica.*
