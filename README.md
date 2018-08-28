## What are Microservices?

There are thousands of definitions out there about what are microservices, but the one I like best is that of the great master Martin Fowler

> In short, the microservice architectural style is an approach to developing a single application as a suite of small services, each running in its own process and communicating with lightweight mechanisms, often an HTTP resource API. These services are built around business capabilities and independently deployable by fully automated deployment machinery. There is a bare minimum of centralized management of these services, which may be written in different programming languages and use different data storage technologies.
    
>**James Lewis and Martin Fowler**

# 

### Stack
- **Spring Cloud Configuration Server** with a **git repository**
- **Feign** to create REST Clients
- **Ribbon** to Client Side Load Balance
- **Eureka** to a Naming Server
- **Zuul** to a API Gateway
- **Sleuth** **Zipkin** to a Distributed Tracing, using **RabbitMQ** for save logs
- **Hystrix** to a Fault Tolerance


### Ports

|     Application       |     Port          |
| ------------- | ------------- |
| Limits Service | 8080, 8081, ... |
| Spring Cloud Config Server | 8888 |
|  |  |
| Currency Exchange Service | 8000, 8001, 8002, ..  |
| Currency Conversion Service | 8100, 8101, 8102, ... |
| Netflix Eureka Naming Server | 8761 |
| Netflix Zuul API Gateway Server | 8765 |
| Zipkin Distributed Tracing Server | 9411 |

### Zipkin
Execute
    ```RABBIT_URI=amqp://localhost java -jar zipkin.jar```


## References

- [Microservices Resource Guide - Martin Fowler](https://martinfowler.com/microservices/)
- [Master Microservices with Spring Boot and Spring Cloud - in28Minutes](https://www.udemy.com/microservices-with-spring-boot-and-spring-cloud/)