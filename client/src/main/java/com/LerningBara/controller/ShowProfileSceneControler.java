package com.LerningBara.controller;

import java.io.ByteArrayInputStream;

import com.LerningBara.app.App;

import ImageData.ImageData;
import UserData.UserData;
import connection.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShowProfileSceneControler {
    @FXML
    private Label email;
    @FXML
    private Label username;
    @FXML
    private ImageView profile_pic;
    @FXML
    private Button visibleButton;

    @FXML
    public void initialize() {
        App app = App.getInstance();
        email.setText(app.user.getEmail());
        username.setText(app.user.getUsername());
        profile_pic.setPreserveRatio(true);
        UserData usr = new UserData(app.user.getUsername());
        ImageData img;
        Image loaded_image = null;
        App.getInstance().client.sendMessage("Get profile icon", usr);
        System.out.println("Waiting for server response");
        Message messageReceived = App.getInstance().client.getOneRecivedObject();
        Object obj = messageReceived.getObject();
        if (obj instanceof ImageData) {
            img = (ImageData) obj;
            if (img.map != null) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(img.map);
                loaded_image = new Image(inputStream);
                profile_pic.setImage(loaded_image);
            }
        } else {
            System.out.println((String) obj);
        }
        App.getInstance().client.sendMessage("Get visibility", usr);
        System.out.println("Waiting for server response");
        messageReceived = App.getInstance().client.getOneRecivedObject();
        obj = messageReceived.getObject();
        if (!(Boolean)obj){
            visibleButton.setText("Public");
        }

    }

    public void changeVisibility() throws Exception {
        UserData usr = new UserData(App.getInstance().user.getUsername(), visibleButton.getText());
        App.getInstance().client.sendMessage("Set visibility", usr);
        System.out.println("Waiting for server response");
        Message messageReceived = App.getInstance().client.getOneRecivedObject();
        Object obj = messageReceived.getObject();
        if((Boolean)obj){
            if((visibleButton.getText()).equals("Public")){
                visibleButton.setText("Private");
            }
            else{
                visibleButton.setText("Public");
            }
        }
    }

    public void goBack() throws Exception {
        App.setRoot("UserMenuScene");
    }
}
