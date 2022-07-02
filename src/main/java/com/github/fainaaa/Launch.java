package com.github.fainaaa;
import org.apache.logging.log4j.*;

import com.github.fainaaa.controllers.UserSceneController;
import com.github.fainaaa.entities.User;
import com.github.fainaaa.helpers.JacksonUserWriterReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Launch extends Application {
    File currentUserFile = new File("src/main/resources/com/github/fainaaa/userData/currentUser.json");
    Logger logger = LogManager.getRootLogger();

    @Override
    public void start(Stage stage) throws IOException {
        logger.info("Start...");
        FXMLLoader fxmlLoader;
        Scene scene;
        if (currentUserFile.exists()) {
            User user = JacksonUserWriterReader.unmarshall();
            fxmlLoader = new FXMLLoader(Launch.class.getResource("scenes/userScene.fxml"));
            fxmlLoader.setControllerFactory(c -> new UserSceneController(user));
            // scene.getStylesheets().add(Launch.class.getResource("styles/lightStyle.css").toExternalForm());
        } else {
            fxmlLoader = new FXMLLoader(Launch.class.getResource("scenes/mainScene.fxml"));
            //scene.getStylesheets().add(Launch.class.getResource("styles/mainStyleDark.css").toExternalForm());
            //scene.getStylesheets().add(Launch.class.getResource("styles/mainStyleLight.css").toExternalForm());
        }
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}