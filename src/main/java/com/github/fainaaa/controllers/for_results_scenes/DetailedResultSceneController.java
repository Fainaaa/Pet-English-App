package com.github.fainaaa.controllers.for_results_scenes;

import com.github.fainaaa.Launch;
import com.github.fainaaa.controllers.UserSceneController;
import com.github.fainaaa.entities.User;
import com.github.fainaaa.entities.for_grading_test.CommonTestResult;
import com.github.fainaaa.entities.for_grading_test.TestingPhrase;
import com.github.fainaaa.helpers.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
public class DetailedResultSceneController implements Initializable {
    private User currentUser;
    private CommonTestResult commonTestResult;

    public DetailedResultSceneController(User user, CommonTestResult commonTestResult){
        this.currentUser = user;
        this.commonTestResult = commonTestResult;

    }
    @FXML
    private TableView choosingTranslationResultTable;
    private ObservableList<TestingPhrase> choosingTranslationPhrasesList;

    @FXML
    private TableView writingTranslationResultTable;
    private ObservableList<TestingPhrase> writingTranslationPhrasesList;
    @FXML
    protected TableView anagramsResultTable;
    private ObservableList<TestingPhrase> anagramsPhrasesList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] fieldNames = {"givenPhrase", "userAnswer", "correctAnswer", "isAnsweredCorrectForShowingInResultsTable"};
        setCellValueFactories(choosingTranslationResultTable, fieldNames);
        setCellValueFactories(writingTranslationResultTable, fieldNames);
        setCellValueFactories(anagramsResultTable, fieldNames);
        initWordsTableWithWordsList(choosingTranslationResultTable, choosingTranslationPhrasesList, commonTestResult.getResultsOfAllParts()[0].getTestingPhrases());
        initWordsTableWithWordsList(writingTranslationResultTable, writingTranslationPhrasesList, commonTestResult.getResultsOfAllParts()[1].getTestingPhrases());
        initWordsTableWithWordsList(anagramsResultTable, anagramsPhrasesList, commonTestResult.getResultsOfAllParts()[2].getTestingPhrases());
    }
    private void setCellValueFactories(TableView table, String [] classFieldNames){
        for(int i = 0; i < table.getColumns().size(); i++){
            ((TableColumn)table.getColumns().get(i)).
                    setCellValueFactory(new PropertyValueFactory<>(classFieldNames[i]));
        }
    }
    private void initWordsTableWithWordsList(TableView wordsTable, ObservableList wordsObservableList, List<TestingPhrase> wordsList){
        wordsObservableList = FXCollections.observableList(wordsList);
        wordsTable.setItems(wordsObservableList);
    }

    @FXML
    private void onClickEndUp(MouseEvent event){
        URL url = Launch.class.getResource("scenes/userScene.fxml");
        Scenes.sceneChange(event, url, new UserSceneController(currentUser));
    }
}
