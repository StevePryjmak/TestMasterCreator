package com.LerningBara.controller;
import com.LerningBara.app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
//import javafx.scene.control.ImageView;


public class ShowProfileSceneControler {
    @FXML
    private Label email;
    @FXML
    private Label username;

    @FXML
    public void initialize(){
        App app = App.getInstance();
        email.setText(app.user.getEmail());
        username.setText(app.user.getUsername());
    }

    public void goBack() throws Exception{
        App.setRoot("UserMenuScene");
    }
}
