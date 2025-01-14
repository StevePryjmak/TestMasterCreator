package com.LerningBara.app;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.LerningBara.model.AbstractQuestion;
import com.LerningBara.model.Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import client.Client;
import TestData.TestInfoData;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;

import com.LerningBara.controller.MainLayoutController;

import com.LerningBara.controller.CreateTestController;

import UserData.User;

public class App extends Application {
    private static App instance;
    public static Stage stage;
    private Test test;
    private static Iterator<AbstractQuestion> testIterator;
    public Client client;
    public static CreateTestController createTestController;

    public User user = new User();
    public TestInfoData testInfoData;

    @Override
    public void start(Stage stage) {
        try {
            App.stage = stage;
            Image icon = new Image("/kapibara.png");
            stage.getIcons().add(icon);
            stage.setTitle("LearningBara");
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginScene.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/css/DefaultStyle.css");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setRoot(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml"));
            if (fxml.equals("CreateTestScene")) {
                System.out.println("CreateTestScene loaded");
                if (createTestController == null) {
                    createTestController = new CreateTestController(); // Create if not already created
                }
                loader.setController(createTestController); // Set controller
            }
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/css/DefaultStyle.css");
            scene.getStylesheets().add("/css/CreateTest.css");
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        App.getInstance().client = new Client("localhost", 8080);
        launch(args);
    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void runExampleTest() {
        // test = TestInitializer.createExampleTest();
        testIterator = test.iterator();
        nextQuestion();
    }

    public void nextQuestion() {
        if (!testIterator.hasNext()) {
            // start(stage);
            // return;
            App.setRoot("EndTestScene");
        } else {
            AbstractQuestion question = testIterator.next();
            stage.setScene(question.getScene());
        }
    }

    public void showTestsList(List<TestInfoData> testsInfo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainLayout.fxml"));
        ScrollPane root = loader.load();
        MainLayoutController controller = loader.getController();

        controller.setTests(testsInfo);

        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add(getClass().getResource("/css/RoundedSearchBar.css").toExternalForm());
        stage.setScene(scene);
    }

    public Stage getPrimaryStage() {
        return stage;
    }

}
