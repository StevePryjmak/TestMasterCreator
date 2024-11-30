package com.LerningBara.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class SignInSceneControler {
   @FXML
   private TextField loginField;
   @FXML
   private PasswordField passwordField;


   @FXML
   public void signIn() {
      String login = loginField.getText();
      String password = passwordField.getText();
      System.out.println(login+password);
      //TODO CHECK THE DATABASE
      //TODO CHANGE SCENE TO MAIN MENU
   }

}
