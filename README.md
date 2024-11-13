# Spring micrometer observation demo

## Running the application

### Prerequisites

- Java 21
- Docker
- Shell (to run scripts below)

### Locally

To start/restart docker containers
```shell
 sh ./compose/start_local_compose.sh 
```

To stop docker containers
```shell
 sh ./compose/stop_local_compose.sh 
```

- Once started you can call REST API's to localhost:8080 using Bruno's collection 
  [spring-micrometer-observation](bruno%2Fspring-micrometer-observation) or any other tool.
- To reach Prometheus UI use http://localhost:9090/ in your browser

