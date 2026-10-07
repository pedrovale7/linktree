# Linktree

API REST em Spring Boot que gerencia os links exibidos na página de bio (estilo Linktree), com contagem de cliques por link.

## Stack

- **Java + Spring Boot** (Web, Data JPA, Validation)
- **PostgreSQL** (hospedado no Supabase)
- Arquitetura em camadas: `Controller → Service → Repository → Banco`, com DTOs separando o que entra/sai da API da entidade persistida.

## Arquitetura

```
Cliente (frontend React)
        │  HTTP (JSON)
        ▼
  LinkController          ← recebe o request, só trabalha com DTOs
        │
        ▼
  LinkService              ← regra de negócio, converte DTO ↔ entity
        │
        ▼
  LinkRepository            ← fala com o banco via JPA, sem SQL manual
        │
        ▼
    PostgreSQL (Supabase)
```

## Estrutura de pastas

```
src/main/java/com/pedrovale/linktree/
├── config/
│   └── CorsConfig.java        # libera o frontend a chamar a API
├── controller/
│   └── LinkController.java    # endpoints HTTP
├── dto/
│   ├── LinkRequestDTO.java    # o que o cliente pode enviar
│   └── LinkResponseDTO.java   # o que a API devolve
├── expection/
│   └── LinkNotFoundException  # Mensagem de Erro 404
├── infra/
│   └── RestExceptionHandler   # Advice Controller 
├── model/
│   └── Link.java              # entidade JPA (tabela `links`)
├── repository/
│   └── LinkRepository.java    # acesso ao banco
└── service/
    └── LinkService.java       # lógica de negócio
```

## Endpoints

| Método | Rota                  | Body                              | O que faz                                         |
|--------|------------------------|------------------------------------|-----------------------------------------------------|
| GET    | `/api/links`            | —                                   | Lista todos os links                                |
| POST   | `/api/links`            | `{ "title": "...", "url": "..." }`  | Cria um link novo                                   |
| GET    | `/api/links/{id}`       | —                                   | Lista link especificado pelo id   |
| GET    | `/api/links/{id}/go`    | —                                   | Soma 1 clique no link e redireciona (302) pra URL   |
| PUT    | `/api/links/{id}`    | `{"title": "...", "url": "..."}`       | Atualiza as informações do link   |
| DELETE    | `/api/links/{id}`    | —                                   | Exclui o link especificado pelo id   |


## Como rodar localmente

### Pré-requisitos
- JDK 17+
- Maven (ou o wrapper `./mvnw`)
- Uma instância Postgres (recomendado: Supabase, free tier)

### 1. Configure o banco

- Crie um .env na raiz do projeto, seguindo a estrutura do .env.example

```
NAME_DB=
USER_DB=
PASSW_DB=
```

- No `src/main/resources/application.properties` coloque a url do seu banco (recomendado: Postgres ou Supabase):

```properties
spring.datasource.url=jdbc:postgresql://SEU_HOST_SUPABASE:5432/postgres
```

### 2. Rode a aplicação

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`. O Hibernate cria a tabela `links` automaticamente na primeira execução.

## Próximos Passos
- Criação e Integração com o FrontEnd
- Adicionar Entidade Usuário e suas regras de Negócio