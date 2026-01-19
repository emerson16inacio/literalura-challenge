# 📚 LiterAlura - Catálogo de Livros

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-green)
![Postgres](https://img.shields.io/badge/PostgreSQL-16-blue)

## 📝 Descrição

O **LiterAlura** é uma aplicação Back-End desenvolvida durante o desafio da especialização Java da Alura. O objetivo do projeto é consumir a API [Gutendex](https://gutendex.com/), extrair dados de livros e autores, persisti-los em um banco de dados relacional (PostgreSQL) e realizar consultas complexas.

## 🔨 Funcionalidades

O projeto conta com um menu interativo via console que oferece as seguintes opções:

- **1 - Buscar livro pelo título:** Consome a API Gutendex, busca o livro e salva automaticamente no banco de dados (junto com o autor). Verifica duplicidade antes de salvar.
- **2 - Listar livros registrados:** Exibe todos os livros salvos localmente no banco de dados, com detalhes de título, autor, idioma e downloads.
- **3 - Listar autores registrados:** Mostra os autores salvos e seus respectivos livros.
- **4 - Listar autores vivos em determinado ano:** Permite filtrar autores que estavam vivos em um ano específico (ex: 1800), usando lógica de banco de dados para cruzar datas de nascimento e falecimento.
- **5 - Listar livros em determinado idioma:** Filtra os livros salvos por idioma (EN, ES, FR, PT).

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot** (Framework principal)
- **Spring Data JPA** (Persistência de dados e Repositórios)
- **PostgreSQL** (Banco de dados)
- **Jackson** (Deserialização de dados JSON)
- **Maven** (Gerenciador de dependências)

## 🚀 Como executar o projeto

### Pré-requisitos
- Java 17 instalado
- PostgreSQL instalado e rodando
- Maven (opcional se usar o wrapper do projeto)

### Passos
1. Clone o repositório:
   ```bash
   git clone [https://github.com/emerson16inacio/literalura-challenge.git](https://github.com/emerson16inacio/literalura-challenge.git)
