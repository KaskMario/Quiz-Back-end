# QuizApp

This is the backend of QuizApp.
It includes logic for authentication, security, database and functionality for the end user.

## Setup

To get the backend running for local development environment you need to: 
1. Clone this repository to your local machine
2. Setup empty database and update `application.properties` with the correct values
3. run `QuizAppApplication.java` file
4. Insert roles into the database by running the following SQL commands:
   ```sql
   INSERT INTO role (role) VALUES ('ROLE_USER');
   INSERT INTO role (role) VALUES ('ROLE_ADMIN');
