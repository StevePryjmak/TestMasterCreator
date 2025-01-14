package com.LerningBara.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.LerningBara.app.App;
import com.LerningBara.controller.MultipleChoicesQuestionController;

import QuestionData.MultipleChoicesQuestionData;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MultipleChoicesQuestion extends AbstractQuestion {

    private MultipleChoicesQuestionData questionData;
    private MultipleChoicesQuestionController controller;


    public MultipleChoicesQuestion(String question, List<String> options, int[] correctAnswerIndexes) throws IOException {
        questionData = new MultipleChoicesQuestionData(question, options, correctAnswerIndexes);
        initializeScene();
    }

    public MultipleChoicesQuestion(MultipleChoicesQuestionData questionData) throws IOException {
        this.questionData = questionData;
        initializeScene();
    }

    private void initializeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/MultipleChoicesQuestionScene.fxml"));
        Parent root = loader.load();

        controller = loader.getController();
        controller.setQuestionAndAnswers(questionData.getQuestion(), questionData.getOptions(),
                questionData.getCorrectAnswers());

        scene = new Scene(root);
    }

    @Override
    public boolean checkAnswer(String answer) {
        // Implement answer checking logic if necessary
        return false;
    }

    @Override
    public VBox getDetailsBox(int index) {
        VBox questionBox = new VBox(10);
        questionBox.getStyleClass().add("question-box");

        Label questionLabel = new Label("Question #" + (index + 1) + ": " + questionData.getQuestion());
        questionLabel.getStyleClass().add("question-label");
        questionBox.getChildren().add(questionLabel);

        List<String> options = questionData.getOptions();
        int[] correctAnswerIndexes = questionData.getCorrectAnswerIndexes();

        HBox answerHBox = new HBox(10);
        answerHBox.setAlignment(Pos.CENTER_LEFT);

        // Create a Set for quick lookup of correct answer indexes
        Set<Integer> correctAnswerSet = new HashSet<>();
        for (int correctIndex : correctAnswerIndexes) {
            correctAnswerSet.add(correctIndex);
        }

        for (int i = 0; i < options.size(); i++) {
            CheckBox answerCheckBox = new CheckBox(options.get(i));
            answerCheckBox.setUserData(i);

            // Check if this option is correct based on the correctAnswerIndexes array
            if (correctAnswerSet.contains(i)) {
                answerCheckBox.setSelected(true);
            }

            answerHBox.getChildren().add(answerCheckBox);

            // Add the current row of checkboxes to the question box
            if (i % 2 == 1 || i == options.size() - 1) {
                questionBox.getChildren().add(answerHBox);
                answerHBox = new HBox(10);
                answerHBox.setAlignment(Pos.CENTER_LEFT);
            }
        }

        // Add a delete button image for removing the question (optional)
        Image deleteImage = new Image(getClass().getResourceAsStream("/images/delete.png"));
        ImageView deleteImageView = new ImageView(deleteImage);
        deleteImageView.setFitHeight(20);
        deleteImageView.setFitWidth(20);
        deleteImageView.setOnMouseClicked(event -> {
            App.createTestController.handleDeleteQuestion(index);
            event.consume();
        });
        questionBox.getChildren().add(deleteImageView);

        return questionBox;
    }


}
