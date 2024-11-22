package QuestionData;
import java.io.Serializable;

public class MyClass implements Serializable {
    private static final long serialVersionUID = 1L; // Add serialVersionUID
    private String name;
    private int value;

    public MyClass(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "MyClass{name='" + name + "', value=" + value + "}";
    }
}
