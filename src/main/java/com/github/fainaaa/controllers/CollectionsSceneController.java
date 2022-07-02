package com.github.fainaaa.controllers;

import com.github.fainaaa.DBEntities.CollectionsTableColumns;
import com.github.fainaaa.DBEntities.PhrasesTableColumns;
import com.github.fainaaa.DBHandler.DBHandler;
import com.github.fainaaa.Launch;
import com.github.fainaaa.entities.Collection;
import com.github.fainaaa.entities.Phrase;
import com.github.fainaaa.entities.User;

import com.github.fainaaa.helpers.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CollectionsSceneController implements Initializable {

    Logger logger = LogManager.getRootLogger();
    User user;
    @FXML
    private Button repeatButton;
    @FXML
    private Button studyButton;
    @FXML
    private Button testButton;
    @FXML
    private Button deleteCollectionButton;
    @FXML
    private Label noSelectedCollectionLabel;

    @FXML
    private ListView<Collection> collectionsListView;
    private ObservableList<Collection> collectionsObservableList;
    Collection selectedCollection;

    public CollectionsSceneController(User user){
        this.user = user;
        logger.info("ON COLLECTIONS SCENE CONTROLLER");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        initListView();
        getCollectionsFromDB();
        initSelectionModel();
        setTooltipForButton(repeatButton, "You'll translate phrases\nfrom English into Russian");
        setTooltipForButton(studyButton, "You'll translate phrases\nfrom Russian into English");
        setTooltipForButton(testButton, "You will be given a test \nto check how well you learned the material");
        setTooltipForButton(deleteCollectionButton, "Click this button\n if you want to delete selected collection");
    }
    private void initListView(){
        collectionsObservableList = FXCollections.observableArrayList();
        collectionsListView.setItems(collectionsObservableList);
        collectionsListView.setCellFactory(new Callback<ListView<Collection>,ListCell<Collection>>() {
            @Override
            public ListCell<Collection> call(ListView<Collection> param)
            {
                return new CollectionsListCell();
            }
        });
        //collectionsList.setCellFactory(p -> new CollectionsListCell());
    }
    static private class CollectionsListCell extends ListCell<Collection>{
        @Override
        protected void updateItem(Collection collection, boolean arg1) {
            super.updateItem(collection, arg1);
            if(collection == null) {
                this.setText("");
            }
            else {
                this.setText("Collection name: " + collection.getName() + " (" + collection.getPhrasesNumber() + " words)");
            }
        }
    }
    private void getCollectionsFromDB(){
        String sql = String.format("SELECT %s, %s, %s FROM %s WHERE %s = %s",
                CollectionsTableColumns.ID.getNameInDB(), CollectionsTableColumns.NAME.getNameInDB(), CollectionsTableColumns.WORDS_NUMBER.getNameInDB(),
                CollectionsTableColumns.TABLE_NAME.getNameInDB(), CollectionsTableColumns.USER_ID, user.getID());
        try(DBHandler dbHandler = new DBHandler()){
            ResultSet resultSet = dbHandler.executeQueryStatement(sql);
            while (resultSet.next()){
                Collection collection = new Collection();
                collection.setID(resultSet.getInt(CollectionsTableColumns.ID.getNameInDB()));
                collection.setName(resultSet.getString(CollectionsTableColumns.NAME.getNameInDB()));
                collection.setPhrasesNumber(resultSet.getInt(CollectionsTableColumns.WORDS_NUMBER.getNameInDB()));

                getPhrasesFromDB(collection);
                collectionsObservableList.add(collection);
            }
            logger.info("Выгрузка коллекций для списка коллекций: Успешно");
        }
        catch(SQLException e){
            logger.error("Ошибка выгрузки коллекций для списка коллекций\n" +  e.getMessage());
            throw new RuntimeException(e);
        }
    }
    private void getPhrasesFromDB(Collection collection){
        String sql = String.format("SELECT %s, %s, %s  FROM %s WHERE %s = %s;",
                PhrasesTableColumns.PHRASE.getNameInDB(), PhrasesTableColumns.TRANSLATION.getNameInDB(), PhrasesTableColumns.DESCRIPTION.getNameInDB(),
                PhrasesTableColumns.TABLE_NAME.getNameInDB(), PhrasesTableColumns.COLLECTION_ID.getNameInDB(),
                collection.getID());
        try(DBHandler dbHandler = new DBHandler()){
            ResultSet resultSet = dbHandler.executeQueryStatement(sql);
            while(resultSet.next()){
                Phrase phrase = new Phrase();
                phrase.setPhrase(resultSet.getString(PhrasesTableColumns.PHRASE.getNameInDB()));
                phrase.setTranslation(resultSet.getString(PhrasesTableColumns.TRANSLATION.getNameInDB()));
                phrase.setDescription(resultSet.getString(PhrasesTableColumns.DESCRIPTION.getNameInDB()));
                collection.getPhrases().add(phrase);
            }
        } catch (SQLException e) {
            logger.error("Ошибка при добавлении слов в коллекцию\n" + e.getMessage());
            throw new RuntimeException(e);
        }


    }
    private void initSelectionModel(){
        SelectionModel<Collection> collectionSelectionModel = collectionsListView.getSelectionModel();

        collectionSelectionModel.selectedItemProperty().addListener((observableValue, oldItem, item) -> {
            selectedCollection = item;
            logger.info("В списке коллекций выбрана коллекция");
        });
    }

    public void setTooltipForButton(Button button, String message){
        Tooltip tooltip = new Tooltip(message);
        tooltip.setShowDelay(Duration.millis(50));
        button.setTooltip(tooltip);
    }
    @FXML
    void onClickRepeat(MouseEvent event){
        if(selectedCollection != null){
            changeToIntermediateScene(event, CollectionsSceneButtons.REPEAT_BUTTON);
        }
        else{
            showNoSelectedCollectionLabel();
        }
    }
    @FXML
    private void onClickStudy(MouseEvent event){
        if(selectedCollection != null){
            changeToIntermediateScene(event, CollectionsSceneButtons.STUDY_BUTTON);
        }
        else {
            showNoSelectedCollectionLabel();
        }
    }
    @FXML
    private void onClickTest(MouseEvent event){
        if(selectedCollection != null){
            changeToIntermediateScene(event, CollectionsSceneButtons.TEST_BUTTON);
        }
        else {
            showNoSelectedCollectionLabel();
        }
    }
    private void changeToIntermediateScene(MouseEvent event, CollectionsSceneButtons buttonInvokingOtherScene){
        URL url = Launch.class.getResource("scenes/intermediateScene.fxml");
        Scenes.sceneChange(event,url, new IntermediateSceneController(user, selectedCollection, buttonInvokingOtherScene));
    }
    private void showNoSelectedCollectionLabel(){
        noSelectedCollectionLabel.setStyle("-fx-font-family: Calibri; -fx-font-size: 14px; -fx-text-fill: #990000;");
        noSelectedCollectionLabel.setText("No selected collection. Please, select collection \nbefore pressing on any button.");
    }

    @FXML
    private void onClickGoBack(MouseEvent event){
        URL url = Launch.class.getResource("scenes/userScene.fxml");
        Scenes.sceneChange(event, url, new UserSceneController(user));
    }
    @FXML
    private void hideNoSelectedCollectionLabel(){
        noSelectedCollectionLabel.setText("");
    }

}
