<p align="center">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/spring%20boot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white" />
  <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white" />
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" />
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" />
  <img src="https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white" />
  <img src="https://img.shields.io/badge/Grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white" />
  <img src="https://img.shields.io/badge/Apache%20Kafka-FF6A00?style=for-the-badge&logo=apachekafka&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring%20Actuator-6DB33F?style=for-the-badge&logo=spring&logoColor=white" />
  <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" />
  <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white" />
  <img src="https://img.shields.io/badge/SonarQube-4E9BCD?style=for-the-badge&logo=sonarqube&logoColor=white" />
  <img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white" />

</p>

# E-Ballot UK

E-Ballot UK is a web-based election simulator that demonstrates a First-Past-The-Post voting system. It is a personal project exploring how online voting could improve accessibility for users who face challenges voting in person due to address changes, constituency mismatches, or practical issues such as travel distance, registration problems, or postal voting delays.

**Disclaimer**: This project is for educational and demonstration purposes only and is not intended for real-world electoral use. The UI is currently under development.


## Features
- First-Past-The-Post voting system simulation 
- Multistep user verification (email, poll card reference, National Insurance number, name, address, date of birth)
- Constituency-based voting eligibility 
- One vote per user enforcement 
- Session timeout with automatic logout after 15 minutes

## Business Requirements
- Each user must only be allowed to vote once per election 
- Users must only vote within their assigned constituency 
- Identity must be verified before voting access is granted 
- Voting sessions must expire after a fixed time period for security 
- System must prevent duplicate, fraudulent, or unauthorised votes

## Technologies
- Java 21
- Spring Boot 3 (Web)
- Spring Data JPA, MySQL
- Spring Boot Actuator
- JUnit, Mockito, MockMvc
- Swagger (OpenAPI)
- API documentation
- SLF4J (logging)
- Prometheus (metrics monitoring)
- Grafana (metrics visualisation)
- Postman (API and performance testing)
- SonarQube (code quality analysis)
- Maven

---

## Architectural Design
This project is built using a layered monolithic architecture, chosen to keep the system simple, maintainable, and easy to extend while preserving clear separation of concerns between components.

<img src="https://res.cloudinary.com/dphavvlgs/image/upload/v1786626092/Container-eballot_nwqo6y.png" width="600" alt="eBallot">)

## Sequence Diagram
This sequence diagram shows the step-by-step process of user authentication and vote casting within the system.

[View Diagram]()

## Logging & Observability
- Critical actions logged with SLF4J
- Supports debugging, auditing, and transparency
- Prometheus and Grafana are used for monitoring and visualisation of system metrics


---

## API Documentation

The services are documented using Swagger (OpenAPI). To access the API documentation locally, run the application and navigate to: http://localhost:8080/swagger-ui/index.html

## Testing
This project is also being actively checked with Sonarqube integrated in the developer's IDE. An analysis with SonarQube to would reveal the following:

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

---

## Author
@reicraftscodes
