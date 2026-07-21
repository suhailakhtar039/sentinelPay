# SentinelPay

## Production-Grade FinTech Payment Platform

SentinelPay is a production-oriented digital payment platform built using modern Java microservices and Angular. The project is designed to simulate the architecture and engineering practices used in real-world fintech companies such as Stripe, Razorpay, and PayPal.

The focus of this project is not only implementing payment features but also learning enterprise software architecture, distributed systems, scalability, security, and event-driven design.

---

# Tech Stack

## Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* MySQL
* Apache Kafka
* Redis
* Spring Cloud Gateway
* JWT Authentication
* Maven

## Frontend

* Angular 21 (Standalone Components)
* Angular Material
* Tailwind CSS v4
* RxJS
* Reactive Forms
* Functional HTTP Interceptors

---

# Microservices

* API Gateway
* Auth Service
* Wallet Service
* Payment Service
* Ledger Service
* Fraud Service
* Analytics Service (In Progress)
* Notification Service (Planned)

---

# System Architecture

```text
                Angular Application
                        │
                        ▼
                 Spring Cloud Gateway
                        │
 ┌───────────────┬───────────────┬───────────────┐
 │               │               │               │
 ▼               ▼               ▼               ▼
Auth        Wallet        Payment        Fraud
 Service      Service       Service       Service
                  │               │
                  ▼               ▼
             Ledger Service   Kafka Events
```

---

# Implemented Features

## Authentication

* User Registration
* User Login
* JWT Authentication
* JWT Validation
* Spring Security
* Role-based Authorization
* API Gateway Authentication Filter
* User ID propagation across services

---

## Production Logout

Implemented secure logout using Redis-backed JWT blacklisting.

### Flow

```
Client
    │
POST /logout
    │
Extract JWT
    │
Extract JTI
    │
Calculate Remaining Expiry
    │
Redis
blacklist:<jti>
    │
TTL = Remaining Token Lifetime
```

Every authenticated request validates:

* JWT Signature
* Expiration
* Username
* Redis Blacklist

---

## Wallet

* Wallet Creation
* Wallet Balance
* Wallet Top-up
* Redis Caching
* Configurable TTL

Cache Key Format

```
wallet::<userId>
```

---

## Payments

Implemented asynchronous payment processing using Kafka.

### Payment Flow

```
Payment Service
        │
        ▼
PaymentInitiatedEvent
        │
        ▼
Fraud Service
        │
        ▼
FraudApprovedEvent
        │
        ▼
Wallet Service
        │
        ▼
PaymentCompletedEvent
        │
        ▼
Ledger Service
```

Features

* Payment Validation
* Balance Validation
* Asynchronous Processing
* Event-Driven Architecture
* Transaction History

---

## Ledger

* Automatic Ledger Entries
* Transaction History
* Material Table
* Pagination
* Sorting
* Transaction Detail Dialog

---

## Dashboard

Current Dashboard includes:

* Wallet Balance
* Total Payments
* Successful Payments
* Failed Payments
* Recent Payments

Currently, dashboard data is aggregated on the frontend using `forkJoin()`. The next phase migrates this aggregation to a dedicated Analytics Service.

---

## Angular Features

* Standalone Components
* Angular Material
* Reactive Forms
* Functional Interceptors
* Route Guards
* Session Management
* Snackbar Notifications
* Profile Management
* Change Password

---

# Redis Integration

Redis is currently used for:

* Wallet Cache
* JWT Blacklisting

Future plans include:

* Analytics Caching
* Dashboard Caching
* Frequently Accessed User Data
* Distributed Rate Limiting

---

# Event-Driven Architecture

Apache Kafka powers asynchronous communication between services.

Current Events:

* PaymentInitiatedEvent
* FraudApprovedEvent
* PaymentCompletedEvent
* UserRegisteredEvent

---

# Current Project Status

## Completed

* API Gateway
* Authentication
* JWT Security
* Redis Integration
* Wallet Module
* Payment Module
* Ledger Module
* Fraud Flow
* Angular Dashboard
* Angular Authentication
* Profile Module
* Session Management

---

## Currently Working On

### Phase 22.3 — Analytics Dashboard

Goals:

* Dedicated Analytics Service
* Dashboard Aggregation API
* ApexCharts Integration
* Monthly Payment Volume
* Payment Status Distribution
* Daily Transactions
* Top Receivers
* Average Transaction Amount

---

# Planned Features

* Notification Service
* Docker
* Kubernetes
* OpenTelemetry
* Prometheus
* Grafana
* ELK Stack
* Distributed Tracing
* Circuit Breakers
* Resilience4j
* Saga Pattern
* Outbox Pattern
* CI/CD Pipeline
* API Documentation
* Integration Testing
* Load Testing
* Cloud Deployment

---

# Learning Objectives

This project focuses on mastering:

* Enterprise Java Development
* Microservices Architecture
* Distributed Systems
* Event-Driven Design
* Production Security
* High-Performance Backend Engineering
* Modern Angular Development
* Scalable System Design
* FinTech Architecture

---

# Vision

The goal is to build SentinelPay into a production-grade fintech platform that demonstrates the architecture, scalability, security, and engineering practices expected in modern payment systems.
