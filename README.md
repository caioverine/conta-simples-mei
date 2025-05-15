# Conta Simples MEI

**Micro SaaS para gestão financeira simples voltado a MEIs**  
Uma plataforma web para controle de receitas, despesas e saldo, com foco na simplicidade e eficiência para microempreendedores individuais.

---

## 🧱 Tecnologias

- **Backend**: Kotlin + Spring Boot
- **Frontend**: React (com Vite)
- **Banco de Dados**: PostgreSQL
- **Infraestrutura**: Docker + Docker Compose

---

## 🚀 Funcionalidades (MVP)

- Cadastro e login de usuários
- Lançamento de receitas e despesas
- Painel com resumo financeiro (saldo, entrada, saída)
- Relatórios mensais
- Categorias personalizáveis
- Exportação dos dados em CSV (futuro)

---

## 🗂 Estrutura do Projeto

contasimplesmei/
├── backend/ # API REST em Kotlin + Spring Boot
├── frontend/ # Aplicação web em React
├── docs/ # Documentação do projeto
├── docker-compose.yml
└── README.md


---

## 🧑‍💻 Como rodar localmente

### Pré-requisitos

- Docker + Docker Compose
- Java 17+ (se quiser rodar sem Docker)
- Node.js 18+ (para frontend)

### 1. Clone o repositório

git clone https://github.com/seu-usuario/contasimplesmei.git
cd contasimplesmei

## Suba a stack com Docker

docker-compose up --build
O backend ficará disponível em http://localhost:8080
O frontend ficará disponível em http://localhost:5173

## Em desenvolvimento

Este projeto está em fase inicial de desenvolvimento. Contribuições e sugestões são bem-vindas!
