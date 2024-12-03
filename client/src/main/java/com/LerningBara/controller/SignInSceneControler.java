package com.LerningBara.controller;

import com.LerningBara.app.App;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class SignInSceneControler {
   @FXML
   private TextField loginField;
   @FXML
   private PasswordField passwordField;
   @FXML
   private Label passLabel;
   @FXML
   private Label loginLabel;


   @FXML
   public void signIn() throws Exception{
      String login = loginField.getText();
      String password = passwordField.getText();
      if (App.database.userExists(login)){
         if (App.database.checkPassword(login, password)){
            App.setRoot("StartScene");
         }
         else{
            passLabel.setText("Incorrect password.");
         }
      }
      else {
         passLabel.setText("");
         loginLabel.setText("Username not found.");
      }
   }
   @FXML
   public void goBack() throws Exception{
      App.setRoot("LoginScene");
   }
}
