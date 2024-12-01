package com.LerningBara.controller;

import com.LerningBara.app.App;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class CreateAccountSceneControler {
    @FXML
    private CheckBox termsField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField1;
    @FXML
    private PasswordField passwordField2;

    public void createAccount() throws Exception{
        if((!termsField.isSelected())){
            System.out.println("You have to accept terms and conditions");
            return;
        }
        String login = loginField.getText();
        String email = emailField.getText();
        String password = passwordField1.getText();
        if(!(password.equals(passwordField2.getText()))){
            System.out.println("Password is not matching.");
        }
        System.out.println(login+email);
        // TODO INSERT INTO DATABASE
        // TODO CHANGE SCENE TO MAIN MENU

    }
}
