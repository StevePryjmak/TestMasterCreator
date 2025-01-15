package com.LerningBara.controller.CreateTests;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.LerningBara.app.App;
import QuestionData.*;

public class CreateSingleChoiceQuestionWithPictureController extends CreateAbstractQestionController {

    @FXML
    private TextField questionInput;

    @FXML
    private VBox answerList;

    @FXML
    private Button addAnswerButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button uploadImageButton;

    @FXML
    private ImageView imageView;

    private ToggleGroup toggleGroup;

    private String questionText;
    private List<String> answers;
    private int correctAnswerIndex;
    private byte[] imageBytes;

    @FXML
    public void initialize() {
        toggleGroup = new ToggleGroup();
        addAnswerField(); // Adds default fields
        addAnswerField();

        uploadImageButton.setOnAction(event -> handleImageUpload());
        addAnswerButton.setOnAction(event -> addAnswerField());
        saveButton.setOnAction(event -> handleSaveQuestion());

        // Populate form if data exists
        populateForm();
    }

    /**
     * Populates the form with existing values from the class variables.
     */
    private void populateForm() {
        if (questionText != null) {
            questionInput.setText(questionText);
        }

        if (answers != null && !answers.isEmpty()) {
            answerList.getChildren().clear();
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

        if (imageBytes != null && imageBytes.length > 0) {
            Image image = new Image(new ByteArrayInputStream(imageBytes));
            imageView.setImage(image);
        }
    }


    private void handleImageUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        File file = fileChooser.showOpenDialog(App.stage);

        if (file != null) {
            try (FileInputStream fis = new FileInputStream(file)) {
                imageBytes = fis.readAllBytes();
                Image image = new Image(new FileInputStream(file));
                imageView.setImage(image);
            } catch (IOException e) {
                System.out.println("Error loading image: " + e.getMessage());
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
    private void handleSaveQuestion() {
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

        if (imageBytes == null || imageBytes.length == 0) {
            System.out.println("Please upload an image.");
            return;
        }
        
        if (!isEdit) {
            App.createTestController.addQuestion(this);
        }
        App.setRoot("CreateTestScene");
        System.out.println("Question with picture saved successfully!");
    }

    @Override
    public AbstractQuestionData getQuestionData() {
        // Assuming you have a method to collect the question data from the UI
        SingleChoiceQuestionWithPictureData questionData = new SingleChoiceQuestionWithPictureData(
            questionText, answers, correctAnswerIndex, imageBytes);

        return questionData;
    }
}
