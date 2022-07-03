package com.github.fainaaa.controllers;

import com.github.fainaaa.DBEntities.UsersTableColumns;
import com.github.fainaaa.DBHandler.DBHandler;
import com.github.fainaaa.Launch;
import com.github.fainaaa.helpers.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;



public class RegistrationSceneController {
    private static final Logger logger = LogManager.getLogger(RegistrationSceneController.class);
    @FXML
    private Label invalidPassMessage;
    @FXML
    private Label notUniqueLoginMessage;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passField;
    @FXML
    private TextField nameField;

    @FXML
    void onClickRegister(MouseEvent event) {
        String login = emailField.getText();
        String pass = passField.getText();
        String name = nameField.getText();
        if (isUniqueLogin(login)) {
            if(isPasswordValid(pass)) {
                createAccount(login, pass, name);
                logger.info("Attempt to create an account: Successfully");
                onClickGoBack(event);
            }
        }
        else {
            showNonUniqueLoginMessage();
            logger.info("Attempt to create an account: Unsuccessfully");
        }
    }
    private boolean isUniqueLogin(String login) {
        String sql = String.format("SELECT %s FROM %s WHERE %s = '%s';",
                UsersTableColumns.LOGIN.getNameInDB(), UsersTableColumns.TABLE_NAME.getNameInDB(),
                UsersTableColumns.LOGIN.getNameInDB(),
                login);
        try(DBHandler handler = new DBHandler()){
            ResultSet resultSet = handler.executeQueryStatement(sql);
            if(resultSet.next()){
                return false;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return true;
    }

    private boolean isPasswordValid(String password){
        String pattern = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[._!,-])[0-9a-zA-Z!._,-]{7,20}";
        if(password.matches(pattern)){
            return true;
        }
        else if (password.length() < 7 || password.length() > 20){
            invalidPassMessage.setLayoutY(154);
            invalidPassMessage.setText("Password must contain from 7 to 20 characters.");
                return false;
        }
        else{
            invalidPassMessage.setLayoutY(145);
            invalidPassMessage.setText("Password must contain latin letters, numbers and\n" +
                    "at least one of the characters: !,.-_");
            return false;
        }
    }
    private void showNonUniqueLoginMessage(){
        notUniqueLoginMessage.setText("Such email is already present, try to sign in or use another one");
    }

    private void createAccount(String login, String pass, String name) {
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES ('%s', '%s', '%s', %s);",
                UsersTableColumns.TABLE_NAME.getNameInDB(),
                UsersTableColumns.LOGIN.getNameInDB(),UsersTableColumns.PASSWORD.getNameInDB(),
                UsersTableColumns.NAME.getNameInDB(), UsersTableColumns.IS_DARK_THEME_ON.getNameInDB(),
                login, pass, name, '0');
        try(DBHandler handler = new DBHandler()) {
            handler.executeUpdateStatement(sql);
        }
    }

    @FXML
    private void hideInvalidPassMessage(){
        invalidPassMessage.setText("");
    }
    @FXML
    private void hideNonUniqueLoginMessage(){
        notUniqueLoginMessage.setText("");
    }
    @FXML
    void onClickGoBack(MouseEvent event) {
        URL previousSceneUrl = Launch.class.getResource("scenes/loginScene.fxml");
        Scenes.sceneChange(event, previousSceneUrl);
    }

}
