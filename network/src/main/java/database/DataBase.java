package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import QuestionData.AbstractQuestionData;
import QuestionData.SingleChoiceQuestionData;
import QuestionData.MultipleChoicesQuestionData;
import TestData.AvalibleTestsList;
import TestData.TestData;
import TestData.TestInfoData;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/FREEPDB1";
    private static final String USER = "System";
    private static final String PASSWORD = "projektpap2024";
    private static Connection connection = null;

    private static void connect() {
        if (connection == null)
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connection established successfully.");
            } catch (SQLException e) {
                System.err.println("Failed to connect to the database " + e);
            }
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                System.err.println("Failed to close the connection: " + e.getMessage());
            }
        }
    }

    public static AvalibleTestsList getTests() {
        connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<TestInfoData> tests = new ArrayList<>();
        try {
            statement = connection.prepareStatement(
                    "SELECT LOGIN, T.*, (SELECT COUNT(*) FROM TEST_QUESTION WHERE TESTS_TESTID = T.TESTID) N FROM TESTS T JOIN USERS ON USERID = USERS_USERID");
            System.out.println("Executing query: " + statement.unwrap(oracle.jdbc.OraclePreparedStatement.class));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TestInfoData testInfoData3 = new TestInfoData(resultSet.getString("Name"),
                        resultSet.getString("Description"), resultSet.getString("Login"), resultSet.getString("Date"),
                        resultSet.getString("field"), resultSet.getInt("n"));
                tests.add(testInfoData3);
            }
        } catch (SQLException e) {
            System.err.println("Query execution failed: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return new AvalibleTestsList(tests);
    }

    public static TestData getTest(String testName) {
        connect();
        PreparedStatement statement = null;
        ResultSet questionsSet = null;
        ResultSet answersSet = null;
        List<AbstractQuestionData> questions = new ArrayList<>();
        try {
            statement = connection.prepareStatement(
                    "SELECT q.QuestionId, q.Text, q.Types_TypeId FROM Tests t JOIN Test_question tg ON TestID = Tests_TestID JOIN Questions q ON QuestionId = Questions_QuestionId WHERE Name=? ORDER BY QuestionOrder");
            statement.setString(1, testName);
            questionsSet = statement.executeQuery();
            while (questionsSet.next()) {
                List<String> options = new ArrayList<>();
                List<Integer> correctAnswers = new ArrayList<>();
                int i = 0;
                statement = connection.prepareStatement(
                        "SELECT a.text, qa.IsCorrect FROM Questions q JOIN Question_answer qa ON QuestionId = Questions_QuestionId JOIN Answers a ON AnswerId = Answers_AnswerId WHERE QuestionId=?");
                statement.setInt(1, questionsSet.getInt("QuestionId"));
                answersSet = statement.executeQuery();
                while (answersSet.next()) {
                    options.add(answersSet.getString("Text"));
                    if ((answersSet.getString("IsCorrect")).equals("T")) {
                        correctAnswers.add(i);
                    }
                    i++;
                }
                AbstractQuestionData q = null;
                switch (questionsSet.getInt("Types_TypeId")) {
                    case 1:
                        q = new SingleChoiceQuestionData(questionsSet.getString("Text"), options,
                                (int) (correctAnswers.toArray()[0]));
                        break;
                    case 2:
                        q = new MultipleChoicesQuestionData(questionsSet.getString("Text"), options,
                                correctAnswers.stream().mapToInt(Integer::intValue).toArray());
                        break;
                }
                questions.add(q);
            }
        } catch (SQLException e) {
            System.err.println("Query execution failed: " + e.getMessage());
        } finally {
            try {
                if (questionsSet != null)
                    questionsSet.close();
                if (answersSet != null)
                    answersSet.close();
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return new TestData(questions);
    }

    public boolean userExists(String username) {
        connect();
        return true;
    }

    public boolean checkPassword(String username, String password) {
        connect();
        return true;
    }

    public void addUser(String username, String password) {
        connect();

    }

    public void addTest(TestData testData) {
        connect();
    }

    // class with user info profile image and some useful information idk ...
    // public User getUser(String username) {
    // return new User("username", "password");
    // }

    // public void updateUser(User user) {
    // // update user in database
    // }
}
