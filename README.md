# Proejct 5 - Yoga-app

Fifth project of OpenClassrooms Java/Angular Fullstack Training Course.

Global Setup

Requirements:
* Git
* Node >= 16.10.0
* OpenJDK8 or Oracle JDK8
* MySQL Database >= 8.0
* An IDE for Java (Eclipse/IntelliJ/etc.)

First thing you need to do is clone the project with git clone command in a terminal.

Setup DB

In my SQL command line (Or you can use a database tool like DBeaver):
mysql> CREATE DATABASE test;
mysql> USE test;
mysql> SOURCE /path/to/file.sql
mysql> CREATE USER 'user'@'localhost' IDENTIFIED BY '123456';
mysql> GRANT SELECT, INSERT, UPDATE, DELETE ON test . * TO 'user'@'localhost';
You can change user, password or database name but you will have to change the application.properties in the back end project accordingly.
SQL script for creating the schema (the one used with SOURCE) is available there: ressources/sql/script.sql

Setup Back

* Launch your preferred IDE and open the folder where you cloned the backend project.
* Check if you have correct JDK version for project (Java 8) and Maven.
* Download dependencies and build project through Maven: mvn install
* Run the application with command man springboot:run

Setup and launch Front

* Go inside front folder
* Install dependencies: npm install. !!! Check that your node version is the most recent
* Launch Front-end: npm run start;
If you didn't install DB and launch the Back, you won't be able to go further than homepage, login and register.
By default, the admin user account that you can use to connect is:
* login: yoga@studio.com
* password: test!1234
* 
How to run tests

Front unit and integration tests

Go to the root of the front app and open a terminal.
If you want to run a single test file:
npx jest yourfile.ts
If you want to run all tests:
npm test

Back unit and integration tests

Open your IDE and use maven with the following command to run all tests:
mvn test

Front e2e tests

Go to the root of the front app (project5-yoga-app/front) and open a terminal.
npm run e2e
Cypress should open and ask you which browser you want to use for your tests.

Once chosen, go to the specs menu on the left and then you can select which e2e test you want to run.
If you want to run all e2e test, you can use the command: npm run cypress:run but you need to launch the frontend application first.

How to generate coverage tests

Front unit and integration tests

Go to the root of the front app (project5-yoga-app/front) and run the following command in terminal:
npx jest --coverage
Results should show up in the terminal.

Back unit and integration tests

Open your IDE and use maven with the following command:
mvn test
You can find the report in the target folder: target/site/jacoco/index.html by opening the index.html file in a browser.

Front e2e tests

Go to the root of the front app (project5-yoga-app/front) and run the following commands in terminal:
npm run e2e
npm run e2e:coverage
Results should show up in the terminal.
You can find a more detailed report in the coverage folder coverage/lcov-report/index.html by opening the index.html file in a browser.
If the coverage appears low, try to run the two following commands altogether:
npm run cypress:run  
npx nyc report --reporter=html
It will run the e2e test and the generate the accurate coverage
