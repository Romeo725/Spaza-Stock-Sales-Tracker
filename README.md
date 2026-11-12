# Spaza Stock & Sales Tracker

A full-stack inventory management, point-of-sale (POS), low-stock alert, and smart restocking platform built for spaza shops and small retailers. Designed and developed as part of the **Samsung Innovation Internship 2027** entry assignment (Route A).

---

## 🚀 Features

- **User Authentication & Role-Based Access Control**: Secure JWT authentication supporting `ROLE_OWNER` and `ROLE_STAFF`.
- **Real-Time Inventory Tracking**: Product catalog with category filtering, instant search, and stock status indicators.
- **Point-of-Sale (POS) Checkout**: Fast transaction recording with automatic inventory stock reduction and balance verification.
- **Smart Restocking Suggestions**: Automated reorder recommendation engine calculating optimal reorder quantities and estimated supplier costs.
- **Sales Analytics & Reports**: Comprehensive revenue summaries, daily sales history, and cashier audit logs.

---

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3 (Java 21), Spring Security, Spring Data JPA, H2 / MySQL.
- **Frontend**: React 18, Tailwind CSS, Single-Page Application served directly by Spring Boot.
- **Build Tool**: Maven.

---

## 🔑 Evaluator Test Access

The application automatically seeds test accounts upon startup:

| Role | Username | Password |
| :--- | :--- | :--- |
| **Owner** | `owner` | `password123` |
| **Staff** | `staff` | `password123` |

---

## 🏃 Quick Start

1. **Clone and Run**:
   ```bash
   mvn spring-boot:run
   ```
2. **Access the Web Dashboard**:
   Open [http://localhost:8080](http://localhost:8080) in your browser. Use the top test access banner to instantly log in as Owner or Staff.

---

## 📂 Project Documentation

- Project Walkthrough & Assignment Report: [`docs/WALKTHROUGH.md`](docs/WALKTHROUGH.md)
- Implementation Plan: [`implementation_plan.artifact.md`](.artifacts/d4efcbad-5d9f-4150-9ff8-02e77474b013/implementation_plan.artifact.md)
