package com.github.fainaaa.controllers;

import com.github.fainaaa.DBEntities.UsersTableColumns;
import com.github.fainaaa.DBHandler.DBHandler;
import com.github.fainaaa.Launch;
import com.github.fainaaa.entities.User;
import com.github.fainaaa.helpers.JacksonUserWriterReader;
import com.github.fainaaa.helpers.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainSceneController {
    Logger logger = LogManager.getRootLogger();
    {
        logger.info("ON MAIN SCENE CONTROLLER");
    }

    @FXML
    private Label invalidLoginOrPasswordMessage;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    void onClickRegister(MouseEvent event) {
        URL url = Launch.class.getResource("scenes/registration.fxml");
        Scenes.sceneChange(event, url);
    }
    @FXML
    void onClickSignIn(MouseEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();
        if (userIsCorrect(login, password)) {
            User user = getUserFromDB(login);
            JacksonUserWriterReader.marshall(user);
            URL url = Launch.class.getResource("scenes/userScene.fxml");
            Scenes.sceneChange(event, url, new UserSceneController(user));
        }
        else {
            showInvalidLoginOrPasswordMessage();
        }
    }

    private boolean userIsCorrect(String login, String password) {
        String sql = String.format("SELECT %s, %s FROM %s WHERE %s = '%s';",
                UsersTableColumns.LOGIN.getNameInDB(), UsersTableColumns.PASSWORD.getNameInDB(),
                UsersTableColumns.TABLE_NAME.getNameInDB(), UsersTableColumns.LOGIN.getNameInDB(),
                login);
        try (DBHandler handler = new DBHandler()) {
            ResultSet resultSet = handler.executeQueryStatement(sql);
            if (resultSet.next()) {
                if (password.equals(resultSet.getString(UsersTableColumns.PASSWORD.getNameInDB()))) {
                    logger.info("Проверка данных для входа: Успешно");
                    return true;
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        logger.info("Проверка данных для входа: Безуспешно");
        return false;
    }
    private User getUserFromDB(String login) {
        User user = null;
        String sql = String.format("SELECT * FROM %s WHERE %s ='%s';",
                UsersTableColumns.TABLE_NAME.getNameInDB(), UsersTableColumns.LOGIN.getNameInDB(),
                login);
        try (DBHandler handler = new DBHandler()) {
            ResultSet resultSet = handler.executeQueryStatement(sql);
            user = new User(resultSet.getInt(UsersTableColumns.ID.getNameInDB()),
                    resultSet.getString(UsersTableColumns.LOGIN.getNameInDB()),
                    resultSet.getString(UsersTableColumns.PASSWORD.getNameInDB()),
                    resultSet.getString(UsersTableColumns.NAME.getNameInDB()),
                    resultSet.getBoolean(UsersTableColumns.IS_DARK_THEME_ON.getNameInDB()));
            logger.info("Юзер успешно взят из бд и записан в объект");
        } catch (SQLException throwables ) {
            logger.error("Ошибка при взятии юзера из бд и записи в объект" + throwables.getMessage());
        }
        return user;
    }

    private void showInvalidLoginOrPasswordMessage() {
        invalidLoginOrPasswordMessage.setStyle("-fx-font-family: Calibri; -fx-text-fill: #990000; -fx-font-size: 14px");
        invalidLoginOrPasswordMessage.setText("Invalid login or password, try again or go to register");
    }

    @FXML
    private void hideInvalidLoginOrPasswordMessage() {

        invalidLoginOrPasswordMessage.setText("");
    }
}
