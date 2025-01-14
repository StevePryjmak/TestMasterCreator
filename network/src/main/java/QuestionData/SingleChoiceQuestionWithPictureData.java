package QuestionData;

import java.util.List;

public class SingleChoiceQuestionWithPictureData extends AbstractQuestionData {

    private String question;
    private List<String> options;
    private int correctAnswerIndex;
    private byte[] image;

    public SingleChoiceQuestionWithPictureData(String question, List<String> options, int correctAnswerIndex,
            byte[] image) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
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

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public String getCorrectAnswer() {
        return options.get(correctAnswerIndex);
    }

}
