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

public class LoginSceneController {
    private static final Logger logger = LogManager.getLogger(LoginSceneController.class);
    @FXML
    private Label invalidLoginOrPasswordMessage;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    void onClickRegister(MouseEvent event) {
        URL nextSceneUrl = Launch.class.getResource("scenes/registrationScene.fxml");
        Scenes.sceneChange(event, nextSceneUrl);
    }
    @FXML
    void onClickSignIn(MouseEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();
        if (userIsCorrect(login, password)) {
            User currentUser = getUserFromDB(login);
            JacksonUserWriterReader.marshall(currentUser);
            URL nextSceneUrl = Launch.class.getResource("scenes/userScene.fxml");
            Scenes.sceneChange(event, nextSceneUrl, new UserSceneController(currentUser));
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
                    logger.info("User's data verification: Successfully");
                    return true;
                }
            }
           logger.info("User's data verification: Unsuccessfully");
        } catch (SQLException e) {
            logger.info("User's data verification: FAILED\n" + e.getMessage());
            throw new RuntimeException(e);
        }
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
            logger.info("Get user from DB + write it to an object: Successfully");
        } catch (SQLException e) {
            logger.error("Get user from DB + write it to an object: FAILED" + e.getMessage());
            throw new RuntimeException(e);
        }
        return user;
    }

    private void showInvalidLoginOrPasswordMessage() {
        invalidLoginOrPasswordMessage.setText("Invalid login or password, try again or go to register");
    }

    @FXML
    private void hideInvalidLoginOrPasswordMessage() {

        invalidLoginOrPasswordMessage.setText("");
    }
}
