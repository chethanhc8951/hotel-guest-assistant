# Hotel Guest Assistant — Backend

Spring Boot backend for an AI-powered hotel guest assistant.

## Features

* Hotel FAQ and information queries
* AI-powered guest responses
* Conversation context and follow-up questions
* Room availability checking
* Hotel knowledge base
* Validation and error handling
* Fallback responses for unsupported questions
* Automated tests

<img width="1886" height="915" alt="Screenshot 2026-09-16 170300" src="https://github.com/user-attachments/assets/9283eb29-e263-4068-a226-1f574251c3d5" />
<img width="1887" height="912" alt="Screenshot 2026-09-16 170030" src="https://github.com/user-attachments/assets/ac480eea-0337-4123-9e68-e12637ad5bc7" />
<img width="1919" height="918" alt="Screenshot 2026-09-16 165843" src="https://github.com/user-attachments/assets/ed3c8c63-1ef2-436f-8da8-400cdf3b542c" />
<img width="1919" height="952" alt="Screenshot 2026-09-16 171338" src="https://github.com/user-attachments/assets/a35319f3-33fd-4e5d-a602-c6cb78f94477" />
<img width="1918" height="914" alt="Screenshot 2026-09-16 171600" src="https://github.com/user-attachments/assets/3649791b-25c8-4cd0-b68b-f20374d7cf0a" />


## Tech Stack

* Java
* Spring Boot
* Maven
* REST API
* AI / LLM
* JSON Knowledge Base

## Run Locally

```bash
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

## Test

```bash
mvn test
```

## API

```text
POST /api/chat
```

The frontend sends guest questions to the backend, and the backend processes the request using hotel data, AI, conversati
