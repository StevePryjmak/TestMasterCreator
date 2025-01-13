package com.LerningBara.controller;

import com.LerningBara.app.App;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginSceneControler {
   @FXML
   private ImageView logoView;

   @FXML
   public void initialize() throws Exception{
      Image image = new Image("/kapibara.png");
      logoView.setImage(image);
   }

   @FXML
   public void signIn() throws Exception{
    System.out.println("Go to login window");
    App.setRoot("SignInScene");
   }

   public void createAccount() throws Exception{
    System.out.println("Go to create account window");
    App.setRoot("CreateAccountScene");
   }
}
