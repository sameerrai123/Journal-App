#  Journal App - Spring Boot Backend

A secure and scalable **Journal Management Backend Application** built using **Spring Boot, MongoDB, and Spring Security**.  
This project allows users to create, manage, and secure their personal journal entries with authentication and authorization.

---

##  Features

###  Authentication & Security
- User registration and login
- Password encryption using BCrypt
- JWT-based authentication
- Role-based authorization
- Secure API endpoints using Spring Security

###  Journal Management
- Create journal entries
- View all journal entries
- Get journal entry by ID
- Update journal entries
- Delete journal entries

###  User Management
- Create users
- Manage user data
- User-specific journal access

###  Other Features
- Environment-based configuration
- MongoDB Atlas integration
- RESTful API design
- Layered architecture
- Exception handling
- API testing with Postman

---

#  Tech Stack

## Backend
- Java 17
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data MongoDB
- Maven

## Database
- MongoDB Atlas

## Tools
- IntelliJ IDEA
- Postman
- Git & GitHub
- Railway (Deployment)

---

#  Project Architecture

```
Client
  |
  | HTTP Requests
  ↓
Controller Layer
  |
  ↓
Service Layer
  |
  ↓
Repository Layer
  |
  ↓
MongoDB Database
```

---

#  Project Structure

```
src/main/java/com/edigest/mysecondproject

├── config
│   └── SecurityConfig.java
│
├── controller
│   ├── PublicController.java
│   ├── UserController.java
│   └── JournalEntryController.java
│
├── entity
│   ├── User.java
│   └── JournalEntry.java
│
├── repository
│   ├── UserRepository.java
│   └── JournalEntryRepository.java
│
├── service
│   ├── UserService.java
│   └── JournalEntryService.java
│
└── security
    ├── JwtFilter.java
    └── JwtUtil.java
```

---

#  API Endpoints

Base URL:

```
/journal
```

## Health Check

### GET

```
/public/health-check
```

Response:

```json
"OK"
```

---

#  User APIs

## Create User

### POST

```
/public/create-user
```

Request Body:

```json
{
    "userName": "sameer",
    "password": "123456"
}
```

Response:

```
User created successfully
```

---

#  Journal APIs

## Create Journal Entry

### POST

```
/journal
```

Request:

```json
{
    "title": "Learning Spring Boot",
    "content": "Today I learned REST APIs"
}
```

---

## Get All Journal Entries

### GET

```
/journal
```

---

## Get Journal By ID

### GET

```
/journal/{id}
```

---

## Update Journal Entry

### PUT

```
/journal/{id}
```

---

## Delete Journal Entry

### DELETE

```
/journal/{id}
```

---

# ⚙️ Installation & Setup

## 1. Clone Repository

```bash
git clone https://github.com/yourusername/journal-app.git
```

## 2. Navigate to Project

```bash
cd journal-app
```

## 3. Configure Environment Variables

Create `application-prod.yml`:

```yaml
spring:
  data:
    mongodb:
      uri: YOUR_MONGODB_URI
```

---

## 4. Run Application

Using Maven:

```bash
mvn spring-boot:run
```

Application runs on:

```
http://localhost:8080
```

---

#  Deployment

The application is deployed on:

```
Railway
```

Production URL:

```
https://journal-app-production-cc4b.up.railway.app
```

---

#  Future Improvements

- Refresh token implementation
- Email verification
- Forgot password functionality
- Pagination and sorting
- Docker deployment
- Redis caching
- Frontend integration

---

#  Author

**Sameer Rai**

B.Tech Information Technology  
Backend Developer | Java | Spring Boot | MongoDB

---

 If you find this project useful, consider giving it a star!
