# Samsung Innovation Internship 2027 — Project Walkthrough

**Candidate / Author:** Romeo Mduduzi Mangena
**Project:** Spaza Stock & Sales Tracker  
**Submission Due Date:** 1 December 2026  
**Route:** Route A (Verified Local Establishment Study)  

---

## 01. Establishment & Problem Statement

### The Establishment (Route A)
* **Business Name:** Dlamini General Spaza Shop (Soweto, Johannesburg)
* **Owner:** Mrs. Nomusa Dlamini
* **Nature of Business:** Independent township grocery and household goods retailer servicing daily community needs.

### The Problem
During on-site observation and interviews with Mrs. Dlamini and her shop assistants, we identified a critical operational bottleneck: **manual notebook record-keeping and memory-based stock monitoring**.
1. **Inaccurate Stock Counts:** Sales of fast-moving items (such as milk, bread, maize meal, and soft drinks) were tallied manually at the end of the day. Misplaced notebooks and missed entries made it harder to reconcile physical stock with recorded quantities. Because the weekly discrepancy percentage does not have a reproducible calculation, this report does not quantify the impact.
2. **Stockouts & Lost Revenue:** Mrs. Dlamini often only realised items were out of stock when customers asked for them at the counter, resulting in lost sales and frustrated community patrons walking to competing stores.
3. **Inefficient Restocking:** Without visibility into sales velocity or minimum reorder thresholds, capital was tied up in slow-moving stock while essential staples ran out unexpectedly.

---

## 02. Solution & Architectural Design

We built the **Spaza Stock & Sales Tracker** — a lightweight, full-stack web application tailored to small-retailer workflows.

### Tech Stack
* **Backend:** Spring Boot 3 (Java 21), Spring Security, Spring Data JPA, and a relational database. Local development uses H2; the deployment Blueprint configures PostgreSQL. A MySQL runtime driver is also included.
* **Frontend:** Responsive React 18 single-page application with Tailwind CSS, bundled by Vite for production and served by Spring Boot.
* **Architecture:** REST API with a service layer. Sale processing locks the product row, validates stock, and records the sale and stock deduction in one transaction.

### Core Features
1. **Real-Time Inventory & Search:** Instant search by product name and category, with clear color-coded badges for *In Stock*, *Low Stock*, and *Out of Stock*.
2. **Point of Sale (POS) Checkout:** Over-the-counter sale recorder that automatically calculates total costs and atomically decrements inventory quantities to prevent overselling.
3. **Smart Restocking Recommendation Engine:** Automatically flags items where `quantity <= reorderLevel`. The target quantity is `max(reorderLevel * 2, 10)`; the suggested order quantity is `max(target quantity - current stock, 1)`. Estimated cost is calculated from the product price.
4. **Sales History & Dashboard Summary:** The dashboard shows recorded revenue, sales count, product count, low-stock count, and estimated inventory value. Sales history records transaction details and the staff username.

---

## 03. Evaluator Test Access & Credentials

To fully satisfy submission criterion #2 ("Full test access"), the application auto-seeds dedicated test accounts and realistic spaza inventory at startup:

| Role | Username | Password | Access Permissions |
| :--- | :--- | :--- | :--- |
| **Owner** | `owner` | `password123` | View shop data; create, update, and delete products; restock products; record sales. |
| **Staff** | `staff` | `password123` | View shop data and record sales. |

### Running & Deploying Locally
1. Clone repository and run with Maven:
   ```bash
   cd frontend
   npm ci
   npm run build
   cd ..
   mvn spring-boot:run
   ```
2. Open browser at: `http://localhost:8080`
3. Sign in through the username and password form. To test staff permissions, sign out and sign in with the staff account.

The public GitHub Pages site contains only the static landing page. The current Codespaces preview is available at `https://scaling-space-barnacle-r6qxxr67wqrfwqj-8080.app.github.dev/` while the Codespace is active and requires GitHub account access; use the Render deployment for a stable public review link.

---

## 04. Customer Feedback & Future Roadmap

### Owner Feedback (Mrs. Dlamini)
> *"Having an app that tells me instantly when milk or sugar is running low without having to count shelves every evening is a game changer. The 1-click restock suggestions help me order exactly what I need from the wholesaler before we run out."*

### What We Would Build Next (Future Roadmap)
1. **Barcode Scanning:** Integration with mobile device cameras and USB barcode scanners for rapid checkout and inventory lookup.
2. **Automated SMS Supplier Alerts:** Direct SMS notifications sent to local wholesalers when critical stock thresholds are breached.
3. **AI-Powered Demand Forecasting:** Machine learning integration (LiteRT) to predict stock depletion based on local payday cycles, weather, and historical sales trends.
