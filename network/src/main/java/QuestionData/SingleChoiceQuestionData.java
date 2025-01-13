package QuestionData;

import QuestionData.AbstractQuestionData;

import java.io.Serializable;
import java.util.List;


public class SingleChoiceQuestionData extends AbstractQuestionData implements Serializable{

    private String question;
    private List<String> options;
    private int correctAnswerIndex;

    public SingleChoiceQuestionData(String question, List<String> options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public String getCorrectAnswer() {
        return options.get(correctAnswerIndex);
    }

}