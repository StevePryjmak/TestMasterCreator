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

public class CreateMultipleChoicesQuestionWithPictureController extends CreateAbstractQestionController {

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

    private String questionText;
    private List<String> answers;
    private List<Integer> correctAnswers;
    private byte[] imageBytes;

    @FXML
    public void initialize() {
        addAnswerField(); // Adds default fields
        addAnswerField();

        uploadImageButton.setOnAction(_ -> handleImageUpload());
        addAnswerButton.setOnAction(_ -> addAnswerField());
        saveButton.setOnAction(_ -> handleSaveQuestion());

        // Populate form if data exists
        populateForm();
    }

    private void populateForm() {
        if (questionText != null) {
            questionInput.setText(questionText);
        }

        if (answers != null && !answers.isEmpty()) {
            answerList.getChildren().clear();
            for (int i = 0; i < answers.size(); i++) {
                addAnswerField(answers.get(i), correctAnswers.contains(i));
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
        addAnswerField("", false);
    }

    private void addAnswerField(String answerText, boolean isSelected) {
        HBox answerBox = new HBox(10);

        TextField answerInput = new TextField(answerText);
        answerInput.setPromptText("Enter answer option");

        CheckBox correctAnswerCheckBox = new CheckBox("Correct");
        correctAnswerCheckBox.setSelected(isSelected);

        Button deleteButton = new Button("Remove");
        deleteButton.setOnAction(_ -> answerList.getChildren().remove(answerBox));

        answerBox.getChildren().addAll(correctAnswerCheckBox, answerInput, deleteButton);
        answerList.getChildren().add(answerBox);
    }

    private void handleSaveQuestion() {
        questionText = questionInput.getText().trim();
        if (questionText.isEmpty()) {
            System.out.println("Please provide a valid question.");
            return;
        }

        answers = new ArrayList<>();
        correctAnswers = new ArrayList<>();

        for (int i = 0; i < answerList.getChildren().size(); i++) {
            if (answerList.getChildren().get(i) instanceof HBox) {
                HBox answerBox = (HBox) answerList.getChildren().get(i);
                TextField answerInput = (TextField) answerBox.getChildren().get(1);
                CheckBox correctAnswerCheckBox = (CheckBox) answerBox.getChildren().get(0);

                String answer = answerInput.getText().trim();
                if (!answer.isEmpty()) {
                    answers.add(answer);
                    if (correctAnswerCheckBox.isSelected()) {
                        correctAnswers.add(answers.size() - 1);
                    }
                }
            }
        }

        if (answers.size() < 2) {
            System.out.println("Please provide at least two valid answers.");
            return;
        }

        if (correctAnswers.isEmpty()) {
            System.out.println("Please select at least one correct answer.");
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
        return new MultipleChoicesQuestionWithPictureData(
                questionText, answers, correctAnswers.stream().mapToInt(i -> i).toArray(), imageBytes);
    }
}
