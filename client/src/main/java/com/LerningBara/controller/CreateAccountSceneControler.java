package com.LerningBara.controller;

import com.LerningBara.app.App;

import UserData.User;
import UserData.UserData;
import connection.Message;
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

    public void createAccount() throws Exception {
        boolean create_accout = true;
        String login = loginField.getText();
        String email = emailField.getText();
        String password = passwordField1.getText();
        Boolean db_resp = false;
        UserData usr = new UserData(login, password, email);

        App.getInstance().client.sendMessage("User exists", usr);
        System.out.println("Waiting for server response");
        Message messageReceived = App.getInstance().client.getOneRecivedObject();
        Object obj = messageReceived.getObject();
        if (obj instanceof Boolean) {
            db_resp = (Boolean) obj;
        }

        if (login.equals("")) {
            loginLabel.setText("Login cannot be empty.");
            create_accout = false;
        } else if (db_resp) {
            loginLabel.setText("Username already taken.");
            create_accout = false;
        } else {
            loginLabel.setText("");
        }

        if (email.equals("")) {
            emailLabel.setText("E-mail cannot be empty.");
            create_accout = false;
        } else {
            emailLabel.setText("");
        }

        if (password.equals("")) {
            passLabel.setText("Password cannot be empty.");
            create_accout = false;
        } else {
            passLabel.setText("");
        }
        if (!(password.equals(passwordField2.getText()))) {
            repeatLabel.setText("Different password.");
            create_accout = false;
        } else {
            repeatLabel.setText("");
        }

        if ((!termsField.isSelected())) {
            termsLabel.setText("You have to accept terms and conditions.");
            create_accout = false;
        } else {
            termsLabel.setText("");
        }

        if (create_accout) {
            // add user to db
            App.getInstance().client.sendMessage("Add user", usr);
            System.out.println("Waiting for server response");
            messageReceived = App.getInstance().client.getOneRecivedObject();

            // get user id from db
            App.getInstance().client.sendMessage("Get user", usr);
            System.out.println("Waiting for server response");
            messageReceived = App.getInstance().client.getOneRecivedObject();
            obj = messageReceived.getObject();
            if (obj instanceof User) {
                App.getInstance().user.setAttributes((User) obj);
            }
            App.setRoot("QuizMenuScene");
        }
    }

    @FXML
    public void goBack() throws Exception {
        App.setRoot("LoginScene");
    }
}
