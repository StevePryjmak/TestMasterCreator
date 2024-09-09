
package testmastercreator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class App extends Application 
{

    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hello, World!");
        Scene scene = new Scene(helloWorld, 400, 200);
        stage.setTitle("Hello JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
        System.out.println("Hello, World!");
    }
}

