package com.github.fainaaa.entities.for_grading_test;

import java.util.Arrays;

public class CommonTestResult {
    private int numberOfAllPhrases;
    OnePartOfTestResult [] resultsOfAllParts;

    public CommonTestResult(int numberOfAllPhrases, OnePartOfTestResult [] resultsOfAllParts){
        this.numberOfAllPhrases = numberOfAllPhrases;
        this.resultsOfAllParts = resultsOfAllParts;
    }

    public int getNumberOfAllPhrases() {
        return numberOfAllPhrases;
    }

    public void setNumberOfAllPhrases(int numberOfAllPhrases) {
        this.numberOfAllPhrases = numberOfAllPhrases;
    }

    public OnePartOfTestResult[] getResultsOfAllParts() {
        return resultsOfAllParts;
    }

    public void setResultsOfAllParts(OnePartOfTestResult[] resultsOfAllParts) {
        this.resultsOfAllParts = resultsOfAllParts;
    }

    @Override
    public String toString() {
        return "CommonTestResult{" +
                "numberOfAllPhrases=" + numberOfAllPhrases +
                ", resultsOfAllParts=" + Arrays.toString(resultsOfAllParts) +
                '}';
    }
}