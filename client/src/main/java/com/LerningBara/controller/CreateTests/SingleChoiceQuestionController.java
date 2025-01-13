package com.LerningBara.controller.CreateTests;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import QuestionData.*;
import com.LerningBara.app.App;


public class SingleChoiceQuestionController extends CreateAbstractQestionController {

    @FXML
    private TextField questionInput;

    @FXML
    private VBox answerList;

    @FXML
    private Button addAnswerButton;

    @FXML
    private Button saveButton;

    private ToggleGroup toggleGroup;

    @FXML
    public void initialize() {
        toggleGroup = new ToggleGroup();
        addAnswerField();
        addAnswerField();

        addAnswerButton.setOnAction(event -> addAnswerField());
        saveButton.setOnAction(event -> handleSaveQuestion());
    }

    private void addAnswerField() {
        HBox answerBox = new HBox(10);

        TextField answerInput = new TextField();
        answerInput.setPromptText("Enter answer option");

        RadioButton correctAnswerButton = new RadioButton();
        correctAnswerButton.setToggleGroup(toggleGroup);

        
        Button deleteButton = new Button("Remove");
        deleteButton.setOnAction(event -> {
            answerList.getChildren().remove(answerBox);
            toggleGroup.getToggles().remove(correctAnswerButton);
        });

        answerBox.getChildren().addAll(correctAnswerButton, answerInput, deleteButton);
        answerList.getChildren().add(answerBox);
    }

    @FXML
    public void handleSaveQuestion() {
        String questionText = questionInput.getText().trim();
        if (questionText.isEmpty()) {
            System.out.println("Please provide a valid question.");
            return;
        }

        List<String> answers = new ArrayList<>();
        int correctAnswerIndex = -1;

        for (int i = 0; i < answerList.getChildren().size(); i++) {
            if (answerList.getChildren().get(i) instanceof HBox) {
                HBox answerBox = (HBox) answerList.getChildren().get(i);
                TextField answerInput = (TextField) answerBox.getChildren().get(1);
                RadioButton correctAnswerButton = (RadioButton) answerBox.getChildren().get(0);

                String answer = answerInput.getText().trim();
                if (!answer.isEmpty()) {
                    answers.add(answer);
                    if (correctAnswerButton.isSelected()) {
                        correctAnswerIndex = answers.size() - 1;
                    }
                }
            }
        }


        if (answers.size() < 2) {
            System.out.println("Please provide at least two valid answers.");
            return;
        }

        if (correctAnswerIndex == -1) {
            System.out.println("Please select the correct answer.");
            return;
        }

        SingleChoiceQuestionData question = new SingleChoiceQuestionData(
            questionText, answers, correctAnswerIndex
        );

        App.createTestController.addQuestion(question);
        App.setRoot("CreateTestScene");
        System.out.println("Question saved successfully!");
    }

    public AbstractQuestionData getQuestionData() {
        return null;
    }
}
