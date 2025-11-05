# 🛒 Sistema de Pedidos — Java Spring Boot + MariaDB

Este projeto é um **sistema simples de pedidos online**, desenvolvido em **Java 21** com **Spring Boot**, **Maven** e **MariaDB**.  
Permite o cadastro e autenticação de clientes, criação de pedidos e listagem de todos os pedidos por um administrador.

---

## ⚙️ Tecnologias utilizadas

- **Java 21**
- **Spring Boot 3.2**
- **Spring Data JPA**
- **MariaDB 12.0**
- **Maven**
- **Tomcat (embutido no Spring Boot)**
- **HTML + JavaScript (front-end simples)**

---

## 🧩 Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

1. **Java 21** (configurado no PATH)  
2. **MariaDB** (rodando na porta 3307 ou a configurada no projeto)  
3. **Maven** (para build e execução)  
4. **Eclipse IDE** ou similar  

> ⚠️ **Não é necessário instalar o Tomcat separadamente**, pois o Spring Boot já traz um servidor embutido.

---

## 🗄️ Configuração do Banco de Dados (MariaDB)

### 1️⃣ Localize o executável do MariaDB

Normalmente o caminho é:
```
C:\Program Files\MariaDB 12.0\bin\mysql
```

### 2️⃣ Acesse o banco via terminal:

```bash
"C:\Program Files\MariaDB 12.0\bin\mysql" -u root -p --port=3307
```

Digite a senha configurada (no exemplo, `123`).

Se estiver tudo certo, você verá:
```
Welcome to the MariaDB monitor...
```

### 3️⃣ Crie o banco de dados:

```sql
CREATE DATABASE sistemapedidos CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

Verifique:
```sql
SHOW DATABASES;
```

Saída esperada:
```
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| sistemapedidos     |
+--------------------+
```

### 4️⃣ Garanta privilégios de acesso:

```sql
GRANT ALL PRIVILEGES ON sistemapedidos.* TO 'root'@'localhost' IDENTIFIED BY '123';
FLUSH PRIVILEGES;
```

> Altere `'root'` e `'123'` conforme suas credenciais.

---

## ⚙️ Configuração do Spring Boot

Arquivo: `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mariadb://localhost:3307/sistemapedidos
spring.datasource.username=root
spring.datasource.password=123
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect

# Porta do servidor
server.port=8080
```

> 🔧 Substitua `username`, `password` e `port` conforme sua instalação local do MariaDB.

---

## 🚀 Executando o Projeto

### 1️⃣ Iniciar o servidor Spring Boot

- No Eclipse:
  - Clique com o botão direito no projeto → **Run As → Java Application**
  - Escolha a classe principal `SistemaPedidosApplication`

### 2️⃣ Acessar no navegador

```
http://localhost:8080
```

Se tudo estiver certo, abrirá a tela inicial do sistema (`index.html`).

---

## 👤 Usuários padrão

Ao iniciar o projeto pela primeira vez, dois usuários são criados automaticamente:

| Tipo        | Nome           | E-mail              | Senha |
|--------------|----------------|---------------------|-------|
| **Admin**    | Administrador  | admin@teste.com     | 123   |
| **Cliente**  | Cliente Teste  | cliente@teste.com   | 123   |

> Você pode cadastrar novos clientes pelo formulário de registro.  
> O administrador padrão é criado automaticamente e não pode ser cadastrado manualmente.

---

## 💻 Funcionalidades

### 👥 Cliente
- Cadastro e login  
- Criação de pedidos  
- Listagem dos próprios pedidos  
- Exclusão de pedidos próprios  

### 🧑‍💼 Administrador
- Visualização de **todos os pedidos** cadastrados (de todos os clientes)

---

## 🖥️ Interface (Front-end)

O front-end é um arquivo simples em **HTML + JavaScript**, localizado em:
```
src/main/resources/static/index.html
```

### Telas disponíveis:
- Tela de Login e Registro  
- Área do Cliente (com pedidos e botão de exclusão)  
- Área do Administrador (com listagem geral de pedidos)

---

