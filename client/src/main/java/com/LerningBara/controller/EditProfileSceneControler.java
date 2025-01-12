package com.LerningBara.controller;

import com.LerningBara.app.App;

import UserData.UserData;
import client.Client;
import connection.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
//import javafx.scene.control.ImageView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class EditProfileSceneControler {
    @FXML
    private Label email;
    @FXML
    private Label username;
    @FXML
    private Label emailLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField oldPassField;
    @FXML
    private PasswordField newPassField;

    @FXML
    public void initialize(){
        App app = App.getInstance();
        email.setText(app.user.getEmail());
        username.setText(app.user.getUsername());
    }

    public void changeEmail(){
        passLabel.setText("");
        usernameLabel.setText("");

        String emailStr = emailField.getText();
        if (emailStr.equals("")){
            emailLabel.setStyle("-fx-text-fill: red");
            emailLabel.setText("Email cannot be empty.");
            return;
        }

        int id = App.getInstance().user.getId();
        String usernameStr = App.getInstance().user.getUsername();
        App.getInstance().user.setAttributes(id, usernameStr, emailStr);
        // TODO change email in database
        email.setText(emailStr);
        emailLabel.setStyle("-fx-text-fill: blue");
        emailLabel.setText("Email changed.");
    }

    public void changeUsername(){
        passLabel.setText("");
        emailLabel.setText("");
        String usernameStr = usernameField.getText();
        Boolean db_resp = false;
        UserData usr = new UserData(usernameStr);
        App.getInstance().client = new Client("localhost", 8080);
        System.out.println("Connected to server");
        App.getInstance().client.sendMessage("User exists", usr);
        System.out.println("Waiting for server response");
        Message messageReceived = App.getInstance().client.getOneRecivedObject();
        Object obj = messageReceived.getObject();
        if (obj instanceof Boolean){
            db_resp = (Boolean)obj;
        }

        if (usernameStr.equals("")){
            usernameLabel.setStyle("-fx-text-fill: red");
            usernameLabel.setText("Username cannot be empty.");
            return;
        }
        else if(db_resp){
            usernameLabel.setStyle("-fx-text-fill: red");
            usernameLabel.setText("Username already taken.");
            return;
        }

        int id = App.getInstance().user.getId();
        String emailStr = App.getInstance().user.getEmail();
        App.getInstance().user.setAttributes(id, usernameStr, emailStr);
        // TODO change username in database
        username.setText(usernameStr);
        usernameLabel.setStyle("-fx-text-fill: blue");
        usernameLabel.setText("Username changed.");
    }

    public void changePassword(){
        usernameLabel.setText("");
        emailLabel.setText("");
        String usernameStr = App.getInstance().user.getUsername();
        String passStr = newPassField.getText();
        Boolean db_resp = false;
        UserData usr = new UserData(usernameStr, oldPassField.getText());
        App.getInstance().client = new Client("localhost", 8080);
        System.out.println("Connected to server");
        App.getInstance().client.sendMessage("Check password", usr);
        System.out.println("Waiting for server response");
        Message messageReceived = App.getInstance().client.getOneRecivedObject();
        Object obj = messageReceived.getObject();
        if (obj instanceof Boolean){
            db_resp = (Boolean)obj;
        }

        if (passStr.equals("")){
            passLabel.setStyle("-fx-text-fill: red");
            passLabel.setText("New password cannot be empty.");
            return;
        }
        else if(!db_resp){
            passLabel.setStyle("-fx-text-fill: red");
            passLabel.setText("Incorrect password.");
            return;
        }

        // TODO change password in database
        passLabel.setStyle("-fx-text-fill: blue");
        passLabel.setText("Password changed.");
    }

    public void goBack() throws Exception{
        App.setRoot("UserMenuScene");
    }
}
