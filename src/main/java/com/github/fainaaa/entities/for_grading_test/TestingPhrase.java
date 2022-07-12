package com.github.fainaaa.entities.for_grading_test;

public class TestingPhrase {
    String givenPhrase;
    String correctAnswer;
    String userAnswer;

    public TestingPhrase(){

    }
    public TestingPhrase(String givenPhrase, String correctAnswer, String userAnswer) {
        this.givenPhrase = givenPhrase;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
    }

    public String getGivenPhrase() {
        return givenPhrase;
    }

    public void setGivenPhrase(String givenPhrase) {
        this.givenPhrase = givenPhrase;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    @Override
    public String toString() {
        return "TestingPhrase{" +
                "givenPhrase='" + givenPhrase + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", userAnswer='" + userAnswer + '\'' +
                '}';
    }
}
