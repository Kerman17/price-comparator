# Market Price Comparator - Backend

A spring boot application that helps users find the best deals across multiple stores. Users can optimize their shopping baskets for savings and can see the latest and best discounts added in the system. They can set target prices for needed products and the application will notify them when price of said product will go below the price tag appointed. According to the product chosen, the application can suggest substitutes and recommendations if better value per unit products exist across the tracked stores.

## Project Structure Overview

This project follows a typical layered architecture commonly used in Spring Boot applications:

 - Controllers: Handle HTTP requests and responses. They receive requests, process the necessary data, and determine the appropriate response
 - Services: Contain the business logic of the application. They encapsulate the business logic and provide a layer of abstraction between controllers and repositories, while handling data processing, validation, and other application-specific logic
 - Repositories: Interface with the database using Spring Data JPA. Each repository is linked to a specific entity and provides access to CRUD operations and custom queries
 - Domain entities: Application's model, such as **User**, **Store**, **StoreProduct**, **Discount**, **Basket**, **BasketItem**, **PriceAlert**
 - Data Transfer Objects (DTOs): Used to transfer data between different layers of the application, especially between the server and the client
 - Exceptions: Custom exceptions to ensure user-friendly error responses
 - Scheduler: Continuosly runs a task after a set period of time (10 minutes)
 - Data Loader: Utility class which loads the data from csv files to the desired tables

The project also includes configuration files (application.properties) and a standard Maven pom.xml for dependency management and build configuration

## How to Build and Run the Application

### Prerequisites

- Java 17+
- Maven 3.8.1+
- PostgreSQL

### Setup instructions

1. **Clone the repository from GitHub**

```bash
git clone https://github.com/Kerman17/price-comparator.git
cd market-price-comparator
```

2. **Create PostgreSQL database**
```bash
CREATE DATABASE price_comparator;
```

3. **Configure database credentials *(in src/main/resources/application.properties)***
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/price_comparator
spring.datasource.username=your_username
spring.datasource.password=your_password
```
4. **Build and run the application**
- The API will be available at [http://localhost:8080](http://localhost:8080/api/v1)

## Assumptions & Simplifications

- Notifications are stored as a List<String> in the User entity (no separate notification table).
- No authentication is implemented yet

## Use of Implemented Features

To use this backend, send an HTTP request to the URL:
http://localhost:8080/api/v1

List of URL paths
Each URL path gives a different response when you send a request to it.

### For Stores
- [GET /stores](#get-stores)
- [GET /stores/{storeid}](#get-storesstoreid)
- [POST /stores](#post-stores)
- [DELETE /stores/{storeid}](#delete-storesstoreid)
- [PUT /stores/{storeid}](#put-storesstoreid)

### For User
- [GET /users](#get-users)
- [GET /users/{userid}](#get-usersuserid)
- [POST /users](#post-users)
- [DELETE /users/{userid}](#delete-usersuserid)

### For Discount
- [GET /discounts](#get-discounts)
- [GET /discounts/{discountid}](#get-discountsdiscountid)
- [GET /discounts/best](#get-discountsbest)
- [GET /discounts/last-24h](#get-discountslast-24h)
- [POST /discounts](#post-discounts)
- [DELETE /discounts/{discountid}](#delete-discountsdiscountid)

### For StoreProduct
- [GET /stores-products](#get-stores-products)
- [GET /stores-products/{storesproductsid}](#get-stores-productsstoresproductsid)
- [GET /stores-products/price-history](#get-stores-productsprice-history)
- [GET /stores-products/recommendations](#get-stores-productsrecommendations)
- [DELETE /stores-products/{storesproductsid}](#delete-stores-productsstoresproductsid)

### For Basket
- [GET /baskets](#get-baskets)
- [GET /baskets/{basketid}/cheapest-options](#get-basketsbasketidcheapest-options)
- [POST /baskets](#post-baskets)

### For BasketItem
- [GET /basket-items](#get-basket-items)
- [POST /basket-items](#post-basket-items)

### For PriceAlert
- [GET /price-alerts](#get-price-alerts)
- [POST /price-alerts](#post-price-alerts)

### GET /stores

Description
- returns all stores

### GET /stores/{storeId}

Description
- returns store with id *storeId*

### POST /stores

Description
- posts a new store

Example input raw body:

```bash
{
	"name": "Profi Romania"
}
```

### DELETE /stores/{storeId}

Description
- deletes the store with id *storeId* if existent

### PUT /stores/{storeId}

Description
- updates the name of store with id *storeId*

Example request

```bash
http://localhost:8080/api/v1/stores/1?name=Changed Name
```

</br>

### GET /users

Description
- returns all stores

### GET /users/{userId}

Description
- returns user with id *userId* 
Example request:
```bash
http://localhost:8080/api/v1/users/1
```

### POST /users

Description
- posts a new user

Example input raw body
```bash
{
  "email": "user email",
  "password": "user password",
	"name": "Raul"
}
```

### DELETE /users/{userId}

Description
- deletes the user with id *userId*

</br>

### GET /discounts

Description
-returns all the discounts

### GET /discounts/{discountId}

Description
- returns the discount with the id *discountId*

### GET /discounts/best

Description
- returns the best 10 discounts across all the stores

### GET /discounts/last-24h

Description
- returns the last 10 discounts added in the last day

### POST /discounts

Description
- posts a new discount
Example raw input

```bash
{
    "storeProductId": 2,
    "fromDate": "2032-12-17",
    "toDate": "2032-12-31",
    "percentage": 50
}
```
### DELETE /discounts/{discountId}

Description
- deletes the discount with the id *discountId*

</br>

### GET /stores-products

Description
- returns all the stores-products

### GET /stores-products/{storesproductsId}

Description
- returns the store-product with the id *storesproductsID*

### GET /stores-products/price-history

Description
- returns the price history of a given product by product name, filtrable by store name, brand, category name

Example HTTP to hit for returning price history of product with name as productName, filtered by storeName, then by brand, then by categoryName.

```bash
http://localhost:8080/api/v1/stores-products/price-history?productName=lapte zuzu

http://localhost:8080/api/v1/stores-products/price-history?productName=lapte zuzu&storeName=lidl

http://localhost:8080/api/v1/stores-products/price-history?productName=lapte zuzu&brand=Zuzu

http://localhost:8080/api/v1/stores-products/price-history?productName=lapte zuzu&categoryName=lactate
```

### GET /stores-products/recommendations

Description
- returns the best product substitutes across the stores for the input categoryName, by price per unit, filterable by storeName

Example HTTP to hit for returning best product substitutes for the input categoryName and then when the store filter is applied.

```bash
http://localhost:8080/api/v1/stores-products/recommendations?categoryName=lactate

http://localhost:8080/api/v1/stores-products/recommendations?categoryName=lactate&storeName=profi
```

### DELETE /stores-products/{storesProductsId}

Description
- deletes the store product with the id *storesProductsId*

Example

```bash
http://localhost:8080/api/v1/stores-products/1
```

</br>

### GET /baskets

Description
- returns all the baskets

```bash
http://localhost:8080/api/v1/baskets
```

### GET /baskets/{basketId}/cheapest-options

Description
- returns the cheapest items on the database for the basked with id *baskedId*

```bash
http://localhost:8080/api/v1/baskets/1/cheapest-options
```

### POST /baskets

Description
- adds a new basket to a existing user ( a user can only have 1 basket )

Example raw body input

```bash
{
    "userId": 1
}
```

</br>

### GET /basket-items

Description
- returns all the items existent in baskets

```bash
http://localhost:8080/api/v1/basket-items
```

### POST /basket-items

Description
- adds a product name to a basket

Example raw body input:

```bash
{
    "productName": "iaurt grecesc",
    "basketId": 1
}
```

</br>

### GET /price-alerts

Description
- returns all the price alerts

### POST /price-alerts

Description
- adds a new price alert to a user by userId with the targeted price and product name

Example raw input body

```bash
{
  "productName": "lapte zuzu",
  "targetPrice": 9.4,
	"userId": 1
}
```


