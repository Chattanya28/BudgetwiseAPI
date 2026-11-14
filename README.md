# 💰 BudgetWise API

A secure, role-based REST API built with Spring Boot to track daily expenses, manage monthly budgets, and receive smart alerts before overspending.

## 📖 Overview
BudgetWise is a backend service designed to help users manage their personal finances. Users can set category-wise monthly budgets, log their daily expenses, and get real-time warnings (e.g., 80% threshold) when they are close to exceeding their budget. 

## 🛠️ Tech Stack
- **Language:** Java 17 (or 24)
- **Framework:** Spring Boot (v3.x)
- **Security:** Spring Security + JWT (JSON Web Tokens)
- **Data Access:** Spring Data JPA (Hibernate)
- **Database:** H2 (In-memory for dev/testing)
- **Validation:** Spring Boot Starter Validation
- **Build Tool:** Maven
- **Utilities:** Lombok (Boilerplate reduction)

## 🏗️ Architecture & Flow

The application follows a standard Layered Architecture to ensure separation of concerns.

```text
[ Client (Postman/Browser) ]
           │
           ▼
┌────────────────────────────────────────────────────────┐
│                   Spring Security Filter Chain         │
│  (JwtFilter checks Bearer Token for protected routes)  │
└────────────────────────────────────────────────────────┘
           │
           ▼
┌────────────────────────────────────────────────────────┐
│                     Controller Layer                   │
│    (AuthController, BudgetWiseController, etc.)        │
│        - Handles HTTP requests & validations           │
└────────────────────────────────────────────────────────┘
           │
           ▼
┌────────────────────────────────────────────────────────┐
│                      Service Layer                     │
│      (AuthService, ExpenseService, BudgetService)      │
│   - Core Business Logic (e.g., 80% Alert Calculation)  │
└────────────────────────────────────────────────────────┘
           │
           ▼
┌────────────────────────────────────────────────────────┐
│                    Repository Layer                    │
│         (UserRepository, ExpenseRepository)            │
│            - Spring Data JPA Interfaces                │
│         - Custom JPQL Aggregation Queries              │
└────────────────────────────────────────────────────────┘
           │
           ▼
┌────────────────────────────────────────────────────────┐
│                       Database                         │
│                    (H2 Database)                       │
└────────────────────────────────────────────────────────┘
