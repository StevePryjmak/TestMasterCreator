package com.testMC.controller;

import com.testMC.app.App;

import javafx.fxml.FXML;

public class StartSceneControler {
    @FXML
    public void startTest() throws Exception {
        App app = new App();
        app.runExampleTest();
    }
}
