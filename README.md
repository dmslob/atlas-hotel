# atlas-hotel
#### To build docker image for each service:
```mvn spring-boot:build-image```

#### To push the image to docker hub, run for each service:
```docker image push docker.io/dmslob/config-server:v1```

```docker image push docker.io/dmslob/eurekaserver:v1```

```docker image push docker.io/dmslob/gatewayserver:v1```

```docker image push docker.io/dmslob/guest-service:v1```

```docker image push docker.io/dmslob/room-service:v1```

```docker image push docker.io/dmslob/reservation-service:v1```

#### To test rate limiter, we can use Apache Benchmark tool, for example:
#### Here we are testing guest-service endpoint with 10 requests and concurrency of 2, and verbose output:
```ab -n 10 -c 2 -v 3 http://localhost:8072/reservations```

#### To run container locally, run command from docker-compose/default folder:
```docker compose up -d```

#### To access Eureka server dashboard:
```http://localhost:8070```
```http://localhost:8070/eureka/apps```

#### To access config-server properties
```http://localhost:8071/guest-service/default```

```http://localhost:8071/room-service/default```

```http://localhost:8071/reservation-service/default```

#### documentation for guest-service
```http://localhost:8080/swagger-ui/index.html```
#### documentation for room-service
```http://localhost:8081/swagger-ui/index.html```
#### documentation for reservation-service
```http://localhost:8082/swagger-ui/index.html```

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

#### Grafana dashboard for monitoring:
```http://localhost:3000```
```admin:12345```

#### Grafana dashboards:
```https://grafana.com/grafana/dashboards/4701-jvm-micrometer/```

```https://grafana.com/grafana/dashboards/11378-justai-system-monitor/```

#### Prometheus endpoint for scraping metrics:
```http://localhost:{port}/actuator/prometheus```

#### Prometheus dashboard:
```http://localhost:9090/targets```

#### References:
- Spring cloud config: https://docs.spring.io/spring-cloud-config/reference/server.html