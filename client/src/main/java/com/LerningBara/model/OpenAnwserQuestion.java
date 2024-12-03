package com.LerningBara.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;

import com.LerningBara.app.App;
import com.LerningBara.controller.OpenAnwserQuestionController;

import QuestionData.OpenAnwserQuestionData;

public class OpenAnwserQuestion extends AbstractQuestion {

    private OpenAnwserQuestionData questionData;
    private OpenAnwserQuestionController controller;

    public OpenAnwserQuestion(String question, String correctAnswer) throws IOException {
        questionData = new OpenAnwserQuestionData(question, correctAnswer);
        initializeScene();
    }

    public OpenAnwserQuestion(OpenAnwserQuestionData questionData) throws IOException {
        this.questionData = questionData;
        initializeScene();
    }

    private void initializeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/OpenAnwserQuestionScene.fxml"));
        Parent root = loader.load();

        controller = loader.getController();
        controller.setQuestionAndAnswers(questionData.getQuestion(), questionData.getCorrectAnswer());

        scene = new Scene(root);
    }

    @Override
    public boolean checkAnswer(String answer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkAnswer'");
    }
}