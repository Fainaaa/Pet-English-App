package com.github.fainaaa.controllers.for_manipulating_collections;

import com.github.fainaaa.DBEntities.PhrasesTableColumns;
import com.github.fainaaa.DBHandler.DBHandler;
import com.github.fainaaa.Launch;
import com.github.fainaaa.controllers.CollectionsSceneController;
import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.Phrase;
import com.github.fainaaa.entities.User;
import com.github.fainaaa.helpers.Scenes;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyingCollectionSceneController extends ManipulateCollectionController implements Initializable {

    public ModifyingCollectionSceneController(User user, Collection collection){
        super(user);
        this.currentCollection = collection;
    }
    private SelectionModel<Phrase> phraseSelectionModel;
    private Phrase selectedPhrase;

    @FXML
    private Label labelForShowingWarnMessages;
    @FXML
    private TextField deletingPhraseField;
    @FXML
    private TextField deletingTranslationField;
    @FXML
    private TextField deletingDescriptionField;
    @FXML
    private TextField modifyingPhraseField;
    @FXML
    private TextField modifyingTranslationField;
    @FXML
    private TextField modifyingDescriptionField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        initSelectionModel();
    }
    @Override
    protected void initWordsTableWithNewWordsList(){
        phrasesList = FXCollections.observableList(currentCollection.getPhrases());
        phrasesTable.setItems(phrasesList);
    }
    private void initSelectionModel(){
        phraseSelectionModel = phrasesTable.getSelectionModel();

        phraseSelectionModel.selectedItemProperty().addListener((observableValue, previousItem, currentItem) -> {
            selectedPhrase = currentItem;
        });
    }
    @FXML
    private void onClickTableLine(){
        showPhraseInTextFields(deletingPhraseField, deletingTranslationField, deletingDescriptionField);
        showPhraseInTextFields(modifyingPhraseField, modifyingTranslationField, modifyingDescriptionField);
        hideNoSelectedPhraseLabel();
    }
    @FXML
    private void onClickModify(MouseEvent event){
        if(selectedPhrase == null){
            showNoSelectedPhraseMessage();
        }
        else {
            int indexOfSelectedPhrase = phrasesList.indexOf(selectedPhrase);
            Phrase newPhrase = new Phrase(selectedPhrase.getID(),
                    modifyingPhraseField.getText().trim(),
                    modifyingTranslationField.getText().trim(),
                    modifyingDescriptionField.getText());
            modifyPhraseInDB(newPhrase);
            phrasesList.remove(selectedPhrase);
            phrasesList.add(indexOfSelectedPhrase, newPhrase);
            phraseSelectionModel.clearSelection();
            clearPhraseFields(modifyingPhraseField, modifyingTranslationField, modifyingDescriptionField);
        }
    }
    private void modifyPhraseInDB(Phrase phrase){
        String sql = String.format("UPDATE %s SET %s='%s', %s='%s', %s='%s' WHERE %s = %s;",
                PhrasesTableColumns.TABLE_NAME.getNameInDB(),
                PhrasesTableColumns.PHRASE.getNameInDB(), phrase.getPhrase(),
                PhrasesTableColumns.TRANSLATION.getNameInDB(), phrase.getTranslation(),
                PhrasesTableColumns.DESCRIPTION.getNameInDB(),phrase.getDescription(),
                PhrasesTableColumns.ID.getNameInDB(), phrase.getID());
        try(DBHandler dbHandler = new DBHandler()){
            dbHandler.executeUpdateStatement(sql);
        }
    }
    @FXML
    private void onClickDelete(MouseEvent event){
        if(selectedPhrase == null){
            showNoSelectedPhraseMessage();
        }
        else {
            deletePhraseFromDB();
            phrasesList.remove(selectedPhrase);
            clearPhraseFields(deletingPhraseField, deletingTranslationField, deletingDescriptionField);
            phraseSelectionModel.clearSelection();
            clearPhraseFields(deletingPhraseField, deletingTranslationField, deletingDescriptionField);
        }
    }
    private void deletePhraseFromDB(){
        String sql = String.format("DELETE FROM %s WHERE %s = %s",
                PhrasesTableColumns.TABLE_NAME.getNameInDB(),
                PhrasesTableColumns.ID.getNameInDB(),
                selectedPhrase.getID());
        try(DBHandler dbHandler = new DBHandler()){
            dbHandler.executeUpdateStatement(sql);
        }
    }
    @FXML
    private void onClickAdd(MouseEvent event){
        if(addingPhraseField.getText().isEmpty() || addingTranslationField.getText().isEmpty()){
            showInvalidEmptyFieldsMessage();
        }
        else {
            Phrase newPhrase = new Phrase(addingPhraseField.getText().trim(),
                    addingTranslationField.getText().trim(),
                    addingDescriptionField.getText().trim());
            phrasesList.add(newPhrase);
            downloadPhraseIntoDB(newPhrase, currentCollection.getID());
        }
    }
    @FXML
    private void onClickGBack(MouseEvent event){
        URL previousSceneUrl = Launch.class.getResource("scenes/collectionsScene.fxml");
        Scenes.sceneChange(event, previousSceneUrl, new CollectionsSceneController(currentUser));
    }

    private void showNoSelectedPhraseMessage(){
        labelForShowingWarnMessages.setText("No selected word/phrase. Please, select it before pressing on this button.");
    }

    @FXML private void hideNoSelectedPhraseLabel(){
        labelForShowingWarnMessages.setText("");
    }
    private void showInvalidEmptyFieldsMessage(){
        labelForShowingWarnMessages.setText("Fields for word/phrase and translation are empty. You need to fill them in before adding.");
    }
    private void showPhraseInTextFields(TextField phraseField, TextField translationField, TextField descriptionField){
        phraseField.setText(selectedPhrase.getPhrase());
        translationField.setText(selectedPhrase.getTranslation());
        descriptionField.setText(selectedPhrase.getDescription());
    }

}