package com.LerningBara.controller;

import com.LerningBara.app.App;
import javafx.fxml.FXML;

public class UserMenuSceneControler {

   @FXML
   public void quizMenu() throws Exception{
    System.out.println("Go to quiz menu");
    App.setRoot("QuizMenuScene");
   }

   public void logOut() throws Exception{
    App.setRoot("LoginScene");
   }

   public void showProfile() throws Exception{
    App.setRoot("ShowProfileScene");
   }

   public void editProfile() throws Exception{
    // App.setRoot("EditProfileScene");
   }
}
