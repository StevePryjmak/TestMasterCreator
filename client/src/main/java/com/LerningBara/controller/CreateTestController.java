package com.LerningBara.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class CreateTestController {

    @FXML
    private Button addQuestionButton;

    @FXML
    private VBox questionList;

    @FXML
    public void handleAddQuestion() {
        System.out.println("Add Question Button Clicked");

        FXMLLoader questionBoxLoader = new FXMLLoader(getClass().getResource("/fxml/QuestionBox.fxml"));
        try {
            VBox questionBox = questionBoxLoader.load();
            questionList.getChildren().add(questionBox);
        } catch (IOException e) {
            System.out.println("Error loading QuestionBox.fxml: " + e);
        }
    }
}
