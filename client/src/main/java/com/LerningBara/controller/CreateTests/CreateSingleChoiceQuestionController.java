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


public class CreateSingleChoiceQuestionController extends CreateAbstractQestionController {

    @FXML
    private TextField questionInput;

    @FXML
    private VBox answerList;

    @FXML
    private Button addAnswerButton;

    @FXML
    private Button saveButton;

    private ToggleGroup toggleGroup;

    private String questionText;
    private List<String>answers;
    private int correctAnswerIndex;

    @FXML
    public void initialize() {
        toggleGroup = new ToggleGroup();
        addAnswerField();
        addAnswerField();
        initializeFields();

        addAnswerButton.setOnAction(event -> addAnswerField());
        saveButton.setOnAction(event -> handleSaveQuestion());
    }

    private void initializeFields() {
        if (questionText != null && !questionText.isEmpty()) {
            questionInput.setText(questionText);
        }
        else isEdit = false;
    
        if (answers != null && !answers.isEmpty()) {
            answerList.getChildren().clear();
            toggleGroup = new ToggleGroup();
    
            for (int i = 0; i < answers.size(); i++) {
                HBox answerBox = new HBox(10);
    
                TextField answerInput = new TextField(answers.get(i));
                answerInput.setPromptText("Enter answer option");
    
                RadioButton correctAnswerButton = new RadioButton();
                correctAnswerButton.setToggleGroup(toggleGroup);
                if (i == correctAnswerIndex) {
                    correctAnswerButton.setSelected(true);
                }
    
                Button deleteButton = new Button("Remove");
                deleteButton.setOnAction(event -> {
                    answerList.getChildren().remove(answerBox);
                    toggleGroup.getToggles().remove(correctAnswerButton);
                });
    
                answerBox.getChildren().addAll(correctAnswerButton, answerInput, deleteButton);
                answerList.getChildren().add(answerBox);
            }
        }
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
        questionText = questionInput.getText().trim();
        if (questionText.isEmpty()) {
            System.out.println("Please provide a valid question.");
            return;
        }

        answers = new ArrayList<>();
        correctAnswerIndex = -1;

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


        if(!isEdit) {
            App.createTestController.addQuestion(this);
        }
        App.setRoot("CreateTestScene");
        System.out.println("Question saved successfully!");
    }

    @Override
    public AbstractQuestionData getQuestionData() {
        return  (AbstractQuestionData) new SingleChoiceQuestionData(
            questionText, answers, correctAnswerIndex
        );
    }
}
