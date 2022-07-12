package com.github.fainaaa.controllers;

import com.github.fainaaa.DBEntities.CollectionsTableColumns;
import com.github.fainaaa.DBEntities.PhrasesTableColumns;
import com.github.fainaaa.DBHandler.DBHandler;
import com.github.fainaaa.Launch;
import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.User;
import com.github.fainaaa.entities.Phrase;
import com.github.fainaaa.helpers.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddCollectionSceneController implements Initializable {
    private static final Logger logger = LogManager.getLogger(AddCollectionSceneController.class);

    Collection currentCollection;
    User user;
    public AddCollectionSceneController(User user){
        this.user = user;
    }

    @FXML
    private TableView phrasesTable;
    @FXML
    private TableColumn<Phrase, String> phraseColumn;
    @FXML
    private TableColumn<Phrase, String> translationColumn;
    @FXML
    private TableColumn<Phrase, String> descriptionColumn;
    private ObservableList<Phrase> phrasesList;

    @FXML
    private TextField collectionNameField;
    @FXML
    private TextField phraseField;
    @FXML
    private TextField translationField;
    @FXML
    private TextField descriptionField;
    @FXML
    private Label invalidCollectionNameMessage;
    @FXML
    private Label emptyCollectionMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        phraseColumn.setCellValueFactory(new PropertyValueFactory<Phrase, String>("phrase"));
        translationColumn.setCellValueFactory(new PropertyValueFactory<Phrase, String>("translation"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Phrase, String>("description"));
        initWordsTableWithNewWordsList();
    }
    private void initWordsTableWithNewWordsList(){
        phrasesList = FXCollections.observableList(new ArrayList<Phrase>());
        phrasesTable.setItems(phrasesList);

    }

    @FXML
    void onClickAddWord(MouseEvent event){
        phrasesList.add(new Phrase(phraseField.getText().trim(),
                translationField.getText().trim(),
                descriptionField.getText().trim()));
        clearCurrentPhraseFields();
    }
    @FXML
    void onClickComplete(MouseEvent event){
        if(phrasesList.size() == 0) {
            if (isCollectionNameUnique(collectionNameField.getText().trim())) {
                currentCollection = new Collection(collectionNameField.getText().trim(), phrasesList.size(), phrasesList);
                downloadCollectionIntoDB(currentCollection);
                int downloadedCollectionId = getCollectionIdFromDB(currentCollection);
                for (Phrase phrase : phrasesList) {
                    downloadPhraseIntoDB(phrase, downloadedCollectionId);
                }
                logger.info("Download phrases from collection into DB: Successfully");
                clearCollectionNameField();
                initWordsTableWithNewWordsList();
            }
            else {
                showInvalidCollectionNameMessage();
                logger.info("Download phrases from collection into DB: Unsuccessfully");
            }
        }
        else {
            showEmptyCollectionMessage();
        }
    }
    private boolean isCollectionNameUnique(String collectionName){
        String sql = String.format("SELECT * FROM %s WHERE %s = '%s';",
                CollectionsTableColumns.TABLE_NAME.getNameInDB(), CollectionsTableColumns.NAME.getNameInDB(), collectionName);
        try(DBHandler handler = new DBHandler()){
            ResultSet resultSet = handler.executeQueryStatement(sql);
            if (resultSet.next()){
                logger.info("Check the collection name for uniqueness: Non-unique name");
                return false;
            }
            logger.info("Check the collection name for uniqueness: Unique name");
            return true;
        }
        catch (SQLException e){
            logger.error("Check the collection name for uniqueness: FAILED\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    private void downloadCollectionIntoDB(Collection collection){
        String sql = String.format("INSERT INTO %s (%s, %s, %s) VALUES ('%s', %s, %s);",
                CollectionsTableColumns.TABLE_NAME.getNameInDB(), CollectionsTableColumns.NAME.getNameInDB(),
                CollectionsTableColumns.USER_ID.getNameInDB(), CollectionsTableColumns.WORDS_NUMBER.getNameInDB(),
                collection.getName(), user.getID(), collection.getPhrasesNumber());
        try(DBHandler handler = new DBHandler()){
            handler.executeUpdateStatement(sql);
            logger.info("Download collection into DB: Successfully");
        }
    }
    private int getCollectionIdFromDB(Collection collection){
        String sql = String.format("SELECT %s FROM %s WHERE %s='%s';", CollectionsTableColumns.ID.getNameInDB(),
                CollectionsTableColumns.TABLE_NAME.getNameInDB(), CollectionsTableColumns.NAME.getNameInDB(),
                collection.getName());
        try(DBHandler handler = new DBHandler()){
            ResultSet resultSet = handler.executeQueryStatement(sql);
            if(resultSet.next()) {
               return resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            logger.error("Finding id of given collection: FAILED" + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("Finding id of given collection: Unsuccessfully. No such collection id");
        throw new IllegalArgumentException("No such collection id");
    }
    private void downloadPhraseIntoDB(Phrase phrase, int collectionId){
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (%s, '%s','%s','%s');",
                PhrasesTableColumns.TABLE_NAME.getNameInDB(),
                PhrasesTableColumns.COLLECTION_ID.getNameInDB(), PhrasesTableColumns.PHRASE.getNameInDB(),
                PhrasesTableColumns.TRANSLATION.getNameInDB(), PhrasesTableColumns.DESCRIPTION.getNameInDB(),
                collectionId, phrase.getPhrase(), phrase.getTranslation(),phrase.getDescription());
        try(DBHandler dbHandler = new DBHandler()){
            dbHandler.executeUpdateStatement(sql);
        }
    }
    private void clearCurrentPhraseFields(){
        phraseField.setText("");
        descriptionField.setText("");
        translationField.setText("");
    }
    private void clearCollectionNameField(){
        collectionNameField.setText("");
    }
    private void showInvalidCollectionNameMessage(){
        invalidCollectionNameMessage.setText("Collection with the same name is already exists, please, rename this collection");
    }
    @FXML
    private void hideInvalidCollectionNameMessage(){
        invalidCollectionNameMessage.setText("");
    }
    @FXML
    private void showEmptyCollectionMessage(){
        emptyCollectionMessage.setText("You cant create collection without any words. Add them at first.");
    }
    @FXML
    private void hideEmptyCollectionMessage(){
        invalidCollectionNameMessage.setText("");
    }
    @FXML
    private void onClickGBack(MouseEvent event){
        URL previousSceneUrl = Launch.class.getResource("scenes/userScene.fxml");
        Scenes.sceneChange(event, previousSceneUrl, new UserSceneController(user));
    }

}
