package com.github.fainaaa.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;

public class Scenes {
    public static void sceneChange(EventObject event, URL FXMLfile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FXMLfile);
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Неверное имя файла");
            e.printStackTrace();
        }
    }

    public static void sceneChange(EventObject event, URL FXMLfile, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FXMLfile);
            fxmlLoader.setControllerFactory(c -> controller);
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Неверное имя файла");
            e.printStackTrace();
        }
    }
}

