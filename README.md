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


