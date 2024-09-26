# Dynamic Pricer Service

### Modifications

- I found it appropriate to rename some fields:
    - `PRICE_LIST` -> `PRICE_ID`: Since it's the identifier of the entity and not a list.
    - `CURR` -> `CURRENCY`: For better clarity.

- I made a key decision to replace the use of `int` identifiers with UUIDs for `productId`, `brandId`, and `priceId`..
    - Why?
        - **Global Uniqueness**: UUIDs ensure unique IDs across systems, useful for distributed environments.
        - **Security**: Harder to guess than sequential IDs.
        - **Scalability**: Decouples ID generation from the database, making the system more flexible and secure.

### Technologies used

This project was built using Spring Boot to develop a RESTful service.
Some key decisions and the technologies chosen:

- JPA and Hibernate
    - I used JPA (Java Persistence API) with Hibernate as the default ORM provider. This allows easy mapping between
      Java objects and database tables, with a clear separation between domain models and database entities. It
      simplifies database interactions by using Java objects instead of SQL queries, reducing boilerplate code.

- UUID-based Identifiers
    - As previously mentioned, the switch to UUIDs for `productId`, `brandId`, and `priceId` was a design choice made
      for
      flexibility, security, and scalability.

- H2 Database
    - As requested, I used H2 as an in-memory database to ensure fast initialization and teardown for testing. H2 is
      ideal for testing and fast development purposes because of its lightweight nature and ease of setup

- DTO and Mapping Layer
    - I used mappers to convert between the database persistence models (PricePersistence) and the domain models
      (Price). This ensures a clean separation between the database layer and business logic.

