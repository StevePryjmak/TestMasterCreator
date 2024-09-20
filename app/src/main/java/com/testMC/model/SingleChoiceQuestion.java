package com.testMC.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;

import com.testMC.app.App;
import com.testMC.controller.SingleChoiceQuestionController;

public class SingleChoiceQuestion extends AbstractQuestion {

    private String question;
    private List<String> options;
    private int correctAnswerIndex;

    private SingleChoiceQuestionController controller;


    public SingleChoiceQuestion(String question, List<String> options, int correctAnswerIndex) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/SingleChoiceQuestionScene.fxml"));
        Parent root = loader.load();

        controller = loader.getController();
        controller.setQuestionAndAnswers(question, options, options.get(correctAnswerIndex));

        scene = new Scene(root);

        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    @Override
    public boolean checkAnswer(String answer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkAnswer'");
    }
}