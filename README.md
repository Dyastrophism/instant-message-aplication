# Instant Message App

## Overview

The Instant Message App is a web application that allows users to send and receive instant messages. It is built using Angular for the frontend and Spring Boot for the backend. The application also integrates with Keycloak for authentication and authorization.

## Features

- User authentication and authorization with Keycloak
- Real-time messaging using WebSockets
- RESTful API for message management
- API documentation with OpenAPI

## Technologies

- **Frontend**: Angular, TypeScript, SCSS
- **Backend**: Java, Spring Boot, Maven
- **Database**: PostgreSQL
- **Authentication**: Keycloak
- **API Documentation**: OpenAPI (Springdoc)
- **Build Tool**: Maven

## Prerequisites

- Node.js and npm
- Java 17
- Maven
- PostgreSQL
- Keycloak

## Setup

### Backend

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/instant-message-app.git
    cd instant-message-app/instant-message-api
    ```

2. Configure the database in `application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/yourdatabase
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword
    ```

3. Run the backend application:
    ```sh
    mvn spring-boot:run
    ```

### Frontend

1. Navigate to the frontend directory:
    ```sh
    cd ../instant-message-ui
    ```

2. Install dependencies:
    ```sh
    npm install
    ```

3. Run the frontend application:
    ```sh
    ng serve
    ```

## Usage

1. Open your browser and navigate to `http://localhost:4200`.
2. Log in using your Keycloak credentials.
3. Start sending and receiving messages.

## API Documentation

The API documentation is available at `http://localhost:8080/swagger-ui.html`.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

## License

This project is licensed under the MIT License.