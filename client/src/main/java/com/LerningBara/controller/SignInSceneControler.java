package com.LerningBara.controller;

import com.LerningBara.app.App;

import UserData.UserData;
import client.Client;
import connection.Message;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import database.DataBase;

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
      Boolean user_exists = false;
      Boolean corr_pass = false;
      UserData usr = new UserData(login, password);
      App.getInstance().client = new Client("localhost", 8080);
      System.out.println("Connected to server");
      App.getInstance().client.sendMessage("User exists", usr);
      System.out.println("Waiting for list of tests");
      Message messageReceived = App.getInstance().client.getOneRecivedObject();
      Object obj = messageReceived.getObject();
      if (obj instanceof Boolean){
         user_exists = (Boolean)obj;
      }
      App.getInstance().client = new Client("localhost", 8080);
      System.out.println("Connected to server");
      App.getInstance().client.sendMessage("Check password", usr);
      System.out.println("Waiting for list of tests");
      messageReceived = App.getInstance().client.getOneRecivedObject();
      obj = messageReceived.getObject();
      if (obj instanceof Boolean){
         corr_pass= (Boolean)obj;
      }

      if (user_exists){
         loginLabel.setText("");
         if (DataBase.checkPassword(login, password)){
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
