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

- **Backend**: Spring Boot 3 (Java 21), Spring Security, Spring Data JPA, H2 / MySQL / PostgreSQL.
- **Frontend**: React 18 and Tailwind CSS, built with Vite and served by Spring Boot.
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

1. **Build the frontend and run the application**:
   ```bash
   cd frontend
   npm ci
   npm run build
   cd ..
   mvn spring-boot:run
   ```
2. **Access the Web Dashboard**:
   Open [http://localhost:8080](http://localhost:8080) and sign in with one of the local test accounts above.

### Frontend development

Start the backend with `mvn spring-boot:run`, then in a second terminal run:

```bash
cd frontend
npm ci
npm run dev
```

Open the Vite URL printed in the terminal. API requests are proxied to the local Spring Boot server.

## ☁️ Render Deployment

The Docker build compiles the React frontend with Vite before packaging it with the Spring Boot application. The root [`render.yaml`](render.yaml) Blueprint provisions the application and persistent PostgreSQL database together. It uses paid Render plans. During the initial Blueprint setup, provide an owner username and a strong password of at least 12 characters when prompted; Render generates the JWT secret. The Spring application serves both the dashboard and API from the same origin.

The public GitHub Pages site is a static landing page only; it does not run the Spring Boot API.
The current Codespaces preview is available at [the running demo](https://scaling-space-barnacle-r6qxxr67wqrfwqj-8080.app.github.dev/) while its Codespace is active. The port is publicly reachable, and app sign-in is still required. For a stable public review link, deploy the application using the Render Blueprint above.

---

## 📂 Project Documentation

- Project Walkthrough & Assignment Report: [`docs/WALKTHROUGH.md`](docs/WALKTHROUGH.md)
- Site Walkthrough Presentation: [Romeo Mduduzi Mangena - Spaza Stock & Sales Tracker Site Guide](docs/Romeo_Mduduzi_Mangena_Spaza_Stock_Sales_Tracker_Site_Guide.pptx)
- Samsung Solution Brief: [Romeo Mduduzi Spaza Stock PDF](docs/Romeo_Mduduzi_Spaza_Stock.pdf)
- Implementation Plan: [`implementation_plan.artifact.md`](.artifacts/d4efcbad-5d9f-4150-9ff8-02e77474b013/implementation_plan.artifact.md)
