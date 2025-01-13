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


import com.LerningBara.model.AbstractQuestion;
import com.LerningBara.model.QuestionConventor;
import javafx.scene.Scene;


import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;


public class CreateTestController {

    @FXML
    private Button addQuestionButton;

    @FXML
    private VBox questionList;

    @FXML
    private VBox questionsShortcuts;

    @FXML
    private ScrollPane scrollPane;

    private List<AbstractQuestionData> questions;

    @FXML
    public void initialize() {
        System.out.println("CreateTestController initialized");
  
        if (questionList == null) {
            questionList = new VBox();
        }
        if(questions == null) {
            questions = new ArrayList<>();
        }      
        System.out.println(questions.size());
        updateQuestionList();
        updateQuestionShortcuts();
    }

    @FXML
    public void handleAddQuestion() {
        System.out.println("Add Question Button Clicked");
        
        FXMLLoader questionBoxLoader = new FXMLLoader(getClass().getResource("/fxml/QuestionBox.fxml"));

        App.setRoot("CreateTests/SingleChoiceQuestionScene");
        App.createTestController = this;
    }

    public void addQuestion(SingleChoiceQuestionData question) {
        if (this.questions == null) {
            this.questions = new ArrayList<>();
        }
        
        this.questions.add(question); 
        System.out.println("Question added successfully!");
    }


    public void updateQuestionList() {
        questionList.getChildren().clear();
        int index = 0;
        for (AbstractQuestionData question : questions) {
            questionList.getChildren().add( createQuestionBox(question, index++) );
            
        }
    }
    
    private VBox createQuestionBox(AbstractQuestionData question, int index) {
        AbstractQuestion tempQestion = QuestionConventor.convertToQuestion(question);
        return tempQestion.getDetailsBox(index);
    }

    public void updateQuestionShortcuts() {
        questionsShortcuts.getChildren().clear();
        for (int i = 0; i < questions.size(); i++) {
            Button shortcutButton = new Button(questions.get(i).getQuestion() + (i + 1));
            int questionIndex = i;

            shortcutButton.setOnAction(event -> scrollToQuestion(questionIndex));
            questionsShortcuts.getChildren().add(shortcutButton);
        }
    }

    private void scrollToQuestion(int index) {
        if (index < 0 || index >= questionList.getChildren().size()) return;
        Node targetNode = questionList.getChildren().get(index);
        scrollPane.layout();
        double targetY = targetNode.getLayoutY();
        double scrollRatio = targetY / (questionList.getHeight() - scrollPane.getViewportBounds().getHeight());
        scrollPane.setVvalue(scrollRatio);
    }
    
    
    public void handleQuestionEdit(int index){
        System.out.println("Question #" + (index+1) + " clicked");

    }

    public void handleDeleteQuestion(int index) {
        System.out.println("Question #" + (index+1) + " deleted");
    }



    
}
