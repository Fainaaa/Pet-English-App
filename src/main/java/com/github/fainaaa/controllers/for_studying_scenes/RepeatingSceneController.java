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
import java.util.Collections;
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
        logger.info("On repeating scene");
        setStudyingCollectionNameLabel();
        setAllPhrasesNumberLabel();
        setTooltipForReadyButton();
        Collections.shuffle(currentCollection.getPhrases());
        setNextPhrase();
    }
    @Override
    protected void validateUserAnswer(){
        if(currentPhrase.getTranslation().trim().toLowerCase().equals(userAnswerField.getText())){
            currentPhrase.setAnswered(true);
            increaseAnsweredPhrasesNumber();
        }
        else{
            movePhraseToRandomPositionOfPhrasesList();
        }
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