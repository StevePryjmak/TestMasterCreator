package com.LerningBara.controller;
import java.io.ByteArrayInputStream;

import com.LerningBara.app.App;

import ImageData.ImageData;
import UserData.UserData;
import client.Client;
import connection.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    public void initialize(){
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
        if(obj instanceof ImageData){
            img = (ImageData)obj;
            if(img.map != null){
                ByteArrayInputStream inputStream = new ByteArrayInputStream(img.map);
                loaded_image = new Image(inputStream);
                profile_pic.setImage(loaded_image);
            }
        }
        else {
            System.out.println((String)obj);
        }
    }

    public void goBack() throws Exception{
        App.setRoot("UserMenuScene");
    }
}
