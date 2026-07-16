# Restaurant Manager API

Vinicius Oliveira do Carmo — RM370572 — Pós Tech 12ADJT
Tech Challenge Fase 2 — Arquitetura e Desenvolvimento Java

API REST para gerenciamento de um sistema compartilhado de restaurantes, permitindo o cadastro de tipos de usuário, restaurantes e itens de cardápio, com autenticação via JWT e controle de acesso por papel e por posse do recurso. Construída com **Java 21**, **Spring Boot 3** e **Clean Architecture**, evoluindo a estrutura da [Fase 1](https://github.com/ViniCarmo/Restaurant-manager-api) para uma organização em camadas totalmente desacoplada de frameworks no núcleo do domínio.

---

## Sumário

- [Sobre o projeto](#sobre-o-projeto)
- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Como executar](#como-executar)
- [Variáveis de ambiente](#variáveis-de-ambiente)
- [Endpoints](#endpoints)
- [Autenticação e autorização](#autenticação-e-autorização)
- [Testes](#testes)
- [Documentação Swagger](#documentação-swagger)
- [Estrutura de pastas](#estrutura-de-pastas)
- [Documentação completa](#documentação-completa)

---

## Sobre o projeto

Um grupo de restaurantes decidiu compartilhar um único sistema de gestão em vez de manter soluções individuais. Esta fase do desafio implementa a base desse sistema:

- **Tipos de usuário** (`Customer` e `Restaurant Owner`), modelados como entidade persistida — e não como Enum — pois o enunciado exige um CRUD completo para gerenciá-los.
- **Restaurantes**, com nome, endereço, tipo de cozinha, horário de funcionamento e proprietário.
- **Itens de cardápio**, associados a um restaurante, com nome, descrição, preço, disponibilidade para consumo local e caminho da foto do prato.

Além do escopo mínimo do enunciado, o projeto implementa autenticação stateless com **Spring Security + JWT** e um segundo nível de autorização que valida se o usuário autenticado é realmente o **dono do recurso** que está tentando alterar — não bastando apenas possuir a role `RESTAURANT_OWNER`.

## Tecnologias

| Tecnologia | Finalidade |
|---|---|
| Java 21 | Linguagem principal |
| Spring Boot 3 | Framework de aplicação |
| Spring Security + JWT | Autenticação stateless e autorização |
| Spring Data JPA | Persistência e derived queries |
| PostgreSQL | Banco de dados relacional |
| Flyway | Versionamento do schema do banco |
| Swagger / OpenAPI (springdoc) | Documentação interativa da API |
| JUnit 5 + Mockito | Testes unitários |
| MockMvc | Testes de integração dos controllers |
| Docker + Docker Compose | Containerização e orquestração |
| Maven | Build e gerenciamento de dependências |

## Arquitetura

O projeto segue **Clean Architecture** organizada por módulo de domínio (*Package by Feature*). Os módulos `user`, `userType`, `restaurant` e `menuItem` compartilham a mesma estrutura interna de quatro camadas:

```
modulo/
├── domain/
│   ├── entity/          # Entidades puras, sem dependência de framework
│   ├── repository/      # Interfaces de repositório
│   └── exception/       # Exceções de domínio
├── application/
│   └── useCases/        # Uma classe por ação de negócio
├── infrastructure/
│   ├── persistence/      # JpaEntity, JpaRepository, RepositoryImpl
│   └── mapper/           # Conversão entre domínio e JPA
└── presentation/
    ├── controller/       # Controllers REST
    ├── dto/              # Request/Response DTOs
    └── mapper/           # Conversão entre domínio e DTO
```

A regra central: **as dependências apontam sempre para dentro**. O `domain` não conhece Spring, JPA ou HTTP — é Java puro. As camadas externas (`infrastructure`, `presentation`) é que dependem do domínio, nunca o contrário.

Ao todo, o projeto possui **23 casos de uso** distribuídos entre os quatro módulos, cada um com responsabilidade única, orquestrando a interação entre entidade e repositório.

Detalhamento completo de cada camada e de todas as decisões arquiteturais está na [documentação técnica completa](#documentação-completa).

## Como executar

A aplicação é totalmente dockerizada — não é necessário instalar Java, Maven ou PostgreSQL localmente.

**Pré-requisito:** Docker Desktop instalado e em execução.

```bash
# Clone o repositório
git clone https://github.com/ViniCarmo/restaurant-manager-api-v2
cd restaurant-manager-api-v2

# Suba a aplicação e o banco de dados
docker-compose up --build
```

Aguarde a mensagem de inicialização do servidor. A aplicação estará disponível em `http://localhost:8080`.

Para encerrar:

```bash
docker-compose down
```

Para encerrar e apagar os dados persistidos do banco:

```bash
docker-compose down -v
```

### Executando localmente sem Docker

Pré-requisitos: Java 21, Maven e PostgreSQL instalados e em execução.

```bash
git clone https://github.com/ViniCarmo/restaurant-manager-api-v2
cd restaurant-manager-api-v2
./mvnw spring-boot:run
```

Configure as variáveis de ambiente abaixo (ou ajuste diretamente `src/main/resources/application.yaml`) apontando para sua instância local do PostgreSQL.

## Variáveis de ambiente

| Variável | Descrição | Valor padrão (Docker Compose) |
|---|---|---|
| `SPRING_DATASOURCE_URL` | URL de conexão com o banco de dados | `jdbc:postgresql://db:5432/restaurant_manager` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco de dados | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco de dados | `postgres` |
| `JWT_SECRET` | Chave secreta para assinar e validar os tokens JWT | Definida em `docker-compose.yml` — **substitua por um valor forte em produção** |

## Endpoints

Todos os endpoints seguem o prefixo `/api/v1`. Documentação interativa completa disponível via [Swagger](#documentação-swagger).

### Authentication

| Método | Endpoint | Descrição | Autenticação |
|---|---|---|---|
| POST | `/api/v1/auth/login` | Autentica o usuário e retorna um token JWT | Não |

### UserType

| Método | Endpoint | Descrição | Autenticação |
|---|---|---|---|
| POST | `/api/v1/user-types` | Cria um tipo de usuário | Sim |
| GET | `/api/v1/user-types` | Lista todos os tipos de usuário | Sim |
| GET | `/api/v1/user-types/{id}` | Busca um tipo de usuário por ID | Sim |
| GET | `/api/v1/user-types/search?name=` | Busca um tipo de usuário por nome | Sim |
| DELETE | `/api/v1/user-types/{id}` | Remove um tipo de usuário | Sim |

### User

| Método | Endpoint | Descrição | Autenticação |
|---|---|---|---|
| POST | `/api/v1/users` | Cadastra um novo usuário | Não |
| GET | `/api/v1/users/{id}` | Busca um usuário por ID | Sim |
| GET | `/api/v1/users/search?email=` | Busca um usuário por e-mail | Sim |
| PUT | `/api/v1/users/{id}` | Atualiza nome e e-mail do usuário | Sim |
| PATCH | `/api/v1/users/{id}/password` | Atualiza a senha do usuário | Sim |
| DELETE | `/api/v1/users/{id}` | Remove um usuário | Sim |

### Restaurant

| Método | Endpoint | Descrição | Autenticação | Permissão |
|---|---|---|---|---|
| POST | `/api/v1/restaurants` | Cria um restaurante para o usuário autenticado | Sim | `RESTAURANT_OWNER` |
| GET | `/api/v1/restaurants` | Lista todos os restaurantes | Sim | `CUSTOMER` ou `RESTAURANT_OWNER` |
| GET | `/api/v1/restaurants/search?name=&kitchenType=` | Busca restaurantes por nome e/ou tipo de cozinha | Sim | `CUSTOMER` ou `RESTAURANT_OWNER` |
| GET | `/api/v1/restaurants/{id}` | Busca um restaurante por ID | Sim | `CUSTOMER` ou `RESTAURANT_OWNER` |
| PUT | `/api/v1/restaurants/{id}` | Atualiza um restaurante | Sim | `RESTAURANT_OWNER` **dono do recurso** |
| DELETE | `/api/v1/restaurants/{id}` | Remove um restaurante | Sim | `RESTAURANT_OWNER` **dono do recurso** |

### MenuItem

| Método | Endpoint | Descrição | Autenticação | Permissão |
|---|---|---|---|---|
| POST | `/api/v1/menu-items` | Cria um item de cardápio | Sim | `RESTAURANT_OWNER` **dono do restaurante** |
| GET | `/api/v1/menu-items` | Lista todos os itens de cardápio | Sim | `CUSTOMER` ou `RESTAURANT_OWNER` |
| GET | `/api/v1/menu-items/{id}` | Busca um item de cardápio por ID | Sim | `CUSTOMER` ou `RESTAURANT_OWNER` |
| GET | `/api/v1/menu-items/{restaurantId}/menu-items` | Lista os itens de cardápio de um restaurante | Sim | `CUSTOMER` ou `RESTAURANT_OWNER` |
| PUT | `/api/v1/menu-items/{id}` | Atualiza um item de cardápio | Sim | `RESTAURANT_OWNER` **dono do restaurante** |
| DELETE | `/api/v1/menu-items/{id}` | Remove um item de cardápio | Sim | `RESTAURANT_OWNER` **dono do restaurante** |

## Autenticação e autorização

A autenticação é **stateless**, baseada em JWT:

1. `POST /api/v1/auth/login` com e-mail e senha retorna um token válido.
2. Envie o token em requisições subsequentes no header `Authorization: Bearer {token}`.

A autorização acontece em dois níveis:

- **Role** (Spring Security) — define quais tipos de usuário podem, em geral, acessar cada endpoint.
- **Posse do recurso** (domínio) — mesmo sendo `RESTAURANT_OWNER`, o usuário só pode alterar ou excluir restaurantes e itens de cardápio **dos quais ele é o dono**, validado via `restaurant.belongsTo(loggedUser)` em cada caso de uso sensível. Essa checagem evita que um proprietário manipule recursos pertencentes a outro.

## Testes

```bash
./mvnw test
```

Cobertura inclui:

- **Testes unitários de domínio** — entidades `User`, `UserType`, `Restaurant` e `MenuItem`, sem dependência de Spring ou banco.
- **Testes unitários dos 23 casos de uso** — com Mockito, isolando cada Use Case de suas dependências de repositório e do provedor de usuário autenticado.
- **Testes de integração dos controllers** — com MockMvc e banco H2 em memória, cobrindo autenticação, autorização por role e validação de posse de recurso.

## Documentação Swagger

Com a aplicação em execução, acesse:

```
http://localhost:8080/swagger-ui.html
```
## Collection Postman

A collection com exemplos de requisições para todos os endpoints está disponível em
[`postman/collections`](./postman/collections).

Para importar: abra o Postman → **Import** → selecione o arquivo `.json` da pasta.


Para testar endpoints protegidos: realize o login em `/api/v1/auth/login`, copie o token retornado, clique em **Authorize** e cole o token.

## Estrutura de pastas

```
restaurant-manager-api-v2/
├── src/
│   ├── main/
│   │   ├── java/com/vinicius/restaurant_manager_api_v2/restaurant_manager_api/
│   │   │   ├── user/
│   │   │   ├── userType/
│   │   │   ├── restaurant/
│   │   │   ├── menuItem/
│   │   │   └── shared/
│   │   │       ├── config/          # OpenApiConfig
│   │   │       ├── exception/       # GlobalExceptionHandler
│   │   │       └── security/        # SecurityConfig, SecurityFilter, TokenService
│   │   └── resources/
│   │       ├── db/migration/        # Scripts Flyway
│   │       └── application.yaml
│   └── test/
│       ├── java/...                 # Testes unitários e de integração
│       └── resources/application.yaml
├── docker-compose.yml
├── Dockerfile
└── pom.xml



```
**Autor:** Vinicius Carmo
[LinkedIn](https://www.linkedin.com/in/viniciuscarmoo/) · [GitHub](https://github.com/ViniCarmo)
