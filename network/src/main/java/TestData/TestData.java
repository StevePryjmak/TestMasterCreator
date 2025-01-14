package TestData;

import java.io.Serializable;
import java.util.List;
import QuestionData.AbstractQuestionData;

public class TestData implements Serializable {
    private String name;
    public List<AbstractQuestionData> questions;

    public TestData(List<AbstractQuestionData> questions) {
        this.questions = questions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
