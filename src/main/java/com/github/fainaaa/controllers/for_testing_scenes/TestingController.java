package com.github.fainaaa.controllers.for_testing_scenes;

import com.github.fainaaa.Launch;
import com.github.fainaaa.controllers.CollectionsSceneController;
import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.Phrase;
import com.github.fainaaa.entities.User;
import com.github.fainaaa.entities.for_grading_test.CommonTestResult;
import com.github.fainaaa.entities.for_grading_test.OnePartOfTestResult;
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
    protected CommonTestResult commonTestResult;

    public TestingController(User user, Collection collection, CommonTestResult commonTestResult){
        this.currentUser = user;
        this.currentCollection = collection;
        this.commonTestResult = commonTestResult;
        currentTestResult = new OnePartOfTestResult();
    }
    protected OnePartOfTestResult currentTestResult;
    protected Phrase currentPhrase;
    protected TestPartsNumbers currentTestPart;

    @FXML
    private Label studyingCollectionNameLabel;
    @FXML
    protected TextField userAnswerField;
    @FXML
    protected Button nextPartButton;
    @FXML
    protected Button skipCurrentButton;
    @FXML
    protected Button readyButton;

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
    protected void setElementsDisable(){
        skipCurrentButton.setDisable(true);
        readyButton.setDisable(true);
    }
    protected abstract boolean setNextPhrase();
    protected abstract void validateUserAnswer();
    protected abstract void reportThatAllPhrasesAnswered();

    @FXML
    protected void onClickSkipCurrent(MouseEvent event){
        currentPhrase.setAnswered(true);
        currentTestResult.setNumberOfUnAnswered(currentTestResult.getNumberOfUnAnswered()+1);
        boolean isNotAnsweredPhrasePresent = setNextPhrase();
        if(!isNotAnsweredPhrasePresent) {
            setElementsDisable();
            reportThatAllPhrasesAnswered();
            enableComingToTheNextPart();
        }
    }
    @FXML
    private void onClickGoBack(MouseEvent event){
        URL previousSceneUrl = Launch.class.getResource("scenes/collectionsScene.fxml");
        Scenes.sceneChange(event, previousSceneUrl, new CollectionsSceneController(currentUser));
    }
    @FXML
    protected void onClickNextPart(MouseEvent event){
        resetAllIsAnsweredStatuses();
        commonTestResult.getResultsOfAllParts()[currentTestPart.ordinal()] = currentTestResult;
        System.out.println(currentTestResult.getTestingPhrases());
        System.out.println("Отвечено верно" + currentTestResult.getNumberOfAnsweredCorrect());
        System.out.println("Не отвечено " + currentTestResult.getNumberOfUnAnswered());
    }
    protected void resetAllIsAnsweredStatuses(){
        for(Phrase p: currentCollection.getPhrases()){
            p.setAnswered(false);
        }
    }

    protected void clearUserAnswerField(){
        userAnswerField.setText("");
    }
    private void enableComingToTheNextPart(){
        nextPartButton.setDisable(false);
    }

}
