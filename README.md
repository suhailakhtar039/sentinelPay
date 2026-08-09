# SentinelPay 💳

> A production-oriented fintech payment platform built with Java 21, Spring Boot 3, Angular, Apache Kafka, Redis, MySQL, JWT, Docker, and AWS.

SentinelPay is a distributed payment platform designed to demonstrate how modern fintech systems can be built using **microservices, event-driven architecture, distributed processing, secure authentication, caching, fraud detection, financial ledgering, analytics, containerization, and cloud deployment**.

The system simulates a digital payment ecosystem where users can register, authenticate, manage wallets, initiate payments, process transactions asynchronously, perform fraud checks, maintain financial ledger entries, receive notifications, and monitor payment activity through an analytics dashboard.

---

## 📌 Project Overview

SentinelPay is designed around a **microservices architecture** where individual business capabilities are separated into independently deployable services.

The core payment workflow uses **Apache Kafka** for asynchronous communication between services.

Instead of making the payment service synchronously call every downstream service, SentinelPay publishes domain events that are consumed by the relevant services.

This provides:

- Loose coupling between services
- Asynchronous processing
- Independent service scalability
- Event-driven communication
- Better separation of business responsibilities
- A foundation for implementing distributed transaction patterns

---

# 🏗️ Architecture

```text
                         ┌──────────────────────┐
                         │       Browser        │
                         └──────────┬───────────┘
                                    │
                                    │ HTTP
                                    ▼
                         ┌──────────────────────┐
                         │   Angular Frontend  │
                         │       + Nginx        │
                         └──────────┬───────────┘
                                    │
                                    │ /api/*
                                    ▼
                         ┌──────────────────────┐
                         │     API Gateway      │
                         │  Spring Cloud GW     │
                         └──────────┬───────────┘
                                    │
             ┌──────────────────────┼──────────────────────┐
             │                      │                      │
             ▼                      ▼                      ▼
      ┌─────────────┐       ┌─────────────┐       ┌─────────────┐
      │    Auth     │       │   Wallet    │       │   Payment   │
      │   Service   │       │   Service   │       │   Service   │
      └─────────────┘       └─────────────┘       └──────┬──────┘
                                                          │
                                                          │ Events
                                                          ▼
                                               ┌────────────────────┐
                                               │       Kafka         │
                                               │   Event Broker      │
                                               └─────────┬──────────┘
                                                         │
                         ┌───────────────────────────────┼──────────────────┐
                         │                               │                  │
                         ▼                               ▼                  ▼
                  ┌─────────────┐                 ┌─────────────┐    ┌─────────────┐
                  │    Fraud    │                 │   Ledger    │    │Notification │
                  │   Service   │                 │   Service   │    │   Service   │
                  └─────────────┘                 └─────────────┘    └─────────────┘

                    ┌──────────────────────────────────────┐
                    │         Shared Infrastructure         │
                    │                                      │
                    │       MySQL │ Redis │ Kafka           │
                    └──────────────────────────────────────┘
```

---

# 🧩 Microservices

| Service | Port | Responsibility |
|---|---:|---|
| API Gateway | `8080` | Central API entry point and routing |
| Auth Service | `8081` | Registration, login and JWT authentication |
| Wallet Service | `8082` | Wallet creation, balance and wallet operations |
| Payment Service | `8083` | Payment processing, transaction history and analytics |
| Fraud Service | `8084` | Fraud evaluation and transaction approval |
| Notification Service | `8085` | Payment and system notifications |
| Ledger Service | `8086` | Financial transaction ledger |
| Frontend | `80` | Angular application served through Nginx |

## Infrastructure

| Component | Port | Purpose |
|---|---:|---|
| MySQL | `3306` | Persistent relational data |
| Redis | `6379` | Caching |
| ZooKeeper | `2181` | Kafka coordination |
| Kafka | `9092` | Event streaming and asynchronous communication |

---

# 🔄 Payment Processing Flow

A payment is processed using an event-driven workflow.

```text
User
 │
 ▼
Angular Frontend
 │
 ▼
API Gateway
 │
 ▼
Payment Service
 │
 │ PaymentInitiatedEvent
 ▼
Kafka
 │
 ▼
Fraud Service
 │
 │ Fraud Decision
 ▼
FraudApprovedEvent
 │
 ▼
Wallet Service
 │
 │ PaymentCompletedEvent
 ▼
Kafka
 │
 ├─────────────────────┐
 ▼                     ▼
Ledger Service     Notification Service
```

### Detailed flow

1. User initiates a payment from the Angular frontend.
2. Request reaches the API Gateway.
3. API Gateway routes the request to Payment Service.
4. Payment Service validates the payment request.
5. Payment Service publishes a `PaymentInitiatedEvent`.
6. Fraud Service consumes the event.
7. Fraud Service evaluates the transaction.
8. If approved, a `FraudApprovedEvent` is published.
9. Wallet Service consumes the approval event.
10. Wallet balances are updated.
11. Wallet Service publishes a `PaymentCompletedEvent`.
12. Ledger Service consumes the completion event and creates ledger entries.
13. Notification Service consumes relevant events and handles notifications.
14. Payment status is updated accordingly.

---

# ⚡ Event-Driven Architecture

SentinelPay uses **Apache Kafka** as the event backbone.

Important events include:

```text
PaymentInitiatedEvent
FraudApprovedEvent
PaymentCompletedEvent
UserRegisteredEvent
```

Kafka topics used by the platform include:

```text
user.registered
payment.initiated
payment.completed
user.registered.dlt
```

The architecture allows services to communicate without tightly coupling their implementations.

For example:

```text
Payment Service
      │
      │ publishes
      ▼
payment.initiated
      │
      ├──────────────► Fraud Service
      │
      └──────────────► Wallet Service
```

This approach provides a foundation for:

- Asynchronous processing
- Consumer groups
- Event replay
- Fault isolation
- Service independence
- Distributed transaction workflows

---

# 🔐 Authentication & Security

SentinelPay uses **Spring Security and JWT** for authentication.

### Authentication flow

```text
User
 │
 ▼
POST /api/auth/register
 │
 ▼
Auth Service
 │
 ▼
User persisted
```

Login:

```text
User
 │
 ▼
POST /api/auth/login
 │
 ▼
Auth Service
 │
 ▼
JWT generated
 │
 ▼
Frontend
```

Authenticated requests are then sent through the API Gateway.

### Authentication endpoints

```text
POST /api/auth/register
POST /api/auth/login
POST /api/auth/validate
```

JWT contains authentication information used to authorize requests.

---

# 👛 Wallet Management

The Wallet Service manages user wallet information and balances.

Typical responsibilities include:

- Wallet creation
- Wallet balance
- Wallet top-up
- Debit operations
- Credit operations
- Wallet status
- Payment-related balance updates

Redis is used to cache frequently accessed wallet information.

Example cached data:

```text
wallet::<userId>
```

---

# 💸 Payment Service

Payment Service is responsible for:

- Creating payments
- Validating payment requests
- Payment status management
- Transaction history
- Payment analytics
- Publishing payment events

Example payment lifecycle:

```text
PENDING
   │
   ▼
FRAUD CHECK
   │
   ├──────────────► REJECTED
   │
   ▼
COMPLETED
```

Payment processing is coordinated using Kafka events rather than tightly coupled synchronous service calls.

---

# 🛡️ Fraud Detection

The Fraud Service evaluates payment events independently from the Payment Service.

```text
PaymentInitiatedEvent
        │
        ▼
Fraud Service
        │
        ├──── Fraudulent ────► Rejected
        │
        └──── Approved ──────► FraudApprovedEvent
```

This separation allows fraud detection logic to evolve independently from payment processing.

It also provides a foundation for future enhancements such as:

- Risk scoring
- Velocity checks
- Transaction limits
- User behaviour analysis
- Rule engines
- Machine-learning based fraud detection

---

# 📒 Ledger Service

The Ledger Service maintains financial transaction records.

After a payment is successfully completed:

```text
PaymentCompletedEvent
        │
        ▼
Ledger Service
        │
        ▼
Ledger Entry
```

The ledger provides a separate financial record of completed transactions.

This separation is important in financial systems because payment state and accounting records represent different concerns.

---

# 🔔 Notification Service

The Notification Service consumes relevant events from Kafka and handles notification-related operations.

This service is intentionally separated from payment processing so that notification failures do not need to block the core payment workflow.

---

# 📊 Analytics Dashboard

SentinelPay includes a dashboard for monitoring payment activity.

The analytics functionality currently includes:

- Total payments
- Successful payments
- Failed payments
- Pending payments
- Total transaction volume
- Average transaction amount
- Payment status distribution
- Daily transactions
- Monthly transaction volume
- Top receivers
- Recent payment history

Example analytics flow:

```text
Angular Dashboard
       │
       ▼
API Gateway
       │
       ▼
Payment Service
       │
       ├── MySQL
       │
       └── Redis Cache
```

Analytics responses are cached using Redis where appropriate to reduce repeated database queries.

---

# ⚡ Redis Caching

Redis is used for caching frequently accessed information.

Current cache areas include:

```text
ANALYTICS
PROFILE
WALLET
```

Example:

```text
wallet::6
```

can contain cached wallet information such as:

```json
{
  "walletId": 4,
  "userId": 6,
  "balance": 10500.00,
  "currency": "INR",
  "status": "ACTIVE"
}
```

The purpose of Redis is to reduce database load and improve response times for frequently accessed data.

---

# 🗄️ Database

SentinelPay uses **MySQL 8.4** as the primary relational database.

Spring Data JPA and Hibernate are used for persistence.

The database stores information related to:

- Users
- Wallets
- Payments
- Ledger entries
- Notifications

The application uses separate service-level responsibilities while sharing the underlying MySQL infrastructure.

---

# 🌐 API Gateway

The API Gateway is the single public backend entry point.

External clients communicate through:

```text
/api/*
```

instead of directly accessing individual microservices.

Example:

```text
Browser
   │
   ▼
Nginx
   │
   ▼
API Gateway :8080
   │
   ├── Auth Service
   ├── Wallet Service
   ├── Payment Service
   ├── Fraud Service
   ├── Notification Service
   └── Ledger Service
```

Internal service URLs use Docker's internal DNS:

```text
auth-service:8081
wallet-service:8082
payment-service:8083
fraud-service:8084
notification-service:8085
ledger-service:8086
```

These service names are resolved through the Docker network.

---

# 🖥️ Frontend

The frontend is built using:

- Angular
- TypeScript
- Angular Material
- RxJS
- Standalone Components

The production frontend is served through Nginx.

Angular uses:

```typescript
export const BASE_URL = '/api';
```

This avoids hardcoding the EC2 IP address into the application.

The browser communicates with:

```text
http://<server>/api/*
```

and Nginx forwards API requests to:

```text
http://api-gateway:8080
```

This allows the frontend and backend APIs to be exposed through the same public origin.

---

# 🌐 Nginx

Nginx serves the Angular application and acts as a reverse proxy for backend API requests.

Simplified routing:

```text
/        → Angular application

/api/*   → API Gateway
```

Example:

```text
Browser
   │
   ├── /
   │     └── Angular
   │
   └── /api/*
         └── API Gateway
```

---

# 🐳 Docker

Every major application component is containerized.

Example:

```text
sentinelpay/
│
├── frontend
├── api-gateway
├── auth-service
├── wallet-service
├── payment-service
├── fraud-service
├── notification-service
├── ledger-service
├── mysql
├── redis
├── kafka
└── zookeeper
```

Each Spring Boot service uses a Java 21 runtime image.

Example:

```dockerfile
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/paymentservice-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

# 🐳 Docker Compose

Docker Compose manages the complete SentinelPay runtime environment.

The Compose environment contains:

```text
MySQL
ZooKeeper
Kafka
Redis

Auth Service
Wallet Service
Payment Service
Fraud Service
Notification Service
Ledger Service
API Gateway
Frontend
```

All application containers communicate through:

```text
sentinelpay-network
```

Persistent storage is provided through Docker volumes:

```text
mysql-data
redis-data
```

---

# ☁️ AWS Deployment

SentinelPay has been deployed on an **AWS EC2 Ubuntu Server**.

Current deployment architecture:

```text
                         Internet
                            │
                            ▼
                    AWS EC2 Public IP
                            │
                            ▼
                     Nginx / Port 80
                            │
                 ┌──────────┴──────────┐
                 │                     │
                 ▼                     ▼
             Angular               /api/*
              Frontend                 │
                                      ▼
                                API Gateway
                                      │
                                      ▼
                              Microservices
```

The application can be accessed through the EC2 public IPv4 address.

For production environments, a domain name, HTTPS/TLS, an Elastic IP, and additional infrastructure controls should be used.

---

# 🛠️ Technology Stack

## Backend

| Technology | Purpose |
|---|---|
| Java 21 | Backend runtime |
| Spring Boot 3 | Microservices framework |
| Spring Security | Authentication and authorization |
| Spring Data JPA | Database access |
| Hibernate | ORM |
| Spring Cloud Gateway | API Gateway |
| Apache Kafka | Event-driven communication |
| Redis | Caching |
| MySQL | Relational database |
| JWT | Authentication tokens |
| Maven | Build and dependency management |
| Lombok | Boilerplate reduction |

## Frontend

| Technology | Purpose |
|---|---|
| Angular | Frontend framework |
| TypeScript | Frontend language |
| Angular Material | UI components |
| RxJS | Reactive programming |
| Nginx | Static hosting and reverse proxy |

## Infrastructure

| Technology | Purpose |
|---|---|
| Docker | Containerization |
| Docker Compose | Local/server orchestration |
| Ubuntu | Server OS |
| AWS EC2 | Cloud deployment |
| GitHub | Source control |

---

# 📁 Repository Structure

The backend consists of independently buildable Maven projects.

```text
sentinel-pay-be/
│
├── api-gateway/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
├── auth-service/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
├── wallet-service/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
├── payment-service/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
├── fraud-service/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
├── notification-service/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
├── ledger-service/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
├── common-lib/
│   ├── src/
│   └── pom.xml
│
├── docker-compose.yml
└── .env.docker
```

The frontend is maintained separately:

```text
sentinel-pay-fe/
│
├── src/
├── angular.json
├── package.json
├── Dockerfile
└── nginx.conf
```

---

# 🚀 Running the Project Locally

## Prerequisites

Install:

- Java 21
- Maven
- Node.js
- npm
- Docker
- Docker Compose
- Git

---

## 1. Clone the repositories

```bash
git clone <backend-repository-url>
git clone <frontend-repository-url>
```

---

## 2. Build Common Library

Because the services are independent Maven projects and some services depend on `common-lib`, install it into the local Maven repository:

```bash
cd common-lib

mvn clean install -DskipTests
```

---

## 3. Build Backend Services

Each service has its own `pom.xml`.

For example:

```bash
cd auth-service
mvn clean package -DskipTests
```

```bash
cd wallet-service
mvn clean package -DskipTests
```

```bash
cd payment-service
mvn clean package -DskipTests
```

Repeat for the remaining services.

---

## 4. Build Docker Images

Example:

```bash
docker build -t sentinelpay/payment-service:1.0 ./payment-service
```

Build the remaining service images similarly.

---

## 5. Configure Environment Variables

Create:

```text
.env.docker
```

Example configuration:

```env
DB_URL=jdbc:mysql://mysql:3306/sentinelpay
DB_USERNAME=root
DB_PASSWORD=your-password

KAFKA_BOOTSTRAP_SERVERS=kafka:9092

REDIS_HOST=redis
REDIS_PORT=6379

JWT_SECRET=your-secret

AUTH_SERVICE_URL=http://auth-service:8081
WALLET_SERVICE_URL=http://wallet-service:8082
PAYMENT_SERVICE_URL=http://payment-service:8083
FRAUD_SERVICE_URL=http://fraud-service:8084
NOTIFICATION_SERVICE_URL=http://notification-service:8085
LEDGER_SERVICE_URL=http://ledger-service:8086
```

> **Never commit real passwords, JWT secrets, API keys, or other credentials to GitHub.**

---

## 6. Start the Platform

From the directory containing `docker-compose.yml`:

```bash
docker compose --env-file .env.docker up -d
```

Check running containers:

```bash
docker compose --env-file .env.docker ps
```

---

## 7. View Logs

Example:

```bash
docker logs payment-service
```

Follow logs:

```bash
docker logs -f payment-service
```

Or:

```bash
docker compose logs -f payment-service
```

---

# 🔧 Rebuilding a Single Service

If only one service changes, there is no need to rebuild the entire platform.

For example, after modifying `payment-service`:

```bash
cd payment-service

mvn clean package -DskipTests

docker build -t sentinelpay/payment-service:1.0 .
```

Then recreate only that container:

```bash
cd ..

docker compose --env-file .env.docker \
  up -d --no-deps --force-recreate payment-service
```

This keeps the remaining services running.

---

# 🔎 Useful Docker Commands

Check containers:

```bash
docker ps
```

Check all containers:

```bash
docker ps -a
```

Check images:

```bash
docker images
```

Check resource consumption:

```bash
docker stats
```

Check a container:

```bash
docker inspect payment-service
```

Restart a service:

```bash
docker restart payment-service
```

Stop the environment:

```bash
docker compose down
```

Start the environment:

```bash
docker compose up -d
```

---

# 📡 API Examples

## Authentication

### Register

```http
POST /api/auth/register
```

### Login

```http
POST /api/auth/login
```

### Validate Token

```http
POST /api/auth/validate
```

---

## Analytics

Dashboard analytics:

```http
GET /api/analytics/dashboard
```

Additional analytics include:

```http
GET /api/analytics/overview
GET /api/analytics/payment-status
GET /api/analytics/monthly-volume
GET /api/analytics/daily-transactions
GET /api/analytics/top-receivers
GET /api/analytics/average-amount
```

---

# 📈 Engineering Concepts Demonstrated

SentinelPay demonstrates practical implementation of the following concepts:

### Microservices

Business capabilities are separated into independently deployable services.

### Event-Driven Architecture

Kafka events are used to decouple services and process transactions asynchronously.

### API Gateway

External clients communicate through a single gateway rather than directly accessing internal microservices.

### JWT Authentication

Authentication and authorization are implemented using Spring Security and JWT.

### Distributed Processing

Payment processing involves multiple independent services communicating through events.

### Caching

Redis reduces repeated database access for frequently requested information.

### Financial Ledgering

Completed transactions are persisted as ledger entries independently of the payment workflow.

### Fraud Detection

Payment events are evaluated by an independent fraud service.

### Containerization

Services are packaged as Docker images and orchestrated with Docker Compose.

### Cloud Deployment

The application has been deployed to AWS EC2.

---

# 🧠 Design Principles

The project follows several important engineering principles:

- Separation of concerns
- Loose coupling
- Single responsibility
- API-first communication
- Event-driven processing
- Stateless authentication
- Centralized API routing
- Containerized deployment
- Externalized configuration
- Cache-aside style caching
- Independent service deployment

---

The current AWS deployment is primarily intended for:
- Architecture experimentation
---

# 🧪 Testing

The project uses Spring Boot testing infrastructure for service-level testing.

Future testing improvements include:

- Unit tests
- Integration tests
- Repository tests
- Kafka integration tests
- Contract tests
- End-to-end payment workflow tests
- Load testing
- Failure/recovery testing

---

# 📦 Deployment Architecture

Current deployment:

```text
AWS EC2
│
├── Nginx
│
├── Angular Frontend
│
├── API Gateway
│
├── Auth Service
│
├── Wallet Service
│
├── Payment Service
│
├── Fraud Service
│
├── Notification Service
│
├── Ledger Service
│
├── Kafka
│
├── ZooKeeper
│
├── Redis
│
└── MySQL
```

All containers communicate using the Docker bridge network:

```text
sentinelpay-network
```

---

# 🔐 Environment & Secrets

Sensitive configuration should never be committed.

The following should remain outside source control:

```text
.env
.env.docker
JWT secrets
Database passwords
Cloud credentials
API keys
Private keys
```

Use environment variables or a dedicated secrets manager for production environments.

---

# 🤝 Contributing

Contributions and suggestions are welcome.

Suggested workflow:

```bash
git checkout -b feature/<feature-name>
```

Make your changes, test them, and create a pull request.

For larger architectural changes, document:

1. Problem
2. Proposed solution
3. Architectural impact
4. Alternatives considered
5. Testing strategy

---

# 📄 License

This project is currently intended as a personal learning and portfolio project.

A formal open-source license can be added if the project is released for external contributions.

---

# 👨‍💻 Author

**Suhail Akhtar**

Software Development Engineer focused on:

- Java
- Spring Boot
- Microservices
- Distributed Systems
- Kafka
- Redis
- Angular
- Cloud & DevOps
- System Design

---

# ⭐ Project Highlights

SentinelPay demonstrates a complete journey from application development to cloud deployment:

```text
Java + Spring Boot
        │
        ▼
Microservices
        │
        ▼
Kafka Event-Driven Architecture
        │
        ▼
Redis Caching
        │
        ▼
Docker
        │
        ▼
Docker Compose
        │
        ▼
AWS EC2
        │
        ▼
Nginx
        │
        ▼
Publicly Accessible Application
```

The primary goal of SentinelPay is to provide a practical implementation of **production-oriented fintech backend engineering and distributed system design**.

---

## 🔒 Security Notice

Do not commit actual credentials to the repository.

Create a safe example file such as `.env.docker.example`:

```env
DB_URL=jdbc:mysql://mysql:3306/sentinelpay
DB_USERNAME=root
DB_PASSWORD=change-me

KAFKA_BOOTSTRAP_SERVERS=kafka:9092

REDIS_HOST=redis
REDIS_PORT=6379

JWT_SECRET=change-me

AUTH_SERVICE_URL=http://auth-service:8081
WALLET_SERVICE_URL=http://wallet-service:8082
PAYMENT_SERVICE_URL=http://payment-service:8083
FRAUD_SERVICE_URL=http://fraud-service:8084
NOTIFICATION_SERVICE_URL=http://notification-service:8085
LEDGER_SERVICE_URL=http://ledger-service:8086
```

If real credentials have ever been pushed to a public repository, rotate them instead of only deleting them from the latest commit.
