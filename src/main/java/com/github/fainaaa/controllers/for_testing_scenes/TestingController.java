package com.github.fainaaa.controllers.for_testing_scenes;

import com.github.fainaaa.Launch;
import com.github.fainaaa.controllers.CollectionsSceneController;
import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.Phrase;
import com.github.fainaaa.entities.User;
import com.github.fainaaa.helpers.Scenes;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public abstract class TestingController implements Initializable {

    protected User currentUser;
    protected Collection currentCollection;

    public TestingController(User user, Collection collection){
        this.currentUser = user;
        this.currentCollection = collection;
    }

    protected Phrase currentPhrase;
    @FXML
    private Label studyingCollectionNameLabel;
    @FXML
    protected TextField userAnswerField;
    @FXML
    protected Button nextPartButton;

    @Override
    public void initialize(URL url, ResourceBundle bundle){
        setStudyingCollectionNameLabel();
        Collections.shuffle(currentCollection.getPhrases());
        setNextPhrase();
    }
    protected void setStudyingCollectionNameLabel(){
        studyingCollectionNameLabel.setText("Current collection: " + currentCollection.getName());
    }
    @FXML
    protected void onClickReady(Event event){
        validateUserAnswer();
        boolean isNotAnsweredPhrasePresent = setNextPhrase();
        if(!isNotAnsweredPhrasePresent){
            setElementsDisable();
            reportThatAllPhrasesAnswered();
            enableComingToTheNextPart();
        }
    }
    protected abstract void setElementsDisable();
    protected abstract boolean setNextPhrase();
    protected abstract void validateUserAnswer();
    protected abstract void reportThatAllPhrasesAnswered();

    @FXML
    private void onClickSkipCurrent(MouseEvent event){
        currentPhrase.setAnswered(true);
        currentPhrase.setAnsweredCorrect(false);
        setNextPhrase();
    }
    @FXML
    private void onClickGoBack(MouseEvent event){
        URL previousSceneUrl = Launch.class.getResource("scenes/collectionsScene.fxml");
        Scenes.sceneChange(event, previousSceneUrl, new CollectionsSceneController(currentUser));
    }
    @FXML
    protected void onClickNextPart(MouseEvent event){
        resetAllIsAnsweredStatuses();
    }
    protected void resetAllIsAnsweredStatuses(){
        for(Phrase p: currentCollection.getPhrases()){
            p.setAnswered(false);
            p.setAnsweredCorrect(false);
        }
    }

    protected void clearUserAnswerField(){
        userAnswerField.setText("");
    }
    private void enableComingToTheNextPart(){
        nextPartButton.setDisable(false);
    }

}
