package server;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import QuestionData.AbstractQuestionData;
import QuestionData.SingleChoiceQuestionData;
import QuestionData.MultipleChoicesQuestionData;
import TestData.TestData;

public class ExampleTest {

    public static TestData exampleTestData() {

        List<AbstractQuestionData> questions = new ArrayList<>();

        // Question 1
        String question1 = "What is the capital of France?";
        List<String> options1 = Arrays.asList("Berlin", "Paris", "Rome", "Madrid");
        int correctAnswerIndex1 = 1; // "Paris"
        SingleChoiceQuestionData q1 = new SingleChoiceQuestionData(question1, options1, correctAnswerIndex1);
        questions.add(q1);

        // Question 2
        String question2 = "Which of the following is a prime number?";
        List<String> options2 = Arrays.asList("12", "5", "15", "20");
        int correctAnswerIndex2 = 1; // "5"
        SingleChoiceQuestionData q2 = new SingleChoiceQuestionData(question2, options2, correctAnswerIndex2);
        questions.add(q2);

        // Question 3
        String question3 = "Which are NOT the capital of France?";
        List<String> options3 = Arrays.asList("Berlin", "Paris", "Rome", "Madrid");
        int[] correctAnswerIndex3 = { 0, 2, 3 };
        MultipleChoicesQuestionData q3 = new MultipleChoicesQuestionData(question3, options3, correctAnswerIndex3);
        questions.add(q3);

        // Question 4
        String question4 = "Which of the following are a prime number?";
        List<String> options4 = Arrays.asList("11", "5", "13", "29");
        int[] correctAnswerIndex4 = { 0, 1, 2, 3 };
        MultipleChoicesQuestionData q4 = new MultipleChoicesQuestionData(question4, options4, correctAnswerIndex4);
        questions.add(q4);

        // Question 5
        String question5 = "What is the largest planet in our solar system?";
        List<String> options5 = Arrays.asList("Earth", "Mars", "Jupiter", "Saturn");
        int correctAnswerIndex5 = 2; // "Jupiter"
        SingleChoiceQuestionData q5 = new SingleChoiceQuestionData(question5, options5, correctAnswerIndex5);
        questions.add(q5);

        return new TestData(questions, 0);
    }
}
