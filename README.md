# 🤝 Colaboradores API - Spring Boot

API REST completa para gerenciamento de colaboradores com sistema de pontuação, hierarquia de chefes, autenticação JWT e frontend integrado.

---

## 🛠️ Tecnologias

<div align="center">

[![My Skills](https://skillicons.dev/icons?i=java,spring,mysql,js,html,css,git,github&theme=dark)](https://skillicons.dev)

</div>

- **Java 17** + **Spring Boot 4**
- **Spring Data JPA** — comunicação com banco de dados
- **Spring Security** — segurança e autenticação
- **JWT** (JSON Web Token) — autenticação stateless
- **BCrypt** — criptografia de senhas
- **MySQL** — banco de dados relacional
- **HTML, CSS e JavaScript** — frontend integrado

---

## ▶️ Como rodar localmente

**Pré-requisitos:** Java 17, MySQL, Maven

```bash
# Clone o repositório
git clone https://github.com/winiciuscdc404/colaboradores-api-spring-boot.git

# Entre na pasta
cd colaboradores-api-spring-boot

# Configure o banco de dados
cp src/main/resources/application.properties.example src/main/resources/application.properties
# Edite o application.properties com suas credenciais MySQL

# Rode o projeto
.\mvnw spring-boot:run
```

Acesse: `http://localhost:8080`

---

## 📡 Endpoints

### 🔐 Autenticação — pública
| Método | URL | Descrição |
|--------|-----|-----------|
| `POST` | `/auth/login` | Login — retorna token JWT |

### 👥 Colaboradores — requer token JWT
| Método | URL | Descrição |
|--------|-----|-----------|
| `GET` | `/colaborador` | Lista todos os colaboradores |
| `GET` | `/colaborador/{id}` | Busca colaborador por ID |
| `POST` | `/colaborador` | Cria novo colaborador |
| `PUT` | `/colaborador/{id}` | Edita colaborador |
| `DELETE` | `/colaborador/{id}` | Deleta colaborador |
| `PATCH` | `/colaborador/{id}/add-score?pontos=X` | Adiciona pontos |

---

## 📋 Exemplos de uso

### Login
```json
POST /auth/login
{
    "nome": "João Silva",
    "senha": "minhasenha123"
}
```
Retorna:
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Usar o token

Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...


### Criar colaborador
```json
POST /colaborador
{
    "nome": "João Silva",
    "senha": "minhasenha123",
    "score": 0
}
```

### Adicionar pontos

PATCH /colaborador/1/add-score?pontos=100

O colaborador recebe **100 pontos** e o chefe recebe **10% de bônus (10 pontos)**.

---

## 📊 Banco de dados

```sql
CREATE TABLE COLABORADORES (
    ID       INTEGER AUTO_INCREMENT PRIMARY KEY,
    NOME     VARCHAR(200) NOT NULL,
    SENHA    VARCHAR(100) NOT NULL,
    SCORE    INTEGER NULL,
    ID_CHEFE INTEGER NULL
);
```

Auto-relacionamento — um colaborador pode ter outro colaborador como chefe.

---

## 🏗️ Arquitetura

src/
├── config/
│   ├── JwtUtil.java          → geração e validação de tokens JWT
│   ├── JwtFiltro.java        → filtro que intercepta requisições
│   └── SecurityConfig.java   → configuração de segurança
├── controller/
│   ├── AuthController.java         → endpoint de login
│   ├── BackendController.java      → endpoints de colaboradores
│   └── GlobalExceptionHandler.java → tratamento de erros
├── service/
│   └── BackendService.java   → regras de negócio
├── repository/
│   └── BackendRepository.java → acesso ao banco
└── model/
├── BackendEntity.java    → entidade colaborador
├── LoginRequest.java     → DTO de login
├── LoginResponse.java    → DTO de resposta
└── ErroResponse.java     → DTO de erro


---

## 🔒 Segurança

- ✅ Senhas criptografadas com **BCrypt**
- ✅ Autenticação via **JWT** com validade de 8 horas
- ✅ Endpoints protegidos — token obrigatório
- ✅ Rotas públicas: `/auth/**`, `/login.html`, arquivos estáticos

---

## 🖥️ Frontend

Interface web integrada em HTML, CSS e JS:

- Tela de login com autenticação JWT
- Redirecionamento automático se não estiver logado
- Cards de estatísticas em tempo real
- CRUD completo via modais
- Adição de pontos direto na tabela
- Botão de logout

---

## ⚠️ Tratamento de erros

Respostas padronizadas para todos os erros:

```json
{
    "status": 404,
    "erro": "Não encontrado",
    "mensagem": "Colaborador não encontrado com id: 999"
}
```

---

## 🚧 Próximas funcionalidades

- [ ] Roles de usuário (admin, colaborador)
- [ ] Paginação na listagem
- [ ] Deploy em produção
- [ ] Testes automatizados

---

## 👤 Autor

**Winicius** — [@winiciuscdc404](https://github.com/winiciuscdc404)
