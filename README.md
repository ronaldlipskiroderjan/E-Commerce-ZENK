# Zenk

API REST para um e-commerce, desenvolvida com Java e Spring Boot. O projeto reúne a estrutura de domínio de uma loja virtual, persistência em PostgreSQL e autenticação stateless com Spring Security e JWT.

## Funcionalidades

- Cadastro de usuários
- Autenticação e autorização com JWT
- Senhas protegidas com BCrypt
- Validação dos dados de entrada
- Modelagem de produtos, categorias, pedidos, pagamentos, endereços, fretes e transportadoras
- Tratamento global de exceções

## Tecnologias

- Java 21
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Bean Validation
- PostgreSQL
- JSON Web Token (JJWT)
- Lombok
- Maven

## Pré-requisitos

Antes de iniciar, instale:

- JDK 21
- Maven 3.9 ou superior
- PostgreSQL 14 ou superior

## Configuração

1. Crie o banco de dados:

```sql
CREATE DATABASE zenk_db;
```

A aplicação se conecta, por padrão, a `jdbc:postgresql://localhost:5432/zenk_db`.

2. Defina as variáveis de ambiente usadas pela aplicação:

```bash
export DATABASE_USERNAME="seu_usuario"
export DATABASE_PASSWORD="sua_senha"
export JWT_KEY="sua_chave_secreta"
export JWT_EXPIRATION="900000"
```

`JWT_EXPIRATION` é opcional e, por padrão, corresponde a 15 minutos. Use uma chave JWT longa e aleatória. No Linux, uma chave pode ser gerada com:

```bash
LC_ALL=C tr -dc 'A-Za-z0-9' </dev/urandom | head -c 344
```

> Atenção: a configuração atual usa `spring.jpa.hibernate.ddl-auto=create-drop`. As tabelas são recriadas ao iniciar a aplicação e removidas ao encerrá-la, portanto os dados não são persistidos entre execuções.

## Como executar

Clone o repositório, entre na pasta do projeto e execute:

```bash
mvn spring-boot:run
```

Por padrão, a API ficará disponível em `http://localhost:8080`.

Para gerar o pacote da aplicação:

```bash
mvn clean package
java -jar target/Zenk-0.0.1-SNAPSHOT.jar
```

## Endpoints

### Cadastrar usuário

```http
POST /v1/auth/register
Content-Type: application/json
```

Exemplo de requisição:

```json
{
  "nome": "Maria Silva",
  "email": "maria@example.com",
  "senha": "uma-senha-segura",
  "cpf": "12345678900",
  "telefone": "11999999999"
}
```

Resposta de sucesso: `201 Created`.

As rotas em `/v1/auth/**` aceitam requisições sem autenticação. As demais rotas são protegidas e devem receber um token no cabeçalho:

```http
Authorization: Bearer <token>
```

## Testes

Execute a suíte de testes com:

```bash
mvn test
```

## Estrutura do projeto

```text
src/
├── main/
│   ├── java/br/com/e_commerce/Zenk/
│   │   ├── config/       # Segurança e JWT
│   │   ├── controller/   # Endpoints REST
│   │   ├── database/
│   │   │   ├── model/    # Entidades JPA
│   │   │   └── repository/
│   │   ├── dtos/         # Objetos de entrada da API
│   │   ├── enums/
│   │   ├── exception/
│   │   ├── handler/      # Tratamento global de erros
│   │   └── service/      # Regras de negócio
│   └── resources/
│       └── application.yaml
└── test/
```

## Modelo de dados

O diagrama entidade-relacionamento do projeto está disponível abaixo:

![Diagrama entidade-relacionamento](Docs/Diagrama%20Entidade-Relacionamento.png)
