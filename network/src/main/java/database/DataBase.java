package database;

import java.util.ArrayList;
import java.util.List;

import QuestionData.AbstractQuestionData;
import QuestionData.SingleChoiceQuestionData;
import QuestionData.MultipleChoicesQuestionData;
import QuestionData.OpenAnwserQuestionData;
import TestData.AvalibleTestsList;
import TestData.TestData;
import TestData.TestInfoData;
import UserData.User;
import UserData.UserData;

import static org.junit.jupiter.api.DynamicTest.stream;

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
                    "SELECT LOGIN, T.*, (SELECT COUNT(*) FROM TEST_QUESTION WHERE TESTS_TESTID = T.TESTID) N FROM TESTS T JOIN USERS ON USERID = T.USERS_USERID");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                TestInfoData testInfoData3 = new TestInfoData(resultSet.getInt("testid"), resultSet.getString("Name"),
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

        // TODO: remove this hardcoded data
        return new AvalibleTestsList(tests);
    }

    public static AvalibleTestsList getLikedTests(int userID) {
        connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<TestInfoData> tests = new ArrayList<>();
        try {
            statement = connection.prepareStatement(
                    "SELECT LOGIN, T.*, (SELECT COUNT(*) FROM TEST_QUESTION WHERE TESTS_TESTID = T.TESTID) N FROM LIKES L JOIN TESTS T ON L.TESTS_TESTID = T.TESTID JOIN USERS ON USERID = T.USERS_USERID WHERE USERID = ?");
            statement.setInt(1, userID);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                TestInfoData testInfoData3 = new TestInfoData(resultSet.getInt("testid"), resultSet.getString("Name"),
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

        return new AvalibleTestsList(tests);
    }

    public static AvalibleTestsList getUserTests(int userID) {
        connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<TestInfoData> tests = new ArrayList<>();
        try {
            statement = connection.prepareStatement(
                    "SELECT LOGIN, T.*, (SELECT COUNT(*) FROM TEST_QUESTION WHERE TESTS_TESTID = T.TESTID) N FROM TESTS T JOIN USERS ON USERID = T.USERS_USERID WHERE USERID = ?");
            statement.setInt(1, userID);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                TestInfoData testInfoData3 = new TestInfoData(resultSet.getInt("testid"), resultSet.getString("Name"),
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
                        "SELECT a.text, a.IsCorrect FROM Answers a WHERE Questions_QuestionId=?");
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

    public static void addToLikes(int userID, int testID) {
        connect();
        int date = 1; // TODO: implement getting current date
        String str_insert = String.format(
                "INSERT INTO LIKES(date, users_userid , tests_testid ) VALUES ('%d', '%d', '%d')",
                date, userID, testID);
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
                    System.out.println("Successfully added test to liked.");
                } catch (IOException e) {
                    System.err.println("An error occured: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Insert execution failed: " + e.getMessage());
        }
    }

    public static void addTest(TestData testData, int userId, String testName) {
        connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int testId;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO Tests(Name, Users_UserId) VALUES (?, ?)");
            statement.setString(1, testName);
            statement.setInt(2, userId);
            statement.executeUpdate();
            statement = connection.prepareStatement(
                    "SELECT TestId FROM Tests WHERE Name = ?");
            statement.setString(1, testName);
            resultSet = statement.executeQuery();
            resultSet.next();
            testId = resultSet.getInt(1);
            for (var questionData : testData.questions) {
                addQuestion(questionData, testId);
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
    }

    private static void addQuestion(AbstractQuestionData questionData, int testId) {
        connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int questionId;
        int position = 1;
        int type = -1;
        String text = "nie dziala";
        try {
            if (questionData instanceof SingleChoiceQuestionData) {
                type = 1;
                text = ((SingleChoiceQuestionData) questionData).getQuestion();
            } else if (questionData instanceof MultipleChoicesQuestionData) {
                type = 2;
                text = ((MultipleChoicesQuestionData) questionData).getQuestion();
            } else if (questionData instanceof OpenAnwserQuestionData) {
                type = 3;
                text = ((OpenAnwserQuestionData) questionData).getQuestion();
            }
            statement = connection.prepareStatement(
                    "INSERT INTO Questions(Text, Types_TypeId, Position, Tests_TestId) VALUES (?, ?, ?, ?)");
            statement.setString(1, text);
            statement.setInt(2, type);
            statement.setInt(3, position);
            statement.setInt(4, testId);
            statement.executeUpdate();
            statement = connection.prepareStatement(
                    "SELECT QuestionId FROM Questions WHERE Position = ? AND Tests_TestId = ?");
            statement.setInt(1, position);
            statement.setInt(2, testId);
            resultSet = statement.executeQuery();
            resultSet.next();
            questionId = resultSet.getInt(1);
            addAnswer(questionData, questionId);
            position++;
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
    }

    private static void addAnswer(AbstractQuestionData questionData, int questionId) {
        connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<String> options = new ArrayList<>();
        ;
        List<Integer> correctAnswerIndexes = new ArrayList<>();
        ;
        try {
            if (questionData instanceof SingleChoiceQuestionData) {
                options = ((SingleChoiceQuestionData) questionData).getOptions();
                correctAnswerIndexes.add((Integer) (((SingleChoiceQuestionData) questionData).getCorrectAnswerIndex()));
            } else if (questionData instanceof MultipleChoicesQuestionData) {
                options = ((MultipleChoicesQuestionData) questionData).getOptions();
                for (int i : ((MultipleChoicesQuestionData) questionData).getCorrectAnswerIndexes()) {
                    correctAnswerIndexes.add(i);
                }
            } else if (questionData instanceof OpenAnwserQuestionData) {
                options.add(((OpenAnwserQuestionData) questionData).getCorrectAnswer());
                correctAnswerIndexes.add(1);
            }
            for (int i = 0; i < options.size(); i++) {
                statement = connection.prepareStatement(
                        "INSERT INTO Answers(Text, IsCorrect, Questions_QuestionId) VALUES (?, ?, ?)");
                statement.setString(1, options.get(0));
                statement.setBoolean(2, correctAnswerIndexes.contains(i));
                statement.setInt(3, questionId);
                statement.executeUpdate();
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
    }

    public static void updateUser(UserData user) {
        connect();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "UPDATE Users SET Login = ?, Password = ?, Email = ? WHERE Login = ?");
            statement.setString(1, user.username);
            statement.setString(2, user.password);
            statement.setString(3, user.email);
            statement.setString(4, user.username);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Query execution failed: " + e.getMessage());
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
    }

    public static User getUser(String username) {
        connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = new User();
        try {
            statement = connection.prepareStatement(
                    "SELECT UserId, Login, Email FROM Users WHERE Login = ?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            resultSet.next();
            user.setAttributes(resultSet.getInt("UserId"), resultSet.getString("Login"), resultSet.getString("Email"));
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
        return user;
    }

    public static void addFavorites(int userId, int testId) {
        connect();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO Likes (UserId, TestId, Date) VALUES (?, ?, CURRENT_DATE)");
            statement.setInt(1, userId);
            statement.setInt(2, testId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Query execution failed: " + e.getMessage());
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
    }

    public static void deleteFavorites(int userId, int testId) {
        connect();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "DELETE FROM Likes WHERE UserId = ? AND TestId = ?");
            statement.setInt(1, userId);
            statement.setInt(2, testId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Query execution failed: " + e.getMessage());
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
    }

    public static AvalibleTestsList getFavorites(int userId) {
        connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<TestInfoData> tests = new ArrayList<>();
        try {
            statement = connection.prepareStatement(
                    "SELECT LOGIN, T.*, (SELECT COUNT(*) FROM Questions WHERE TESTS_TESTID = T.TESTID) N FROM LIKES L JOIN USERS LU ON LU.USERID = L.USERS_USERID JOIN TESTS T ON L.TESTS_TESTID = T.TESTID JOIN USERS U ON U.USERID = T.USERS_USERID WHERE LU.USERID = ?");
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TestInfoData testInfoData3 = new TestInfoData(resultSet.getInt("testid"), resultSet.getString("Name"),
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
        return new AvalibleTestsList(tests);
    }

    public static void SaveResult(int userId, int testId, int points) {
        connect();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO Results (UserId, TestId, Points, Date) VALUES (?, ?, ?, CURRENT_DATE)");
            statement.setInt(1, userId);
            statement.setInt(2, testId);
            statement.setInt(3, points);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Query execution failed: " + e.getMessage());
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
    }

    public static List<Integer> getResults(int testId, int userId) {
        connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Integer> results = new ArrayList<>();
        try {
            statement = connection.prepareStatement(
                    "SELECT Points FROM Results WHERE TestId = ? AND UserId = ?");
            statement.setInt(1, testId);
            statement.setInt(2, userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.add(resultSet.getInt("Points"));
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
        return results;
    }

    // public static void deleteUser(int userId)
    // public static void deleteTest(int testId)

    // public static void setUserIcon(int userId, Image icon) or use UpdateUser
    // method

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
