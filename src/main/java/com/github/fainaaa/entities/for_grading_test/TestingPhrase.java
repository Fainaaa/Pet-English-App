package com.github.fainaaa.entities.for_grading_test;

public class TestingPhrase {
    private String givenPhrase;
    private String correctAnswer;
    private String userAnswer;

    boolean isAnsweredCorrect = false;
    String isAnsweredCorrectForShowingInResultsTable = "-";

    public TestingPhrase(){

    }
    public TestingPhrase(String givenPhrase, String correctAnswer){
        this.givenPhrase = givenPhrase;
        this.correctAnswer = correctAnswer;
    }
    public TestingPhrase(String givenPhrase, String correctAnswer, String userAnswer){
        this(givenPhrase, correctAnswer);
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

    public boolean isAnsweredCorrect() {
        return isAnsweredCorrect;
    }

    public void setAnsweredCorrect(boolean answeredCorrect) {
        isAnsweredCorrect = answeredCorrect;
        setIsAnsweredCorrectForShowingInResultsTable(isAnsweredCorrect);
    }

    public String getIsAnsweredCorrectForShowingInResultsTable() {
        return isAnsweredCorrectForShowingInResultsTable;
    }

    public void setIsAnsweredCorrectForShowingInResultsTable(boolean isAnsweredCorrect) {
        if(isAnsweredCorrect){
            this.isAnsweredCorrectForShowingInResultsTable = "+";
        }
        else{
            this.isAnsweredCorrectForShowingInResultsTable = "-";
        }
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
