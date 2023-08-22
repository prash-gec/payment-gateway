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

## Assumptions
1. Merchants will be created at gateway to provide payment endpoint.
2. Shoppers will be paying on merchant's site using merchant's payment endpoint.
3. Assuming the authentication will be in place.
4. No IdempotencyKey is supplied at this moment hence duplicate transactions will be processed.

## Area of improvement
1. Adding IdempotencyKey will make sure duplicate transactions are not being processed.
2. Authentication can be added, as at the moment any actor can access any endpoint (SCA and auth token).
3. Payment should be divided in `Charge` and `Authorize`.
4. Ideally a new row for all the state of payment should be inserted in the `payment_details` table, so any payment can be tracked end to end.
5. Previous point will also help in analysing the round trip for payment request and failure rate.
6. Retry mechanism should be added based on the return status.

## Cloud tech consideration

1. As its cloud native application, it can be deployed in EC2 instance.
2. Using cloud DB instance is also helpful.
3. Cloud notification services can also be leveraged.
4. Analytics and performance can be easily tracked using the metrics provided in cloud tooling.

**why ??** Using the cloud infra security and scaling can be deligated to the cloud provider, all these features can definitely be implemented without cloud but if the volume is very low using cloud will save cost end efforts on building infra to support all these features.







