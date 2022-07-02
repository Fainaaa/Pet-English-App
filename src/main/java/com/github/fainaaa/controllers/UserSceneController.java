package com.github.fainaaa.controllers;

import com.github.fainaaa.Launch;
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

    Logger logger = LogManager.getRootLogger();
    User user;

    public UserSceneController(User user) {
        logger.info("ON USER SCENE CONTROLLER");
        this.user = user;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setGreeting();
    }
    @FXML
    private Label greetingLabel;
    @FXML
    void onClickAddCollection(MouseEvent event){
        URL url = Launch.class.getResource("scenes/addCollectionScene.fxml");
        Scenes.sceneChange(event, url, new AddCollectionSceneController(user));
    }

    @FXML
    void onClickSignOut(MouseEvent event) {
        File file = new File("src/main/resources/com/github/metakol/userData/currentUser.json");
        file.delete();
        URL url = Launch.class.getResource("scenes/mainScene.fxml");
        Scenes.sceneChange(event, url);
    }
    @FXML
    void onClickMyCollections(MouseEvent event){
        URL url = Launch.class.getResource("scenes/collectionsScene.fxml");
        Scenes.sceneChange(event, url, new CollectionsSceneController(user));
    }

    private void setGreeting(){
        int currentHourOfDay = LocalTime.now().getHour();
        String greeting;
        if(currentHourOfDay >= 5 && currentHourOfDay <= 12){
            greeting = "Good morning, " + user.getName()+", how are you???";
        }
        else if(currentHourOfDay >= 13 && currentHourOfDay <= 16){
            greeting = "Good day, " + user.getName()+", how are you???";
        }
        else if(currentHourOfDay >= 17 && currentHourOfDay <= 22){
            greeting = "Good evening, " + user.getName()+", how are you???";
        }
        else{
            greeting = "Good night, " + user.getName()+", how are you???";
        }
        greetingLabel.setText(greeting);
    }
}
