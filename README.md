## Overview
This project contains prototype modules for making a simple product order management system based on micro-service architecture. It based on [Spring](https://spring.io/) framework using Spring Boot, Spring Cloud.

## License
This project is currently available under the [MIT](https://github.com/congcoi123/product-order-services/blob/master/LICENSE) License.

## Installation
You can get the sources:
```
git clone https://github.com/congcoi123/product-order-services.git
```

## Manual
### Project Structure
#### System Services
These services based on the [Netflix operations support system](https://spring.io/projects/spring-cloud-netflix) and services [token](https://en.wikipedia.org/wiki/JSON_Web_Token) security.

##### Configuration Service
This service provides configurations for all other services (centralized configuration for all services).  
See more: https://cloud.spring.io/spring-cloud-config/multi/multi__spring_cloud_config_server.html

##### Gateway Service
This service is responsible for mapping requests from a client to the desired service endpoint. It based on [Hystrix](https://github.com/Netflix/Hystrix).  
See more: http://microservices.io/patterns/apigateway.html

##### Discovery Service
Eureka Server is service discovery for your microservices, where all client applications can register by themselves and other microservices look up the Eureka Server to get independent microservices to get the job complete.
Eureka Server is also known as Discovery Server and it contains all the information about client microservices running on which IP address and port.  
See more: https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html  
Eureka service URL: http://localhost:8001/eureka

##### Authentication Service
Coming soon !

##### Monitoring Service
Comming soon !

#### Feature Services

### How to start
Please run services as the following orders:
```
1) Start the configuration service
2) Start the eureka service
3) Start the api-gateway service
5) Start the monitoring service
6) Start the auth-role service
7) Now you can start other future services
```

### Configurations
All configuration files can be found here: https://github.com/congcoi123/product-order-services-configuration

### Documentations
Each service directory contains their own `README` file and `ENDPOINTS` documentation.

### How to deploy
Coming soon !

> Happy coding !
