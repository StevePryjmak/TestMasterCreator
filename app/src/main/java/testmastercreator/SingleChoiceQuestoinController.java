package testmastercreator;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class SingleChoiceQuestoinController {

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

    private String correctAnswer;

    public void setQuestionAndAnswers(String question, List<String> options) {
        questionLabel.setText(question);
        
        // Initialize the RadioButtons with options if they exist
        RadioButton[] buttons = {answerButton1, answerButton2, answerButton3, answerButton4, 
                                 answerButton5, answerButton6, answerButton7, answerButton8};
        
        for (int i = 0; i < buttons.length; i++) {
            if (i < options.size()) {
                buttons[i].setText(options.get(i));
                buttons[i].setVisible(true);
            } else {
                buttons[i].setVisible(false);
            }
        }
    }
    @FXML
    private void handleAnswer1() {
        checkAnswer(answerButton1.getText());
    }

    @FXML
    private void handleAnswer2() {
        checkAnswer(answerButton2.getText());
    }

    @FXML
    private void handleAnswer3() {
        checkAnswer(answerButton3.getText());
    }

    @FXML
    private void handleAnswer4() {
        checkAnswer(answerButton4.getText());
    }

    private void checkAnswer(String selectedAnswer) {
        if (selectedAnswer.equals(correctAnswer)) {
            System.out.println("Correct!");
        } else {
            System.out.println("Wrong answer.");
        }
    }
}
