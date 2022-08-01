package com.github.fainaaa.controllers.for_results_scenes;

import com.github.fainaaa.Launch;
import com.github.fainaaa.controllers.for_testing_scenes.TestPartsNumbers;
import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.User;
import com.github.fainaaa.entities.for_grading_test.CommonTestResult;
import com.github.fainaaa.helpers.Scenes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CommonResultsSceneController implements Initializable{
    User currentUser;
    CommonTestResult commonTestResult;

    public CommonResultsSceneController(User user, CommonTestResult commonTestResult){
        this.currentUser = user;
        this.commonTestResult = commonTestResult;
    }

    @FXML
    private ProgressBar commonTestProgressBar;
    @FXML
    private Label commonTestPercent;
    @FXML
    private ProgressBar firstPartProgressBar;
    @FXML
    private Label firstPartPercent;
    @FXML
    private ProgressBar secondPartProgressBar;
    @FXML
    private Label secondPartPercent;
    @FXML
    private ProgressBar thirdPartProgressBar;
    @FXML
    private Label thirdPartPercent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCommonTestProgress();
        showTestProgressForOnePart(TestPartsNumbers.FIRST_PART, firstPartProgressBar, firstPartPercent);
        showTestProgressForOnePart(TestPartsNumbers.SECOND_PART, secondPartProgressBar, secondPartPercent);
        showTestProgressForOnePart(TestPartsNumbers.THIRD_PART, thirdPartProgressBar, thirdPartPercent);
    }
    private void showCommonTestProgress(){
        int allPhrasesNumber = commonTestResult.getNumberOfAllPhrases() * commonTestResult.getResultsOfAllParts().length;
        int answeredPhrases = commonTestResult.getResultsOfAllParts()[TestPartsNumbers.FIRST_PART.ordinal()].getNumberOfAnsweredCorrect() +
                commonTestResult.getResultsOfAllParts()[TestPartsNumbers.SECOND_PART.ordinal()].getNumberOfAnsweredCorrect() +
                commonTestResult.getResultsOfAllParts()[TestPartsNumbers.THIRD_PART.ordinal()].getNumberOfAnsweredCorrect();

        ProgressBarEngine commonTestProgress = new ProgressBarEngine(allPhrasesNumber, answeredPhrases);
        Thread commonTestThread = new Thread(commonTestProgress);
        commonTestThread.start();
        commonTestProgressBar.progressProperty().bind(commonTestProgress.progressProperty());
        commonTestPercent.textProperty().bind(commonTestProgress.messageProperty());
    }
    private void showTestProgressForOnePart(TestPartsNumbers showingPart, ProgressBar certainPartBar, Label certainLabel){
        ProgressBarEngine progressBarEngine = new ProgressBarEngine(commonTestResult.getNumberOfAllPhrases(),
                commonTestResult.getResultsOfAllParts()[showingPart.ordinal()].getNumberOfAnsweredCorrect());
        Thread certainPartProgressThread = new Thread(progressBarEngine);
        certainPartProgressThread.start();
        certainPartBar.progressProperty().bind(progressBarEngine.progressProperty());
        certainLabel.textProperty().bind(progressBarEngine.messageProperty());
    }
    @FXML
    private void onClickSeeDetailedResults(MouseEvent event){
        URL url = Launch.class.getResource("scenes/detailedResultScene.fxml");
        Scenes.sceneChange(event, url, new DetailedResultSceneController(currentUser, commonTestResult));
    }




}
