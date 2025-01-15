package database;

import java.util.ArrayList;
import java.util.List;

import QuestionData.AbstractQuestionData;
import QuestionData.SingleChoiceQuestionData;
import QuestionData.MultipleChoicesQuestionData;
import QuestionData.OpenAnwserQuestionData;
import QuestionData.SingleChoiceQuestionWithPictureData;
import QuestionData.MultipleChoicesQuestionWithPictureData;
import TestData.AvalibleTestsList;
import TestData.TestData;
import TestData.TestInfoData;
import UserData.User;
import UserData.UserData;

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
    public static Connection connection = null;

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

    public static List<Integer> getUserTestResults(int userID, int testID) {
        connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Integer> result = new ArrayList<>();
        try {
            statement = connection.prepareStatement(
                    "SELECT points from results where tests_testid = ? and users_userid = ?");
            statement.setInt(1, testID);
            statement.setInt(2, userID);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getInt("points"));
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

        return result;
    }

    public static int getTestLikeCount(int testID) {
        connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int result = 0;
        try {
            statement = connection.prepareStatement(
                    "SELECT count(*) from likes where tests_testid = ?");
            statement.setInt(1, testID);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt(1);
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

        return result;
    }

    public static AvalibleTestsList getTests() {
        connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<TestInfoData> tests = new ArrayList<>();
        try {
            statement = connection.prepareStatement(
                    "SELECT LOGIN, T.*, (SELECT COUNT(*) FROM Questions WHERE TESTS_TESTID = T.TESTID) N FROM TESTS T JOIN USERS ON USERID = USERS_USERID WHERE visibilitylevel = 0");
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
                    "SELECT LOGIN, T.*, (SELECT COUNT(*) FROM QUESTIONS WHERE TESTS_TESTID = T.TESTID) N FROM TESTS T JOIN USERS ON USERID = T.USERS_USERID WHERE USERID = ?");
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
        boolean shuffled = false;
        try {
            statement = connection.prepareStatement(
                    "SELECT q.QuestionId, q.Text, q.Types_TypeId, q.image, t.shuffled FROM Tests t JOIN Questions q ON TestId = Tests_TestId  WHERE Name=? ORDER BY Position");
            statement.setString(1, testName);
            questionsSet = statement.executeQuery();

            while (questionsSet.next()) {
                shuffled = (questionsSet.getBoolean("shuffled"));
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
                        System.out.println(((SingleChoiceQuestionData) q).getCorrectAnswerIndex());
                        break;
                    case 2:
                        q = new MultipleChoicesQuestionData(questionsSet.getString("Text"), options,
                                correctAnswers.stream().mapToInt(Integer::intValue).toArray());
                        break;
                    case 3:
                        q = new OpenAnwserQuestionData(questionsSet.getString("Text"), (String) options.toArray()[0]);
                        break;
                    case 4:
                        q = new MultipleChoicesQuestionWithPictureData(questionsSet.getString("Text"), options,
                                correctAnswers.stream().mapToInt(Integer::intValue).toArray(),
                                questionsSet.getBytes("image"));
                        break;
                    case 5:
                        q = new SingleChoiceQuestionWithPictureData(questionsSet.getString("Text"), options,
                                (int) (correctAnswers.toArray()[0]), questionsSet.getBytes("image"));
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
        TestData result = new TestData(questions);
        result.setShuffled(shuffled);
        return result;
    }

    public static boolean userExists(String username) {
        connect();
        PreparedStatement statement = null;
        ResultSet userSet = null;
        boolean exists = false;
        try {
            statement = connection
                    .prepareStatement("SELECT COUNT(*) AS EX FROM USERS WHERE LOGIN = ? AND ISACTIVE = 'T'");
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
            statement = connection.prepareStatement("SELECT PASSWORD FROM USERS WHERE LOGIN = ? AND ISACTIVE = 'T'");
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

    public static void addTest(TestData testData, int userId) {
        connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int testId, position = 1;
        String testName = testData.getName();
        String testDescription = testData.getDescription();
        String testField = testData.getFiled();
        boolean bool = testData.getShuffled();
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO Tests(Name, Users_UserId, creationdate, field, description, shuffled) VALUES (?, ?, CURRENT_DATE, ?, ?, ?)");
            statement.setString(1, testName);
            statement.setInt(2, userId);
            statement.setString(3, testField);
            statement.setString(4, testDescription);
            statement.setBoolean(5, bool);
            statement.executeUpdate();
            statement = connection.prepareStatement(
                    "SELECT TestId FROM Tests WHERE Name = ?");
            statement.setString(1, testName);
            resultSet = statement.executeQuery();
            resultSet.next();
            testId = resultSet.getInt(1);
            for (var questionData : testData.questions) {
                addQuestion(questionData, testId, position);
                position++;
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

    private static void addQuestion(AbstractQuestionData questionData, int testId, int position) {
        connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int questionId;
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
        List<Integer> correctAnswerIndexes = new ArrayList<>();
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
                statement.setString(1, options.get(i));
                String correct = correctAnswerIndexes.contains(i) ? "T" : "F";
                statement.setString(2, correct);
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
        String str;
        int i = 1;
        if (user.password.isEmpty()) {
            str = "UPDATE Users SET Login = ?, Email = ? WHERE UserId = ? AND IsActive = 'T'";
        } else {
            str = "UPDATE Users SET Login = ?, Password = ?, Email = ? WHERE UserId = ? AND IsActive = 'T'";
        }
        try {
            statement = connection.prepareStatement(
                    str);
            statement.setString(i++, user.username);
            if (!user.password.isEmpty()) {
                statement.setString(i++, user.password);
            }
            statement.setString(i++, user.email);
            statement.setInt(i++, user.id);
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
                    "SELECT UserId, Login, Email FROM Users WHERE Login = ? AND IsActive = 'T'");
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
                    "INSERT INTO Likes (users_UserId, tests_TestId, \"Date\") VALUES (?, ?, CURRENT_DATE)");
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
                    "DELETE FROM Likes WHERE users_UserId = ? AND tests_TestId = ?");
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
                    "SELECT lu.LOGIN, T.*, (SELECT COUNT(*) FROM Questions q WHERE q.TESTS_TESTID = T.testid) N FROM LIKES L JOIN USERS LU ON LU.USERID = L.USERS_USERID JOIN TESTS T ON L.TESTS_TESTID = T.testid JOIN USERS U ON U.USERID = T.USERS_USERID WHERE LU.USERID = ?");
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

    public static void saveResult(int userId, int testId, int points) {
        connect();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO Results (users_userid, tests_testid, Points, \"Date\") VALUES (?, ?, ?, CURRENT_DATE)");
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
                    "SELECT Points FROM Results WHERE tests_TestId = ? AND users_UserId = ?");
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

    public static byte[] getUserIcon(String username) {
        connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        byte[] image = null;
        try {
            statement = connection.prepareStatement(
                    "SELECT Icon FROM Users WHERE Login = ? AND IsActive = 'T'");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            resultSet.next();
            image = resultSet.getBytes("Icon");
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
        return image;
    }

    public static void setUserIcon(String username, byte[] image) {
        connect();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "UPDATE Users SET Icon = ? WHERE Login = ? AND IsActive = 'T'");
            statement.setBytes(1, image);
            statement.setString(2, username);
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

    public static void deleteUser(String username) {
        connect();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "UPDATE Users SET IsActive = 'F' WHERE Login = ? AND IsActive = 'T'");
            statement.setString(1, username);
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

    public static void setUserVisibility(String username, int visibilityLevel) {
        connect();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "UPDATE Users SET VisibilityLevel = ? WHERE Login = ? AND IsActive = 'T'");
            statement.setInt(1, visibilityLevel);
            statement.setString(2, username);
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

    public static Boolean getUserVisibility(String username) {
        connect();
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        Boolean result = false;
        try {
            statement = connection.prepareStatement(
                    "SELECT VisibilityLevel FROM Users WHERE Login = ? AND IsActive = 'T'");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            resultSet.next();
            if (resultSet.getInt("VisibilityLevel") == 1) {
                result = true;
            }

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
        return result;
    }

    // public static void deleteTest(int testId)

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
