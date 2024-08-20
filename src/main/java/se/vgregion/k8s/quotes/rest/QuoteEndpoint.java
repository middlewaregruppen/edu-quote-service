package se.vgregion.k8s.quotes.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.vgregion.k8s.quotes.common.QuoteService;

@RestController
@CrossOrigin(origins = "https://quotes-frontend-edu-johro35.k8s.vgregion.se")
@RequiredArgsConstructor
class QuoteEndpoint {

  private final QuoteService quoteService;

  @GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Response> hello() {
    return new ResponseEntity<>(new Response(quoteService.getFakeQuote()), HttpStatus.OK);
  }
}
