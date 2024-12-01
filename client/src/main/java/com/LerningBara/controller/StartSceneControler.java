package com.LerningBara.controller;

import com.LerningBara.app.App;
import TestData.TestData;
import com.LerningBara.model.Test;
import QuestionData.AbstractQuestionData;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.List;
import client.Client;
import connection.Message;

import TestData.*;

public class StartSceneControler {
    
    @FXML
    public void startTest() throws Exception {
        this.getTestsList();
    }

    public void getTestsList() {
        System.out.println("Connected to server");
        App.getInstance().client = new Client("localhost", 8080);
        Message message = new Message("List of tests", null);
        App.getInstance().client.sendObject(message);
        System.out.println("Waiting for list of tests");
        
        Message messageReceived = App.getInstance().client.getOneRecivedObject();;
        Object r = messageReceived.getObject();
        if(r instanceof AvalibleTestsList) {
            AvalibleTestsList avalibleTestsList = (AvalibleTestsList) r;
            List<TestInfoData> tests = avalibleTestsList.getTests();
            try {
                App.getInstance().showTestsList(tests);
            }
            catch (IOException e) {
                System.out.println(e);
            }
            
        }
    }
}