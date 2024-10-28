package com.example.quoteservice;

import com.github.javafaker.Faker;

import java.util.HashMap;

public class QuoteService {
  private final Faker faker = new Faker();
  private String currentMovie;

  private final String[] movies = {
      "starwars",
      "harrypotter",
      "gameofthrones",
      "lebowski",
      "princessbride",
      "rickandmorty",
      "hitchhiker"
  };

  private HashMap<String, String> movieNames;

  public QuoteService() {
    movieNames = new HashMap<String, String>();
    movieNames.put("starwars", "Star Wars");
    movieNames.put("harrypotter", "Harry Potter");
    movieNames.put("gameofthrones", "Game of Thrones");
    movieNames.put("lebowski", "The Big Lebowski");
    movieNames.put("princessbride", "The Princess Bride");
    movieNames.put("rickandmorty", "Rick and Morty");
    movieNames.put("hitchhiker", "The Hitchhiker's Guide to the Galaxy");
  }

  public void setMovie(String movie) {
    currentMovie = movie;
  }

  public String getMovie() {
    return currentMovie;
  }

  public String getMovieName(String key) {
    return movieNames.get(key);
  }

  public String randomQuote() {
    String quote = "";

    currentMovie = movies[(int) (Math.random() * movies.length)];

    switch (currentMovie) {
      case "starwars":
        quote = faker.yoda().quote();
        break;
      case "harrypotter":
        quote = faker.harryPotter().quote();
        break;
      case "gameofthrones":
        quote = faker.gameOfThrones().quote();
        break;
      case "lebowski":
        quote = faker.lebowski().quote();
        break;
      case "princessbride":
        quote = faker.princessBride().quote();
        break;
      case "rickandmorty":
        quote = faker.rickAndMorty().quote();
        break;
      case "hitchhiker":
        quote = faker.hitchhikersGuideToTheGalaxy().quote();
        break;
      default:
        quote = "No quotes available for " + currentMovie;
        break;
    }

    return quote;
  }

}
