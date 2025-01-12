package com.LerningBara.controller;

import com.LerningBara.app.App;
import javafx.fxml.FXML;

public class UserMenuSceneControler {

   @FXML
   public void quizMenu() throws Exception {
      System.out.println("Go to quiz menu");
      App.setRoot("QuizMenuScene");
   }

   @FXML
   public void logOut() throws Exception {
      App.setRoot("LoginScene");
   }

   @FXML
   public void showProfile() throws Exception {
      // App.setRoot("ShowProfileScene");
   }

   @FXML
   public void editProfile() throws Exception {
      // App.setRoot("EditProfileScene");
   }
}
