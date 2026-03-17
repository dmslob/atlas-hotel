# atlas-hotel
## 1. Start config-server
### To access config-server properties
```http://localhost:8071/guest-service/default```

```http://localhost:8071/room-service/default```

```http://localhost:8071/reservation-service/default```

## 2. Start other services

### documentation for guest-service
```http://localhost:8080/swagger-ui/index.html```
### documentation for room-service
```http://localhost:8081/swagger-ui/index.html```
### documentation for reservation-service
```http://localhost:8082/swagger-ui/index.html```

### h2 database for guest-service
```http://localhost:8080/h2-console```
### h2 database for room-service
```http://localhost:8081/h2-console```
### h2 database for reservation-service
```http://localhost:8082/h2-console```

#### To run the app as a docker container
```docker run -d -p 8080:8080 dmslob/guest-service:v1.0.0```

```docker run -d -p 8081:8081 dmslob/room-service:v1.0.0```

```docker run -d -p 8082:8082 dmslob/reservation-service:v1.0.0```

#### To check all actuator endpoints, use:
```http://localhost:{service_port}/actuator```

#### To refresh config properties without restarting the app, use actuator endpoint:
```POST http://localhost:{service_port}/actuator/refresh```
#### Or use Spring Cloud Bus to broadcast refresh event to all services:
#### But before we need to have RabbitMQ running, you can run it using docker:
```docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4-management```

```POST http://localhost:{service_port}/actuator/busrefresh```

#### References:
- Spring cloud config: https://docs.spring.io/spring-cloud-config/reference/server.html