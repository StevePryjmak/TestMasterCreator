package QuestionData;

import java.io.Serializable;
import java.util.List;

public class TestData implements Serializable{
    public List<AbstractQuestionData> questions;

    public TestData(List<AbstractQuestionData> questions) {
        this.questions = questions;
    }
}
