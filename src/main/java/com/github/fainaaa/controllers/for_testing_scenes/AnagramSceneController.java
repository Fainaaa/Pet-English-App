package com.github.fainaaa.controllers.for_testing_scenes;

import com.github.fainaaa.Launch;
import com.github.fainaaa.controllers.for_results_scenes.CommonResultsSceneController;
import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.User;
import com.github.fainaaa.entities.for_grading_test.CommonTestResult;
import com.github.fainaaa.entities.for_grading_test.TestingPhrase;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class AnagramSceneController extends TestingController{
    private static final Logger logger = LogManager.getLogger(AnagramSceneController.class);
    public AnagramSceneController(User user, Collection collection, CommonTestResult result){
        super(user, collection, result);
        currentTestPart = TestPartsNumbers.THIRD_PART;
    }
    @FXML
    private Label anagramLabel;
    @FXML
    private Button readyButton;
    @FXML
    private Label userAnswerIsEmptyMessage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        super.initialize(url, resourceBundle);
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
            showNoSelectedOptionMessage();
        }
        else{
            super.onClickReady(event);
        }
    }

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
        TestingPhrase testingPhrase = new TestingPhrase(anagramLabel.getText(),
                currentPhrase.getPhrase(), userAnswerField.getText());
        if (currentPhrase.getPhrase().equalsIgnoreCase(userAnswerField.getText().trim().toLowerCase())) {
            currentTestResult.setNumberOfAnsweredCorrect(currentTestResult.getNumberOfAnsweredCorrect()+1);
            testingPhrase.setAnsweredCorrect(true);
        }
        currentTestResult.getTestingPhrases().add(testingPhrase);
    }

    private void showNoSelectedOptionMessage(){
        userAnswerIsEmptyMessage.setText("You haven't wrote your answer. Please, do it before clicking on Ready, or click on Skip current button");
    }
    private void hideNoSelectedOptionMessage(){
        userAnswerIsEmptyMessage.setText("");
    }
    @Override
    protected void reportThatAllPhrasesAnswered() {
        anagramLabel.setText("You've finished the first part of the test. " +
                "Click on ready again to continue or click on Go back to stop testing.");
    }

    @FXML
    protected void onKeyPressedInUserAnswerField(KeyEvent event){
        hideNoSelectedOptionMessage();
        if(event.getCode() == KeyCode.ENTER){
            onClickReady(event);
        }
    }
    @FXML
    @Override
    protected void onClickSkipCurrent(MouseEvent event){
        currentTestResult
                .getTestingPhrases()
                .add(new TestingPhrase(anagramLabel.getText(), currentPhrase.getPhrase()));
        super.onClickSkipCurrent(event);
    }
    @FXML
    @Override
    protected void onClickNextPart(MouseEvent event){
        super.onClickNextPart(event);
        URL nextPartUrl = Launch.class.getResource("scenes/commonResultsScene.fxml");
        Scenes.sceneChange(event, nextPartUrl, new CommonResultsSceneController(currentUser, commonTestResult));
    }


}
