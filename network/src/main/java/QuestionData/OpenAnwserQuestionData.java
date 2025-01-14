package QuestionData;

public class OpenAnwserQuestionData extends AbstractQuestionData {

    private String question;
    private String correctAnswer;

    public OpenAnwserQuestionData(String question, String givenCorrectAnwser) {
        this.question = question;
        this.correctAnswer = givenCorrectAnwser;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

}