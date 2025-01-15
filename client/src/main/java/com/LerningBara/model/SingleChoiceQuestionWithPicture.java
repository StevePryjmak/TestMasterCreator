package com.LerningBara.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.LerningBara.app.App;
import com.LerningBara.controller.SingleChoiceQuestionWithPictureController;
import QuestionData.SingleChoiceQuestionWithPictureData;
import javafx.fxml.FXMLLoader;
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
        // Handle answer checking logic here
        return answer.equals(questionData.getCorrectAnswer());
    }

    @Override
    public VBox getDetailsBox(int index) {
        VBox questionBox = new VBox(10);
        questionBox.getStyleClass().add("question-box");

        Label questionLabel = new Label("Question #" + (index + 1) + ": " + questionData.getQuestion());
        questionLabel.getStyleClass().add("question-label");

        ImageView imageView = new ImageView(byteArrayToImage(questionData.getImage()));
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        HBox answersBox = new HBox(10);
        for (String option : questionData.getOptions()) {
            RadioButton answerButton = new RadioButton(option);
            answerButton.setDisable(true);
            answersBox.getChildren().add(answerButton);
        }

        questionBox.getChildren().addAll(questionLabel, imageView, answersBox);
        return questionBox;
    }
}
