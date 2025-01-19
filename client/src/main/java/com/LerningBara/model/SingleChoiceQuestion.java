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

/**
 * Represents a single-choice question in a test.
 * Handles the initialization and display of the question and its options.
 */
public class SingleChoiceQuestion extends AbstractQuestion {

    /**
     * Data object representing the question, its options, and the correct answer.
     */
    private SingleChoiceQuestionData questionData;

    /**
     * Controller for managing the question's UI logic.
     */
    private SingleChoiceQuestionController controller;

    /**
     * Constructs a SingleChoiceQuestion with the given question text, options, and correct answer index.
     * 
     * @param question The question text.
     * @param options The list of answer options.
     * @param correctAnswerIndex The index of the correct answer in the options list.
     * @throws IOException If the FXML file for the scene cannot be loaded.
     */
    public SingleChoiceQuestion(String question, List<String> options, int correctAnswerIndex) throws IOException {
        questionData = new SingleChoiceQuestionData(question, options, correctAnswerIndex);
        initializeScene();
    }

    /**
     * Constructs a SingleChoiceQuestion with pre-defined question data.
     * 
     * @param questionData A SingleChoiceQuestionData object containing question details.
     * @see SingleChoiceQuestionData
     * @throws IOException If the FXML file for the scene cannot be loaded.
     */
    public SingleChoiceQuestion(SingleChoiceQuestionData questionData) throws IOException {
        this.questionData = questionData;
        initializeScene();
    }

    /**
     * Initializes the scene for the single-choice question.
     * Loads the FXML layout and sets up the controller with the question data.
     * 
     * @throws IOException If the FXML file cannot be loaded.
     */
    private void initializeScene() throws IOException {
        // Load the FXML layout for the single-choice question scene
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/SingleChoiceQuestionScene.fxml"));
        Parent root = loader.load();
    
        // Get the controller associated with the loaded FXML and pass question data
        controller = loader.getController();
        controller.setQuestionAndAnswers(questionData.getQuestion(), questionData.getOptions(),
                questionData.getCorrectAnswer());
    
        // Create a new scene using the loaded layout
        scene = new Scene(root);
    }

    /**
     * Checks if the provided answer is correct.
     * 
     * @param answer The user's answer to check.
     * @return true if the answer is correct; false otherwise.
     */
    @Override
    public boolean checkAnswer(String answer) {
        // TODO: Implement this method to verify the correctness of the answer.
        throw new UnsupportedOperationException("Unimplemented method 'checkAnswer'");
    }

    /**
     * Generates a detailed VBox representation of the question, including its text, options, and a delete button.
     * 
     * @param index The index of the question in the test neded only for string.
     * @return A VBox containing the question details.
     */
    @Override
    public VBox getDetailsBox(int index) {
        VBox questionBox = new VBox(10); // VBox with spacing of 10
        questionBox.getStyleClass().add("question-box"); // Add CSS class for styling
    
        // Handle click to allow editing this question in the test
        questionBox.setOnMouseClicked(_ -> {
            App.createTestController.handleQuestionEdit(index, false);
        });
    
        // Create a label for the question and add it to the box
        Label questionLabel = new Label("Question #" + (index + 1) + ": " + questionData.getQuestion());
        questionLabel.getStyleClass().add("question-label");
        questionBox.getChildren().add(questionLabel);
    
        List<String> options = questionData.getOptions(); // Get options for the question
    
        HBox answerHBox = new HBox(10); // Create a horizontal box for answers
        answerHBox.getStyleClass().add("hbox");
        answerHBox.setAlignment(Pos.CENTER_LEFT);
    
        for (int i = 0; i < options.size(); i++) {
            RadioButton answerButton = new RadioButton(options.get(i)); // Create a radio button for each option
            answerButton.setUserData(i); // Store the index as user data
    
            // Style the button based on whether it's the correct answer
            if (i == questionData.getCorrectAnswerIndex()) {
                answerButton.getStyleClass().add("correct-answer");
                answerButton.setSelected(true);
            } else {
                answerButton.getStyleClass().add("incorrect-answer");
            }
    
            answerButton.setDisable(true); // Disable the button as it's for display only
            answerHBox.getChildren().add(answerButton);
    
            // Add the current HBox to the VBox and create a new row if necessary
            if (i % 2 == 1 || i == options.size() - 1) {
                questionBox.getChildren().add(answerHBox);
                answerHBox = new HBox(10);
                answerHBox.getStyleClass().add("hbox");
                answerHBox.setAlignment(Pos.CENTER_LEFT);
            }
        }
    
        // Add a delete button to the question box
        Image deleteImage = new Image(getClass().getResourceAsStream("/images/delete.png"));
        ImageView deleteImageView = new ImageView(deleteImage);
        deleteImageView.setFitHeight(20);
        deleteImageView.setFitWidth(20);
    
        // Handle delete action when the delete button is clicked
        deleteImageView.setOnMouseClicked(event -> {
            App.createTestController.handleDeleteQuestion(index);
            event.consume(); // Prevent further event propagation
        });
        questionBox.getChildren().add(deleteImageView);
    
        return questionBox;
    }
}
