# Reactive Quotes Service using Spring WebFlux and Faker

## Prerequisites

- Docker
- Java 11
- kubectl (Kubernetes CLI)

## Building the Application

Fist we need to build our application, create a Docker image and push it to the
VGR Harbor registry so that we can use it later in our Kubernetes cluster.

```bash
VGR_USER=johro35
IMAGE_NAME=harbor.vgregion.se/education/quotes-service:0.0.1-$VGR_USER

./gradlew bootBuildImage --imageName $IMAGE_NAME    #<1>

docker push $IMAGE_NAME   #<2>

docker run -d -p 8080:8080 $IMAGE_NAME    #<3>
docker run -d -p 6565:6565 -e GRPC_ENABLED=true $IMAGE_NAME    #<4>
```

---

1. Build an image.
2. Push an image to Docker Hub.
3. Run microservice app locally.
4. Run gRPC service locally.

## Deploying the Application to Kubernetes

We will deploy two flavours of the application to Kubernetes, one that will get
quotes from the Hitchhiker's Guide to the Galaxy and another that will get quotes
from The Big Lebowski.

```bash
kubectl apply -f ./kubernetes/hitchhikersguide       #<5>
kubectl apply -f ./kubernetes/biglebowski            #<6>
```

<5> Deploy apps, services, ingress for The Hitchhikers Guide quotes service.
<6> Deploy apps, services, ingress for The Big Lebowski quotes service.
