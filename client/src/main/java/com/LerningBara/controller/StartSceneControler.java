package com.LerningBara.controller;

import com.LerningBara.app.App;
import QuestionData.TestData;
import com.LerningBara.model.Test;
import QuestionData.AbstractQuestionData;
import javafx.fxml.FXML;
import java.util.List;
import client.Client;
import connection.Message;

public class StartSceneControler {
    
    @FXML
    public void startTest() throws Exception {
        this.getTest();
        App.getInstance().runExampleTest();
    }

    public void getTest() {
        System.out.println("Connected to server");
        App.getInstance().client = new Client("localhost", 8080);
        Message message = new Message("Give me the test", null);
        App.getInstance().client.sendObject(message);
        System.out.println("Waiting for test");
        
        Message messageReceived = App.getInstance().client.getOneRecivedObject();;
        Object r = messageReceived.getObject();
        if(r instanceof TestData) {
            TestData testData = (TestData) r;
            List<AbstractQuestionData> questions = testData.questions;
            Test test = new Test(questions, true);
            App.getInstance().setTest(test);
            App.getInstance().runExampleTest();
        }
    }
}