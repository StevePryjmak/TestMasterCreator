package TestData;

import java.io.Serializable;

public class TestInfoData implements Serializable {
    public int testID;
    public String name;
    public String description;
    public String author;
    public String date;
    public String field;
    public int questionCount;

    public TestInfoData(int testID, String name, String description, String author, String date, String field,
            int questionCount) {
        this.testID = testID;
        this.name = name;
        this.description = description;
        this.author = author;
        this.date = date;
        this.field = field;
        this.questionCount = questionCount;
    }
}