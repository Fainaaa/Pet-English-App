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

import java.sql.ResultSet;
import java.sql.SQLException;



public class RegisterSceneController {
    Logger logger = LogManager.getRootLogger();
    {
        logger.info("ON REGISTER SCENE CONTROLLER");
    }

    @FXML
    private Label invalidPassMessage;
    @FXML
    private Label nonUniqueLoginMessage;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passField;
    @FXML
    private TextField nameField;

    @FXML
    void onClickGoBack(MouseEvent event) {
        Scenes.sceneChange(event, Launch.class.getResource("scenes/mainScene.fxml"));
    }

    @FXML
    void onClickRegister(MouseEvent event) {
        String login = emailField.getText();
        String pass = passField.getText();
        String name = nameField.getText();
        if (isUniqueLogin(login)) {
            if(isPasswordValid(pass)) {
                createAccount(login, pass, name);
                logger.info("Попытка создать аккаунт: Успешно");
                onClickGoBack(event);
            }
        }
        else {
            showNonUniqueLoginMessage();
            logger.info("Попытка создать аккаунт: Безуспешно");
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
        invalidPassMessage.setStyle("-fx-font-family: Calibri; -fx-text-fill: #990000; -fx-font-size: 14px");
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
        nonUniqueLoginMessage.setStyle("-fx-font-family: Calibri; -fx-text-fill: #990000; -fx-font-size: 14px");
        nonUniqueLoginMessage.setText("Such email is already present, try to sign in or use another one");
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
        nonUniqueLoginMessage.setText("");
    }
}
