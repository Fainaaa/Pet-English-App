package com.github.fainaaa.controllers.for_manipulating_collections;

import com.github.fainaaa.DBEntities.PhrasesTableColumns;
import com.github.fainaaa.DBHandler.DBHandler;
import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.Phrase;
import com.github.fainaaa.entities.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;


public abstract class ManipulateCollectionController implements Initializable{
    protected User currentUser;
    protected Collection currentCollection;

    public ManipulateCollectionController(User user){
        this.currentUser = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        phraseColumn.setCellValueFactory(new PropertyValueFactory<>("phrase"));
        translationColumn.setCellValueFactory(new PropertyValueFactory<>("translation"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        initWordsTableWithNewWordsList();
    }
    protected abstract void initWordsTableWithNewWordsList();

    @FXML
    protected TableView phrasesTable;
    @FXML
    protected TableColumn<Phrase, String> phraseColumn;
    @FXML
    protected TableColumn<Phrase, String> translationColumn;
    @FXML
    protected TableColumn<Phrase, String> descriptionColumn;
    protected ObservableList<Phrase> phrasesList;

    @FXML
    protected TextField addingPhraseField;
    @FXML
    protected TextField addingTranslationField;
    @FXML
    protected TextField addingDescriptionField;

    protected void downloadPhraseIntoDB(Phrase phrase, int collectionId){
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (%s, '%s','%s','%s');",
                PhrasesTableColumns.TABLE_NAME.getNameInDB(),
                PhrasesTableColumns.COLLECTION_ID.getNameInDB(), PhrasesTableColumns.PHRASE.getNameInDB(),
                PhrasesTableColumns.TRANSLATION.getNameInDB(), PhrasesTableColumns.DESCRIPTION.getNameInDB(),
                collectionId, phrase.getPhrase(), phrase.getTranslation(),phrase.getDescription());
        try(DBHandler dbHandler = new DBHandler()){
            dbHandler.executeUpdateStatement(sql);
        }
    }
    protected void clearPhraseFields(TextField phraseField, TextField translationField, TextField descriptionField){
        phraseField.setText("");
        translationField.setText("");
        descriptionField.setText("");
    }
}
