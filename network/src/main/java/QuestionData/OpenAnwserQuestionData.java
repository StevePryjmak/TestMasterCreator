package QuestionData;

import QuestionData.AbstractQuestionData;

import java.io.Serializable;
import java.util.List;

public class OpenAnwserQuestionData extends AbstractQuestionData implements Serializable {

    private String question;
    private String correctAnswer;

    public OpenAnwserQuestionData(String question, String givenCorrectAnwser) {
        this.question = question;
        this.correctAnswer = givenCorrectAnwser;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

}