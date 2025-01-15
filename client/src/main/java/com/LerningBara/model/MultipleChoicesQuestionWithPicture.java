package com.LerningBara.model;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.LerningBara.app.App;
import com.LerningBara.controller.MultipleChoicesQuestionWithPictureController;

import QuestionData.MultipleChoicesQuestionWithPictureData;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;

public class MultipleChoicesQuestionWithPicture extends AbstractQuestion {

    private MultipleChoicesQuestionWithPictureData questionData;
    private MultipleChoicesQuestionWithPictureController controller;

    public MultipleChoicesQuestionWithPicture(String question, List<String> options, int[] correctAnswerIndexes,
            byte[] image)
            throws IOException {
        questionData = new MultipleChoicesQuestionWithPictureData(question, options, correctAnswerIndexes, image);
        initializeScene();
    }

    public MultipleChoicesQuestionWithPicture(MultipleChoicesQuestionWithPictureData questionData)
            throws IOException {
        this.questionData = questionData;
        initializeScene();
    }

    private Image byteArrayToImage(byte[] byteArray) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        return new Image(inputStream);
    }

    private void initializeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/MultipleChoicesQuestionWithPictureScene.fxml"));
        Parent root = loader.load();

        controller = loader.getController();
        controller.setQuestionAndAnswers(questionData.getQuestion(), questionData.getOptions(),
                questionData.getCorrectAnswers(), byteArrayToImage(questionData.getImage()));

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
    
        questionBox.setOnMouseClicked(event -> {
            App.createTestController.handleQuestionEdit(index, false);
        });
    
        // Question label
        Label questionLabel = new Label("Question #" + (index + 1) + ": " + questionData.getQuestion());
        questionLabel.getStyleClass().add("question-label");
        questionBox.getChildren().add(questionLabel);
    
        // Question image
        if (questionData.getImage() != null) {
            ImageView imageView = new ImageView(byteArrayToImage(questionData.getImage()));
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
            questionBox.getChildren().add(imageView);
        }
    
        // Answer options
        List<String> options = questionData.getOptions();
        VBox answersBox = new VBox(5);
        answersBox.getStyleClass().add("answers-box");

        Set<Integer> correctAnswerSet = new HashSet<>();
        for (int correctIndex : questionData.getCorrectAnswerIndexes()) {
            correctAnswerSet.add(correctIndex);
        }
    
        for (int i = 0; i < options.size(); i++) {
            CheckBox answerCheckBox = new CheckBox(options.get(i));
            answerCheckBox.setUserData(i);
            answerCheckBox.setDisable(true); // Prevent modification during viewing
    
            // Highlight the correct answers
            if (correctAnswerSet.contains(i)) {
                answerCheckBox.setSelected(true);
                answerCheckBox.getStyleClass().add("correct-answer");
            } else {
                answerCheckBox.getStyleClass().add("incorrect-answer");
            }
    
            answersBox.getChildren().add(answerCheckBox);
        }
    
        questionBox.getChildren().add(answersBox);
    
        // Delete button
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
