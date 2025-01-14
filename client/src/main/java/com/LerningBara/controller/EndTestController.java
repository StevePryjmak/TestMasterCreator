package com.LerningBara.controller;

import java.io.IOException;
import java.util.List;

import com.LerningBara.app.App;

import TestData.AvalibleTestsList;
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
    private Label bestResultLabel;
    @FXML
    private Label latestResultLabel;
    @FXML
    private Label likeInfoLabel;

    private TestInfoData currentTest;

    @FXML
    public void addToLiked() {
        App.getInstance().client.sendMessage("Add to likes", currentTest);
        System.out.println("Waiting to add test to likes");
        Message messageReceived = App.getInstance().client.getOneRecivedObject();
        Object obj = messageReceived.getObject();
        if ((Boolean) obj) {
            likeInfoLabel.setText("Added to liked");
        } else {
            likeInfoLabel.setText("Already liked");
        }
    }

    @FXML
    public void returnToBrowsing() {
        App.getInstance().client.sendMessage("List of tests", null);
        System.out.println("Waiting for list of tests");
        Message messageReceived = App.getInstance().client.getOneRecivedObject();

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

    @FXML
    @SuppressWarnings("unchecked")
    private void initialize() {
        currentTest = App.getInstance().testInfoData;
        currentTest.currentUserID = App.getInstance().user.getId();

        App.getInstance().client.sendMessage("Add result", currentTest);
        System.out.println("Waiting to add test result to database");
        Message messageReceived = App.getInstance().client.getOneRecivedObject();
        Object obj = messageReceived.getObject();
        // TODO: change this into some sort of feedback to server or sth

        if ((Boolean) obj) {
            System.out.println(
                    "Successfuly added the result, result: " + currentTest.result + "/" + currentTest.questionCount);
        } else {
            System.out.println("Failed to add the result");
        }

        App.getInstance().client.sendMessage("Get user's test results", currentTest);
        System.out.println("Waiting to user's test results");
        messageReceived = App.getInstance().client.getOneRecivedObject();
        obj = messageReceived.getObject();

        int bestResult = 0;
        for (int i : (List<Integer>) obj) {
            if (i > bestResult) {
                bestResult = i;
            }
        }
        String text = "Best result: " + String.valueOf(bestResult) + "/" + currentTest.questionCount;
        bestResultLabel.setText(text);

        text = "Latest result: " + currentTest.result + "/" + currentTest.questionCount;
        latestResultLabel.setText(text);
        likeInfoLabel.setText("");

    }
}
