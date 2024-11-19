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
        App.getInstance().runExampleTest();
    }
}