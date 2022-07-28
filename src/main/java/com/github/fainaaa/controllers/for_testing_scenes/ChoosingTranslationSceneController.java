package com.github.fainaaa.controllers.for_testing_scenes;

import com.github.fainaaa.Launch;
import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.User;
import com.github.fainaaa.entities.for_grading_test.CommonTestResult;
import com.github.fainaaa.entities.for_grading_test.TestingPhrase;
import com.github.fainaaa.helpers.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

public class ChoosingTranslationSceneController extends TestingController {
    private static final Logger logger = LogManager.getLogger(AnagramSceneController.class);
    public ChoosingTranslationSceneController(User user, Collection collection, CommonTestResult result){
        super(user, collection, result);
        currentTestPart = TestPartsNumbers.FIRST_PART;
    }
    static final int ANSWER_OPTIONS_MAX_NUMBER = 4;
    @FXML
    private ListView<String> answerOptionsListView;
    @FXML
    private Label translationLabel;
    @FXML
    private Label noSelectedOptionMessage;

    private ObservableList<String> answerOptionsObservableList;

    private SelectionModel<String> answerOptionSelectionModel;
    private String selectedAnswerOption;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        initSelectionModel();
        setNextPhrase();
        super.initialize(url,resourceBundle);
    }

    @FXML
    protected void onKeyPressedAfterOptionChoosing(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            onClickReady(event);
        }
    }
    @FXML
    @Override
    protected void onClickReady(Event event){
        hideNoSelectedOptionMessage();
        if(selectedAnswerOption != null){
            super.onClickReady(event);
            clearSelectedAnswerOption();
        }
        else{
            showNoSelectedOptionMessage();
        }

    }
    private void showNoSelectedOptionMessage(){
        noSelectedOptionMessage.setText("You haven't selected answer Option. Do it before clicking on Ready");
    }
    private void hideNoSelectedOptionMessage(){
        noSelectedOptionMessage.setText("");
    }
    @Override
    protected boolean setNextPhrase() {
        int i = 0;
        while(i < currentCollection.getPhrases().size()) {
            if(!currentCollection.getPhrases().get(i).isAnswered()) {
                currentPhrase = currentCollection.getPhrases().get(i);
                translationLabel.setText(currentPhrase.getTranslation());
                getAnswerOptions();
                return true;
            }
            else{
                i++;
            }
        }
        return false;
    }

    private void clearSelectedAnswerOption(){
        selectedAnswerOption = null;
        answerOptionSelectionModel.clearSelection();
    }

    private void getAnswerOptions(){
        Random random = new Random();
        Set<String> uniqueAnswerOptions = new HashSet<>();
        int answerOptionsNumber = Math.min(currentCollection.getPhrasesNumber(), ANSWER_OPTIONS_MAX_NUMBER);
        uniqueAnswerOptions.add(currentPhrase.getPhrase());
        while(uniqueAnswerOptions.size()<answerOptionsNumber){
            int randomIndex = random.nextInt(currentCollection.getPhrasesNumber());
            uniqueAnswerOptions.add(currentCollection.getPhrases().get(randomIndex).getPhrase());
        }
        answerOptionsObservableList = FXCollections.observableArrayList(uniqueAnswerOptions);
        initListViewWithObservableList();
    }
    private void initListViewWithObservableList(){
        answerOptionsListView.setItems(answerOptionsObservableList);
    }
    private void initSelectionModel(){
        answerOptionSelectionModel = answerOptionsListView.getSelectionModel();
        answerOptionSelectionModel.selectedItemProperty().addListener((observableValue, previousItem, currentItem) -> {
            selectedAnswerOption = currentItem;
        });
    }

    @Override
    protected void validateUserAnswer() {
        currentPhrase.setAnswered(true);
        TestingPhrase testingPhrase = new TestingPhrase(currentPhrase.getTranslation(),
                currentPhrase.getPhrase(), selectedAnswerOption);
        if(currentPhrase.getPhrase().equals(selectedAnswerOption)){
            currentTestResult.setNumberOfAnsweredCorrect(currentTestResult.getNumberOfAnsweredCorrect()+1);
            testingPhrase.setAnsweredCorrect(true);
        }
        currentTestResult.getTestingPhrases().add(testingPhrase);
    }

    @Override
    protected void reportThatAllPhrasesAnswered() {
        translationLabel.setText("You've finished the first part of the test. " +
                "Click on Next part button to continue or click on Go back to stop testing.");
    }
    @Override
    protected void setElementsDisable() {
        skipCurrentButton.setDisable(true);
        answerOptionsListView.setDisable(true);
    }

    @FXML
    @Override
    protected void onClickNextPart(MouseEvent event){
        super.onClickNextPart(event);
        URL nextPartUrl = Launch.class.getResource("scenes/writingTranslationScene.fxml");
        Scenes.sceneChange(event, nextPartUrl, new WritingTranslationSceneController(currentUser,currentCollection, commonTestResult));
    }

    @FXML
    @Override
    protected void onClickSkipCurrent(MouseEvent event){
        hideNoSelectedOptionMessage();
        currentTestResult
                .getTestingPhrases()
                .add(new TestingPhrase(currentPhrase.getTranslation(), currentPhrase.getPhrase()));
        super.onClickSkipCurrent(event);
    }
}
