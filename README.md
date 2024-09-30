# Dynamic Pricer Service

# Table of Contents

- [Dynamic Pricer Service](#dynamic-pricer-service)
    - [Build](#build)
        - [Instructions](#instructions)
            - [Build the project](#instructions)
            - [Run the application](#instructions)
            - [Access H2 database](#instructions)
            - [Testing the API](#testing-the-api)
    - [Personal Notes](#personal-notes)
        - [Modifications](#modifications)
        - [Custom exceptions and REST error codes integration](#custom-exceptions-and-rest-error-codes-integration)
        - [Technologies used](#technologies-used)
            - [Domain Service](#domain-service)
            - [JPA and Hibernate](#jpa-and-hibernate)
            - [UUID-based Identifiers](#uuid-based-identifiers)
            - [H2 Database](#h2-database)
            - [Mapping](#mapping)

## Build

### Instructions

- Build the project

    ```bash
     mvn clean install
    ```
- Run the application
    ```bash
     mvn spring-boot:run
    ```
- Access H2 database

  If you want to check the in-memory database (H2), you can access it via:
  ```
  http://localhost:8080/h2-console
  ```
  Use the following credentials:
    - **JDBC URL**: jdbc:h2:mem:db
    - **Username**: sa
    - **Password**: (leave blank)

#### Testing the API

A Postman collection has been included in this repository, containing example requests based on the project
requirements.
This collection covers scenarios such as:

- Requests with different `productId`, `brandId`, and `applicationDate`.
- Handling of both valid and invalid date formats.
- Responses for valid prices and cases where no price is found for the given parameters.

**Instructions**

- **Import the Postman collection**

  Go to Postman, click on `File > Import`, and import the provided Postman collection file (`postman_collection.json`).

- **Send requests**

  Use the imported collection to test the API with example requests, including scenarios for price lookup and error
  handling.

## Personal Notes

### Modifications

- I found it appropriate to rename some fields:
    - `PRICE_LIST` -> `PRICE_ID`: Since it's the identifier of the entity and not a list.
    - `CURR` -> `CURRENCY`: For better clarity.

- I made a key decision to replace the use of `int` identifiers with UUIDs for `productId`, `brandId`, and `priceId`
  fields.
    - Why?
        - **Global Uniqueness**: UUIDs ensure unique IDs across systems, useful for distributed environments.
        - **Security**: Harder to guess than sequential IDs.
        - **Scalability**: Decouples ID generation from the database, making the system more flexible and secure.

### Custom exceptions and REST error codes integration

I implemented a custom exception system tailored to the needs of the application, allowing for clear separation of
concerns while providing detailed error handling. The custom exceptions are designed to be flexible, enabling the
encapsulation of various business and validation rules at the domain level.

### Technologies used

This project was built using Spring Boot to develop a RESTful service.
Some key decisions and the technologies chosen:

#### Domain Service

Instead of directly querying the database with the exact query needed, I implemented a more comprehensive solution
by utilizing a domain service. This allowed me to separate the business logic from the data access layer, adhering
to clean architecture principles. This approach provides more flexibility for adding new business rules in the
future while maintaining a loosely coupled system. It also demonstrates my knowledge of proper architectural
practices, making the solution more scalable, extendable, and easier to maintain.

#### JPA and Hibernate

I used JPA (Java Persistence API) with Hibernate as the default ORM provider. This allows easy mapping between
Java objects and database tables, with a clear separation between domain models and database entities. It
simplifies database interactions by using Java objects instead of SQL queries, reducing boilerplate code.

#### UUID-based Identifiers

As previously mentioned, the switch to UUIDs for `productId`, `brandId`, and `priceId` was a design choice made
for flexibility, security, and scalability.

#### H2 Database

As requested, I used H2 as an in-memory database to ensure fast initialization and teardown for testing. H2 is
ideal for testing and fast development purposes because of its lightweight nature and ease of setup

#### Mapping

I used mappers to convert between the database persistence models (PricePersistence) and the domain models
(Price). This ensures a clean separation between the database layer and business logic.

