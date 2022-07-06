package com.github.fainaaa.controllers.for_studying_scenes;

import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class MemorizingSceneController extends StudyingController implements Initializable {
    private static final Logger logger = LogManager.getLogger(MemorizingSceneController.class);

    public MemorizingSceneController(User user, Collection collection){
        super(user, collection);
    }

    @FXML
    private TextField userAnswerField;
    @FXML
    private Label translationLabel;
    @FXML
    private Button seePhraseButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        super.initialize(url, resourceBundle);
        logger.info("On memorizing scene");
    }
    @Override
    protected boolean setNextPhrase(){
        userAnswerField.setText("");
        int i = 0;
        while(i < currentCollection.getPhrases().size()) {
            if(!currentCollection.getPhrases().get(i).isAnswered()) {
                currentPhrase = currentCollection.getPhrases().get(i);
                translationLabel.setText(currentPhrase.getTranslation());
                return true;
            }
            else{
                i++;
            }
        }
        return false;
    }

    @Override
    protected void validateUserAnswer(){
        if(currentPhrase.getPhrase().trim().toLowerCase().equals(userAnswerField.getText())){
            currentPhrase.setAnswered(true);
            increaseAnsweredPhrasesNumber();
        }
        else{
            movePhraseToRandomPositionOfPhrasesList();
        }
    }
    @Override
    protected void reportThatAllPhrasesAnswered(){
        translationLabel.setText("All phrases are answered. You can click on Go back button to return to your collections list.");
    }
    @Override
    protected void setButtonsDisable(){
        super.setButtonsDisable();
        seePhraseButton.setDisable(true);
    }
    @FXML
    private void onClickSeePhrase(MouseEvent event){
        promptLabel.setText("Translation: " + currentPhrase.getPhrase());
    }
}
