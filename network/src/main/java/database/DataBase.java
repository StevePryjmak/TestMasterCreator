package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import QuestionData.AbstractQuestionData;
import QuestionData.SingleChoiceQuestionData;
import TestData.AvalibleTestsList;
import TestData.TestData;
import TestData.TestInfoData;
import java.util.ArrayList;

public class DataBase {
    
    public static AvalibleTestsList getTests() {
        List<TestInfoData> tests = new ArrayList<>();
        // @TODO: Implement database conector
        TestInfoData testInfoData = new TestInfoData("Test1", "this is my firs test", "Me", "26/11/2024", "Math", 15);
        tests.add(testInfoData);
        TestInfoData testInfoData2 = new TestInfoData("Test2", "this is my second test", "Me", "26/11/2024", "Math", 15);
        tests.add(testInfoData2);
        TestInfoData testInfoData3 = new TestInfoData("Test3", "this is my third test", "Me", "26/11/2024", "Math", 15);
        tests.add(testInfoData3);
        return new AvalibleTestsList(tests);
    }

    public static TestData getTest(String testName) {

        // @TODO: Implement database conector

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
        String question3 = "What is the largest planet in our solar system?";
        List<String> options3 = Arrays.asList("Earth", "Mars", "Jupiter", "Saturn");
        int correctAnswerIndex3 = 2; // "Jupiter"
        SingleChoiceQuestionData q3 = new SingleChoiceQuestionData(question3, options3, correctAnswerIndex3);
        questions.add(q3);

        return new TestData(questions);
    }

    public boolean userExists(String username) { 
        return true;
    }

    public boolean checkPassword(String username, String password) { 
        return true;
    }

    public void addUser(String username, String password) { 

    }

    public void addTest(TestData testData) { 

    }

    // class with user info profile image and some useful information idk ...   
    // public User getUser(String username) { 
    //     return new User("username", "password");
    // }

    // public void updateUser(User user) {
    //     // update user in database
    // }

}

