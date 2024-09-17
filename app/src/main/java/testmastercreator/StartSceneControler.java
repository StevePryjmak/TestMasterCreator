package testmastercreator;

import javafx.fxml.FXML;

public class StartSceneControler {
    @FXML
    public void startTest() throws Exception {
        App app = new App();
        app.runExampleTest();
    }
}
