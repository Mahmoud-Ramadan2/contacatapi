# Contact API

This is a Spring Boot application that provides a simple contact management system. It allows users to register, manage contacts, and perform CRUD (Create, Read, Update, Delete) operations on contacts. The application includes JWT-based authentication, password hashing, and features like pagination and sorting for retrieving contacts.

## Features
- **User Registration and Authentication**: Secure user authentication with JWT tokens.
- **CRUD Operations**: Users can create, read, and delete their contacts.
- **Address Book**: Manage contacts with fields such as name, email, phone number, etc.
- **Hash passwords and store them securely**
- **Pagination and Sorting**: Supports pagination and sorting for the `GET /api/contacts` endpoint.
- **Swagger UI for API testing**

## Prerequisites
Before you begin, ensure you have the following installed on your system:
-Java 21
-MySQL Server
-Maven
-Postman (or any API client)

## Installation
1. **Clone the repository**:
   git clone https://github.com/Mahmoud-Ramadan2/contacatapi.git
   cd contacatapi
2. **Configure MySQL**
3. **Build the application**:
   mvn clean install
   mvn spring-boot:run

## API Endpoints
- **User Registration and Authentication**
  POST /api/auth/signup: Register a new user (requires username, password, and email).
  POST /api/auth/signin: Login with credentials to receive a JWT token.
- **Contacts Management**
  GET /api/contacts/{userid}: Get a list of contacts for a user (supports pagination and sorting).
  POST /api/contacts/{userid}: Create a new contact for user
  DELETE /api/contacts/{userid}/{id}: Delete a contact

### All requests to the /api/contacts endpoints must include a valid JWT token in the Authorization header
 Authorization: Bearer <your-jwt-token>

## Example Usage
- Register a New User
  POST /api/auth/signup
  {
  "name": "mahmoud ramadam",
  "email": "mahmoud@example.com",
  "password": "password123"
  }

- Login and Get JWT
  POST /api/auth/signin
  {
  "email": "john_doe",
  "password": "password123"
  }
## Testing
  - Unit tests are included in the project, and you can run them using Maven:
    mvn test

  



