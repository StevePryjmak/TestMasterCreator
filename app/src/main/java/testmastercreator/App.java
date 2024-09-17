
package testmastercreator;

import java.io.IOException;
import java.util.Arrays;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application 
{
    private static Stage stage;
    private Test test;

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/StartScene.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/Styles.css");
        stage.setScene(scene);
        stage.show();
        runExampleTest();
        //experimental();
    }

    public static void setRoot(String fxml) throws IOException {
        
        Parent root = FXMLLoader.load(App.class.getResource("/" + fxml + ".fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }


    public void experimental() throws IOException {
        String question = "What is the capital of France?";
        var options = Arrays.asList("Berlin", "Madrid", "Paris", "Rome");
        int correctAnswerIndex = 2; // Paris is the correct answer

        // Create an instance of SingleChoiceScene
        SingleChoiceQuestion singleChoiceqQuestion = new SingleChoiceQuestion(question, options, correctAnswerIndex);

        // Set the scene to the primary stage
        stage.setScene(singleChoiceqQuestion.getScene()); // Assuming SingleChoiceScene extends Scene or Parent
        stage.setTitle("Single Choice Question");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    public void runExampleTest() {
        test = TestInitializer.createExampleTest();
        for (AbstractQuestion question : test) {
            stage.setScene(question.getScene());
        }
    }    
}

