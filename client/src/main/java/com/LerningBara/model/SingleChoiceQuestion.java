package com.LerningBara.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;

import com.LerningBara.app.App;
import com.LerningBara.controller.SingleChoiceQuestionController;

import QuestionData.SingleChoiceQuestionData;

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
}