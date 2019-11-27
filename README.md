
## What are Microservices?

There are thousands of definitions out there about what are microservices, but the one I like best is that of the great master Martin Fowler

> In short, the microservice architectural style is an approach to developing a single application as a suite of small services, each running in its own process and communicating with lightweight mechanisms, often an HTTP resource API. These services are built around business capabilities and independently deployable by fully automated deployment machinery. There is a bare minimum of centralized management of these services, which may be written in different programming languages and use different data storage technologies.
    
>**James Lewis and Martin Fowler**

# What is this?
This is a whole environment of a Microservices Architecture using Spring Cloud, Grafana, Prometheus, Postgres Replication Strategy and RabbitMQ.

## Stack
- **RabbitMQ** as a Message Broker
- **Feign** to create REST Clients
- **Ribbon** to Client Side Load Balance
- **Eureka** to a Naming Server
- **Zuul** to a API Gateway
- **Sleuth** and **Zipkin** to a Distributed Tracing
- **Hystrix** to a Fault Tolerance
- **Prometheus** as a Metrics Collector
- **Grafana** as Metrics Analytics and UI
- **Postgres** as Database

## Ports

|     Application       |     Port          |
| ------------- | ------------- |
| Searching Microservice | 8080, 8081, ... |
| Booking Microservice | 8000, 8001, 8002, ..  |
| Auth Microservice | 8300, 8301, 8302, ... |
| RabbitMQ | 5672, 5673 |
| RabbitMQ Admin UI | 15672, 15673 |
| Postgres | 5432 |
| Postgres ReadOnly Replica| 5433 |
| Grafana | 3000 |
| Prometheus | 9090 |
| Netflix Eureka Naming Server | 8761 |
| Netflix Zuul API Gateway Server | 8765 |
| Zipkin Distributed Tracing Server | 9411 |

## References

- [Microservices Resource Guide - Martin Fowler](https://martinfowler.com/microservices/)
- [Master Microservices with Spring Boot and Spring Cloud - in28Minutes](https://www.udemy.com/microservices-with-spring-boot-and-spring-cloud/)