package se.vgregion.k8s.quotes.common;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QuoteService {

  @Value("${QUOTE_SERVICE:HITCHHIKER}")
  private String quoteConfig;

  private final Faker faker;

  public QuoteService() {
    faker = Faker.instance();
  }

  public String getFakeQuote() {
    final String quote;

    switch (quoteConfig.toUpperCase()) {
      case "CHUCK":
        quote = faker.chuckNorris().fact();
        break;
      case "HOBBIT":
        quote = faker.hobbit().quote();
        break;
      case "HITCHHIKER":
        quote = faker.hitchhikersGuideToTheGalaxy().quote();
        break;
      case "LEBOWSKI":
        quote = faker.lebowski().quote();
        break;
      case "RICKANDMORTY":
        quote = faker.rickAndMorty().quote();
        break;
      default:
        quote = faker.hitchhikersGuideToTheGalaxy().quote();
    }
    return quote;
  }
}
