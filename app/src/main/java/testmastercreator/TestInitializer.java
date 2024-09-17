package testmastercreator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestInitializer {

    public static Test createExampleTest() {
        // Create a new test instance
        Test test = new Test();

        try {

            // Question 1
            String question1 = "What is the capital of France?";
            List<String> options1 = Arrays.asList("Berlin", "Paris", "Rome", "Madrid");
            int correctAnswerIndex1 = 1; // "Paris"
            SingleChoiceQuestion q1 = new SingleChoiceQuestion(question1, options1, correctAnswerIndex1);
            test.addQuestion(q1);

            // Question 2
            String question2 = "Which of the following is a prime number?";
            List<String> options2 = Arrays.asList("12", "5", "15", "20");
            int correctAnswerIndex2 = 1; // "5"
            SingleChoiceQuestion q2 = new SingleChoiceQuestion(question2, options2, correctAnswerIndex2);
            test.addQuestion(q2);

            // Question 3
            String question3 = "What is the largest planet in our solar system?";
            List<String> options3 = Arrays.asList("Earth", "Mars", "Jupiter", "Saturn");
            int correctAnswerIndex3 = 2; // "Jupiter"
            SingleChoiceQuestion q3 = new SingleChoiceQuestion(question3, options3, correctAnswerIndex3);
            test.addQuestion(q3);

        } catch (IOException e) {
            e.printStackTrace(); 
        }

        return test;
    }
}
