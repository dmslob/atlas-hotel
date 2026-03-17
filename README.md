# atlas-hotel
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

### to access config-server properties
```http://localhost:8071/guest-service/default```

```http://localhost:8071/room-service/default```

```http://localhost:8071/reservation-service/default```
