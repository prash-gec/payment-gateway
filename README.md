# Checkout.com Assignment

[Checkout.com take home challenge](https://github.com/prash-gec/payment-gateway/blob/main/checkout-assignment.pdf)

## Dependency

1. [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
2. [Gradle 7+](https://gradle.org/releases/)*
3. [H2 DB](http://www.h2database.com/html/download.html)*
4. [Open API](https://spec.openapis.org/oas/latest.html)*
5. [Docker 24.0.5](https://www.docker.com/products/docker-desktop/) (Optional)

`*` marked dependencies will be auto downloaded as a part of build.

## How to Run

Clone the repo on any machine with
dependency [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
 and run below commands from root directory.

On mac
`./gradlew assemble`

On Windows
`gradlew assemble`

- Open api spec should be available after successful run at http://localhost:8080/swagger-ui/index.html (port can be changed
  from `application.properties`)

- If want to run in docker (Optional)
    - build docker image `docker build -t payment-gateway-image .`
    - run docker image `docker run -p 8080:8080 payment-gateway-image`

## System Design

<p align="center">
  <img src="https://github.com/prash-gec/payment-gateway/blob/main/fd.png" alt="Flow Diagram" width="900"/>
</p>

## Test-data

As configured in `application.properties` list of blocked and valid card series.
`valid.cc.series = 4568,4698,9689`
`blocked.cc.series = 1111,2222,3333`

- Any payment with card number starts with the valid series, will result in successful response from `DummyBank`.
- Any payment with card number starts with the blocked series, will be blocked by `DummyBank`

## Deliverables
1. [Git Repo (source code)](https://github.com/prash-gec/payment-gateway/)
2. [Dockerfile](https://github.com/prash-gec/payment-gateway/blob/main/Dockerfile)
2. [build.gradle](https://github.com/prash-gec/payment-gateway/blob/main/build.gradle)
3. [postman_collection](https://github.com/prash-gec/payment-gateway/blob/main/Payment-Gateway.postman_collection.json)
4. [flow diagram](https://github.com/prash-gec/payment-gateway/blob/main/fd.png)
5. [Problem statement](https://github.com/prash-gec/payment-gateway/blob/main/checkout-assignment.pdf)
6. [API Doc](http://localhost:8080/swagger-ui/index.html) will be available after successful run
6. [README.md :)](https://github.com/prash-gec/payment-gateway/blob/main/README.md)

## APIs


| Endpoint                                         | Owner         | User          | Method | Responses     |
|--------------------------------------------------|---------------|---------------|--------|---------------|
| /api/merchants                                   | Admin/Gateway | Admin/Gateway | POST   | 201, 400      |
| /api/merchants                                   | Admin/Gateway | Admin/Gateway | GET    | 200, 400      |
| /api/merchants/{merchantId}/payments             | Merchant      | User          | POST   | 201, 400, 404 |
| /api/merchants/{merchantId}/payments/{paymentId} | Merchant      | User          | GET    | 200, 404      |
| /api/merchants/{merchantId}/payments             | Merchant      | User          | GET    | 200, 400, 404 |



