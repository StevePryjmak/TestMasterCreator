package com.LerningBara.controller;

import com.LerningBara.app.App;
import javafx.fxml.FXML;

public class UserMenuSceneControler {

   @FXML
   public void quizMenu() throws Exception {
      System.out.println("Go to quiz menu");
      App.setRoot("QuizMenuScene");
   }

   public void logOut() throws Exception {
      App.getInstance().user.setAttributes(0, null, null);
      App.setRoot("LoginScene");
   }

   public void showProfile() throws Exception {
      App.setRoot("ShowProfileScene");
   }

   public void editProfile() throws Exception {
      App.setRoot("EditProfileScene");
   }

   public void deleteAccount()throws Exception{
      App.setRoot("DeleteAccountScene");
   }
}
