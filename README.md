# Book-store

## Description

Book Store is an application for managing a bookstore,
allowing users to view, add, edit, and delete books. 
The application also supports functionality for managing users and their orders.

## Functionality
- **View Books:** Users can browse a list of available books with detailed descriptions, prices, and authors.  
- **Manage Books:** Administrators can add new books, edit existing ones, or delete them.  
- **Manage Users:** Users can create accounts, register in the system, and view their orders.  
- **View Orders:** Users can view their orders and their status.

## Technologies Used

- **Spring Boot**: For building and running the application.
- **Spring Data JPA**: For interacting with the database using JPA.
- **Spring Security**: For implementing security features such as authentication and authorization.
- **MySQL**: For the relational database management system.
- **Liquibase**: For database versioning and migrations.
- **JWT**: For secure token-based authentication.
- **MapStruct**: For automatic mapping between entities and DTOs.
- **JUnit 5**: For writing and running tests.
- **TestContainers**: For running isolated test environments with Docker containers.
- **Swagger/OpenAPI**: For automatic generation of REST API documentation.
- **Lombok**: For reducing boilerplate code with annotations like `@Getter`, `@Setter`, `@AllArgsConstructor`, etc.
- **Docker & Docker Compose**

## Project's API
[View Postman Collection](https://planetary-robot-110333.postman.co/workspace/New-Team-Workspace~46449146-b028-4eca-8cfa-e24a2eb681d2/collection/40055606-66feecc3-cd7d-4096-ac1d-94da64da0a5d?action=share&creator=40055606)

## DB Diagram
![Example Image](db-diagram.png)

## Installation and Setup

### Prerequisites

- **Install Docker:** Ensure Docker and Docker Compose are installed on your system. Follow the instructions here.

- **Git for cloning the repository.**

### Steps to Install

- **Clone the Repository**:
   ```bash
   git clone https://github.com/StoneBlood-bit/book-store.git
   cd book-store
- Create the **.env** File: Create a file named **.env** in the root directory of the project and add the following variables:
   ```dotenv
  MYSQLDB_USER=yourname
  MYSQLDB_PASSWORD=yourpassword
  MYSQLDB_DATABASE=yourdatabase
  MYSQLDB_ROOT_PASSWORD=yourrootpassword
  MYSQL_LOCAL_PORT=3305
  MYSQL_DOCKER_PORT=3306

  SPRING_LOCAL_PORT=8080
  SPRING_DOCKER_PORT=8080
  DEBUG_PORT=5005
- Run Docker Compose:
   ```bash
  docker-compose up
- Access the Application: Open your browser and go to http://localhost:8080.