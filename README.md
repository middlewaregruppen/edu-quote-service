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

## So... what wonders await?

Well... you will be rewarded with random quotes from the following films/tv series

- Star Wars
- Harry Potter
- Game of Thrones
- The Big Lebowski
- The Princess Bride
- Rick and Morty
- The Hitchhikers Guide To The Galaxy

## Prometheus metrics

We also expose an endpoint for scraping Prometheus metrics via the Actuator, so
if you go to:

[http://<your-host></your-host>:8080/actuator/prometheus](http://[your-host]:8080/actuator/prometheus)

You will get the metrics in Prometheus format.

I've added a few `custom` metrics that were defined in the code
(see [here](./src/main/java/com/example/quoteservice/QuoteController.java)).

These are:

- `http_requests_length`: a Histogram metric that tracks the request lenghts
- `open_db_connections`: a fake Gauge metric that tracks a value that might increase/decrease
- `http_requests_total`: A counter that keeps track of the requests processed (resets on restart of the app)
- `http_errors_total`: A counter that keeps track of the error (resets on restart of the app)
