package com.github.fainaaa.entities;

import java.util.Objects;

public class Phrase {
    private String phrase;
    private String translation;
    private String description;
    private boolean isAnswered = false;
    public Phrase(String phrase, String translation, String description){
        this.phrase = phrase;
        this.description = description;
        this.translation = translation;
    }

    public Phrase(){

    }
    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAnswered() {
        return isAnswered;
    }
    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    @Override
    public String toString() {
        return "Phrase{" +
                "phrase='" + phrase + '\'' +
                ", translation='" + translation + '\'' +
                ", description='" + description + '\'' +
                ", isAnswered='" + isAnswered + '\''+ '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phrase phrase1 = (Phrase) o;

        if (isAnswered != phrase1.isAnswered) return false;
        if (!Objects.equals(phrase, phrase1.phrase)) return false;
        if (!Objects.equals(translation, phrase1.translation)) return false;
        return Objects.equals(description, phrase1.description);
    }

    @Override
    public int hashCode() {
        int result = phrase != null ? phrase.hashCode() : 0;
        result = 31 * result + (translation != null ? translation.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (isAnswered ? 1 : 0);
        return result;
    }
}
