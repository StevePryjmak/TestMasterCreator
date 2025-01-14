package com.LerningBara.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import com.LerningBara.app.App;

import UserData.User;
import UserData.UserData;
import ImageData.ImageData;
import client.Client;
import connection.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
//import javafx.scene.control.ImageView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;


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
        User curr_user = App.getInstance().user;

        String emailStr = emailField.getText();
        if (emailStr.equals("")){
            emailLabel.setStyle("-fx-text-fill: red");
            emailLabel.setText("Email cannot be empty.");
            return;
        }

        UserData usr = new UserData(curr_user.getUsername(), "", emailStr, curr_user.getId());
        App.getInstance().client.sendMessage("Update user", usr);
        System.out.println("Waiting for server response");
        Message messageReceived = App.getInstance().client.getOneRecivedObject();
        Object obj = messageReceived.getObject();
        if ((Boolean)obj) {
            App.getInstance().user.setAttributes(curr_user.getId(), curr_user.getUsername(), emailStr);
            email.setText(emailStr);
            emailLabel.setStyle("-fx-text-fill: blue");
            emailLabel.setText("Email changed.");
        }
        else {
            emailLabel.setStyle("-fx-text-fill: red");
            emailLabel.setText("Database failure.");
        }

    }

    public void changeUsername(){
        passLabel.setText("");
        emailLabel.setText("");
        String usernameStr = usernameField.getText();
        Boolean db_resp = false;
        UserData usr = new UserData(usernameStr);
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

        User curr_user = App.getInstance().user;
        usr = new UserData(usernameStr, "", curr_user.getEmail(), curr_user.getId());
        App.getInstance().client.sendMessage("Update user", usr);
        System.out.println("Waiting for server response");
        messageReceived = App.getInstance().client.getOneRecivedObject();
        obj = messageReceived.getObject();
        if ((Boolean)obj) {
            App.getInstance().user.setAttributes(curr_user.getId(), usernameStr, curr_user.getEmail());
            username.setText(usernameStr);
            usernameLabel.setStyle("-fx-text-fill: blue");
            usernameLabel.setText("Username changed.");
        }
        else {
            usernameLabel.setStyle("-fx-text-fill: red");
            usernameLabel.setText("Database failure.");
        }
    }

    public void changePassword(){
        usernameLabel.setText("");
        emailLabel.setText("");
        User curr_user = App.getInstance().user;
        String passStr = newPassField.getText();
        Boolean db_resp = false;
        UserData usr = new UserData(curr_user.getUsername(), oldPassField.getText());
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

        usr = new UserData(curr_user.getUsername(), passStr, curr_user.getEmail(), curr_user.getId());
        App.getInstance().client.sendMessage("Update user", usr);
        System.out.println("Waiting for server response");
        messageReceived = App.getInstance().client.getOneRecivedObject();
        obj = messageReceived.getObject();
        if ((Boolean)obj) {
            passLabel.setStyle("-fx-text-fill: blue");
            passLabel.setText("Password changed.");
        }
        else {
            passLabel.setStyle("-fx-text-fill: red");
            passLabel.setText("Database failure.");
        }
    }

    public void uploadPicture() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a picture");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Obrazy", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(App.getInstance().getPrimaryStage());

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            BufferedImage bufferedImage = javafx.embed.swing.SwingFXUtils.fromFXImage(image, null);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            ImageData img = new ImageData();
            img.map = imageBytes;
            img.username = App.getInstance().user.getUsername();

            App.getInstance().client.sendMessage("Add profile icon", img);
            System.out.println("Waiting for server response");
            Message messageReceived = App.getInstance().client.getOneRecivedObject();
            Object obj = messageReceived.getObject();

            if((Boolean)obj){
                System.out.println("Image has been sent succesfully.");
            }

        } else {
            System.out.println("No file has been chosen.");
        }
    }


    public void goBack() throws Exception{
        App.setRoot("UserMenuScene");
    }
}
