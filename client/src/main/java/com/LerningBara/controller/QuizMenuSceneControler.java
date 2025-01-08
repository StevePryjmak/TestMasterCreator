package com.LerningBara.controller;

import com.LerningBara.app.App;
import javafx.fxml.FXML;

public class QuizMenuSceneControler {

   @FXML
   public void userMenu() throws Exception{
    System.out.println("Go to user menu");
    App.setRoot("UserMenuScene");
   }

   public void browseQuiz() throws Exception{
    //można tu wstawić funkcje z start scene, ogólnie bym się pozbyła tej scenki i tu przeniosła
   }

   public void addQuiz() throws Exception{
    // App.setRoot("AddQuizScene");
   }

   public void myQuiz() throws Exception{
    // App.setRoot("MyQuizScene");
   }

   public void savedQuiz() throws Exception{
    // App.setRoot("SavedQuizScene");
   }
}
