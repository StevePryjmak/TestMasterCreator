package com.LerningBara.controller;

import com.LerningBara.app.App;
import javafx.fxml.FXML;

public class LoginSceneControler {
   public LoginSceneControler() {
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
