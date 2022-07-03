package com.github.fainaaa.entities;

import java.util.ArrayList;
import java.util.List;

public class Collection {
    private int ID;
    private String name;
    private int phrasesNumber;
    private List<Phrase> phrases;

    private boolean isAnswered = false;
    private boolean isAnsweredCorrect = false;

    public Collection(String name, int wordsAmount, List<Phrase> words){
        this.name = name;
        this.phrasesNumber = wordsAmount;
        this.phrases = words;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Collection(){
        this.phrases = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhrasesNumber() {
        return phrasesNumber;
    }

    public void setPhrasesNumber(int phrasesNumber) {
        this.phrasesNumber = phrasesNumber;
    }

    public List<Phrase> getPhrases(){
        return phrases;
    }

    public void setPhrases(List<Phrase> phrases) {
        this.phrases = phrases;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public boolean isAnsweredCorrect() {
        return isAnsweredCorrect;
    }

    public void setAnsweredCorrect(boolean answeredCorrect) {
        isAnsweredCorrect = answeredCorrect;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "name='" + name + '\'' +
                ", phrasesNumbert=" + phrasesNumber +
                ", phrases=" + phrases +
                '}';
    }
}
