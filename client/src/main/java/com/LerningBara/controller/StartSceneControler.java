package com.LerningBara.controller;

import com.LerningBara.app.App;
import QuestionData.TestData;
import com.LerningBara.model.Test;
import QuestionData.AbstractQuestionData;
import javafx.fxml.FXML;
import java.util.List;
import client.Client;

public class StartSceneControler {
    
    @FXML
    public void startTest() throws Exception {
        this.getTest();
        App.getInstance().runExampleTest();
    }

    public void getTest() {
        System.out.println("Connected to server");
        App.client = new Client("localhost", 8080);
        App.client.listenForMessages();
        App.client.sendMessage("Give me the test");
        Object received = App.client.getOneRecivedObject();
        if(received instanceof TestData) {
            TestData testData = (TestData) received;
            List<AbstractQuestionData> questions = testData.questions;
            Test test = new Test(questions, true);
            App.getInstance().setTest(test);
            App.getInstance().runExampleTest();
        }
        // while (true) {
        //     System.err.println("Waiting for test data...");
        //     // Object received = client.getOneRecivedObject();
        //     // if (received != null) {
        //     //     if (received instanceof TestData) {
        //     //         TestData testData = (TestData) received;
        //     //         List<AbstractQuestionData> questions = testData.questions;
        //     //         Test test = new Test(questions, true); // Use 'new' keyword to instantiate
        //     //         System.out.println(test);
        //     //         break;
        //     //     }
        //     // }
        // }
    }
}