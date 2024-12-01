package QuestionData;

import QuestionData.AbstractQuestionData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MultipleChoicesQuestionData extends AbstractQuestionData implements Serializable {

    private String question;
    private List<String> options;
    private int[] correctAnswerIndexes;

    public MultipleChoicesQuestionData(String question, List<String> options, int[] correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndexes = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public int[] getCorrectAnswerIndexes() {
        return correctAnswerIndexes;
    }

    public String[] getCorrectAnswers() {
        List<String> correctAnwsers = new ArrayList<>();
        for (int index : correctAnswerIndexes) {
            correctAnwsers.add(options.get(index));
        }
        return correctAnwsers.toArray(new String[0]);
    }

}