package com.LerningBara.controller;

import java.io.IOException;
import java.util.List;

import com.LerningBara.app.App;

import TestData.AvalibleTestsList;
import TestData.TestInfoData;
import connection.Message;
import javafx.fxml.FXML;

public class QuizMenuController {

   @FXML
   public void userMenu() throws Exception {
      System.out.println("Go to user menu");
      App.setRoot("UserMenuScene");
   }

   @FXML
   public void browseQuiz() throws Exception {
      getTestsList("List of tests", -1);
   }

   @FXML
   public void addQuiz() throws Exception {
      App.createTestController = new CreateTestController();
      App.setRoot("CreateTestScene");
   }

   @FXML
   public void myQuiz() throws Exception {
      String msg = "List of user test";
      int userID = App.getInstance().user.getId();
      getTestsList(msg, userID);
   }

   @FXML
   public void savedQuiz() throws Exception {
      String msg = "List of liked test";
      int userID = App.getInstance().user.getId();
      getTestsList(msg, userID);
   }

   public void getTestsList(String msg, int userID) {
      if (userID == -1)
         App.getInstance().client.sendMessage(msg, null);
      else
         App.getInstance().client.sendMessage(msg, userID);

      System.out.println("Waiting for list of tests");
      Message messageReceived = App.getInstance().client.getOneRecivedObject();
      // App.getInstance().client.closeConnection();

      Object r = messageReceived.getObject();
      if (r instanceof AvalibleTestsList) {
         AvalibleTestsList avalibleTestsList = (AvalibleTestsList) r;
         List<TestInfoData> tests = avalibleTestsList.getTests();
         try {
            App.getInstance().showTestsList(tests);
         } catch (IOException e) {
            System.out.println(e);
         }

      }
   }
}
