## Overview

The **Loan Decision Management System (LDMS)** is a simple REST API demonstration of a Spring Boot application that integrates with **Red Hat Decision Manager (RHDM)** running on a **KIE Server** to process loan applications in real time.

Instead of embedding business rules directly into the application code, this project delegates all decision logic to an external **Business Rules Management System (BRMS)**. This approach enables organizations—particularly in the banking sector—to respond rapidly to evolving business requirements without redeploying application code.

---

## Why Outsource Business Rules?

### 1. **The Challenge of Hardcoding Rules**

In many software systems, business rules are hardcoded within application logic. While this may seem convenient initially, it quickly becomes a bottleneck when:

* **Regulatory requirements change** frequently.
* **Management or business analysts** request rule adjustments based on market conditions.
* Changes require **full development cycles**, redeployment, and regression testing—slowing down responsiveness.

Hardcoding rules means **technical teams become the bottleneck for business agility**.

---

### 2. **The Advantage of a BRMS**

A **Business Rules Management System** like Red Hat Decision Manager, TIBCO BusinessEvents, FICO Blaze Advisor, or Camunda DMN allows rules to be:

* **Centrally managed** outside of application code.
* **Modified by non-developers** (business analysts, product managers) using a rule authoring tool.
* **Versioned and tested independently** of the main application.
* **Deployed without redeploying** the core application services.

---

### 3. **Banking Use Case**

Consider a bank’s **loan approval process**:

* Rules depend on **credit scores**, **income levels**, **loan amount ratios**, **regulatory compliance**, and **market risk policies**.
* These rules **change regularly** due to economic trends, interest rate changes, or new compliance requirements.
* Using a BRMS, the bank can **update loan eligibility criteria in hours instead of weeks**, without code changes or downtime.

---

## How This Project Works

### Architecture

1. **Loan Application Service** (Spring Boot)

    * Accepts loan requests via REST API.
    * Sends the request data as a **fact** to the **KIE Server**.

2. **Red Hat Decision Manager (KIE Server)**

    * Hosts and executes Drools rules for loan approval decisions.
    * Returns decision results (status, message) to the Spring Boot service.

3. **Drools Rules** (Deployed on KIE Server)
   Example rules:

   ```java
   rule "Approve Loan"
       when
           $l : LoanApplication(creditScore > 700, income != null && income > 5000, 
                               loanAmount != null && loanAmount < income * 2)
       then
           $l.setStatus("APPROVED");
           $l.setDecision("Loan is approved");
   end
   ```

---

## Endpoints

### Submit Loan Request

**POST** `/api/v1/customers/customerId/loans`
**Request Body**:

```json
{
  "amount": 7500.00,
  "loanType": "BUSINESS"
}
```

**Expected Response**:

```json
{
   "id": 1,
   "amount": 7500.0,
   "customerId": 5,
   "status": "APPROVED",
   "decision": "Loan approved",
   "loanType": "BUSINESS"
}
```

---

## Platforms & Tools

* **Red Hat Decision Manager (KIE Server)** – Business Rules Execution
* **Drools** – Rules Engine part of Decision Manager
* **Spring Boot** – Application Layer
* **Maven** – Build Tool
* **Insomnia / Curl** – API Testing

---

## Benefits Achieved

* **Faster rule changes** — days reduced to hours.
* **Empowered business teams** — analysts manage rules directly.
* **Reduced deployment risk** — rule changes do not require application redeploys.
* **Improved compliance** — rapid adaptation to new regulations.
