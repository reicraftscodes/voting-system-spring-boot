# Online UK Voting System
UK-style election simulator demonstrating a first-past-the-post voting system. Features voter registration, candidate management, vote casting, and result tallying. Built with Spring Boot, this project highlights data handling, application logic, and system workflow implementation.

## Technologies
This project uses a number of tools and frameworks to work properly:
- Java, MySQL
- Spring Boot 3 (Web, Actuator)
- Spring Data JPA
- Maven
- Lombok
- JUnit, Mockito, MockMVC
- Swagger (OpenAPI)
- SLF4J (logging)
- Postman (API & performance testing)

## Testing
The services Voting, UserDetails, PartyList are unit-tested with JUnit, Mockito, and MockMVC.

This project is also being actively checked with SonarLint integrated in the developer's IDE. An analysis with SonarQube to would reveal the following:

![SonarQube](https://res.cloudinary.com/dvwxun4vh/image/upload/v1768582564/sonarqube_wasubl.png)


## API Documentation
The services below have their own Swagger OpenAPI specifications, in order access the API Docs locally copy and paste the following url http://localhost:8080/swagger-ui/index.html

![Swagger](https://res.cloudinary.com/dvwxun4vh/image/upload/v1768582221/Swagger_sauuxo.png)


#### User Details API
| Endpoint                    | Method | Parameter          | Type          | Description                                |
| --------------------------- | ------ | ------------------ | ------------- | ------------------------------------------ |
| `/api/v1/users`             | POST   | `userDetails`      | Object (JSON) | User personal details to create a new user |
| `/api/v1/users/{id}`        | GET    | `id`               | Integer       | ID of the user to fetch details            |
| `/api/v1/users/update/{id}` | PATCH  | `id`               | Integer       | ID of the user to update                   |
| `/api/v1/users/update/{id}` | PATCH  | `updateDetailsDto` | Object (JSON) | Fields to update for the user              |


#### UK Party List API
| Endpoint                 | Method | Parameter   | Type          | Description                  |
| ------------------------ | ------ | ----------- | ------------- | ---------------------------- |
| `/api/v1/uk/parties`     | POST   | `partyList` | Object (JSON) | Create a new political party |
| `/api/v1/uk/parties/all` | GET    | —           | —             | Get all party members        |

#### Voting API
| Endpoint                         | Method | Parameter | Type          | Description                          |
| -------------------------------- | ------ | --------- | ------------- | ------------------------------------ |
| `/api/v1/voting`                 | POST   | `request` | Object (JSON) | Data required to cast a vote         |
| `/api/v1/voting/receipts`        | GET    | —         | —             | Fetch all voting receipts            |
| `/api/v1/voting/count`           | GET    | —         | Integer       | Get total number of votes            |
| `/api/v1/voting/party/{partyId}` | GET    | `partyId` | Integer       | Get total votes for a specific party |


## Performance Testing
This is a simple test scenario to learn how APIs behave under load. We simulate multiple users casting votes at the same time to understand. 
Note: focus is on understanding API performance basics rather than achieving high-scale testing.

Cast Vote API `/api/v1/voting`

* How fast the API responds
* How the system handles concurrent requests
* What happens if many users vote at once

**Setup**

* Initial load: 100 requests almost simultaneously
* Ramp-up: Gradually increase by 25 users
* Goal: Observe response times, successful votes, and any errors

**Example Results**

![Performance Testing](https://res.cloudinary.com/dvwxun4vh/image/upload/v1768584967/2026-01-16_17_35_38-Cast_Vote_Perf_Test_-_Laurate_May_s_Workspace_iqa3af.png)
* Total requests sent: 400
* Requests/sec: 133.33
* Average response time: 23 ms
* P90 response time: 30 ms
* P95 response time: 34 ms
* P99 response time: 41 ms
* Error %: 0.00%
* Failure %: 0.00%


## Cloud & Deployment

Amazon Web Services (AWS)
- Amazon RDS / Aurora (database)
- Elastic Beanstalk (application deployment)

## Disclaimer
It is not intended for real-world election or production use.

## Author
@reicraftscodes
