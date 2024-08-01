package se.vgregion.k8s.quotes.rest;

import se.vgregion.k8s.quotes.common.QuoteService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class QuoteEndpoint {

  private final QuoteService quoteService;

  @GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Response> hello() {
    return new ResponseEntity<>(new Response(quoteService.getFakeQuote()), HttpStatus.OK);
  }
}
