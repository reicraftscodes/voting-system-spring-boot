# Online UK Voting System

UK-style election simulator demonstrating a first-past-the-post voting system. Features voter registration, candidate management, vote casting, and result tallying. Built with Spring Boot, this project highlights backend architecture, data handling, application logic, and system workflow implementation.

## Technologies
- Java, MySQL
- Spring Boot 3 (Web)
- Spring Data JPA
- JUnit, Mockito, MockMVC
- Swagger (OpenAPI)
- Api documentation
- SLF4J (logging)
- Postman (API & performance testing)
- SonarQube (code quality analysis)
- Maven

---

## Architectural Design
This project is also being actively checked with Sonarqube integrated in the developer's IDE. An analysis with SonarQube to would reveal the following:

The backend follows a **layered architecture**:

```
Controller → Service Interface → Service Implementation → Repository → Database
```

### Layers

1. **Controller Layer**
    - Handles HTTP requests/responses
    - Delegates business logic to the service layer
    - Examples: `VotingController`, `UserDetailsController`, `PartyListController`

2. **Service Layer (Interface + Implementation)**
    - Interfaces define available operations (`VotingService`, `UserDetailsService`, `PartyListService`)
    - Implementations (`VotingServiceImpl`, etc.) contain **business logic**, validation, and repository calls
    - Benefits: abstraction, testability, and flexibility for future extensions

3. **Repository Layer**
    - Handles database interactions via Spring Data JPA
    - Examples: `VotingRepository`, `UserDetailsRepository`, `PartyListRepository`
    - Custom queries used for aggregate functions, like total votes per party

---

## Key Backend Design Decisions

### Idempotent Vote Casting
- Each user can vote only once (`user_details_id` is unique in `voting` table)
- Application and database validation prevent duplicates
- Guarantees safe concurrent operations

> “Idempotency is enforced through validation and unique constraints, mimicking production-ready transaction safety.”

### Voter Eligibility & Validation
- Only users **18+** can vote
- Validated against **National Insurance Number** and **Last Name**
- Custom exceptions for meaningful feedback:
    - `DuplicateResourceException`
    - `IneligibleVoterException`
    - `InvalidRequestException`

> “Validation is separated from persistence for clear error handling and maintainable code.”

### Vote Counting & Data Types
- Vote counts use **Long** to safely support large-scale elections
- Aggregation handled efficiently in the service layer

> “Using Long ensures the system can scale to millions of votes without overflow.”

### REST API Design
- `/api/v1/voting` → cast votes, get receipts, retrieve totals
- `/api/v1/users` → manage voters (CRUD)
- `/api/v1/uk/parties` → manage party lists

### Logging & Observability
- Critical actions logged with SLF4J
- Supports debugging, auditing, and transparency

---

## API Documentation
The services below have their own Swagger OpenAPI specifications, in order access the API Docs locally copy and paste the following url http://localhost:8080/swagger-ui/index.html


![Swagger](https://res.cloudinary.com/dvwxun4vh/image/upload/v1768582221/Swagger_sauuxo.png)

### Base URL
```
http://localhost:8080/api/v1
```

#### User Details API

| Endpoint                     | Method | Parameter          | Type          | Description                               |
| ---------------------------- | ------ | ----------------- | ------------- | ----------------------------------------- |
| `/users`                     | POST   | `userDetails`      | Object (JSON) | Create a new user with personal details   |
| `/users/{id}`                | GET    | `id`               | Integer       | Retrieve a user’s personal details        |
| `/users/update/{id}`         | PATCH  | `id`               | Integer       | Update an existing user’s details         |
| `/users/update/{id}`         | PATCH  | `updateDetailsDto` | Object (JSON) | Fields to update for the user             |

**Example JSON for creating a user:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "dateOfBirth": "1990-01-01",
  "nationalInsuranceNumber": "AB123456C"
}
```

---

#### UK Party List API

| Endpoint                  | Method | Parameter   | Type          | Description                  |
| ------------------------- | ------ | ----------- | ------------- | ---------------------------- |
| `/uk/parties`             | POST   | `partyList` | Object (JSON) | Create a new political party |
| `/uk/parties/all`         | GET    | —           | —             | Retrieve all party members   |

**Example JSON for creating a party:**
```json
{
  "partyName": "Example Party",
  "position": "Leader"
}
```

---
## Performance Testing
This is a simple test scenario to learn how APIs behave under load. We simulate multiple users casting votes at the same time to understand. 

Note: focus is on understanding API performance basics rather than achieving high-scale testing.

#### Voting API

| Endpoint                     | Method | Parameter | Type          | Description                          |
| ---------------------------- | ------ | --------- | ------------- | ------------------------------------ |
| `/voting`                    | POST   | `request` | Object (JSON) | Cast a vote for a party               |
| `/voting/receipts`           | GET    | —         | —             | Fetch all voting receipts            |
| `/voting/count`              | GET    | —         | Long          | Get total number of votes            |
| `/voting/party/{partyId}`    | GET    | `partyId` | Integer       | Get total votes for a specific party |

**Example JSON for casting a vote:**
```json
{
  "nationalInsuranceNumber": "AB123456C",
  "lastName": "Doe",
  "partyId": 1
}
```

---

## Testing
This project is also being actively checked with Sonarqube integrated in the developer's IDE. An analysis with SonarQube to would reveal the following:

![SonarQube](https://res.cloudinary.com/dvwxun4vh/image/upload/v1768582564/sonarqube_wasubl.png)


## Performance Testing
![Performance Testing](https://res.cloudinary.com/dvwxun4vh/image/upload/v1768584967/2026-01-16_17_35_38-Cast_Vote_Perf_Test_-_Laurate_May_s_Workspace_iqa3af.png)
- 
- Simulated multiple users casting votes concurrently
- Monitored API response times and errors

**Example Metrics:**
- Requests/sec: 133.33
  - Average response time: 23 ms
  - P90 response time: 30 ms
  - Error %: 0.00%

## Cloud & Deployment
- AWS RDS / Aurora for database
- AWS Elastic Beanstalk for deployment

## Disclaimer
This project is for **educational purposes only** and not suitable for real elections.

## Author
@reicraftscodes
