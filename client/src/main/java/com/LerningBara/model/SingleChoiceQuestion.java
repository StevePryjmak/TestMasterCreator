package com.LerningBara.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;

import com.LerningBara.app.App;
import com.LerningBara.controller.SingleChoiceQuestionController;

import QuestionData.SingleChoiceQuestionData;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;

public class SingleChoiceQuestion extends AbstractQuestion {

    private SingleChoiceQuestionData questionData;
    private SingleChoiceQuestionController controller;

    public SingleChoiceQuestion(String question, List<String> options, int correctAnswerIndex) throws IOException {
        questionData = new SingleChoiceQuestionData(question, options, correctAnswerIndex);
        initializeScene();
    }

    public SingleChoiceQuestion(SingleChoiceQuestionData questionData) throws IOException {
        this.questionData = questionData;
        initializeScene();
    }

    private void initializeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/SingleChoiceQuestionScene.fxml"));
        Parent root = loader.load();

        controller = loader.getController();
        controller.setQuestionAndAnswers(questionData.getQuestion(), questionData.getOptions(),
                questionData.getCorrectAnswer());

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