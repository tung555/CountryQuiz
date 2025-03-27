package edu.uga.cs.countryquiz.models;

public class QuizResult {
    private String quizDate;
    private int quizScore;

    public QuizResult(String quizDate, int quizScore) {
        this.quizDate = quizDate;
        this.quizScore = quizScore;
    }

    public String getQuizDate() {
        return quizDate;
    }
    public int getQuizScore() {
        return quizScore;
    }
}
