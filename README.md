<h1 align="center">🏍️ Projeto Mottu - Gerenciamento Inteligente de Pátios</h1>

<p align="center">
  <strong>Challenge 2025 - 1º Semestre | Disciplina: Java Advanced</strong><br>
  <em>FIAP - 2º Ano - Análise e Desenvolvimento de Sistemas</em>
</p>

---

## 📖 Sobre o Projeto

Aplicação web e API REST construída em **Java 17 + Spring Boot 3** para gestão de pátios da **Mottu** (motos, manutenções, clientes, filiais etc.). O projeto inclui autenticação com Spring Security, telas Thymeleaf e documentação via Swagger.

---

## 👨‍💻 Integrantes do Grupo

| Nome                               | RM     | Turma  |
|------------------------------------|--------|--------|
| Eduarda Tiemi Akamini Machado      | 554756 | 2TDSPH |
| Felipe Pizzinato Bigatto           | 555141 | 2TDSPW |
| Gustavo de Oliveira Turci Sandrini | 557505 | 2TDSPW |

---

## ⚙️ Tecnologias

- Java 17, Spring Boot 3.4.x
- Spring MVC, Spring Data JPA, Bean Validation
- Spring Security (form login)
- Thymeleaf
- H2 Database (modo arquivo)
- Flyway (migrações)
- Swagger/OpenAPI (springdoc)
- Lombok
- Docker (opcional)

---

## 📦 Requisitos

- Java 17+ instalado (JAVA_HOME configurado)
- Maven 3.8+ instalado
- Docker (opcional)

---

## 🚀 Instalação e Execução

### 1) Clonar o repositório

```powershell
git clone https://github.com/dudatiemiak/projeto-mottu.git
cd projeto-mottu
```

### 2) Executar com Maven (modo desenvolvimento)

```powershell
mvn clean install
mvn spring-boot:run
```

Aplicação sobe em: http://localhost:8080

### 3) Executar via JAR (opcional)

```powershell
mvn clean package -DskipTests
java -jar target/projeto-mottu-0.0.1-SNAPSHOT.jar
```

### 4) Executar com Docker (opcional)

Com Docker Desktop aberto:

```powershell
docker build -t projeto-mottu .
docker run -p 8080:8080 --name projeto-mottu projeto-mottu
```

---

## 🔐 Acesso à Aplicação (Web)

- Login: http://localhost:8080/login
- Telas principais:
  - Manutenções: http://localhost:8080/manutencao/lista
  - Motos: http://localhost:8080/moto/lista
  - Clientes: http://localhost:8080/cliente/lista

Perfis e regras de acesso configuradas:
- ADMIN: acesso total (inclui `/funcionario/**` e `/h2-console/**`).
- OPERACIONAL, ATENDIMENTO, ANALISTA (quando existentes): acesso a `/manutencao/**`, `/moto/**` e `/cliente/**`.

---

## 🗃️ Banco de Dados H2

- Console web: http://localhost:8080/h2-console

---

## 📘 Documentação da API

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

---

## 🧭 Funcionalidades (destaques)

- CRUD completo de entidades (motos, manutenções, clientes, filiais, etc.)
- Validações de domínio com Bean Validation (ex.: datas não podem ser futuras)
- Telas web com Thymeleaf
- Autenticação por formulário (email/senha)
- Não-CRUD na lista de Manutenções (telas web):
  - Filtro “Somente em Aberto” (`abertas=true`)
  - Busca por descrição (`q=palavra`)

---


## 📌 Rotas (referência rápida da API)

Consulte o Swagger para a lista completa e atualizada. Principais domínios: Bairros, Cidades, Clientes, Departamentos, Estados, Filiais, FilialDepartamento, Funcionários, Logradouros, Manutenções, Motos, Países e Telefones.

---

<p align="center"><em>Desenvolvido por alunos do 2º ano de ADS - FIAP | 2025</em></p>
