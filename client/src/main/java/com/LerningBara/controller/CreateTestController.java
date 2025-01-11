package com.LerningBara.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import com.LerningBara.app.App;

import QuestionData.*;

import java.util.ArrayList;
import java.util.List;

public class CreateTestController {

    @FXML
    private Button addQuestionButton;

    @FXML
    private VBox questionList;

    private List<AbstractQuestionData> questions;


    @FXML
    public void handleAddQuestion() {
        if(questions == null) {
            questions = new ArrayList<>();
        }
        System.out.println(questions.size());
        System.out.println("Add Question Button Clicked");
        
        FXMLLoader questionBoxLoader = new FXMLLoader(getClass().getResource("/fxml/QuestionBox.fxml"));

        App.setRoot("CreateTests/SingleChoiceQuestionScene1");

        // App.setRoot("CreateTestScene");
        try {
            VBox questionBox = questionBoxLoader.load();
            questionList.getChildren().add(questionBox);
        } catch (IOException e) {
            System.out.println("Error loading QuestionBox.fxml: " + e);
        }
        App.createTestController = this;
    }

    public void addQuestion(SingleChoiceQuestionData question) {
        if (this.questions == null) {
            this.questions = new ArrayList<>();
        }
        
        this.questions.add(question); 
        System.out.println("Question added successfully!");
    }

}
