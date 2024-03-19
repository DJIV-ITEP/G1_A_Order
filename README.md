# Wasale - ‭Food Delivery Service‬

‭ “Wasale” is a popular delivery company that wants to build a food delivery system that facilitates‬
‭ordering food from different restaurants. The system shall support adding/removing different‬
‭restaurants to the restaurant catalog.‬

## Tech Stack

**Backend:** Java, Spring-Boot.

## Scope

This repo is only for `Order` micro-service.

## File Structure

```
├── api # contains the app interface with the client
│   │
│   ├── controller # contains the controller classes
│   │
│   └── dto # contains the Data Transfer object classes
│
├── config # contains app config
│ 
├── helper # contains static helper func
│
├── persistent # contains DB logic
│   │
│   ├── entity # contains DB entity
│   │  
│   ├── seed # contains DB seeders
│   │
│   └── repository # contains DB calls
│
└── service # contains business logic
```

## Before running the project

Make a cope of `src/main/resources/application.yml.example` to `src/main/resources/application.yml` and config the DB connection `username` and `password`

#### Note:
- Change the DB name to `order_service` to be unified for the group members
- The `src/main/resources/application.yml` is not trackable

Run the following command to install dependance  
```bash
mvn clean install
```
Run the following command to list dependance  
```bash
 mvn dependency:tree
```
Run the following command to start the app
```bash
mvn spring-boot:run
```
Navigate to the following URL to test the APIs
```
http://localhost:8080/swagger-ui/index.html
```


## Team Members

1. [Adel Subait](https://github.com/ahdel) - `Project Owner`
2. [Adam Almohammedi](https://github.com/Eng-Adam-Almohammedi) - `Scrum Master`
3. [Waledd Thamer](https://github.com/waleedthamer)
4. [Ala Al-Sanea](https://github.com/Ala-Alsanea)
5. [Amjad Al-Aghbari](https://github.com/amjadfqs)
