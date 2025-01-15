package com.LerningBara.controller.CreateTests;

import com.LerningBara.controller.CreateTests.CreateAbstractQestionController;
import com.LerningBara.controller.CreateTests.CreateSingleChoiceQuestionController;
import com.LerningBara.controller.CreateTests.CreateMultipleChoiceQuestionController;
import com.LerningBara.controller.CreateTests.CreateOpenAnswerQuestionController;
import com.LerningBara.controller.CreateTests.CreateSingleChoiceQuestionWithPictureController;
import com.LerningBara.controller.CreateTests.CreateMultipleChoicesQuestionWithPictureController;

public class CreateQuestionConventor {
    
    public static CreateAbstractQestionController getController(String questionType) {
        switch (questionType) {
            case "Single Choice":
                return new CreateSingleChoiceQuestionController();
            case "Multiple Choice":
                 return new CreateMultipleChoiceQuestionController();
            case "Open Question":
                return new CreateOpenAnswerQuestionController();
            case "Single Choice with Image":
                return new CreateSingleChoiceQuestionWithPictureController();
            case "Multiple Choice with Image":
                return new CreateMultipleChoicesQuestionWithPictureController();
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
            case "Single Choice with Image":
                return "/fxml/CreateTests/CreateSingleChoiceQuestionWithPictureScene.fxml";
            case "Multiple Choice with Image":
                return "/fxml/CreateTests/CreateMultipleChoiceQuestionWithPictureScene.fxml";
            default:
                return null; // it will crash the program
        }
    }
}
