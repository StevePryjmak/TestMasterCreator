package com.LerningBara.app;

import java.io.IOException;
import java.util.Iterator;

import com.LerningBara.controller.TestInitializer;
import com.LerningBara.model.AbstractQuestion;
import com.LerningBara.model.Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import client.Client;

public class App extends Application 
{
    private static App instance;
    private static Stage stage;
    private Test test;
    private static Iterator<AbstractQuestion> testIterator;
    public static Client client;

    @Override
    public void start(Stage stage) {
        try {
            App.stage = stage;
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/StartScene.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/css/Styles.css");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setRoot(String fxml) throws IOException {
        
        Parent root = FXMLLoader.load(App.class.getResource("/fxml/" + fxml + ".fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static App getInstance() {
        if(instance == null) {
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
            start(stage); 
            return;
        }
        AbstractQuestion question = testIterator.next();
        stage.setScene(question.getScene());
        
    }
}

