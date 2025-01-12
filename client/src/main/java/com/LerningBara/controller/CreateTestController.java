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


public class CreateTestController {

    @FXML
    private Button addQuestionButton;

    @FXML
    private VBox questionList;

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
        int index = 1;
        for (AbstractQuestionData question : questions) {
            questionList.getChildren().add( createQuestionBox(question, index++) );
            
        }
    }
    
    private VBox createQuestionBox(AbstractQuestionData question, int index) {
        AbstractQuestion tempQestion = QuestionConventor.convertToQuestion(question);
        return tempQestion.getDetailsBox(index);
    }
    
    public void handleQuestionEdit(int index){
        System.out.println("Question #" + index + " clicked");

    }
    
    
}
