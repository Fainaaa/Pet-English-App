package com.github.fainaaa.entities.for_grading_test;

import java.util.ArrayList;
import java.util.List;

public class OnePartOfTestResult {
    private int numberOfAnsweredCorrect;
    private int numberOfUnAnswered;

    List<TestingPhrase> testingPhrases;

    public OnePartOfTestResult(){
        testingPhrases = new ArrayList<>();
    }

    public OnePartOfTestResult(int numberOfAnsweredCorrect, int numberOfUnAnswered, List<TestingPhrase> testingPhrases) {
        this.numberOfAnsweredCorrect = numberOfAnsweredCorrect;
        this.numberOfUnAnswered = numberOfUnAnswered;
        this.testingPhrases = testingPhrases;
    }

    public int getNumberOfAnsweredCorrect() {
        return numberOfAnsweredCorrect;
    }

    public void setNumberOfAnsweredCorrect(int numberOfAnsweredCorrect) {
        this.numberOfAnsweredCorrect = numberOfAnsweredCorrect;
    }

    public int getNumberOfUnAnswered() {
        return numberOfUnAnswered;
    }

    public void setNumberOfUnAnswered(int numberOfUnAnswered) {
        this.numberOfUnAnswered = numberOfUnAnswered;
    }

    public List<TestingPhrase> getTestingPhrases() {
        return testingPhrases;
    }

    public void setTestingPhrases(List<TestingPhrase> testingPhrases) {
        this.testingPhrases = testingPhrases;
    }
}
