# Reactive Quotes Service using Spring WebFlux and Faker

## Prerequisites

- Docker
- Java 11
- kubectl (Kubernetes CLI)
- kustomize (Kubernetes configuration management tool)
- envsubst (Linux command line tool)

## Building the Application (optional)

Fist we need to build our application, create a Docker image and push it to the
VGR Harbor registry so that we can use it later in our Kubernetes cluster.

> Note: There is already a Docker image for version `0.0.1` of this application
> in the VGR Harbor registry, so you can skip this step if you want to use that
> image.

```bash
VGR_USER=johro35
export VGR_USER  # So it can be used by kustomize later

IMAGE_NAME=harbor.vgregion.se/education/quotes-service:0.0.1-$VGR_USER

# Build the Docker image - if on Mac M1
docker buildx build --platform linux/amd64,linux/arm64 -t $IMAGE_NAME --push .

# Build the Docker image - if NOT on Mac M1
docker build -t $IMAGE_NAME --push .

docker run -d -p 8080:8080 $IMAGE_NAME    #<3>
docker run -d -p 6565:6565 -e GRPC_ENABLED=true $IMAGE_NAME    #<4>
```

1. Build an image.
2. Push an image to Docker Hub.
3. Run microservice app locally.
4. Run gRPC service locally.

## Deploying the Application to Kubernetes

We will deploy two flavours of the application to Kubernetes, one that will get
quotes from `The Hitchhiker's Guide to the Galaxy` and another that will get quotes
from `The Big Lebowski`.

> Note: We are using some ENV variable substitution so that we can reuse the same
> manifests for both deployments. This is why we are using `envsubst` in the
> commands below.

> Note: Make sure to run these commands when connected to the education cluster
> and also in your own namespace

```bash
export QUOTES_SOURCE=hitchhikers
# Deploy the Hitchhikers Guide quotes service
kustomize build kubernetes/quote-service | envsubst | kubectl apply -n $VGR_USER -f - # <5>

export QUOTES_SOURCE=lebowski
# Deploy the Big Lebowski quotes service
kustomize build kubernetes/quote-service | envsubst | kubectl apply -n $VGR_USER -f - # <6>

# Watch to see the external IP appear, then the ingress is ready Ctrl-C to exit wait mode
kubectl get ing -A -w
```

<5> Deploy apps, services, ingress for The Hitchhikers Guide quotes service.
<6> Deploy apps, services, ingress for The Big Lebowski quotes service.

## Generic version

We can also deploy other versions of the application that will get quotes from
one of the other sources available. This also uses the `QUOTES_SOURCE` environment
variable to indicate which quotes service to install.

You can deploy any of the following quptes sources by setting the `ENV` var
`QUOTES_SOURCE` to one of the following values:

- `chuck` - Chuck Norris quotes
- `hobbit` - The Hobbit quotes
- `hitchhiker` - The Hitchhiker's Guide to the Galaxy quotes
- `lebowski` - The Big Lebowski quotes
- `rickandmorty` - Rick and Morty quotes

For example, we will change the value of the `QUOTES_SOURCE` env var to `chuck`
to get Chuck Norris quotes and deploy the application.

```bash
export QUOTES_SOURCE=hitchhiker
# Deploy the Hitchhikers Guide quotes service
kustomize build k8s | envsubst | kubectl apply -f -

kubectl get ing -A -w       # Watch to see the external IP appear, then the ingress is ready Ctrl-C to exit wait mode
```
