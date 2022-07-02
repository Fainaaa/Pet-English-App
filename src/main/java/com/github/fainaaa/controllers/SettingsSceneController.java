package com.github.fainaaa.controllers;

import com.github.fainaaa.Launch;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class SettingsSceneController {
    private boolean isDarkThemeOn;
    private boolean isFirstClickOnSwapScene = true;

    @FXML
    void onClickSwapTheme(MouseEvent event) {
        /* TODO Темы
         * Две наши цветовые темы добавлены в классе Launch
         * А в методе swapTheme() мы меняем их положение, так как он отображает ту, что идёт позже
         * А если в коде будем добавлять тему, как было у тебя, а не свапать их местами, то к окну у нас будет подключенно
         * куча одинаковых файлов. Поэтому вроде пока что оптимальный вариант.
         * */
        Scene scene = ((Button) event.getSource()).getScene();
        /*
         * Смысл в том, что когда ты свапаешься на другую сцену и потом обратно сюда, в Launch у тебя уже
         * стили не инициализируются, соответственно, они по нулям. И нужно их снова инициализировать...
         * */
        if (scene.getStylesheets().size()==0) {
            scene.getStylesheets().add(Launch.class.getResource("styles/mainStyleDark.css").toExternalForm());
            scene.getStylesheets().add(Launch.class.getResource("styles/mainStyleLight.css").toExternalForm());
            System.out.println("Initialization themes...");
        }
        swapTheme(scene);
        System.out.println(scene.getStylesheets());
        if (isDarkThemeOn) {
            isDarkThemeOn = false;
            //btnSwapTheme.setText("Switch to the dark theme");
        } else {
            isDarkThemeOn = true;
            //btnSwapTheme.setText("Switch to the light theme");
        }
    }

    private void swapTheme(Scene scene) {
        //We have dark and light theme.
        String bufURL = scene.getStylesheets().get(0);
        scene.getStylesheets().set(0, scene.getStylesheets().get(1));
        scene.getStylesheets().set(1, bufURL);
    }
}
