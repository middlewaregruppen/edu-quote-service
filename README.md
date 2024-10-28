# 🪴 Random Quote Generator Service

This is a simple service that generates random quotes. It is implemented using
the Faker API to generate random quotes.

## Building the Docker image

To build the Docker image, run the following command:

```bash
docker build -t ghcr.io/middlewaregruppen/quote-service:1.0.0 . --push

# If you're on a Mac M1 or M2, then
docker buildx build --platform linux/amd64,linux/arm64 -t ghcr.io/middlewaregruppen/quote-service:1.0.0 --push .
```
