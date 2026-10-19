# Microservices-Based Airline Reservation System

Learning project: React + Spring Boot microservices + H2 databases.

## Features
- View/search available flights
- Create a passenger booking
- View bookings
- Cancel a booking
- Separate Flight Service (port 8081) and Booking Service (port 8082)
- React/Vite frontend (port 5173)
- H2 in-memory databases for simple local setup

> This is a learning/demo project, not a production airline system. It does not include authentication, payment processing, seat locking, or production-grade distributed transactions.

## Requirements
- Java 17 or newer (Java 25 should work with a compatible Spring Boot release)
- Maven 3.9+
- Node.js LTS and npm

## Run the services

Open three terminals from the extracted project folder.

### Terminal 1 — Flight service
```bash
cd backend/flight-service
mvn spring-boot:run
```
Runs at `http://localhost:8081`. H2 console: `http://localhost:8081/h2-console` (JDBC URL `jdbc:h2:mem:flightdb`, user `sa`, blank password).

### Terminal 2 — Booking service
```bash
cd backend/booking-service
mvn spring-boot:run
```
Runs at `http://localhost:8082`. H2 console: `http://localhost:8082/h2-console` (JDBC URL `jdbc:h2:mem:bookingdb`, user `sa`, blank password).

### Terminal 3 — React frontend
```bash
cd frontend
npm install
npm run dev
```
Open `http://localhost:5173`.

The booking service calls the flight service at `http://localhost:8081`. Start the flight service first.

## API endpoints

Flight service:
- `GET /api/flights`
- `GET /api/flights?from=Chennai&to=Delhi`
- `GET /api/flights/{id}`
- `POST /api/flights` (demo/admin: add flight)

Booking service:
- `GET /api/bookings`
- `GET /api/bookings/{id}`
- `POST /api/bookings` JSON: `{"flightId":1,"passengerName":"Asha","passengerEmail":"asha@example.com"}`
- `DELETE /api/bookings/{id}` (cancels booking)

## Troubleshooting
- If Maven says Java version is unsupported, check `java -version` and `mvn -version`; configure Maven to use JDK 17+.
- If a port is occupied, stop the process using 8081/8082/5173 or change the port in the relevant configuration.
- H2 is in-memory: data resets when the service stops. This is intentional for easy demo setup.
