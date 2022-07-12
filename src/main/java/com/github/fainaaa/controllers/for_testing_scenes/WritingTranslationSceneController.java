package com.github.fainaaa.controllers.for_testing_scenes;
import com.github.fainaaa.Launch;
import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.Phrase;
import com.github.fainaaa.entities.User;
import com.github.fainaaa.helpers.Scenes;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WritingTranslationSceneController extends TestingController{

    public WritingTranslationSceneController(User user, Collection collection){
        super(user, collection);
    }
    private final boolean ENGLISH = false;
    private boolean currentPhraseLanguage;

    @FXML
    private Button readyButton;
    @FXML
    private Label phraseOrTranslationLabel;
    @FXML
    private Label userAnswerIsEmptyMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        super.initialize(url,resourceBundle);
        setTooltipForReadyButton();
    }
    private void setTooltipForReadyButton(){
        Tooltip tooltip = new Tooltip("You can press Enter instead \n of clicking on this button.");
        tooltip.setShowDelay(Duration.millis(50));
        readyButton.setTooltip(tooltip);
    }
    @FXML
    @Override
    protected void onClickReady(Event event){
        if(userAnswerField.getText().isEmpty()){
            showUserAnswerIsEmptyMessage();
        }
        else{
            super.onClickReady(event);
        }
    }
    private void showUserAnswerIsEmptyMessage(){
        userAnswerIsEmptyMessage.setText("You haven't wrote your answer. Please, do it before clicking on Ready, or click on Skip current button");
    }

    @FXML
    protected void onKeyPressedInUserAnswerField(KeyEvent event){
        hideUserAnswerIsEmptyMessage();
        if(event.getCode() == KeyCode.ENTER){
            onClickReady(event);
        }
    }
    private void hideUserAnswerIsEmptyMessage(){
        userAnswerIsEmptyMessage.setText("");
    }

    @Override
    protected boolean setNextPhrase() {
        clearUserAnswerField();

        List<Phrase> phrases = currentCollection.getPhrases();
        int i = 0;

        while(i < phrases.size()) {
            if(!phrases.get(i).isAnswered()) {
                currentPhrase = phrases.get(i);
                setCurrentPhraseLanguage();
                if(currentPhraseLanguage == ENGLISH){
                    phraseOrTranslationLabel.setText(currentPhrase.getPhrase());
                }
                else{
                    phraseOrTranslationLabel.setText(currentPhrase.getTranslation());
                }
                return true;
            }
            else{
                i++;
            }
        }
        return false;
    }
    private void setCurrentPhraseLanguage(){
        currentPhraseLanguage = getRandomBoolean();
    }
    private boolean getRandomBoolean(){
        double num = Math.random();
        if (num < 0.5){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    protected void validateUserAnswer() {
        currentPhrase.setAnswered(true);
        if(currentPhraseLanguage == ENGLISH){
            if(userAnswerField.getText().trim().toLowerCase().equals(currentPhrase.getTranslation()))
                currentPhrase.setAnsweredCorrect(true);
        }
        else{
            if(userAnswerField.getText().trim().toLowerCase().equals(currentPhrase.getPhrase())){
                currentPhrase.setAnsweredCorrect(true);
            }
        }
    }

    @Override
    protected void reportThatAllPhrasesAnswered() {
        phraseOrTranslationLabel.setText("You've finished the second part of the test. " +
                "Click on Next scene button to continue or click on Go back to stop testing.");
    }
    @FXML
    @Override
    protected void onClickNextPart(MouseEvent event){
        super.onClickNextPart(event);
        URL nextPartUrl = Launch.class.getResource("scenes/anagramScene.fxml");
        Scenes.sceneChange(event, nextPartUrl, new AnagramSceneController(currentUser,currentCollection));
    }
}
