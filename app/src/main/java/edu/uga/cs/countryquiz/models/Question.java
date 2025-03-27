package edu.uga.cs.countryquiz.models;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private Country country;
    private List<String> options; // Correct answer + 2 incorrect ones.
    private String correctAnswer;
    private String userAnswer; // Stores the user's selected answer.

    public Question(Country country, List<String> options, String correctAnswer) {
        this.country = country;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.userAnswer = null;
    }

    // Getters and setters
    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }
    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }
    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
    public String getUserAnswer() { return userAnswer; }
    public void setUserAnswer(String userAnswer) { this.userAnswer = userAnswer; }
}
