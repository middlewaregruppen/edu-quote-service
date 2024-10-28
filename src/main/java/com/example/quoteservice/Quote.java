package com.example.quoteservice;

public class Quote {
  private String text;
  private String source;

  public Quote(String text, String source) {
    this.text = text;
    this.source = source;
  }

  // Getters and setters
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }
}