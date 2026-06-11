# Data Governance Platform

A microservices-based data governance platform built with Java and Spring Boot.

## Architecture

The platform consists of three independent services:

- **Registry Service** — registers and manages data sources, schemas, tables, 
  and columns with PII classification
- **Policy Engine Service** — defines governance policies with configurable rules 
  and evaluates datasource compliance
- **Scanner Service** — async PII detection engine using Kafka (in progress)

## Services

| Service | Port | Repository |
|---|---|---|
| Registry Service | 8081 | [registry-service](https://github.com/sagarr19/registry-service) |
| Policy Engine Service | 8082 | [policy-engine-service](https://github.com/sagarr19/data-governance-platform) |
| Scanner Service | 8083 | Coming soon |

## Tech Stack

- Java 17, Spring Boot 4
- PostgreSQL, Flyway migrations
- Docker, Docker Compose
- Spring Data JPA, Spring Validation
- RestClient for inter-service communication

## Running Locally

Start PostgreSQL: 

Start Registry Service (port 8081):

cd registry-service
$env:JAVA_TOOL_OPTIONS="-Duser.timezone=UTC"; .\mvnw.cmd spring-boot:run


Start Policy Engine Service (port 8082):


cd policy-engine-service
$env:JAVA_TOOL_OPTIONS="-Duser.timezone=UTC"; .\mvnw.cmd spring-boot:run


## Domain

Built on real-world experience with enterprise data governance tools including 
Informatica CDGC and SailPoint ISC.
