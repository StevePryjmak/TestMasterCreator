package com.LerningBara.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    @FXML
    private Label likeCountLabel;
    @FXML
    private Button likeButton;
    @FXML
    private Button unlikeButton;
    @FXML
    private Label likeInfoLabel;
    @FXML
    private Label unlikeInfoLabel;

    private TestInfoData testData;

    private void setLikes() {
        System.out.println("Connected to server");
        Message message = new Message("Get test like count", testData.testID);
        App.getInstance().client.sendObject(message);
        System.out.println("Waiting for like count");
        Message messageReceived = App.getInstance().client.getOneRecivedObject();
        Object likes = messageReceived.getObject();
        likeCountLabel.setText("Likes: " + (int) likes);
    }

    public void setData(TestInfoData testData) {
        this.testData = testData;
        testData.currentUserID = App.getInstance().user.getId();

        setLikes();
        titleLabel.setText(testData.name);
        infoLabel.setText(testData.description + " | " + testData.field + " | " + testData.date);
        questionCountLabel.setText("Questions: " + testData.questionCount);
    }

    @FXML
    public void handleUnlike() {
        App.getInstance().client.sendMessage("Remove from likes", testData);
        System.out.println("Waiting to remove test from likes");
        Message messageReceived = App.getInstance().client.getOneRecivedObject();
        Object obj = messageReceived.getObject();
        likeInfoLabel.setText("");
        if ((Boolean) obj) {
            unlikeInfoLabel.setText("Removed from liked");
        } else {
            unlikeInfoLabel.setText("Already not liked");
        }
        setLikes();
    }

    @FXML
    public void handleLike() {
        App.getInstance().client.sendMessage("Add to likes", testData);
        System.out.println("Waiting to add test to likes");
        Message messageReceived = App.getInstance().client.getOneRecivedObject();
        Object obj = messageReceived.getObject();
        unlikeInfoLabel.setText("");
        if ((Boolean) obj) {
            likeInfoLabel.setText("Added to liked");
        } else {
            likeInfoLabel.setText("Already liked");
        }
        setLikes();
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
