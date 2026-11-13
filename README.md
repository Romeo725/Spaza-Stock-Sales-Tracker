# Spaza Stock & Sales Tracker

A full-stack inventory management, point-of-sale (POS), low-stock alert, and smart restocking platform built for spaza shops and small retailers. Designed and developed as part of the **Samsung Innovation Internship 2027** entry assignment (Route A).

**[View the standalone landing page](src/main/resources/static/landing.html)**

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

## 🔑 Local Test Accounts

The default (local development) profile seeds test accounts on startup:

| Role | Username | Password |
| :--- | :--- | :--- |
| **Owner** | `owner` | `password123` |
| **Staff** | `staff` | `password123` |

These credentials are for local testing only. The production profile does not seed demo accounts or sample sales. Production creates its initial owner from the private `APP_BOOTSTRAP_OWNER_USERNAME` and `APP_BOOTSTRAP_OWNER_PASSWORD` environment variables.

---

## 🏃 Quick Start

1. **Clone and Run**:
   ```bash
   mvn spring-boot:run
   ```
2. **Access the Web Dashboard**:
   Open [http://localhost:8080](http://localhost:8080) and sign in with one of the local test accounts above.

## ☁️ Render Deployment

The root [`render.yaml`](render.yaml) Blueprint provisions the Spring Boot application and persistent PostgreSQL database together. It uses paid Render plans. During the initial Blueprint setup, provide an owner username and a strong password of at least 12 characters when prompted; Render generates the JWT secret. The Spring application serves both the dashboard and API from the same origin.

The public GitHub Pages site is a static landing page only; it does not run the Spring Boot API.

---

## 📂 Project Documentation

- Project Walkthrough & Assignment Report: [`docs/WALKTHROUGH.md`](docs/WALKTHROUGH.md)
- Implementation Plan: [`implementation_plan.artifact.md`](.artifacts/d4efcbad-5d9f-4150-9ff8-02e77474b013/implementation_plan.artifact.md)
