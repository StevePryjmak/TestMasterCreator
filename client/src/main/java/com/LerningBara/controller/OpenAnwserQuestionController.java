package com.LerningBara.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.LerningBara.app.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class OpenAnwserQuestionController {
    @FXML
    private Label question;
    @FXML
    private TextField anwser;
    @FXML
    private Button next;

    private String correctAnswer;

    // Method to set question and answers dynamically
    public void setQuestionAndAnswers(String given_question, String correctAnswer) {
        question.setText(given_question);
        anwser.setPromptText("write anwser here");
        this.correctAnswer = correctAnswer;

    }

    // Check if answer is selected and then if the anwser is correct
    @FXML
    private void handleCheckButton() {
        String selectedAnswer = anwser.getText();

        if (selectedAnswer.isEmpty()) {
            System.out.println("Please write an anwser.");
        } else {
            checkAnswer(selectedAnswer);
            App.getInstance().nextQuestion();
        }
    }

    // Check if the selected answer is correct
    private void checkAnswer(String selectedAnswer) {
        if (selectedAnswer.equals(correctAnswer)) {
            System.out.println("Correct Answer!");
        } else {
            System.out.println("Incorrect Answer!");
        }

    }
}
