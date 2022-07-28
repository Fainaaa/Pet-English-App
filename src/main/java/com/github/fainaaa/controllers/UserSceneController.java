package com.github.fainaaa.controllers;

import com.github.fainaaa.Launch;
import com.github.fainaaa.controllers.for_manipulating_collections.AddCollectionSceneController;
import com.github.fainaaa.entities.User;
import com.github.fainaaa.helpers.Scenes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class UserSceneController implements Initializable {

    private static final Logger logger = LogManager.getLogger(UserSceneController.class);
    User user;
    public UserSceneController(User user) {
        this.user = user;
    }

    @FXML
    private Label greetingLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setGreeting();
    }
    private void setGreeting(){
        int currentHourOfDay = LocalTime.now().getHour();
        String greeting;
        if(currentHourOfDay >= 5 && currentHourOfDay <= 12){
            greeting = "Good morning, " + user.getName();
        }
        else if(currentHourOfDay >= 13 && currentHourOfDay <= 16){
            greeting = "Good day, ";
        }
        else if(currentHourOfDay >= 17 && currentHourOfDay <= 22){
            greeting = "Good evening, ";
        }
        else{
            greeting = "Good night, ";
        }

        if(user.getName().isEmpty()) {
            greetingLabel.setText(greeting + "have a good learning!");
        }
        else{
            greetingLabel.setText(greeting + user.getName() + ", have a good learning!");
        }

    }

    @FXML
    private void onClickAddCollection(MouseEvent event){
        URL nextSceneUrl = Launch.class.getResource("scenes/addCollectionScene.fxml");
        Scenes.sceneChange(event, nextSceneUrl, new AddCollectionSceneController(user));
    }
    @FXML
    private void onClickMyCollections(MouseEvent event){
        URL nextSceneUrl = Launch.class.getResource("scenes/collectionsScene.fxml");
        Scenes.sceneChange(event, nextSceneUrl, new CollectionsSceneController(user));
    }
    @FXML
    private void onClickSignOut(MouseEvent event) {
        //ХЗ ПОКА ЧЕ С ЭТИМ ДЕЛАТЬ, МОЖЕТ В .PROPERTIES
        File file = new File("src/main/resources/com/github/fainaaa/userData/currentUser.json");
        file.delete();
        URL nextSceneUrl = Launch.class.getResource("scenes/loginScene.fxml");
        Scenes.sceneChange(event, nextSceneUrl);
    }
}
