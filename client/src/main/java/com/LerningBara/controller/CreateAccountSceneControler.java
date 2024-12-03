package com.LerningBara.controller;

import com.LerningBara.app.App;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
    @FXML
    private Label passLabel;
    @FXML
    private Label loginLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label repeatLabel;
    @FXML
    private Label termsLabel;

    public void createAccount() throws Exception{
        boolean create_accout = true;
        String login = loginField.getText();
        if (login.equals("")){
            loginLabel.setText("Login cannot be empty.");
            create_accout = false;
        }
        else if(App.database.userExists(login)){
            loginLabel.setText("Username already taken.");
            create_accout = false;
        }
        else {
            loginLabel.setText("");
        }
        String email = emailField.getText();
        if (email.equals("")){
            emailLabel.setText("E-mail cannot be empty.");
            create_accout = false;
        }
        else {
            emailLabel.setText("");
        }
        String password = passwordField1.getText();
        if (password.equals("")){
            passLabel.setText("Password cannot be empty.");
            create_accout = false;
        }
        else {
            passLabel.setText("");
        }
        if(!(password.equals(passwordField2.getText()))){
            repeatLabel.setText("Different password.");
            create_accout = false;
        }
        else {
            repeatLabel.setText("");
        }

        if((!termsField.isSelected())){
            termsLabel.setText("You have to accept terms and conditions.");
            create_accout = false;
        }
        else {
            termsLabel.setText("");
        }

        if (create_accout) {
            App.database.addUser(login, password);
            App.setRoot("StartScene");
        }
    }
    @FXML
    public void goBack() throws Exception{
       App.setRoot("LoginScene");
    }
}
