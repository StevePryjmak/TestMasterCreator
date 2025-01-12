package com.LerningBara.controller;

import java.io.IOException;
import java.util.List;

import com.LerningBara.app.App;

import TestData.AvalibleTestsList;
import TestData.TestInfoData;
import client.Client;
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
      // TODO: browseQuiz implementation
      // można tu wstawić funkcje z start scene, ogólnie bym się pozbyła tej scenki i
      // tu przeniosła
   }

   @FXML
   public void addQuiz() throws Exception {
      // App.setRoot("AddQuizScene");
   }

   @FXML
   public void myQuiz() throws Exception {
      String msg = "List of user test";
      int userID = 0; // TODO: change this to current userID
      getTestsList(msg, userID);
   }

   @FXML
   public void savedQuiz() throws Exception {
      String msg = "List of liked test";
      int userID = 0; // TODO: change this to current userID
      getTestsList(msg, userID);
   }

   public void getTestsList(String msg, int userID) {
      App.getInstance().client = new Client("localhost", 8080);
      System.out.println("Connected to server");
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
