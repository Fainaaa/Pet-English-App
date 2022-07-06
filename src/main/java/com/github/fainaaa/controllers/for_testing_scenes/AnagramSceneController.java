package com.github.fainaaa.controllers.for_testing_scenes;

import com.github.fainaaa.Launch;
import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.User;
import com.github.fainaaa.helpers.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AnagramSceneController extends TestingController{
    private static final Logger logger = LogManager.getLogger(AnagramSceneController.class);
    public AnagramSceneController(User user, Collection collection){
        super(user, collection);
    }
    @FXML
    private Button skipCurrentButton;
    @FXML
    private Label anagramLabel;

    @Override
    protected boolean setNextPhrase() {
        clearUserAnswerField();
        int i = 0;
        while(i < currentCollection.getPhrases().size()) {
            if(!currentCollection.getPhrases().get(i).isAnswered()) {
                currentPhrase = currentCollection.getPhrases().get(i);
                anagramLabel.setText(getAnagramFromCurrentPhrase());
                return true;
            }
            else{
                i++;
            }
        }
        return false;
    }
    private String getAnagramFromCurrentPhrase(){
        List<String> lettersArray = Arrays.asList(currentPhrase.getPhrase().split("[.!,-]*"));
        Collections.shuffle(lettersArray);
        return lettersArray.toString().replace("[", "").replace("]","");
    }

    @Override
    protected void validateUserAnswer() {
        currentPhrase.setAnswered(true);
        if(currentPhrase.getPhrase().trim().toLowerCase().equals(userAnswerField.getText().trim().toLowerCase())){
            currentPhrase.setAnsweredCorrect(true);
        }
        else{
            currentPhrase.setAnsweredCorrect(false);
        }
    }
    @Override
    protected void setElementsDisable() {
        skipCurrentButton.setDisable(true);
    }

    @Override
    protected void reportThatAllPhrasesAnswered() {
        anagramLabel.setText("You've finished the first part of the test. " +
                "Click on ready again to continue or click on Go back to stop testing.");
    }

    @FXML
    protected void onKeyPressedInUserAnswerField(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            onClickReady(event);
        }
    }

    @FXML
    @Override
    protected void onClickNextPart(MouseEvent event){
        super.onClickNextPart(event);
        URL nextPartUrl = Launch.class.getResource("scenes/...fxml");
        Scenes.sceneChange(event, nextPartUrl, new AnagramSceneController(currentUser,currentCollection));
    }


}
