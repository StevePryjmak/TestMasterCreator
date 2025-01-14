package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import QuestionData.AbstractQuestionData;
import QuestionData.SingleChoiceQuestionData;
import QuestionData.MultipleChoicesQuestionData;
import QuestionData.OpenAnwserQuestionData;
import TestData.AvalibleTestsList;
import TestData.TestData;
import TestData.TestInfoData;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
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

    public static void start() {
        connect();
    }

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
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                TestInfoData testInfoData3 = new TestInfoData(resultSet.getString("Name"),
                        resultSet.getString("Description"), resultSet.getString("Login"),
                        resultSet.getString("CREATIONDATE"),
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

        // @TODO: remove this hardcoded data
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
                    case 3:
                        q = new OpenAnwserQuestionData(questionsSet.getString("Text"), (String) options.toArray()[0]);
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

    public static boolean userExists(String username) {
        connect();
        PreparedStatement statement = null;
        ResultSet userSet = null;
        boolean exists = false;
        try {
            statement = connection.prepareStatement("SELECT COUNT(*) AS EX FROM USERS WHERE LOGIN = ?");
            statement.setString(1, username);
            userSet = statement.executeQuery();
            userSet.next();
            exists = !(userSet.getString("EX").equals("0"));
        } catch (SQLException e) {
            System.err.println("Query execution failed: " + e.getMessage());
        } finally {
            try {
                if (userSet != null)
                    userSet.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return exists;
    }

    public static boolean checkPassword(String username, String password) {
        connect();
        PreparedStatement statement = null;
        ResultSet userSet = null;
        String correct_pass = "";
        try {
            statement = connection.prepareStatement("SELECT PASSWORD FROM USERS WHERE LOGIN = ?");
            statement.setString(1, username);
            userSet = statement.executeQuery();
            userSet.next();
            correct_pass = userSet.getString("PASSWORD");
        } catch (SQLException e) {
            System.err.println("Query execution failed: " + e.getMessage());
        } finally {
            try {
                if (userSet != null)
                    userSet.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return password.equals(correct_pass);
    }

    public static void addUser(String username, String password, String email) {
        connect();
        String str_insert = String.format("INSERT INTO Users(Login, Password, Email) VALUES ('%s', '%s', '%s')",
                username, password, email);
        int rows_insert;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(str_insert);
            rows_insert = statement.executeUpdate();
            if (rows_insert > 0) {
                try (FileWriter fileWriter = new FileWriter("../Insert_data.sql", true)) {
                    fileWriter.write("\n");
                    fileWriter.write(str_insert);
                    fileWriter.write(";\nCOMMIT;");
                    System.out.println("New user appended to data file.");
                } catch (IOException e) {
                    System.err.println("An error occured: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Insert execution failed: " + e.getMessage());
        }
    }

    public static void addTest(TestData testData) {
        connect();
    }

    // class with user info profile image and some useful information idk ...
    // public User getUser(String username) {
    // return new User("username", "password");
    // class with user info profile image and some useful information idk ...
    // public User getUser(String username) {
    // return new User("username", "password");
    // }

    // public void updateUser(User user) {
    // // update user in database
    // }
}
