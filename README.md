# Spring Retail Management REST API (Reactive) Web App

## Overview
This is a Spring Boot RESTful web application for managing retail operations. The application includes entities such as Vendor, and Categories. This application leverages Spring WebFlux to build reactive and non-blocking web applications, providing a scalable solution for handling a high volume of transactions and user interactions in real-time.

## Technology Stack
- **Spring Boot**: Framework for building Java-based applications
- **Spring WebFlux**: supports reactive programming, enabling the development of non-blocking and event-driven applications.
- **Lombok**: Reducing boilerplate code
- **Reactive MongoDB**: NoSQL Database that supports reactive streams
- **Maven**: Build and dependency management

## Architecture
- Controller Layer: Handles HTTP requests and responses.
- Service Layer: Contains business logic and interacts with the repository layer.
- Repository Layer: Interacts with the database using Spring Data JPA.
- Model Layer: Contains entity classes.

## Key Features
- Reactive Programming: Leveraging Spring WebFlux to handle asynchronous processing, improving application performance and scalability.
- Vendor Management: CRUD operations for managing vendors.
- Category Management: CRUD operations for managing product categories.


## Database Design
#### Entities and Relationships
- **Vendor**
- **Category**

## API Endpoints

### Vendors
- **GET /api/vendors**: Retrieve all vendors
- **GET /api/vendors/{id}**: Retrieve a vendor by ID
- **POST /api/vendors**: Create a new vendor
- **PUT /api/vendors/{id}**: Update a vendor
- **DELETE /api/vendors/{id}**: Delete a vendor

### Categories
- **GET /api/categories**: Retrieve all categories
- **GET /api/categories/{nane}**: Retrieve a category by Name

## Testing
- **Unit Tests:** Using JUnit and Mockito for testing services and controllers.
- **Integration Tests:** Testing the interaction between different layers.
