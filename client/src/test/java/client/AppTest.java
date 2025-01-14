package client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.LerningBara.app.App;

import javafx.stage.Stage;


import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private App app;
    private Stage mockStage;

    @BeforeEach
    public void setUp() {
        app = new App();
        mockStage = Mockito.mock(Stage.class);

    }

    @Test
    public void testAppGetInstance() {
        App app = App.getInstance();
        assertTrue(app != null);
    }

    @Test
    public void testAppGetPrimaryStage() {
        app.start(mockStage);
        Mockito.doNothing().when(mockStage).show();
        Stage stage = App.getInstance().getPrimaryStage();
        assertTrue(stage != null);
    }

}
