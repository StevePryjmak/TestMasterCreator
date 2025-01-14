package database;

import org.junit.jupiter.api.BeforeAll;
import org.h2.store.Data;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import QuestionData.AbstractQuestionData;
import QuestionData.SingleChoiceQuestionData;
import QuestionData.MultipleChoicesQuestionData;
import QuestionData.OpenAnwserQuestionData;
import TestData.AvalibleTestsList;
import TestData.TestData;
import TestData.TestInfoData;
import UserData.User;
import UserData.UserData;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DataBaseTest {

    @BeforeAll
    static void setup() throws SQLException {
        DataBase.connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        Statement stmt = DataBase.connection.createStatement();
        stmt.execute("CREATE TABLE Users (" +
        "UserId INTEGER AUTO_INCREMENT PRIMARY KEY, " +
        "Login VARCHAR(20) NOT NULL, " +
        "Password VARCHAR(84) NOT NULL, " +
        "Email VARCHAR(30), " +
        "Icon BLOB, " +
        "IsActive CHAR(1) DEFAULT 'T', " +
        "VisibilityLevel INTEGER DEFAULT 0" +
        ")");

stmt.execute("CREATE TABLE Tests (" +
        "TestId INTEGER AUTO_INCREMENT PRIMARY KEY, " +
        "Name VARCHAR(20), " +
        "Users_UserId INTEGER, " +
        "Icon BLOB, " +
        "Attempts INTEGER, " +
        "TimeLimit INTEGER, " +
        "CreationDate DATE, " +
        "Field VARCHAR(100), " +
        "Description VARCHAR(185), " +
        "FOREIGN KEY (Users_UserId) REFERENCES Users(UserId)" +
        ")");

    stmt.execute("CREATE TABLE Hints (" +
    "HintId INTEGER AUTO_INCREMENT PRIMARY KEY, " +
    "Text VARCHAR(200)" +
    ")");

    stmt.execute("CREATE TABLE Types (" +
    "TypeId INTEGER AUTO_INCREMENT PRIMARY KEY, " +
    "Description VARCHAR(255)" +
    ")");


    stmt.execute("CREATE TABLE Tags (" +
    "TagId INTEGER AUTO_INCREMENT PRIMARY KEY, " +
    "Name VARCHAR(255)" +
    ")");

stmt.execute("CREATE TABLE Results (" +
        "ResultId INTEGER AUTO_INCREMENT PRIMARY KEY, " +
        "Points INTEGER, " +
        "\"Date\" DATE, " +
        "Users_UserId INTEGER, " +
        "Tests_TestId INTEGER, " +
        "FOREIGN KEY (Users_UserId) REFERENCES Users(UserId), " +
        "FOREIGN KEY (Tests_TestId) REFERENCES Tests(TestId)" +
        ")");

stmt.execute("CREATE TABLE Likes (" +
        "LikeId INTEGER AUTO_INCREMENT PRIMARY KEY, " +
        "\"Date\" DATE, " +
        "Users_UserId INTEGER, " +
        "Tests_TestId INTEGER, " +
        "FOREIGN KEY (Users_UserId) REFERENCES Users(UserId), " +
        "FOREIGN KEY (Tests_TestId) REFERENCES Tests(TestId)" +
        ")");

stmt.execute("CREATE TABLE Questions (" +
        "QuestionId INTEGER AUTO_INCREMENT PRIMARY KEY, " +
        "Text VARCHAR(250), " +
        "Hints_HintId INTEGER, " +
        "Types_TypeId INTEGER, " +
        "Tests_TestId INTEGER, " +
        "Image BLOB, " +
        "Position INTEGER, " +
        "FOREIGN KEY (Tests_TestId) REFERENCES Tests(TestId)" +
        ")");

stmt.execute("CREATE TABLE Answers (" +
        "AnswerId INTEGER AUTO_INCREMENT PRIMARY KEY, " +
        "Text VARCHAR(255), " +
        "IsCorrect BOOLEAN, " +
        "Image BLOB, " +
        "Questions_QuestionId INTEGER, " +
        "FOREIGN KEY (Questions_QuestionId) REFERENCES Questions(QuestionId)" +
        ")");


stmt.execute("CREATE TABLE Test_Tag (" +
        "Test_TagId INTEGER AUTO_INCREMENT PRIMARY KEY, " +
        "Tests_TestId INTEGER, " +
        "Tags_TagId INTEGER, " +
        "FOREIGN KEY (Tests_TestId) REFERENCES Tests(TestId), " +
        "FOREIGN KEY (Tags_TagId) REFERENCES Tags(TagId)" +
        ")");
        stmt.close();
    }

    @AfterAll
    static void tearDown() throws SQLException {
        if (DataBase.connection != null && !DataBase.connection.isClosed()) {
            DataBase.connection.close();
        }
        DataBase.close();
    }

    @Test
    void testUsersMethods() throws SQLException{
        DataBase.addUser("testuser", "pass", "email@domain.com");
        
        User user = DataBase.getUser("testuser");
        User user2 = new User();
        user2.setAttributes(1, "testuser", "email@domain.com");
        assertEquals(user.getId(), user2.getId());
        assertEquals(user.getEmail(), user2.getEmail());
        assertEquals(user.getUsername(), user2.getUsername());

        boolean correctPwd = DataBase.checkPassword("testuser", "pass");
        assertTrue(correctPwd, "Password should be correct");
        correctPwd = DataBase.checkPassword("testuser", "not correct pass");
        assertFalse(correctPwd, "Password should not be correct");

        boolean exists = DataBase.userExists("testuser");
        assertTrue(exists, "User should exist in the database");
        exists = DataBase.userExists("testuser2");
        assertFalse(exists, "User should not exist in the database");

        UserData ud = new UserData("testuser5", "pass", "email5@domain.com", 1);
        DataBase.updateUser(ud);
        user = DataBase.getUser("testuser5");
        assertEquals(user.getId(), 1);
        assertEquals(user.getEmail(), "email5@domain.com");
        assertEquals(user.getUsername(), "testuser5");
    }

    @Test
    void testTestMethods() throws SQLException{
        DataBase.addUser("testuser", "pass", "email@domain.com");
        List<AbstractQuestionData> qs = new ArrayList<>();
        List<String> ans = new ArrayList<>();
        ans.add("a");
        ans.add("b");
        SingleChoiceQuestionData q1 = new SingleChoiceQuestionData("P", ans, 2);
        qs.add(q1);
        TestData td = new TestData(qs);
        td.setFiled("Math");
        td.setDescription("dsc");
        td.setName("test");
        DataBase.addTest(td, 1);

        AvalibleTestsList res = DataBase.getTests();
        assertEquals(res.getTests().get(0).name, "test");
    }

    @Test
    void testResultMethods() throws SQLException{
        DataBase.addUser("testuser", "pass", "email@domain.com");
        List<AbstractQuestionData> qs = new ArrayList<>();
        List<String> ans = new ArrayList<>();
        ans.add("a");
        ans.add("b");
        SingleChoiceQuestionData q1 = new SingleChoiceQuestionData("P", ans, 2);
        qs.add(q1);
        TestData td = new TestData(qs);
        td.setFiled("Math");
        td.setDescription("dsc");
        td.setName("test");
        DataBase.addTest(td, 1);

        DataBase.saveResult(1, 1, 5);

        List<Integer> res = DataBase.getResults(1, 1);
        assertEquals(5, res.get(0));
    }

    @Test
    void testFavoritesMethods() throws SQLException{
        DataBase.addUser("testuser", "pass", "email@domain.com");
        List<AbstractQuestionData> qs = new ArrayList<>();
        List<String> ans = new ArrayList<>();
        ans.add("a");
        ans.add("b");
        SingleChoiceQuestionData q1 = new SingleChoiceQuestionData("P", ans, 2);
        qs.add(q1);
        TestData td = new TestData(qs);
        td.setFiled("Math");
        td.setDescription("dsc");
        td.setName("test");
        DataBase.addTest(td, 1);

        DataBase.addFavorites(1, 1);

        AvalibleTestsList res = DataBase.getFavorites(1);
        assertEquals(1, res.getTests().size());

        DataBase.deleteFavorites(1, 1);
        res = DataBase.getFavorites(1);
        assertEquals(0, res.getTests().size());
    }
}

