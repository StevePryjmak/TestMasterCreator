package com.LerningBara.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import com.LerningBara.app.App;

import QuestionData.*;
import TestData.TestData;
import client.Client;

import java.util.ArrayList;
import java.util.List;

import com.LerningBara.model.AbstractQuestion;
import com.LerningBara.model.QuestionConventor;
import javafx.scene.Scene;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

import com.LerningBara.controller.CreateTests.CreateAbstractQestionController;
import com.LerningBara.controller.CreateTests.CreateSingleChoiceQuestionController;
import javafx.scene.Parent;
import com.LerningBara.model.Test;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.LerningBara.controller.CreateTests.*;;

public class CreateTestController {

    @FXML
    private Button addQuestionButton;

    @FXML
    private VBox questionList;

    @FXML
    private VBox questionsShortcuts;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField testNameField;

    @FXML
    private ComboBox<String> setTestField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ComboBox<String> choseQuestionType;

    private String prevTestName;
    private String prevDescription;
    private String prevTestField;
    private String prevTestType;

    private List<CreateAbstractQestionController> questions;

    @FXML
    public void initialize() {
        System.out.println("CreateTestController initialized");

        if (questionList == null) {
            questionList = new VBox();
        }
        if (questions == null) {
            questions = new ArrayList<>();
        }
        // Populate ComboBoxes
        ObservableList<String> filterOptions = FXCollections.observableArrayList("All", "Math", "Physics", "Chemistry", "Biology");
        setTestField.setItems(filterOptions);

        ObservableList<String> questionTypes = FXCollections.observableArrayList("Single Choice", "Multiple Choice", "Open Question", "Single Choice with Image", "Multiple Choice with Image");
        choseQuestionType.setItems(questionTypes);
        update();
    }

    public void update() {
        testNameField.setText(prevTestName);
        descriptionField.setText(prevDescription);
        setTestField.setValue(prevTestField);
        choseQuestionType.setValue(prevTestType);
        updateQuestionList();
        updateQuestionShortcuts();
    }

    @FXML
    public void handleAddQuestion() {
        System.out.println("Add Question Button Clicked");
        handleQuestionEdit(questions.size(), true);
        App.createTestController = this;
    }

    private void saveCurrentState() {
        prevTestName = testNameField.getText();
        prevDescription = descriptionField.getText();
        prevTestField = setTestField.getValue();
        prevTestType = choseQuestionType.getValue();
    }

    public void addQuestion(CreateAbstractQestionController questionController) {
        if (this.questions == null) {
            this.questions = new ArrayList<>();
        }

        this.questions.add(questionController);
        System.out.println("Question added successfully!");
    }

    public void updateQuestionList() {
        questionList.getChildren().clear();
        int index = 0;
        for (CreateAbstractQestionController question : questions) {
            questionList.getChildren().add(createQuestionBox(question.getQuestionData(), index++));
        }
    }

    private VBox createQuestionBox(AbstractQuestionData question, int index) {
        AbstractQuestion tempQuestion = QuestionConventor.convertToQuestion(question);
        return tempQuestion.getDetailsBox(index);
    }

    public void updateQuestionShortcuts() {
        questionsShortcuts.getChildren().clear();
        for (int i = 0; i < questions.size(); i++) {
            Button shortcutButton = new Button((i + 1) + ". " + questions.get(i).getQuestionData().getQuestion());
            int questionIndex = i;

            shortcutButton.getStyleClass().add("sticker-button");
            shortcutButton.setOnAction(_ -> scrollToQuestion(questionIndex));
            questionsShortcuts.getChildren().add(shortcutButton);
        }
    }

    private void scrollToQuestion(int index) {
        if (index < 0 || index >= questionList.getChildren().size())
            return;
        Node targetNode = questionList.getChildren().get(index);
        scrollPane.layout();
        double targetY = targetNode.getLayoutY();
        double scrollRatio = targetY / (questionList.getHeight() - scrollPane.getViewportBounds().getHeight());
        scrollPane.setVvalue(scrollRatio);
    }

    // TODO incapsulat seting state and add funciotn of initilizing with controler
    // to App
    public void handleQuestionEdit(int index, boolean create) {
        System.out.println("Question #" + (index + 1) + " clicked");
        String questionType = choseQuestionType.getValue();
        saveCurrentState();
        CreateAbstractQestionController questionController;
        if (create) {
            questionController = CreateQuestionConventor.getController(questionType);
            questionController.isEdit = false;
        } else {
            questionController = questions.get(index);
            questionController.isEdit = true;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(CreateQuestionConventor.getFXMLLocation(questionType)));
            
            loader.setController(questionController);
            Parent root = loader.load();

            Scene editScene = new Scene(root);
            App.stage.setScene(editScene);
            App.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the edit window.");
        }
    }

    public void handleDeleteQuestion(int index) {
        System.out.println("Question #" + (index + 1) + " deleted");
        questions.remove(index);
        // TODO only neded update funciotin if initilize changes to overvriting some
        // values this need to be change only to update
        initialize();
    }

    // TODO save it to corect location and do corect logic after also send and save
    // to database
    @FXML
    private void handleSaveTest() {
        System.out.println("Save Test Button Clicked!");
        if (testNameField.getText().isEmpty()) {
            System.out.println("Test name is empty!");
            return;
        }
        if (setTestField.getValue() == null) {
            System.out.println("Test field is empty!");
            return;
        }
        if (descriptionField.getText().isEmpty()) {
            System.out.println("Test description is empty!");
            return;
        }
        if (questions.isEmpty()) {
            System.out.println("Test has no questions!");
            return;
        } // For now good enoth

        List<AbstractQuestionData> questionsData = new ArrayList<>();
        for (CreateAbstractQestionController question : questions) {
            questionsData.add(question.getQuestionData());
        }
        TestData testData = new TestData(questionsData);
        testData.setName(testNameField.getText());
        testData.setFiled(setTestField.getValue());
        testData.setDescription(descriptionField.getText());

        App.getInstance().client.sendMessage("Save test", testData);
        App.setRoot("QuizMenuScene");
    }

    @FXML
    private void handleGoBack() {
        System.out.println("Cancel Button Clicked!");
        App.setRoot("QuizMenuScene");
    }
}
