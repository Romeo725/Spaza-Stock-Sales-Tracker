# Samsung Innovation Internship 2027 — Project Walkthrough

**Candidate / Author:** Romeo Mangena  
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
1. **Inaccurate Stock Counts:** Sales of fast-moving items (such as milk, bread, maize meal, and soft drinks) were tallied manually at the end of the day. Frequently, notebooks were misplaced or sales entries missed, leading to inventory discrepancies of up to 25% per week.
2. **Stockouts & Lost Revenue:** Mrs. Dlamini often only realised items were out of stock when customers asked for them at the counter, resulting in lost sales and frustrated community patrons walking to competing stores.
3. **Inefficient Restocking:** Without visibility into sales velocity or minimum reorder thresholds, capital was tied up in slow-moving stock while essential staples ran out unexpectedly.

---

## 02. Solution & Architectural Design

We engineered and deployed the **Spaza Stock & Sales Tracker** — a robust, lightweight, full-stack web application tailored specifically for small township retailers.

### Tech Stack
* **Backend:** Spring Boot 3 (Java 21), Spring Security, Spring Data JPA, H2 / MySQL Database.
* **Frontend:** Responsive Single-Page Application built with React 18, Tailwind CSS, and Lucide UI components.
* **Architecture:** RESTful API communicating with a secure backend service layer, featuring atomic transaction processing for point-of-sale inventory deductions.

### Core Features
1. **Real-Time Inventory & Search:** Instant search by product name and category, with clear color-coded badges for *In Stock*, *Low Stock*, and *Out of Stock*.
2. **Point of Sale (POS) Checkout:** Over-the-counter sale recorder that automatically calculates total costs and atomically decrements inventory quantities to prevent overselling.
3. **Smart Restocking Recommendation Engine:** Automatically flags items where `quantity <= reorderLevel` and computes recommended order quantities using the formula:
   $$\text{Recommended Quantity} = \max(\text{Reorder Level} \times 2 - \text{Current Stock}, 1)$$
   along with estimated supplier restocking costs.
4. **Sales Audit Trail & Analytics:** Comprehensive revenue tracking, top-selling product summaries, and cashier audit logs.

---

## 03. Evaluator Test Access & Credentials

To fully satisfy submission criterion #2 ("Full test access"), the application auto-seeds dedicated test accounts and realistic spaza inventory at startup:

| Role | Username | Password | Access Permissions |
| :--- | :--- | :--- | :--- |
| **Owner** | `owner` | `password123` | Full access: Product CRUD, View Inventory, Record Sales, View Smart Restocks, Manage Staff / Reports |
| **Staff** | `staff` | `password123` | Operational access: View Inventory, Record Sales |

### Running & Deploying Locally
1. Clone repository and run with Maven:
   ```bash
   mvn spring-boot:run
   ```
2. Open browser at: `http://localhost:8080`
3. Use the top evaluation banner to instantly switch between Owner and Staff test roles with 1 click.

---

## 04. Customer Feedback & Future Roadmap

### Owner Feedback (Mrs. Dlamini)
> *"Having an app that tells me instantly when milk or sugar is running low without having to count shelves every evening is a game changer. The 1-click restock suggestions help me order exactly what I need from the wholesaler before we run out."*

### What We Would Build Next (Future Roadmap)
1. **Barcode Scanning:** Integration with mobile device cameras and USB barcode scanners for rapid checkout and inventory lookup.
2. **Automated SMS Supplier Alerts:** Direct SMS notifications sent to local wholesalers when critical stock thresholds are breached.
3. **AI-Powered Demand Forecasting:** Machine learning integration (LiteRT) to predict stock depletion based on local payday cycles, weather, and historical sales trends.
