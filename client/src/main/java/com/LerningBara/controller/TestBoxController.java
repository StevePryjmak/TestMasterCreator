package com.LerningBara.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import connection.Message;

import java.util.List;

import com.LerningBara.app.App;
import com.LerningBara.model.Test;

import QuestionData.AbstractQuestionData;
import TestData.TestData;
import TestData.TestInfoData;
import client.Client;

public class TestBoxController {
    @FXML
    private Label titleLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private Label questionCountLabel;

    private TestInfoData testData;

    public void setData(TestInfoData testData) {
        this.testData = testData;
        titleLabel.setText(testData.name);
        infoLabel.setText(testData.description + " | " + testData.field + " | " + testData.date);
        questionCountLabel.setText("Questions: " + testData.questionCount);
    }

    @FXML
    private void handleTestClick() {
        System.out.println("Test clicked: " + testData.name);
        App.getInstance().testInfoData = testData;
        App.getInstance().testInfoData.result = 0;
        getTest();
    }

    public void getTest() {
        System.out.println("Connected to server");
        App.getInstance().client = new Client("localhost", 8080);
        Message message = new Message("Give me the test", new String(testData.name));
        App.getInstance().client.sendObject(message);
        System.out.println("Waiting for test");

        Message messageReceived = App.getInstance().client.getOneRecivedObject();
        Object r = messageReceived.getObject();
        if (r instanceof TestData) {
            TestData testData = (TestData) r;
            List<AbstractQuestionData> questions = testData.questions;
            Test test = new Test(questions, true);
            App.getInstance().setTest(test);
            App.getInstance().runExampleTest();
        }
    }
}
