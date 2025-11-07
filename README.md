# 🚗 Vehicle Analysis API

API desenvolvida em **Spring Boot 3** e **Java 21** para realizar **análises unificadas de veículos**, consultando múltiplas fontes externas (F1, F2 e F3) de forma **paralela e resiliente**.

---

## 📘 Visão Geral

O sistema recebe um identificador de veículo (placa, RENAVAM ou VIN) e realiza consultas integradas a três fornecedores:

- **F1:** Retorna restrições e infrações.  
- **F2:** Retorna dados adicionais quando há restrições (Renajud/Recall).  
- **F3:** Retorna métricas e custos de análise.

A API consolida as respostas, calcula métricas e registra logs detalhados da execução no **PostgreSQL**.

---

## ⚙️ Tecnologias Utilizadas

- ☕ **Java 21**
- 🚀 **Spring Boot 3**
- 🧱 **Gradle 8**
- 🧩 **Resilience4j** (Circuit Breaker, Retry, TimeLimiter)
- 🐘 **PostgreSQL**
- 🗄 **JPA / Hibernate**
- 🧰 **Lombok**
- 🌐 **WebClient**
- ✅ **JUnit 5**

---

## 🧩 Estrutura do Projeto

```text
└── src
    ├── main
    │   ├── java/com/analise/veiculo/api
    │   │   ├── controller/
    │   │   │   └── VehicleAnalysisController.java
    │   │   ├── service/
    │   │   │   ├── VehicleAnalysisUseCaseService.java
    │   │   │   ├── VehicleAnalysisUseCaseServiceImpl.java
    │   │   │   └── client/
    │   │   │       ├── F1Client.java
    │   │   │       ├── F2Client.java
    │   │   │       └── F3Client.java
    │   │   ├── model/
    │   │   │   ├── entity/VehicleAnalysisLogEntity.java
    │   │   │   └── response/
    │   │   │       ├── VehicleAnalysisResponse.java
    │   │   │       ├── ConstraintsResponse.java
    │   │   │       ├── SupplierStatusResponse.java
    │   │   │       ├── F1Response.java
    │   │   │       ├── F2Response.java
    │   │   │       └── F3Response.java
    │   │   └── repository/
    │   │       └── VehicleAnalysisLogRepository.java
    │   └── resources/
    │       └── application.yml
    └── test/
        └── java/com/analise/veiculo/api/VehicleAnalysisControllerTest.java
````
---
🚀 Como Executar o Projeto
Pré-requisitos
Para rodar a aplicação, você precisa ter:

Java 21+

Gradle 8+ (ou utilizar o gradlew wrapper)

Docker (para subir o banco de dados)

Configuração do Banco de Dados
Utilize o docker-compose para iniciar a instância do PostgreSQL:

Bash

docker-compose up -d
O banco de dados estará configurado conforme o application.yml (porta 5432 padrão).

Rodar a Aplicação
Execute o seguinte comando para compilar e iniciar a API:

Bash

./gradlew bootRun
A API estará acessível em http://localhost:8080.

📝 Documentação da API (Swagger UI)
Após iniciar a aplicação, você pode acessar a documentação interativa e testar os endpoints através do Swagger UI:

Swagger UI
