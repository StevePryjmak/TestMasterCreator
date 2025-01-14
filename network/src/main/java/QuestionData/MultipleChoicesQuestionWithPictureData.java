package QuestionData;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoicesQuestionWithPictureData extends AbstractQuestionData {
    private String question;
    private List<String> options;
    private int[] correctAnswerIndexes;
    private byte[] image;

    public MultipleChoicesQuestionWithPictureData(String question, List<String> options, int[] correctAnswerIndex,
            byte[] image) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndexes = correctAnswerIndex;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    @Override
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
