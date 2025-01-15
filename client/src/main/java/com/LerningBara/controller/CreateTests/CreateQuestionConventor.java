package com.LerningBara.controller.CreateTests;

import com.LerningBara.controller.CreateTests.CreateAbstractQestionController;
import com.LerningBara.controller.CreateTests.CreateSingleChoiceQuestionController;
import com.LerningBara.controller.CreateTests.CreateMultipleChoiceQuestionController;
import com.LerningBara.controller.CreateTests.CreateOpenAnswerQuestionController;

public class CreateQuestionConventor {
    
    public static CreateAbstractQestionController getController(String questionType) {
        switch (questionType) {
            case "Single Choice":
                return new CreateSingleChoiceQuestionController();
            case "Multiple Choice":
                 return new CreateMultipleChoiceQuestionController();
            case "Open Question":
                return new CreateOpenAnswerQuestionController();
            default:
                return null;// it will crash the program but should be unreacheble
        }
    }

    public static String getFXMLLocation(String questionType) {
        switch (questionType) {
            case "Single Choice":
                return "/fxml/CreateTests/CreateSingleChoiceQuestionScene.fxml";
            case "Multiple Choice":
                return "/fxml/CreateTests/CreateMultipleChoiceQuestionScene.fxml";
            case "Open Question":
                return "/fxml/CreateTests/CreateOpenAnswerQestionScene.fxml";
            default:
                return null; // it will crash the program
        }
    }
}
