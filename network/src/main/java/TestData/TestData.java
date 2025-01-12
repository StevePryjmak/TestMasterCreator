package TestData;

import java.io.Serializable;
import java.util.List;
import QuestionData.AbstractQuestionData;

public class TestData implements Serializable {
    public List<AbstractQuestionData> questions;
    public int testID;

    public TestData(List<AbstractQuestionData> questions, int testID) {
        this.questions = questions;
        this.testID = testID;
    }
}
