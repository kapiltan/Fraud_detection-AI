🛡️ AI-Powered Real-Time Fraud Detection System

A complete end-to-end fraud detection platform that monitors financial transactions in real time, streams them through a Kafka pipeline, and analyzes risk using a Machine Learning model. Suspicious events are stored, alerted, and visualized through a user-friendly React dashboard.

🔥 Key Highlights

✔ AI-based fraud detection (ML + Anomaly Detection)
✔ Real-time streaming with Apache Kafka
✔ REST APIs for interaction
✔ Modern React UI for transaction monitoring
✔ MongoDB for secure transaction & alert storage
✔ Fully containerized setup using Docker
✔ Modular microservice architecture

🧠 AI Capabilities
Feature	Description
Fraud Scoring Model	ML model trained on historical transaction data (Random Forest + Isolation Forest)
Behavioral Monitoring	Tracks user patterns over time
Anomaly Flags	Sudden location changes, rapid multiple payments
Risk Levels	Low / Medium / High risk classification
Alerts	Sent instantly to alert topic + dashboard notification

Example ML Output:

{
  "transactionId": "abc123",
  "riskScore": 0.92,
  "fraud": true
}

🏗️ Platform Architecture
[React UI]
     ↓ REST
[Spring Boot Producer] → Kafka → [Fraud AI Service (Python)]
                                         ↓
                                Fraud Scoring & Alerts
                                         ↓
                            MongoDB + Kafka Alert Topic
                                         ↓
                               Dashboard Notifications

🚀 Tech Stack
Component	Technology
Backend APIs	Spring Boot (Java 17), Kafka Producer/Consumer
AI Service	Python ML Model (Scikit-Learn, TensorFlow)
Streaming	Apache Kafka + Zookeeper
UI	React + Vite + WebSockets
Database	MongoDB Atlas
DevOps	Docker + Docker Compose
Build Tools	Maven + Node.js
🧪 Core Features
Feature	Status
Transaction creation & monitoring	✔️
AI-driven fraud scoring	✔️
Real-time alerts on dashboard	✔️
Alert history with search	✔️
Multi-user behavior analysis	✔️
Secure MongoDB storage	✔️
📌 REST API Overview
Method	Endpoint	Description
POST	/transactions/create	Create a new transaction
GET	/transactions/all	Get all stored transactions
GET	/alerts	Fetch fraud alerts
GET	/users/{id}/history	Behavioral analysis of a specific user

Example Request:

{
  "userId": "U5",
  "amount": 15000.00,
  "location": "Pune"
}

📊 UI Screens

✔ Submit new transaction
✔ Real-time fraud detection alert panel
✔ Fraud score graph
✔ Transaction history table
✔ User behavior analytics

(Screenshots will be added soon!)

🏃 Run the Project Locally
# Start Kafka & supporting containers
docker compose up -d

# Run Spring Boot backend
mvn spring-boot:run

# Run AI service
python3 ai_service/main.py

# Start React UI
npm install
npm run dev

🔐 MongoDB Schema (sample)
{
  "id": "UUID",
  "userId": "String",
  "amount": "Double",
  "location": "String",
  "timestamp": "LocalDateTime",
  "riskScore": "Double",
  "fraud": "Boolean"
}

📈 Future Enhancements

🚀 Add blockchain-based transaction verification
📲 Mobile app (Android/iOS)
🛰 Geo-fencing anomaly detection
📌 Reports, Analytics & BI dashboards

👨‍💻 Author

Kapil Tanwar
Backend Engineer — Java | Spring Boot | Kafka | AI
