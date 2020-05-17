# Shopping Cart Application

## Build & Run
You will need JDK (minimum 8, also tested with 11) and Apache Maven (tested with 3.6.1) installed in your local environment.
In order to starting playing with the application:

- Build with `mvn install`
- Navigate into `target` folder
- Execute `java -jar shopping-cart-1.0.0-SNAPSHOT.jar`
- Visit `localhost:8080`

## Develop & Hack
Entry point for backend is `ShoppingCartApplication`. You will also need to do `npm start` in `frontend`.
Use / visit `localhost:3000` in development. Changes in `frontend` are watched and re-loaded on the fly.

## Technology Stack
Spring Boot, ReactJS, Bootstrap Front-End Framework and h2 database.

## What It Is
This is a POC shopping-cart application. User can add / read / update / delete products in their shopping carts and submit their shopping carts.
Submitted shopping carts receive a unique order id that can be used to check the contents of previous orders as long as the application lives.
Since h2 lives in memory, everything dies once you shutdown the application.

## To Do
Exception Handling is not implemented in either frontend or backend.
Test coverage is not 100% though there are samples to be followed for testing web-layer and service layer that can be used as examples to increase coverage.
