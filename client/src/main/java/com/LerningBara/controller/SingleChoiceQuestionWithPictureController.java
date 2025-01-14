package com.LerningBara.controller;

import java.util.List;

import com.LerningBara.app.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class SingleChoiceQuestionWithPictureController {

    @FXML
    private Label questionLabel;
    @FXML
    private RadioButton answerButton1;
    @FXML
    private RadioButton answerButton2;
    @FXML
    private RadioButton answerButton3;
    @FXML
    private RadioButton answerButton4;
    @FXML
    private RadioButton answerButton5;
    @FXML
    private RadioButton answerButton6;
    @FXML
    private RadioButton answerButton7;
    @FXML
    private RadioButton answerButton8;
    @FXML
    private Button checkButton;
    @FXML
    private ImageView imageView;

    private ToggleGroup toggleGroup = new ToggleGroup(); // To group the radio buttons
    private String correctAnswer;
    private RadioButton[] buttons;

    @FXML
    public void initialize() {

        buttons = new RadioButton[] { answerButton1, answerButton2, answerButton3, answerButton4,
                answerButton5, answerButton6, answerButton7, answerButton8 };

        for (RadioButton button : buttons) {
            button.setToggleGroup(toggleGroup);
        }
    }

    // Method to set question and answers dynamically
    public void setQuestionAndAnswers(String question, List<String> options, String correctAnswer, Image image) {
        this.imageView.setImage(image);
        questionLabel.setText(question);
        this.correctAnswer = correctAnswer;

        // Set the text for the visible buttons and hide the rest
        for (int i = 0; i < buttons.length; i++) {
            if (i < options.size()) {
                buttons[i].setText(options.get(i));
                buttons[i].setVisible(true);
            } else {
                buttons[i].setVisible(false);
            }
        }

        toggleGroup.selectToggle(null);
    }

    @FXML
    private void handleCheckButton() {

        RadioButton selectedButton = (RadioButton) toggleGroup.getSelectedToggle();

        if (selectedButton == null) {
            System.out.println("Please select an answer.");
        } else {

            String selectedAnswer = selectedButton.getText();
            checkAnswer(selectedAnswer);
            App.getInstance().nextQuestion();
        }
    }

    // Check if the selected answer is correct
    private void checkAnswer(String selectedAnswer) {
        if (selectedAnswer.equals(correctAnswer)) {
            System.out.println("Correct!");
            App.getInstance().testInfoData.result += 1;
        } else {
            System.out.println("Wrong answer.");
        }
    }
}
