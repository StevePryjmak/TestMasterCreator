package com.LerningBara.controller;

import javax.swing.text.StyledEditorKit.BoldAction;

import com.LerningBara.app.App;

import TestData.TestData;
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

        App.getInstance().client.closeConnection();
    }

    // TODO: check if this works
    @FXML
    public void returnToBrowsing() {
        App.setRoot("MainLayoutScene");
    }

    // TODO: check if this works
    @FXML
    private void initialize(int result) {
        // TODO: implement getting TestInfoData

        App.getInstance().client = new Client("localhost", 8080);
        System.out.println("Connected to server");
        App.getInstance().client.sendMessage("Give me the test", currentTest.name);
        System.out.println("Waiting for the test");
        Message messageReceived = App.getInstance().client.getOneRecivedObject();
        // App.getInstance().client.closeConnection();

        Object currentTest = messageReceived.getObject();
        if (currentTest instanceof TestData) {
            TestData currentTestData = (TestData) currentTest;
            String text = currentTestData.questions.size() + "/" + result;
            resultLabel.setText(text);
        }
    }
}
