# Software Desktop Login 

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/javafx-%23FF0000.svg?style=for-the-badge&logo=javafx&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

Essa aplicação foi feita usando **Java com JavaFX.**

Essa software dekstop foi desenvolvida com Postgres para meu portfolio, para demonstrar conhecimento em Java.

## Funcionalidades



- `Login` Formulário de login para entrar no software.
<h1 align="center">
    <img src="./public/imageGit1.png" width="300"/>
</h1>

- `Registro` Formulário de registro para cadastrar.
<h1 align="center">
    <img src="./public/imageGit2.png" width="300"/>
</h1>



## Instalação

1. Clone o repositorio:

```bash
git clone https://github.com/JustinoLucas/Software-Login-JavaFX.git
```

2. Baixe as depencencias a seguir:

>SDK JavaFX 23.0.2 /
>JDK Java 23

```bash
Configure todas as Variables PATHS no seu local de trabalho e na sua IDE
```

3. SQL Postgres
>Crie um banco de dados com o nome que quiser, porem altere no arquivo DBConnection
```bash
CREATE TABLE user_account (
    account_id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    firstname VARCHAR(45) NOT NULL,
    lastname VARCHAR(45) NOT NULL,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    PRIMARY KEY (account_id),
    UNIQUE (username)
);
```
4. Executando o projeto no IntelliJ :
```bash
Shift+10
```