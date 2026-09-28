# AMC Bank Day 13 — AI Enabled Java Full Stack Project

This project extends the final AMC Bank Java Full Stack application with a **secured Spring AI banking assistant**.

## Stack

- Java 17
- Spring Boot 3.5.6
- Spring Web / Data JPA / Security
- JWT authentication with ADMIN and CUSTOMER roles
- PostgreSQL
- Spring AI 1.1.0
- OpenAI chat model starter
- React + Vite

## What it adds

- `AiController` — secured `/api/ai/**` endpoints
- `AiService` — Spring AI `ChatClient` integration
- system instructions that prohibit invented live banking data
- request/response DTOs
- React AI Assistant screen for both ADMIN and CUSTOMER logins
- AI is **disabled by default**, so the existing banking application can run without an API key

The AI layer does not replace `BankService`, Spring Security, JPA, PostgreSQL, EMI calculation, withdrawal validation, customer ownership checks, or JWT authorization.

## 1. PostgreSQL

Create the database:

```sql
CREATE DATABASE amc_bank_customer_login;
```

Default classroom configuration expects:

- host: `localhost`
- port: `5432`
- database: `amc_bank`
- username: `postgres`
- password: `root`

You can override username/password with `DB_USERNAME` and `DB_PASSWORD`.

## 2. Run backend without AI first

Import the `backend` folder into Eclipse as **Existing Maven Project**.

Run `AmcBankApplication.java` as a Spring Boot application.

Because `spring.ai.model.chat` defaults to `none`, no OpenAI API key is needed for the banking application to start.

Demo admin login:

- username: `admin`
- password: `admin123`

## 3. Enable Spring AI

Set environment variables before starting Spring Boot.

### Windows Command Prompt

```cmd
set SPRING_AI_MODEL_CHAT=openai
set OPENAI_API_KEY=YOUR_REAL_API_KEY
```

Optional model override:

```cmd
set OPENAI_MODEL=gpt-4o-mini
```

Then restart Spring Boot.

**Never commit a real API key to GitHub or place it in Java source code.**

If AI remains disabled, the AI screen intentionally returns a teaching message explaining which environment variables are required.

## 4. Run React frontend

Open `frontend` in VS Code.

```cmd
npm install
npm run dev
```

Open the Vite URL, normally `http://localhost:5173`.

Login and select **AI Assistant**.

Try:

- `What is EMI and how does it work?`
- `Explain savings account versus current account.`
- `Explain loan principal, interest rate and tenure.`

## 5. API

Status:

```text
GET /api/ai/status
```

Ask:

```text
POST /api/ai/ask
Authorization: Bearer <JWT>
Content-Type: application/json
```

Body:

```json
{
  "question": "What is EMI?"
}
```

Both AI endpoints require an authenticated JWT. They are available to both `ROLE_ADMIN` and `ROLE_CUSTOMER`.

## 6. Architecture

```text
React AI Assistant
       |
       | JWT + JSON
       v
Spring Security
       |
       v
AiController
       |
       v
AiService
       |
       v
Spring AI ChatClient
       |
       v
Configured AI Model

Existing banking operations continue through:
Controller -> BankService -> Repository -> PostgreSQL
```

## 7. Why this version does not let AI perform transactions

This project intentionally starts with a **read-only educational AI assistant**. This makes the security boundary visible to students: AI-generated text is not authority to change banking data.

A later exercise can add controlled `@Tool` methods that call existing Java services. Any such tool should preserve JWT identity, customer ownership checks, validation, transaction boundaries, and audit requirements. Do not give an LLM unrestricted database access.

## 8. Important classroom note

The previous Day 9 project used Spring Boot 3.3.4. This Day 13 edition moves to Spring Boot 3.5.6 so it can use the Spring AI 1.1.x line, which is intended for Spring Boot 3.4/3.5 applications.
