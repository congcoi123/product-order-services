# Overview
This project contains prototype modules for making a simple product order management system based on micro-service architecture. It based on [Spring](https://spring.io/) framework using Spring Boot, Spring Cloud.

# License
This project is currently available under the [MIT](https://github.com/congcoi123/product-order-services/blob/master/LICENSE) License.

# Installation
You can get the sources:
```
git clone https://github.com/congcoi123/product-order-services.git
```

# Manual
## Project Structure
### System Services
[[link](https://github.com/congcoi123/product-order-services/tree/develop/systems)] These services based on the [Netflix operations support system](https://spring.io/projects/spring-cloud-netflix) and services [token](https://en.wikipedia.org/wiki/JSON_Web_Token) security.

#### Configuration Service
[[link](https://github.com/congcoi123/product-order-services/tree/develop/systems/configuration)] This service provides configurations for all other services (centralized configuration for all services).  
See more: https://cloud.spring.io/spring-cloud-config/multi/multi__spring_cloud_config_server.html

#### Gateway Service
[[link](https://github.com/congcoi123/product-order-services/tree/develop/systems/api-gateway)] This service is responsible for mapping requests from a client to the desired service endpoint. It based on [Hystrix](https://github.com/Netflix/Hystrix).  
See more: http://microservices.io/patterns/apigateway.html

#### Discovery Service
[[link](https://github.com/congcoi123/product-order-services/tree/develop/systems/eureka)] This is service discovery for your microservices, where all client applications can register by themselves and other microservices look up the Eureka Server to get independent microservices to get the job complete. It is also known as Discovery Server and it contains all the information about client microservices running on which IP address and port.  
See more: https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html  
Eureka service URL: http://localhost:8001/eureka

#### Authentication Service
[[link](https://github.com/congcoi123/product-order-services/tree/develop/systems/auth-role)] Authorization Server for all other services which grants tokens for the backend resource services. All other secured services must set jwk uri for endpoint implemented on this service.

#### Monitoring Service
[[link](https://github.com/congcoi123/product-order-services/tree/develop/systems/monitoring)] Monitoring an application's health and metrics helps us manage it better, notice unoptimized behavior and get closer to its performance. This especially holds true when we're developing a system with many microservices, where monitoring each service can prove to be crucial when it comes to maintaining our system.

### Feature Services
[[link](https://github.com/congcoi123/product-order-services/tree/develop/services)]
Comming soon !

#### Administration
[[link](https://github.com/congcoi123/product-order-services/tree/develop/services/admin)]
Comming soon !

#### Customer
[[link](https://github.com/congcoi123/product-order-services/tree/develop/services/customer)]
Comming soon !

#### Logger
[[link](https://github.com/congcoi123/product-order-services/tree/develop/services/logger)]
Comming soon !

#### Master Data
[[link](https://github.com/congcoi123/product-order-services/tree/develop/services/master-data)]
Comming soon !

#### Notification
[[link](https://github.com/congcoi123/product-order-services/tree/develop/services/notification)]
Comming soon !

#### Order
[[link](https://github.com/congcoi123/product-order-services/tree/develop/services/order)]
Comming soon !

#### Payment
[[link](https://github.com/congcoi123/product-order-services/tree/develop/services/payment)]
Comming soon !

#### Product
[[link](https://github.com/congcoi123/product-order-services/tree/develop/services/product)]
Comming soon !

#### Shipment
[[link](https://github.com/congcoi123/product-order-services/tree/develop/services/shipment)]
Comming soon !

#### Stock
[[link](https://github.com/congcoi123/product-order-services/tree/develop/services/stock)]
Comming soon !

#### Template
[[link](https://github.com/congcoi123/product-order-services/tree/develop/services/template)]
Comming soon !

## Diagram
Comming soon !

## How to start
Please run services as the following orders:
```
1) Start the configuration service
2) Start the eureka service
3) Start the api-gateway service
5) Start the monitoring service
6) Start the auth-role service
7) Now you can start other feature services
```

## Configurations
All configuration files can be found here: https://github.com/congcoi123/product-order-services-configuration

## Documentations
Each service directory contains their own `README` file and `ENDPOINTS` documentation.

## How to deploy
Coming soon !

> Happy coding !
