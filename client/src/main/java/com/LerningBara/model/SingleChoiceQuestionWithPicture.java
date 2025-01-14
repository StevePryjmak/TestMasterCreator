package com.LerningBara.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.LerningBara.app.App;
import com.LerningBara.controller.SingleChoiceQuestionWithPictureController;

import QuestionData.SingleChoiceQuestionWithPictureData;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SingleChoiceQuestionWithPicture extends AbstractQuestion {

    private SingleChoiceQuestionWithPictureData questionData;
    private SingleChoiceQuestionWithPictureController controller;

    public SingleChoiceQuestionWithPicture(String question, List<String> options, int correctAnswerIndex, byte[] image)
            throws IOException {
        questionData = new SingleChoiceQuestionWithPictureData(question, options, correctAnswerIndex, image);
        initializeScene();
    }

    public SingleChoiceQuestionWithPicture(SingleChoiceQuestionWithPictureData questionData)
            throws IOException {
        this.questionData = questionData;
        initializeScene();
    }

    private Image byteArrayToImage(byte[] byteArray) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        return new Image(inputStream);
    }

    private void initializeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/SingleChoiceQuestionWithPictureScene.fxml"));
        Parent root = loader.load();

        controller = loader.getController();
        controller.setQuestionAndAnswers(questionData.getQuestion(), questionData.getOptions(),
                questionData.getCorrectAnswer(), byteArrayToImage(questionData.getImage()));

        scene = new Scene(root);
    }

    @Override
    public boolean checkAnswer(String answer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkAnswer'");
    }

    @Override
    public VBox getDetailsBox(int index) {
        VBox questionBox = new VBox(10);
        questionBox.getStyleClass().add("question-box");
        questionBox.setOnMouseClicked(_ -> {
            App.createTestController.handleQuestionEdit(index, false);
        });

        ImageView imageView = new ImageView();
        imageView.setImage(byteArrayToImage(questionData.getImage()));
        questionBox.getChildren().add(imageView);

        Label questionLabel = new Label("Question #" + (index + 1) + ": " + questionData.getQuestion());
        questionLabel.getStyleClass().add("question-label");
        questionBox.getChildren().add(questionLabel);

        List<String> options = questionData.getOptions();

        HBox answerHBox = new HBox(10);
        answerHBox.getStyleClass().add("hbox");
        answerHBox.setAlignment(Pos.CENTER_LEFT);

        for (int i = 0; i < options.size(); i++) {
            RadioButton answerButton = new RadioButton(options.get(i));
            answerButton.setUserData(i);

            if (i == questionData.getCorrectAnswerIndex()) {
                answerButton.getStyleClass().add("correct-answer");
                answerButton.setSelected(true);
            } else {
                answerButton.getStyleClass().add("incorrect-answer");
            }

            answerButton.setDisable(true);
            answerHBox.getChildren().add(answerButton);

            if (i % 2 == 1 || i == options.size() - 1) {
                questionBox.getChildren().add(answerHBox);
                answerHBox = new HBox(10);
                answerHBox.getStyleClass().add("hbox");
                answerHBox.setAlignment(Pos.CENTER_LEFT);
            }
        }

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
