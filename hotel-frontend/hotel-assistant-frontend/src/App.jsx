import { useState } from "react";
import axios from "axios";
import "./App.css";

function App() {
  const [messages, setMessages] = useState([
    {
      sender: "bot",
      text: "Hello! Welcome to Grand Stay Hotel. How can I help you?",
    },
  ]);

  const [input, setInput] = useState("");
  const [loading, setLoading] = useState(false);

  const [conversationId, setConversationId] = useState(null);

  const [showAvailability, setShowAvailability] = useState(false);

  const [checkIn, setCheckIn] = useState("");
  const [checkOut, setCheckOut] = useState("");
  const [adults, setAdults] = useState(1);

  const sendMessage = async (messageText = input) => {
    if (!messageText.trim() || loading) {
      return;
    }

    const userMessage = {
      sender: "user",
      text: messageText,
    };

    setMessages((previous) => [...previous, userMessage]);

    setInput("");
    setLoading(true);

    try {
      const response = await axios.post("http://localhost:8080/api/chat", {
        message: messageText,
        conversationId: conversationId,
      });

      const data = response.data;

      setConversationId(data.conversationId);

      const botMessage = {
        sender: "bot",
        text: data.message,
      };

      setMessages((previous) => [...previous, botMessage]);
    } catch (error) {
      const errorMessage = {
        sender: "bot",
        text: "Sorry, something went wrong. Please try again.",
      };

      setMessages((previous) => [...previous, errorMessage]);
    } finally {
      setLoading(false);
    }
  };

  const checkAvailability = async () => {
    if (!checkIn || !checkOut || !adults) {
      return;
    }

    setLoading(true);

    const userMessage = {
      sender: "user",
      text: `Check availability from ${checkIn} to ${checkOut} for ${adults} adult(s).`,
    };

    setMessages((previous) => [...previous, userMessage]);

    try {
      const response = await axios.post("http://localhost:8080/api/chat", {
        message: "Check room availability",
        conversationId: conversationId,
        checkIn: checkIn,
        checkOut: checkOut,
        adults: Number(adults),
      });

      const data = response.data;

      setConversationId(data.conversationId);

      setMessages((previous) => [
        ...previous,
        {
          sender: "bot",
          text: data.message,
        },
      ]);

      setShowAvailability(false);
    } catch (error) {
      setMessages((previous) => [
        ...previous,
        {
          sender: "bot",
          text: "Sorry, I couldn't check availability right now.",
        },
      ]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app">
      <div className="chat-container">
        <div className="chat-header">
          <h2>Grand Stay Hotel</h2>
          <p>Guest Assistant</p>
        </div>

        <div className="messages">
          {messages.map((message, index) => (
            <div key={index} className={`message ${message.sender}`}>
              {message.text}
            </div>
          ))}

          {loading && <div className="message bot">Thinking...</div>}
        </div>

        <div className="suggestions">
          <button onClick={() => sendMessage("What time is check-in?")}>
            Check-in time
          </button>

          <button onClick={() => sendMessage("Is breakfast included?")}>
            Breakfast
          </button>

          <button
            onClick={() => sendMessage("Does the hotel have a swimming pool?")}
          >
            Swimming pool
          </button>

          <button
            onClick={() => sendMessage("What is the cancellation policy?")}
          >
            Cancellation
          </button>

          <button onClick={() => setShowAvailability(true)}>
            Check availability
          </button>
        </div>

        {showAvailability && (
          <div className="availability-form">
            <h3>Check Room Availability</h3>

            <label>
              Check-in
              <input
                type="date"
                value={checkIn}
                onChange={(e) => setCheckIn(e.target.value)}
              />
            </label>

            <label>
              Check-out
              <input
                type="date"
                value={checkOut}
                onChange={(e) => setCheckOut(e.target.value)}
              />
            </label>

            <label>
              Adults
              <input
                type="number"
                min="1"
                value={adults}
                onChange={(e) => setAdults(e.target.value)}
              />
            </label>

            <div className="availability-actions">
              <button onClick={checkAvailability} disabled={loading}>
                Check Availability
              </button>

              <button onClick={() => setShowAvailability(false)}>Cancel</button>
            </div>
          </div>
        )}

        <div className="input-area">
          <input
            type="text"
            value={input}
            placeholder="Ask something about the hotel..."
            onChange={(e) => setInput(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                sendMessage();
              }
            }}
          />

          <button onClick={() => sendMessage()} disabled={loading}>
            Send
          </button>
        </div>
      </div>
    </div>
  );
}

export default App;
