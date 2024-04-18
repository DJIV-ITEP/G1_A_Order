# Wasale - Food Delivery Service

“Wasale” is a popular delivery company that wants to build a food delivery system that facilitates ordering food from different restaurants. The system shall support adding/removing different restaurants to the restaurant catalog.

## Tech Stack

**Backend:** Java, Spring-Boot.

## Scope

This repo is only for `Order` micro-service.

## Documents

- ### Entity Relationship Diagram

  ### Note : The ERD is still in working Process.

  - #### ERD Image :

  ![ERD Image](/docs/ERD/OrderService_new-ERD.jpg)

  - #### ERD drawio file in docs folder:

    `docs/ERD/OrderService_new-ERD.drawio`

  - #### ERD Link to Contribute :

    [Link to the ERD](https://drive.google.com/file/d/1N1uJ5eSdUZ4qQhMPAPnlYcVdrjakY3bU/view?usp=drive_link "@It's Safe, Don't Worry :)")

## File Structure

```
├── api # contains the app interface with the client
│   │
│   ├── controller # contains the controller classes
│   │
│   └── dto # contains the Data Transfer object classes
│
├── config # contains app config
│   │
│   ├──  mapper # contains mappers between entities and DTOs
│   │
│   └── SwaggerConfig.java # constain swagger config
│
├── helper # contains static helper func
│
├── persistent # contains DB logic
│   │
│   ├── entity # contains DB entity
│   │
│   ├── enum_ # contains enums
│   │
│   ├── seed # contains DB seeders
│   │
│   └── repository # contains DB calls
│
└── service # contains business logic
```

## Before running the project

1. Make a file with name `secrets.properties` in dir `src/main/resources/`.
2. Write the secrets variables to the file.
   > ### EX of the file content:

- Note: Change the variables based on your configurations.

```
# Database
DB_NAME=order_service
DB_USER=postgres
DB_PASS=postgres
DB_PORT=5432
DB_HOST=localhost
# Server
SERVER_PORT=8080
# Other Microservices URL
CUSTOMER_SERVICE_URL=http://localhost:8081
```

## After Setting up the Secrets

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
http://localhost:8080/webjars/swagger-ui/index.html
```

---

# Docker and Docker-Compose :

#### 1. Create `.env` file in root dir

#### 2. Paste the following to the `.env` file:

```
POSTGRES_USER=postgres
POSTGRES_PASSWORD=123
POSTGRES_DB=order_service
```

#### 3. Run the following commands:

```
- docker-compose build
- docker-compose up
```

---

## Team Members

1. [Ala Al-Sanea](https://github.com/Ala-Alsanea) - `Project Owner`
2. [Adam Almohammedi](https://github.com/Eng-Adam-Almohammedi) - `Scrum Master`
3. [Amjad Al-Aghbari](https://github.com/amjadfqs)
4. [Adel Subait](https://github.com/ahdel)
5. [Waledd Thamer](https://github.com/waleedthamer)
