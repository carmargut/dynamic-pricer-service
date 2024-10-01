# Dynamic Pricer Service

# Table of Contents

- [Dynamic Pricer Service](#dynamic-pricer-service)
- [Table of Contents](#table-of-contents)
    - [Build](#build)
        - [Prerequisites](#prerequisites)
        - [Instructions](#instructions)
            - [Local development](#local-development)
            - [Docker development](#docker-development)
        - [Testing the API](#testing-the-api)
            - [Postman](#postman)
            - [SwaggerUI](#swaggerui)
    - [Personal Notes](#personal-notes)
        - [Modifications](#modifications)
        - [Custom exceptions and REST error codes integration](#custom-exceptions-and-rest-error-codes-integration)
        - [Technologies used](#technologies-used)
            - [Domain Service](#domain-service)
            - [JPA and Hibernate](#jpa-and-hibernate)
            - [UUID-based Identifiers](#uuid-based-identifiers)
            - [H2 Database](#h2-database)
            - [Mapping](#mapping)
            - [Indexes for optimization](#indexes-for-optimization)

## Build

### Prerequisites

- Java 23
- Maven (for local development)

### Instructions

1. Build the project (common step)

   Before running the application, you need to build it. Run the following command:

   ```bash
   mvn clean install
   ```
2. Choose your preferred way of running the application: either locally or using Docker.

3. To run only the tests without starting the application, execute the following command:

    ```bash
    mvn test
    ```

#### Local development

If you want to run the application locally, follow these steps:

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

#### Docker development

If you prefer to run the application inside a Docker container, follow these steps:

1. Build the Docker image

   To build the Docker image of the application, use the following command from the root directory of the project (where
   your `Dockerfile` is located):

   ```bash
   docker build -t dynamic-pricer-service .
   ```
2. Run the application with Docker

   Once the image is built, you can run the container using the following command:

   ```bash
   docker run -d -p 8080:8080 dynamic-pricer-service
   ```

   This will start the application and make it available at <http://localhost:8080>

### Testing the API

#### Postman

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

#### SwaggerUI

The API is documented and can be tested interactively using SwaggerUI. SwaggerUI allows you to explore the available
endpoints, see the expected inputs and outputs, and make live requests directly from your browser

**Instructions**

1. Start the application.
2. Open your browser and go to: <http://localhost:8080/swagger-ui/index.html>
3. Explore and test the available endpoints directly through the SwaggerUI interface.

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
ideal for testing and fast development purposes because of its lightweight nature and ease of setup.
On the other hand, personally, I'd have opted for PostgreSQL as the main database for production, given its numerous
advantages such as robustness, scalability, and rich feature set. I'd have kept H2 strictly for testing purposes.

#### Mapping

I used mappers to convert between the database persistence models (PricePersistence) and the domain models
(Price). This ensures a clean separation between the database layer and business logic.

#### Indexes for optimization

To optimize the performance of searches, I added two indexes in the PRICES table:

- **BRAND_ID Index**: I created an index on the `BRAND_ID` column to speed up queries that filter by brand.
- **PRODUCT_ID Index**: An index was also created for the `PRODUCT_ID` column, ensuring faster lookups when searching by
  product.

While indexing `start_date` and `end_date` could also optimize range queries, I chose to handle this logic in the domain
service to keep business rules decoupled from data access, following clean architecture principles [as mentioned
before](#domain-service).
