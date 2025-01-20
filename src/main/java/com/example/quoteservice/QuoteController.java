package com.example.quoteservice;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class QuoteController {

  private final Logger LOG = LoggerFactory.getLogger(QuoteController.class);

  private final QuoteService quoteService = new QuoteService();

  private final DistributionSummary requestLengthSummary;

  MeterRegistry meterRegistry;

  private final Random random = new Random();

  public QuoteController(@Autowired MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;

    requestLengthSummary = DistributionSummary
        .builder("http_requests_length")
        .description("Length of requests for quotes")
        .publishPercentileHistogram()
        .register(meterRegistry);

    Gauge.builder("open_db_connections", this, QuoteController::getActiveConnections)
        .description("Number of fake database connections")
        .register(meterRegistry);
  }

  @CrossOrigin(origins = "https://quotes-frontend-demo.me, https://edu-react-frontend.localdev.me")
  @GetMapping("/quote")
  public ResponseEntity<Quote> getQuote() {
    long start = System.currentTimeMillis();

    HttpStatus status = HttpStatus.OK;

    LOG.info("Getting random quote");
    String quote = quoteService.randomQuote();
    String movieName = "Unknown";

    recordVisit();

    LOG.info("Looking up movie: " + quoteService.getMovie());

    if (quoteService.getMovie() == "princessbride") {
      if (random.nextBoolean()) {
        status = HttpStatus.NOT_IMPLEMENTED;
        recordError(status, "Movie lookup not enabled");
        LOG.warn("Skipping movie lookup - not available");
        movieName = "Unavailable";
      } else {
        status = HttpStatus.NOT_FOUND;
        recordError(status, "Movie not found");
        LOG.error("Error looking up movie: " + quoteService.getMovie(), "Movie not found");
        movieName = "Not found";
      }
    } else {
      movieName = quoteService.getMovieName(quoteService.getMovie());
    }

    recordVisitLength(start, System.currentTimeMillis());

    return new ResponseEntity<Quote>(new Quote(quote, movieName), status);
  }

  /**
   * Record the visit as a Counter metric
   */
  private void recordVisit() {
    Counter.builder("http_requests_total")
        .description("Total number of HTTP requests")
        .register(meterRegistry)
        .increment();
  }

  /**
   * Record the length of the visit as a Histogram metric
   * 
   * @param start start time
   * @param end   end time
   */
  private void recordVisitLength(long start, long end) {
    requestLengthSummary.record(end - start);
  }

  /**
   * Record an Error as a tagged Counter metric
   * 
   * @param status HTTP status code
   * @param error  the error message
   */
  private void recordError(HttpStatus status, String error) {
    // Randomly choose between two different error codes
    Counter.builder("http_errors_total")
        .description("Total number of HTTP errors")
        .tag("status", String.valueOf(status.value()))
        .tag("error", error)
        .register(meterRegistry)
        .increment();
  }

  /**
   * Fake method to return a random number of active connections
   * 
   * @return random number between 0 and 100
   */
  private int getActiveConnections() {
    return random.nextInt(100);
  }
}