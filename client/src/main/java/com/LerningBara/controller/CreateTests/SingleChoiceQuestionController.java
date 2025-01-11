package com.LerningBara.controller.CreateTests;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import QuestionData.SingleChoiceQuestionData;

import java.util.Arrays;
import java.util.List;

import com.LerningBara.app.App;

public class SingleChoiceQuestionController {

    @FXML
    private TextField questionInput;

    @FXML
    private TextField answerInput1;

    @FXML
    private TextField answerInput2;

    @FXML
    private TextField answerInput3;

    @FXML
    private TextField answerInput4;

    @FXML
    public void handleSaveQuestion() {
        String questionText = questionInput.getText();
        String answer1 = answerInput1.getText();
        String answer2 = answerInput2.getText();
        String answer3 = answerInput3.getText();
        String answer4 = answerInput4.getText();

        // Validate inputs
        if (questionText.isEmpty() || answer1.isEmpty() || answer2.isEmpty()) {
            System.out.println("Please fill in the question and at least two answers.");
            return;
        }

        // Save or process the question data
        System.out.println("Question saved:");
        System.out.println("Question: " + questionText);
        System.out.println("Answers: " + answer1 + ", " + answer2 + ", " + answer3 + ", " + answer4);

        List<String> answers = Arrays.asList(answer1, answer2, answer3, answer4);
        SingleChoiceQuestionData question = new SingleChoiceQuestionData(
            questionText,
            answers,
            1 // @TODO CORRECT THIS LATER
        );
        App.createTestController.addQuestion(question);
        App.setRoot("CreateTestScene");


        // Clear inputs for a new question
        questionInput.clear();
        answerInput1.clear();
        answerInput2.clear();
        answerInput3.clear();
        answerInput4.clear();
    }
}
