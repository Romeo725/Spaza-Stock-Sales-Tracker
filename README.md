# Spaza Stock & Sales Tracker

A full-stack inventory management, point-of-sale (POS), low-stock alert, and smart restocking platform built for spaza shops and small retailers. Designed and developed as part of the **Samsung Innovation Internship 2027** entry assignment (Route A).

---

## 🌐 Live Deployment Links

- **GitHub Codespaces Live App**: [https://scaling-space-barnacle-r6qxxr67wqrfwqj-8080.app.github.dev/](https://scaling-space-barnacle-r6qxxr67wqrfwqj-8080.app.github.dev/)
- **GitHub Pages Landing Page**: [https://romeo725.github.io/Spaza-Stock-Sales-Tracker/landing.html](https://romeo725.github.io/Spaza-Stock-Sales-Tracker/landing.html)
- **GitHub Repository**: [https://github.com/Romeo725/Spaza-Stock-Sales-Tracker](https://github.com/Romeo725/Spaza-Stock-Sales-Tracker)

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
- **Frontend**: React 18, Tailwind CSS, Single-Page Application served directly by Spring Boot & GitHub Pages.
- **Build Tool**: Maven.

---

## 🔑 Evaluator Test Access

The application automatically seeds test accounts upon startup:

| Role | Username | Password |
| :--- | :--- | :--- |
| **Owner** | `owner` | `password123` |
| **Staff** | `staff` | `password123` |

---

## 📂 Project Documentation

- Project Walkthrough & Assignment Report: [`docs/WALKTHROUGH.md`](docs/WALKTHROUGH.md)
- Complete Project Documentation (PDF): `Spaza_Stock_Sales_Tracker_Complete_Documentation.pdf`
