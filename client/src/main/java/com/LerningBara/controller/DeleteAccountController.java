package com.LerningBara.controller;

import com.LerningBara.app.App;

import UserData.UserData;
import client.Client;
import connection.Message;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;

public class DeleteAccountController {

   @FXML
   private PasswordField passwordField;
   @FXML
   private Label passLabel;

   @FXML
   public void deleteAccount() throws Exception {
      String password = passwordField.getText();
      UserData usr = new UserData(App.getInstance().user.getUsername(), password);

      App.getInstance().client.sendMessage("Check password", usr);
      System.out.println("Waiting for password confirmation");
      Message messageReceived = App.getInstance().client.getOneRecivedObject();
      Object obj = messageReceived.getObject();

      if ((Boolean)obj) {
         App.getInstance().client.sendMessage("Delete user", usr);
         System.out.println("Waiting for database response");
         messageReceived = App.getInstance().client.getOneRecivedObject();
         obj = messageReceived.getObject();
         if((Boolean)obj){
            App.getInstance().user.setAttributes(0, "unknown", "unknown");
            App.setRoot("LoginScene");
         }
         else{
            passLabel.setText("Something went wrong.");
         }
      } else {
         passLabel.setText("Incorrect password.");
      }
   }

   @FXML
   public void goBack() throws Exception {
      App.setRoot("UserMenuScene");
   }
};
