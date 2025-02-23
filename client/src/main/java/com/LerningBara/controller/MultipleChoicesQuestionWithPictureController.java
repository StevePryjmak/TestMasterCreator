package com.LerningBara.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.LerningBara.app.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class MultipleChoicesQuestionWithPictureController {

    @FXML
    private Label question;
    @FXML
    private CheckBox button1;
    @FXML
    private CheckBox button2;
    @FXML
    private CheckBox button3;
    @FXML
    private CheckBox button4;
    @FXML
    private CheckBox button5;
    @FXML
    private CheckBox button6;
    @FXML
    private CheckBox button7;
    @FXML
    private CheckBox button8;
    @FXML
    private Button next;
    @FXML
    private ImageView picture;
    @FXML
    private Label infoLabel;

    private String[] correctAnswers;
    private CheckBox[] buttons;

    @FXML
    public void initialize() {
        infoLabel.setText("");

        buttons = new CheckBox[] { button1, button2, button3, button4,
                button5, button6, button7, button8 };

    }

    // Method to set question and answers dynamically
    public void setQuestionAndAnswers(String given_question, List<String> options, String[] correctAnswer,
            Image image) {
        picture.setImage(image);
        question.setText(given_question);
        correctAnswers = correctAnswer;

        // Set the text for the visible buttons and hide the rest
        for (int i = 0; i < buttons.length; i++) {
            if (i < options.size()) {
                buttons[i].setText(options.get(i));
                buttons[i].setVisible(true);
                buttons[i].setSelected(false);
            } else {
                buttons[i].setVisible(false);
            }
        }

    }

    // Check if answer is selected and then if the anwser is correct
    @FXML
    private void handleCheckButton() {
        List<String> selectedAnswers = new ArrayList<>();
        for (CheckBox button : buttons) {
            if (button.isVisible() && button.isSelected()) {
                selectedAnswers.add(button.getText());
            }
        }

        if (selectedAnswers.isEmpty()) {
            infoLabel.setText("Please select an answer.");
        } else {
            checkAnswer(selectedAnswers.toArray(new String[0]));
            App.getInstance().nextQuestion();
        }
    }

    // Check if the selected answer is correct
    private void checkAnswer(String[] selectedAnswers) {
        Arrays.sort(correctAnswers);
        Arrays.sort(selectedAnswers);

        if (Arrays.equals(correctAnswers, selectedAnswers)) {
            System.out.println("Correct Answer!");
            App.getInstance().testInfoData.result += 1;
        } else {
            System.out.println("Incorrect Answer!");
        }

    }
}
