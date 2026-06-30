# User Task API

API REST desenvolvida com Spring Boot para gerenciamento de usuários e tarefas, utilizando relacionamento entre entidades, arquitetura em camadas e autenticação básica.

Projeto criado com o objetivo de praticar desenvolvimento de APIs REST, persistência de dados com JPA/Hibernate e aplicação de boas práticas de organização de código.

---

## Tecnologias

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Jakarta Validation
- MySQL
- Maven

---

## Funcionalidades

### Usuários

- Criar usuário
- Listar usuários
- Buscar usuário por ID
- Atualizar usuário
- Remover usuário

### Tarefas

- Criar tarefa vinculada a um usuário
- Listar todas as tarefas
- Buscar tarefa por ID
- Listar tarefas de um usuário
- Listar tarefas concluídas
- Listar tarefas pendentes
- Atualizar tarefa
- Marcar tarefa como concluída
- Remover tarefa

### Autenticação

- Login utilizando e-mail e senha

---

## Validações

O projeto utiliza Bean Validation para validação dos dados recebidos.

Exemplos:

- Nome obrigatório
- E-mail obrigatório e válido
- Senha obrigatória
- Título obrigatório
- Descrição obrigatória

---

## Estrutura do projeto

```
src
├── controller
├── dto
├── entity
├── exception
├── repository
├── service
```

---

## Arquitetura

O projeto foi desenvolvido seguindo arquitetura em camadas:

- **Controller** → recebe as requisições HTTP.
- **Service** → contém as regras de negócio.
- **Repository** → acesso ao banco de dados.
- **Entity** → entidades persistidas.
- **DTO** → objetos utilizados para entrada e saída de dados.
- **Exception** → tratamento centralizado das exceções.

---

## Relacionamento entre entidades

O sistema possui relacionamento **One-to-Many** entre usuários e tarefas.

```
Usuário
│
├── Tarefa
├── Tarefa
└── Tarefa
```

Cada usuário pode possuir várias tarefas, enquanto cada tarefa pertence a apenas um usuário.

---

## Endpoints

### Autenticação

| Método | Endpoint |
|--------|----------|
| POST | `/login` |

### Usuários

| Método | Endpoint |
|--------|----------|
| POST | `/usuarios` |
| GET | `/usuarios` |
| GET | `/usuarios/{id}` |
| PUT | `/usuarios/{id}` |
| DELETE | `/usuarios/{id}` |

### Tarefas

| Método | Endpoint |
|--------|----------|
| POST | `/usuarios/{usuarioId}/tarefas` |
| GET | `/tarefas` |
| GET | `/tarefas/{id}` |
| GET | `/usuarios/{usuarioId}/tarefas` |
| GET | `/tarefas/concluidas` |
| GET | `/tarefas/pendentes` |
| PATCH | `/tarefas/{id}/concluir` |
| PUT | `/tarefas/{id}` |
| DELETE | `/tarefas/{id}` |

---

## Melhorias futuras

- Spring Security
- JWT Authentication
- Swagger/OpenAPI
- Paginação
- Testes unitários
- Docker
- CI/CD

---

## Objetivo

Este projeto foi desenvolvido para consolidar conhecimentos em:

- Spring Boot
- APIs REST
- DTOs
- Relacionamento entre entidades
- JPA/Hibernate
- Bean Validation
- Arquitetura em camadas
- Boas práticas de desenvolvimento