package com.github.fainaaa.controllers.for_studying_scenes;

import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class RepeatingSceneController extends StudyingController implements Initializable {

    private static final Logger logger = LogManager.getLogger(RepeatingSceneController.class);
    public RepeatingSceneController(User user, Collection collection){
        super(user, collection);
    }
    @FXML
    private TextField userAnswerField;
    @FXML
    private Label phraseLabel;

    @FXML
    private Button seeTranslationButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        logger.info("On repeating scene");
    }
    @Override
    protected void validateUserAnswer(){
        List<String> wordsOfCurrentPhraseTranslation = turnTranslationIntoWordsArray(currentPhrase.getTranslation());
        List<String> wordsOfUserTranslation = turnTranslationIntoWordsArray(userAnswerField.getText());
        for(String wordInUserTranslation : wordsOfUserTranslation){
            if(! wordsOfCurrentPhraseTranslation.contains(wordInUserTranslation)) {
                movePhraseToRandomPositionOfPhrasesList();
                return;
            }
        }
        currentPhrase.setAnswered(true);
        increaseAnsweredPhrasesNumber();
    }
    protected List<String> turnTranslationIntoWordsArray(String translation){
        return Arrays.asList(translation.split("(\\s?)([\\s,.!]+)(\\s*)"));
    }
    @Override
    protected boolean setNextPhrase(){
        userAnswerField.setText("");
        int i = 0;
        while(i < currentCollection.getPhrases().size()) {
            if(!currentCollection.getPhrases().get(i).isAnswered()) {
                currentPhrase = currentCollection.getPhrases().get(i);
                phraseLabel.setText(currentPhrase.getPhrase());
                return true;
            }
            else{
                i++;
            }
        }
        return false;
    }
    @Override
    protected void reportThatAllPhrasesAnswered(){
        phraseLabel.setText("All phrases are answered. You can click on Go back button to return to your collections list.");
    }
    @Override
    protected void setButtonsDisable(){
        super.setButtonsDisable();
        seeTranslationButton.setDisable(true);
    }
    @FXML
    private void onClickSeeTranslation() {
        promptLabel.setText("Translation: " + currentPhrase.getTranslation());
    }
}
