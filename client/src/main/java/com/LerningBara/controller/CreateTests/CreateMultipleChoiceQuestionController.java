package com.LerningBara.controller.CreateTests;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import QuestionData.*;
import com.LerningBara.app.App;

public class CreateMultipleChoiceQuestionController extends CreateAbstractQestionController {

    @FXML
    private TextField questionInput;

    @FXML
    private VBox answerList;

    @FXML
    private Button addAnswerButton;

    @FXML
    private Button saveButton;

    @FXML
    private Label infoLabel;

    private List<String> answers;
    private List<Integer> correctAnswerIndexes;

    @FXML
    public void initialize() {
        infoLabel.setText("");
        answers = new ArrayList<>();
        correctAnswerIndexes = new ArrayList<>();
        addAnswerField(); // Add an initial answer field
        addAnswerField(); // Add another initial answer field

        addAnswerButton.setOnAction(_ -> addAnswerField());
        saveButton.setOnAction(_ -> handleSaveQuestion());
    }

    private void addAnswerField() {
        HBox answerBox = new HBox(10);

        TextField answerInput = new TextField();
        answerInput.setPromptText("Enter answer option");

        CheckBox correctAnswerCheckbox = new CheckBox("Correct Answer");

        Button deleteButton = new Button("Remove");
        deleteButton.setOnAction(_ -> {
            answerList.getChildren().remove(answerBox);
        });

        answerBox.getChildren().addAll(answerInput, correctAnswerCheckbox, deleteButton);
        answerList.getChildren().add(answerBox);
    }

    @FXML
    public void handleSaveQuestion() {
        String questionText = questionInput.getText().trim();
        if (questionText.isEmpty()) {
            infoLabel.setText("Please provide a valid question.");
            return;
        }

        answers.clear();
        correctAnswerIndexes.clear();

        for (int i = 0; i < answerList.getChildren().size(); i++) {
            if (answerList.getChildren().get(i) instanceof HBox) {
                HBox answerBox = (HBox) answerList.getChildren().get(i);
                TextField answerInput = (TextField) answerBox.getChildren().get(0);
                CheckBox correctAnswerCheckbox = (CheckBox) answerBox.getChildren().get(1);

                String answer = answerInput.getText().trim();
                if (!answer.isEmpty()) {
                    answers.add(answer);
                    if (correctAnswerCheckbox.isSelected()) {
                        correctAnswerIndexes.add(answers.size() - 1);
                    }
                }
            }
        }

        if (answers.size() < 2) {
            infoLabel.setText("Please provide at least two valid answers.");
            return;
        }

        if (correctAnswerIndexes.isEmpty()) {
            infoLabel.setText("Please select at least one correct answer.");
            return;
        }

        if (!isEdit) {
            App.createTestController.addQuestion(this);
        }
        App.setRoot("CreateTestScene");
        System.out.println("Question saved successfully!");
    }

    @Override
    public AbstractQuestionData getQuestionData() {
        // Convert List<Integer> to int[]
        int[] correctAnswerIndexesArray = correctAnswerIndexes.stream()
                .mapToInt(Integer::intValue)
                .toArray();

        return new MultipleChoicesQuestionData(
                questionInput.getText(), answers, correctAnswerIndexesArray);
    }
}
