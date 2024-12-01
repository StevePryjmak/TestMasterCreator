package com.LerningBara.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.LerningBara.model.MultipleChoicesQuestion;
import com.LerningBara.model.SingleChoiceQuestion;
import com.LerningBara.model.Test;

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
            String question3 = "Which are NOT the capital of France?";
            List<String> options3 = Arrays.asList("Berlin", "Paris", "Rome", "Madrid");
            int[] correctAnswerIndex3 = { 0, 2, 3 }; // "Paris"
            MultipleChoicesQuestion q3 = new MultipleChoicesQuestion(question3, options3, correctAnswerIndex3);
            test.addQuestion(q3);

            // Question 4
            String question4 = "Which of the following are a prime number?";
            List<String> options4 = Arrays.asList("11", "5", "13", "29");
            int[] correctAnswerIndex4 = { 0, 1, 2, 3 }; // "5"
            MultipleChoicesQuestion q4 = new MultipleChoicesQuestion(question4, options4, correctAnswerIndex4);
            test.addQuestion(q4);

            // Question 5
            String question5 = "What is the largest planet in our solar system?";
            List<String> options5 = Arrays.asList("Earth", "Mars", "Jupiter", "Saturn");
            int correctAnswerIndex5 = 2; // "Jupiter"
            SingleChoiceQuestion q5 = new SingleChoiceQuestion(question5, options5, correctAnswerIndex5);
            test.addQuestion(q5);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return test;
    }
}
