# Hotel Guest Assistant — Frontend

Guest-facing frontend application for the **AI-Powered Hotel Guest Assistant**.

The application provides a simple conversational interface where hotel guests can ask questions about the property, rooms, amenities, policies, and room availability.

The frontend communicates with the **Spring Boot backend** through REST APIs. The AI model is not called directly from the browser.

---

## 1. Overview

The frontend allows guests to:

* Ask questions about the hotel
* View assistant responses
* Continue conversations with follow-up questions
* Check room availability
* Provide check-in and check-out dates
* Provide the number of guests
* View availability results
* See loading states while waiting for a response
* See useful error messages when an API request fails
* Use the application on desktop and mobile screens

---

## 2. Technology Stack

* **React**
* **JavaScript**
* **HTML**
* **CSS**
* **Axios / Fetch API**
* **Vite**
* **Spring Boot REST API** — Backend

---

## 3. Application Architecture

```text id="m4o1na"
             Guest
               |
               ↓
        React Frontend
               |
               | HTTP Request
               ↓
        Spring Boot Backend
               |
               ↓
        AI / Hotel Data
               |
               ↓
        Backend Response
               |
               ↓
        React Frontend
               |
               ↓
             Guest
```

The frontend is responsible for the user experience, while the backend handles business logic, hotel data, availability, and AI processing.

---

## 4. Project Structure

Example structure:

```text id="q0j7vf"
frontend/
│
├── src/
│   ├── components/
│   │   ├── Chat/
│   │   ├── Availability/
│   │   └── ...
│   │
│   ├── services/
│   │   └── api.js
│   │
│   ├── App.jsx
│   ├── main.jsx
│   └── ...
│
├── public/
│
├── package.json
├── vite.config.js
└── README.md
```

The exact structure may vary depending on the implementation.

---

## 5. Main Features

### Chat Interface

Guests can enter a question and submit it through the chat interface.

Example:

```text id="p5d4aj"
Guest:
What time is check-in?

Assistant:
Check-in is available from 2:00 PM.
```

---

### Follow-up Questions

The application supports continuing the same conversation.

Example:

```text id="wl3f44"
Guest:
Which room is suitable for three guests?

Assistant:
The Family Room can accommodate up to 4 guests.

Guest:
Does it include breakfast?

Assistant:
Yes, breakfast is included.
```

The conversation ID/context is sent to the backend so the backend can maintain the conversation context.

---

### Room Availability

Guests can provide:

* Check-in date
* Check-out date
* Number of guests

The frontend sends this information to the backend, which performs the availability check.

Example:

```text id="m0z4xh"
Check-in: 20 Sep 2026
Check-out: 22 Sep 2026
Guests: 2

        Check Availability
```

The result is then displayed in a clear format.

---

## 6. Backend Integration

The frontend does **not** call the AI model directly.

```text id="rj6k6x"
React
  ↓
Spring Boot API
  ↓
AI / Knowledge Base / Availability
  ↓
Spring Boot API
  ↓
React
```

This keeps API keys and other sensitive configuration away from the browser.

---

## 7. API Communication

The frontend communicates with the backend using HTTP requests.

Example:

```javascript id="b0vv9r"
const response = await fetch("http://localhost:8080/api/chat", {
    method: "POST",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify({
        message: userMessage,
        conversationId: conversationId
    })
});
```

Update the endpoint above if the actual backend endpoint is different.

---

## 8. Loading State

When the guest submits a question, the UI displays a processing state while waiting for the backend.

Example:

```text id="o7h7z1"
Guest:
Does the hotel have a swimming pool?

Assistant:
Thinking...
```

This prevents the user from thinking that the application has stopped responding.

---

## 9. Error Handling

If the backend or AI service fails, the frontend displays a user-friendly error message.

Example:

```text id="08n9pz"
Sorry, something went wrong.
Please try again.
```

Technical errors should not be displayed directly to the guest.

---

## 10. Responsive Design

The interface is designed to work on:

* Desktop
* Laptop
* Tablet
* Mobile

The chat interface should remain usable on smaller screens without requiring horizontal scrolling.

---

## 11. Running the Frontend

### Prerequisites

Install:

* Node.js
* npm

Check Node.js:

```bash id="pj0g4c"
node -v
```

Check npm:

```bash id="3xv2lh"
npm -v
```

---

### Install Dependencies

Navigate to the frontend directory:

```bash id="1f0s3m"
cd frontend
```

Install dependencies:

```bash id="0e3m7j"
npm install
```

---

### Start Development Server

```bash id="4c4qtc"
npm run dev
```

Vite will provide a local URL, usually similar to:

```text id="e7f4sv"
http://localhost:5173
```

Open the URL in a browser.

---

## 12. Backend Requirement

The Spring Boot backend must be running for the chat and availability features to work.

Example:

```text id="4q7c5u"
Backend:
http://localhost:8080

Frontend:
http://localhost:5173
```

The frontend sends requests from port `5173` to the backend running on port `8080`.

---

## 13. Environment Configuration

Backend/API configuration should not contain sensitive credentials in frontend source code.

If environment variables are used, create a local environment file such as:

```text id="q0r7p4"
.env
```

Example:

```env id="0f9qcm"
VITE_API_BASE_URL=http://localhost:8080
```

Do not commit sensitive API keys or secrets to GitHub.

---

## 14. User Journey

The basic guest journey is:

```text id="1v3qv8"
Open Hotel Assistant
        ↓
Ask a hotel question
        ↓
Frontend sends request
        ↓
Backend processes question
        ↓
AI / Knowledge Base provides answer
        ↓
Assistant response displayed
        ↓
Guest asks follow-up question
        ↓
Conversation continues
        ↓
Guest checks room availability
        ↓
Availability result displayed
```

---

## 15. UX Decisions

The interface is intentionally simple because the main goal is to allow guests to quickly get information without navigating through multiple hotel pages.

The conversational interface allows guests to ask questions using natural language.

Loading and error states provide feedback when the backend takes time to respond or when a dependency fails.

Availability information is collected using structured inputs because dates and guest counts are better handled as structured data rather than relying only on free-form text.

---

## 16. Testing

The frontend should be tested against scenarios such as:

1. Ask a normal hotel question
2. Ask an amenity question
3. Ask a room-related question
4. Ask a policy question
5. Ask a follow-up question
6. Check room availability
7. Submit incomplete availability information
8. Test loading state
9. Test backend/API failure
10. Complete frontend-to-backend flow

The assignment requires evaluation scenarios covering these types of interactions.

---

## 17. Error Scenarios

The frontend handles situations such as:

### Backend unavailable

```text
Unable to connect to the hotel assistant.
Please try again later.
```

### Invalid request

```text
Please provide the required information.
```

### AI/Backend failure

```text
Sorry, I couldn't process your request.
Please try again.
```

### Missing availability information

```text
Please provide your check-in date,
check-out date, and number of guests.
```

---

## 18. Security

The frontend does not contain AI provider API keys.

All AI-related requests are handled through the backend.

```text id="ub7d1h"
Browser
   ↓
Backend
   ↓
AI Provider
```

Instead of:

```text id="w9ydv7"
Browser
   ↓
AI Provider
```

This prevents sensitive backend configuration from being exposed to users.

---

## 19. Future Improvements

Possible production improvements include:

* Better chat history persistence
* Authentication for returning guests
* Streaming AI responses
* Better accessibility support
* Multilingual guest support
* Hotel booking integration
* Rich room cards with images
* Real-time room availability
* Analytics for guest questions
* Improved mobile UX
* Automated frontend end-to-end tests

---

## 20. Related Backend

The frontend communicates with the Spring Boot backend.

```text id="f3s2ka"
hotel-guest-assistant/
│
├── frontend/
│
└── backend/
```

The backend is responsible for:

* AI processing
* Hotel knowledge
* Conversation context
* Availability
* Validation
* Error handling

---

## 21. AI Tools Used

AI-assisted development tools used during the project may include:

* ChatGPT

AI tools were used for development assistance, debugging, implementation guidance, and testing ideas.

---

## 22. Assignment Requirements Covered

The frontend addresses the required guest-facing functionality:

* Conversational interface
* Guest question input
* Assistant responses
* Loading state
* API/AI failure handling
* Follow-up questions
* Check-in date input
* Check-out date input
* Number of guests input
* Availability results
* Responsive interface
* Backend API integration
* No AI API keys exposed in frontend code
