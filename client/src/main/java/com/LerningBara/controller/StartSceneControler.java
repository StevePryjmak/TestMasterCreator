package com.LerningBara.controller;

import com.LerningBara.app.App;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.List;
import connection.Message;

import TestData.*;

public class StartSceneControler {

    @FXML
    public void startTest() throws Exception {
        this.getTestsList();
    }

    @FXML
    public void createTest() {
        App.createTestController = new CreateTestController();
        App.setRoot("CreateTestScene");
    }

    public void getTestsList() {
        App.getInstance().client.sendMessage("List of tests", null);
        System.out.println("Waiting for list of tests");
        Message messageReceived = App.getInstance().client.getOneRecivedObject();
        // App.getInstance().client.closeConnection();

        Object r = messageReceived.getObject();
        if (r instanceof AvalibleTestsList) {
            AvalibleTestsList avalibleTestsList = (AvalibleTestsList) r;
            List<TestInfoData> tests = avalibleTestsList.getTests();
            try {
                App.getInstance().showTestsList(tests, false);
            } catch (IOException e) {
                System.out.println(e);
            }

        }
    }
}