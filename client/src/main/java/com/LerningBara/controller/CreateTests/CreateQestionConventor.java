package com.LerningBara.controller.CreateTests;

import com.LerningBara.controller.CreateTests.CreateAbstractQestionController;
import com.LerningBara.controller.CreateTests.CreateSingleChoiceQuestionController;

public class CreateQestionConventor {
    
    public static CreateAbstractQestionController getController(String questionType) {
        switch (questionType) {
            case "Single choice":
                return new CreateSingleChoiceQuestionController();
            // case "Multiple choice":
            //     return new CreateMultipleChoiceQuestionController();
            // case "Open":
            //     return new CreateOpenQuestionController();
            // case "Order":
            //     return new CreateOrderQuestionController();
            // default:
            //     return null;
        }
    }

    public static String getFXMLLocation(String questionType) {
        switch (questionType) {
            case "Single choice":
                return "/fxml/CreateTests/CreateSingleChoiceQuestionScene.fxml";
            // case "Multiple choice":
            //     return "/fxml/CreateMultipleChoiceQuestion.fxml";
            // case "Open":
            //     return "/fxml/CreateOpenQuestion.fxml";
            // case "Order":
            //     return "/fxml/CreateOrderQuestion.fxml";
            // default:
            //     return null;
        }
    }
}
