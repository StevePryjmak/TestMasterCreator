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
    private RadioButton button1;
    @FXML
    private RadioButton button2;
    @FXML
    private RadioButton button3;
    @FXML
    private RadioButton button4;
    @FXML
    private RadioButton button5;
    @FXML
    private RadioButton button6;
    @FXML
    private RadioButton button7;
    @FXML
    private RadioButton button8;
    @FXML
    private Button checkButton;
    @FXML
    private ImageView imageView;
    @FXML
    private Label infoLabel;

    private ToggleGroup toggleGroup = new ToggleGroup(); // To group the radio buttons
    private String correctAnswer;
    private RadioButton[] buttons;

    @FXML
    public void initialize() {
        infoLabel.setText("");

        buttons = new RadioButton[] { button1, button2, button3, button4,
                button5, button6, button7, button8 };

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

        toggleGroup.selectToggle(null); // Deselect any previously selected button
    }

    @FXML
    private void handleCheckButton() {
        RadioButton selectedButton = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedButton == null) {
            infoLabel.setText("Please select an answer.");
        } else {
            String selectedAnswer = selectedButton.getText();
            checkAnswer(selectedAnswer);
            App.getInstance().nextQuestion(); // Proceed to next question
        }
    }

    // Check if the selected answer is correct
    private void checkAnswer(String selectedAnswer) {
        if (selectedAnswer.equals(correctAnswer)) {
            System.out.println("Correct!");
            App.getInstance().testInfoData.result += 1; // Increment result
        } else {
            System.out.println("Wrong answer.");
        }
    }
}
