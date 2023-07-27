# Application for goods resale platform

## What the project does

The project is a Spring-Boot Java-based application for goods resale platform. Frontend part of this platform is created with ReactJS library.

## Why the project is useful

The application allows users to register and post their advertisements to sell different goods and items. Also users may update personal data and ads, write and update ad comments and delete them.

## How users can get started with the project

To use this application you should install [PostgreSQL Database](https://www.postgresql.org/download/), [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows) with Maven Build system and [Docker Desktop](https://www.docker.com/).

1) Create a database for your application (e.g. via SQL Shell).
2) Connect this database with Spring Boot application: 
- choose 'Database' on the right pannel in IDEA: 

![Снимок экрана (1403)](https://github.com/marina-fomina/graduate-work/assets/111565371/4f9be52f-8bd0-4d3d-ba97-023516767608)

- choose 'PostgreSQL' in Datasources and enter your username, password and database name:

![Снимок экрана (1405)](https://github.com/marina-fomina/graduate-work/assets/111565371/f04cf46c-9494-44af-963c-4539cc0e4930)

3) To create tables in the database you need to write your own scripts (in SQL, XML, YAML or JSON format) with [chagesets](https://docs.liquibase.com/concepts/changelogs/changeset.html) and create [changelog-file](https://docs.liquibase.com/concepts/changelogs/home.html) in main -> resources -> liquibase directories as in example below:

![Снимок экрана (1404)](https://github.com/marina-fomina/graduate-work/assets/111565371/dfece42c-8167-49ea-9b09-4e6cd5f44091)

4) Change spring.datasource.url, username and password for your own ones in application.properties file:

![Снимок экрана (1407)](https://github.com/marina-fomina/graduate-work/assets/111565371/24a7fe2d-502e-4962-a464-e0bc8a383ba3)

### Running the application

1) Run the application in IntelliJ IDEA.

2) Run this command using Docker Desktop: **docker run --rm -p 3000:3000 ghcr.io/bizinmitya/front-react-avito:v1.19**. It is necessary to launch frontend part of your application.

3) On http://localhost:3000/ you will access your resale platform.

![Снимок экрана (1408)](https://github.com/marina-fomina/graduate-work/assets/111565371/c8054fd6-72dc-43d9-8ffc-9f23b1b7d025)

## Who maintains and contributes to the project

The application was written by Scherbakov Anton, Fomina Marina and Stolyarov Vitaliy. This is a graduate project of our team within [Java-developers](https://sky.pro/courses/programming/java-developer) studying process at [Skypro online university](https://sky.pro/).

## Technologies

In this project developers used:
- Java 11;
- Spring Boot / Web / Security;
- PostgreSQL;
- Liquibase;
- Docker.
