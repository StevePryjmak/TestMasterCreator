
package testmastercreator;

import java.io.IOException;
import java.util.Arrays;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class App extends Application 
{
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/StartScene.fxml"));
        Scene scene = new Scene(root);
        //scene.getStylesheets().add("/styles/Styles.css");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        
        Parent root = FXMLLoader.load(App.class.getResource("/" + fxml + ".fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }



    public static void main(String[] args) {
        launch(args);
    }
}

