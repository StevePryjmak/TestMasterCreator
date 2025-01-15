package com.LerningBara.controller.CreateTests;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import QuestionData.OpenAnwserQuestionData;
import com.LerningBara.app.App;

public class CreateOpenAnswerQuestionController extends CreateAbstractQestionController {

    @FXML
    private TextField questionInput;

    @FXML
    private TextField correctAnswerInput;

    @FXML
    private Button saveButton;

    private String questionText;
    private String correctAnswer;

    @FXML
    public void initialize() {
        initializeFields();

        saveButton.setOnAction(event -> handleSaveQuestion());
    }

    private void initializeFields() {
        if (questionText != null) {
            questionInput.setText(questionText);
        }

        if (correctAnswer != null) {
            correctAnswerInput.setText(correctAnswer);
        }
    }

    @FXML
    public void handleSaveQuestion() {
        questionText = questionInput.getText().trim();
        correctAnswer = correctAnswerInput.getText().trim();

        if (questionText.isEmpty() || correctAnswer.isEmpty()) {
            System.out.println("Please provide a valid question and correct answer.");
            return;
        }

        if (!isEdit) {
            App.createTestController.addQuestion(this);
        }
        App.setRoot("CreateTestScene");
        System.out.println("Open question saved successfully!");
    }

    @Override
    public OpenAnwserQuestionData getQuestionData() {
        return new OpenAnwserQuestionData(questionText, correctAnswer);
    }
}
