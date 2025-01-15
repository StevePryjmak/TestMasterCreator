package com.LerningBara.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

import com.LerningBara.app.App;
import com.LerningBara.controller.OpenAnwserQuestionController;

import QuestionData.OpenAnwserQuestionData;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class OpenAnwserQuestion extends AbstractQuestion {

    private OpenAnwserQuestionData questionData;
    private OpenAnwserQuestionController controller;

    public OpenAnwserQuestion(String question, String correctAnswer) throws IOException {
        questionData = new OpenAnwserQuestionData(question, correctAnswer);
        initializeScene();
    }

    public OpenAnwserQuestion(OpenAnwserQuestionData questionData) throws IOException {
        this.questionData = questionData;
        initializeScene();
    }

    private void initializeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/OpenAnwserQuestionScene.fxml"));
        Parent root = loader.load();

        controller = loader.getController();
        controller.setQuestionAndAnswers(questionData.getQuestion(), questionData.getCorrectAnswer());

        scene = new Scene(root);
    }

    @Override
    public boolean checkAnswer(String answer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkAnswer'");
    }

    @Override
    public VBox getDetailsBox(int index) {
        VBox questionBox = new VBox(10);
        questionBox.getStyleClass().add("question-box");
        questionBox.setOnMouseClicked(_ -> {
            App.createTestController.handleQuestionEdit(index, false);
        });

        Label questionLabel = new Label("Question #" + (index + 1) + ": " + questionData.getQuestion());
        questionLabel.getStyleClass().add("question-label");

        Label correctAnswerLabel = new Label("Correct Answer: " + questionData.getCorrectAnswer());
        correctAnswerLabel.getStyleClass().add("answer-label");

        questionBox.getChildren().addAll(questionLabel, correctAnswerLabel);
        return questionBox;
    }

}