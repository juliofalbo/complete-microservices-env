
## What are Microservices?

There are thousands of definitions out there about what are microservices, but the one I like best is that of the great master Martin Fowler

> In short, the microservice architectural style is an approach to developing a single application as a suite of small services, each running in its own process and communicating with lightweight mechanisms, often an HTTP resource API. These services are built around business capabilities and independently deployable by fully automated deployment machinery. There is a bare minimum of centralized management of these services, which may be written in different programming languages and use different data storage technologies.
    
>**James Lewis and Martin Fowler**

# What is this?
This is a whole environment of a Microservices Architecture using Spring Cloud, Grafana, Prometheus, Postgres Replication Strategy and RabbitMQ.

## Architecture Diagram
![HotelBookingSystemArchitecture.png](HotelBookingSystemArchitecture.png)

## Getting Started

### Start
- Clone the repository
  - `git clone git@github.com:juliofalbo/complete-microservices-env.git`
- Build the services
  - `./build.sh`
- Start the whole environment
  - `./start.sh`
  
_Note: If you want build and run just call `./start true`_

### Stop
- Stop all services **removing all volumes**
  - `./stop.sh`

_Note: Doing this you will loose all your data_

## Stack
- **RabbitMQ** as a Message Broker
- **Feign** to create REST Clients
- **Ribbon** to Client Side Load Balance
- **Eureka** to a Service Discovery
- **Sleuth** and **Zipkin** to a Distributed Tracing
- **Hystrix** to a Fault Tolerance
- **Prometheus** as a Metrics Collector
- **Grafana** as Metrics Analytics and UI
- **Postgres** as Database
- **Flyway** as Database Migration Tool
- **Splunk** as Log Analysis Platform
- **Redis** to handle Distributed Lock

## Ports

|     Application       |     Port          |
| ------------- | ------------- |
| Booking Microservice | 8100, 8101, 8102  |
| Searching Microservice | 8200, 8201 |
| Financial Microservice | 8300 |
| Frontend Microservice | 8400 |
| RabbitMQ | 5672, 5673 |
| RabbitMQ Admin UI | 15672, 15673 |
| Booking Postgres Master | 5432 |
| Booking Postgres ReadOnly Replica| 5433 |
| Financial Postgres | 5434 |
| Grafana | 3000 |
| Prometheus | 9090 |
| Splunk | 8000 |
| Netflix Eureka | 8761 |
| Zipkin | 9411 |

## References

- [Microservices Resource Guide - Martin Fowler](https://martinfowler.com/microservices/)
- [Master Microservices with Spring Boot and Spring Cloud - in28Minutes](https://www.udemy.com/microservices-with-spring-boot-and-spring-cloud/)
- [Frontend Template](https://colorlib.com/wp/template/deluxe/)
- [Distributed Lock](https://redis.io/topics/distlock)
- [Spring Distributed Lock Lib](https://github.com/alturkovic/distributed-lock)