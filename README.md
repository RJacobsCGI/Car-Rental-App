# Car Rental App
This is a Spring Boot application for managing car rental bookings.
It allows users to confirm and view booking details for rental cars.
The service enforces age and license requirements and supports multiple car segments.

## Prerequisites
- Java 17+
- Maven
- IntelliJ IDEA (optional, for running via `spring-boot:run`)
- Postman or curl (for testing API endpoints)

## Running the Application

### Using IntelliJ IDEA:
1. Open the project in IntelliJ.
2. Open the Maven tool window (View -> Tool Windows -> Maven).
3. Navigate to Car-Rental-App -> Plugins -> spring-boot -> spring-boot:run.

You can also create a Run Configuration for it:
1. Go to Run -> Edit Configurations.
2. Click + -> Maven.
3. Working directory: Project root
4. Command line: spring-boot:run

### Using Maven in the project root:
```bash
./mvnw spring-boot:run
```


## Base URL
http://localhost:8080

## Swagger
http://localhost:8080/swagger-ui/index.html

---

## Endpoints

### 1. Get Booking Details

- **Endpoint:** `GET /booking/{id}`
- **Description:** Retrieve booking details by booking ID.
- **Path Parameter:**
  - `id` (UUID) – The unique identifier of the booking.
- **Response:** JSON object containing booking details.

**Example Request:**

```bash
curl -X GET http://localhost:8080/booking/123e4567-e89b-12d3-a456-426614174000
```

### 2. Confirm a Booking

- **Endpoint:** `POST /booking/confirm`
- **Description:** Confirm a new booking.
- **Request Body:**
```
{
  "drivingLicenseNumber": "ABC123456",
  "customerAge": 30,
  "reservationStart": "2025-01-01",
  "reservationEnd": "2025-01-10",
  "carSegment": "MEDIUM"
}
```
- **Response:** Returns the booking UUID and sets the Location header to the booking URL.

**Example Request:**

```bash
curl -X POST http://localhost:8080/booking/confirm \
     -H "Content-Type: application/json" \
     -d '{
           "drivingLicenseNumber": "ABC123456",
           "customerAge": 30,
           "reservationStart": "2025-01-01",
           "reservationEnd": "2025-01-10",
           "carSegment": "MEDIUM"
         }'
```
