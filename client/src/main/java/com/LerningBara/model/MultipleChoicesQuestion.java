package com.LerningBara.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;

import com.LerningBara.app.App;
import com.LerningBara.controller.MultipleChoicesQuestionController;

import QuestionData.MultipleChoicesQuestionData;

public class MultipleChoicesQuestion extends AbstractQuestion {

    private MultipleChoicesQuestionData questionData;
    private MultipleChoicesQuestionController controller;

    public MultipleChoicesQuestion(String question, List<String> options, int[] correctAnswerIndexes)
            throws IOException {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkAnswer'");
    }

    
}