package com.github.fainaaa.controllers.for_studying_scenes;

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
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;

public abstract class StudyingController implements Initializable {
    protected Collection currentCollection;
    protected User user;
    public StudyingController(User user, Collection collection){
        this.currentCollection = collection;
        this.user = user;
    }
    protected Phrase currentPhrase;
    protected int answeredPhrasesNumber;

    @Override
    public void initialize(URL url, ResourceBundle bundle){
        setStudyingCollectionNameLabel();
        setAllPhrasesNumberLabel();
        setTooltipForReadyButton();
        Collections.shuffle(currentCollection.getPhrases());
        setNextPhrase();
    }

    @FXML
    private Label studyingCollectionNameLabel;
    protected void setStudyingCollectionNameLabel(){
        studyingCollectionNameLabel.setText("Current collection: " + currentCollection.getName());
    }

    @FXML
    private Label allPhrasesNumberLabel;
    protected void setAllPhrasesNumberLabel(){
        allPhrasesNumberLabel.setText("/" + currentCollection.getPhrasesNumber());
    }

    @FXML
    protected Label promptLabel;
    @FXML
    protected void onClickSeeDescription(){
        if(currentPhrase.getDescription() != null) {
            promptLabel.setText("Description: " + currentPhrase.getDescription());
        }
        else{
            promptLabel.setText("You didn't add description for this phrase");
        }
    }
    protected void hideDescriptionLabel(){
        promptLabel.setText("");
    }

    @FXML
    private Label answeredPhrasesNumberLabel;
    protected void increaseAnsweredPhrasesNumber(){
        answeredPhrasesNumberLabel.setText(String.valueOf(++answeredPhrasesNumber));
    }
    @FXML
    private Button readyButton;
    @FXML Button seeDescriptionButton;
    protected void setTooltipForReadyButton(){
        Tooltip tooltip = new Tooltip("You can press Enter instead \n of clicking on this button.");
        tooltip.setShowDelay(Duration.millis(50));
        readyButton.setTooltip(tooltip);
    }
    @FXML
    protected void onKeyPressedInUserAnswerField(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            onClickReady(event);
        }
    }
    @FXML
    protected void onClickReady(Event event){
        hideDescriptionLabel();
        validateUserAnswer();
        boolean isNotAnsweredPhrasePresent = setNextPhrase();
        if(!isNotAnsweredPhrasePresent){
            setButtonsDisable();
            reportThatAllPhrasesAnswered();
        }
    }

    protected void setButtonsDisable(){
        seeDescriptionButton.setDisable(true);
    }

    protected abstract boolean setNextPhrase();
    protected abstract void validateUserAnswer();
    protected abstract void reportThatAllPhrasesAnswered();

    protected void movePhraseToRandomPositionOfPhrasesList(){
        Random random = new Random();
        int randomIndex = random.nextInt(currentCollection.getPhrases().size());//from 0 to size-1
        currentCollection.getPhrases().remove(currentPhrase);
        currentCollection.getPhrases().add(randomIndex, currentPhrase);
    }

    @FXML
    protected void onClickGoBack(MouseEvent event){
        URL url = Launch.class.getResource("scenes/collectionsScene.fxml");
        Scenes.sceneChange(event, url, new CollectionsSceneController(user));
    }


}
