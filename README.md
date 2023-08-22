# Checkout.com Assignment
[Checkout.com take home challenge](https://github.com/prash-gec/payment-gateway/blob/main/checkout-assignment.pdf)

## Dependency
1. [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
2. [Gradle 7+](https://gradle.org/releases/)
3. [H2 DB](http://www.h2database.com/html/download.html) but will be downloaded as part of gradle dependency
4. [Open API](https://spec.openapis.org/oas/latest.html) but will be downloaded as part of gradle dependency
5. [Docker 24.0.5](https://www.docker.com/products/docker-desktop/)

## How to Run

Clone the repo on any machine, with dependency [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) and [Gradle 7+](https://gradle.org/releases/) and run below commands from root directory.

On mac
`./gradlew assemble`

On Windows 
`gradlew assemble`

- open api spec should be available after successful run at http://localhost:8080/swagger-ui (port can be changed from `application.properties`)

- if want to run in docker
  - build docker image `docker build -t payment-gateway-image .`
  - run docker image `docker run -p 8080:8080 payment-gateway-image`

## System Design

<p align="center">
  <img src="https://github.com/prash-gec/payment-gateway/blob/main/fd.png" alt="Flow Diagram" width="900"/>
</p>

