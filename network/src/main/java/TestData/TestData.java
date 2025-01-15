package TestData;

import java.io.Serializable;
import java.util.List;
import QuestionData.AbstractQuestionData;

public class TestData implements Serializable {
    private String name;
    private String filed;
    private String description;
    private boolean shuffled = false;
    public List<AbstractQuestionData> questions;

    public TestData(List<AbstractQuestionData> questions) {
        this.questions = questions;
    }

    public void setShuffled(boolean bool) {
        shuffled = bool;
    }

    public boolean getShuffled() {
        return shuffled;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public String getFiled() {
        return filed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}