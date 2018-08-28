
## What are Microservices?

There are thousands of definitions out there about what are microservices, but the one I like best is that of the great master Martin Fowler

> In short, the microservice architectural style is an approach to developing a single application as a suite of small services, each running in its own process and communicating with lightweight mechanisms, often an HTTP resource API. These services are built around business capabilities and independently deployable by fully automated deployment machinery. There is a bare minimum of centralized management of these services, which may be written in different programming languages and use different data storage technologies.
    
>**James Lewis and Martin Fowler**

# What is this?
This is a POC of a Microservices Architecture using Spring Cloud.

## Stack
- **Spring Cloud Configuration Server** with a **git repository**
- **Feign** to create REST Clients
- **Ribbon** to Client Side Load Balance
- **Eureka** to a Naming Server
- **Zuul** to a API Gateway
- **Sleuth** and **Zipkin** to a Distributed Tracing, using **RabbitMQ** for save logs
- **Hystrix** to a Fault Tolerance


## Ports

|     Application       |     Port          |
| ------------- | ------------- |
| Copom Microservice | 8080, 8081, ... |
| Central Bank Microservice | 8000, 8001, 8002, ..  |
| Falbo Bank Microservice | 8100, 8101, 8102, ... |
| Arruzzo Bank Microservice | 8200, 8201, 8202, ... |
| Auth Microservice | 8300, 8301, 8302, ... |
| Spring Cloud Config Server | 8888 |
| Netflix Eureka Naming Server | 8761 |
| Netflix Zuul API Gateway Server | 8765 |
| Zipkin Distributed Tracing Server | 9411 |


## git-localconfig-repo
When cloning the project, the git-localconfig-repo project must be linked to the spring-cloud-config-server project.
If you use IntelliJ, it is quite simple to do this. Just go to the menu **File > Project Structure**. To do this, just open your **spring-cloud-config-server** project and click **+ Add Root Content** and choose a folder **git-localconfig-repo**.

![ContentRoot](https://github.com/juliofalbo/microservices-with-spring-cloud/blob/master/contentroot.png?raw=true)


When you do this, you will need to create the **copom.properties**, **copom-dev.properties**, **copom-qa.properties**, **copom-prod.properties** files as shown in the image below.

![gitlocalconfig](https://github.com/juliofalbo/microservices-with-spring-cloud/blob/master/gitlocalconfig.png?raw=true)

### Remember that it is necessary to start a local repository with the git init command

## Zipkin
To run the zipkin server with RabbitMQ config, run this code:

    RABBIT_URI=amqp://localhost java -jar zipkin.jar


## Architecture - In Progress
![Architecture](https://github.com/juliofalbo/microservices-with-spring-cloud/blob/master/arch3.jpeg?raw=true)

## References

- [Microservices Resource Guide - Martin Fowler](https://martinfowler.com/microservices/)
- [Master Microservices with Spring Boot and Spring Cloud - in28Minutes](https://www.udemy.com/microservices-with-spring-boot-and-spring-cloud/)