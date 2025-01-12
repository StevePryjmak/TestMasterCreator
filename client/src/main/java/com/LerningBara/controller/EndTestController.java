package com.LerningBara.controller;

import com.LerningBara.app.App;

import TestData.TestInfoData;
import client.Client;
import connection.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class EndTestController {

    @FXML
    private Button quizBrowserButton;
    @FXML
    private Button likeButton;
    @FXML
    private Label resultLabel;
    @FXML
    private Label likeInfoLabel;

    private TestInfoData currentTest;

    // TODO: check if this works
    @FXML
    public void addToLiked() {
        App.getInstance().client = new Client("localhost", 8080);
        System.out.println("Connected to server");
        App.getInstance().client.sendMessage("Add to likes", currentTest);
        System.out.println("Waiting to add test to likes");
        Message messageReceived = App.getInstance().client.getOneRecivedObject();
        Object obj = messageReceived.getObject();
        if ((Boolean) obj) {
            likeInfoLabel.setText("Added to liked");
        } else {
            likeInfoLabel.setText("Alredy liked");
        }
    }

    // TODO: check if this works
    @FXML
    public void returnToBrowsing() {
        App.setRoot("MainLayoutScene");
    }

    // TODO: check if this works
    @FXML
    private void initialize() {
        currentTest = App.getInstance().testInfoData;
        currentTest.currentUserID = App.getInstance().user.getId();

        App.getInstance().client = new Client("localhost", 8080);
        System.out.println("Connected to server");
        App.getInstance().client.sendMessage("Add result", currentTest);
        System.out.println("Waiting to add test result to database");
        Message messageReceived = App.getInstance().client.getOneRecivedObject();
        Object _ = messageReceived.getObject();

        String text = currentTest.result + "/" + currentTest.questionCount;
        resultLabel.setText(text);
    }
}
