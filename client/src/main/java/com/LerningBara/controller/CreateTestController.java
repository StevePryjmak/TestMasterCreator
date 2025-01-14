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
    private TextField descrioption;

    @FXML
    private ComboBox<String> choseQuestionType;

    private String prevTestName;

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
        System.out.println(questions.size());
        update();
    }

    public void update() {
        testNameField.setText(prevTestName);
        updateQuestionList();
        updateQuestionShortcuts();
    }

    @FXML
    public void handleAddQuestion() {
        System.out.println("Add Question Button Clicked");
        prevTestName = testNameField.getText();
        handleQuestionEdit(questions.size(), true);
        App.createTestController = this;
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
        AbstractQuestion tempQestion = QuestionConventor.convertToQuestion(question);
        return tempQestion.getDetailsBox(index);
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
        CreateAbstractQestionController questionController;
        if (create) {
            questionController = new CreateSingleChoiceQuestionController(); // TODO Add convertor class later
        }

        else
            questionController = questions.get(index);

        // handleDeleteQuestion(index);
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/CreateTests/CreateSingleChoiceQuestionScene.fxml")); // TODO Add
                                                                                                       // convertor
                                                                                                       // class later
            questionController.isEdit = true;
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
        System.out.println("Add Test button clicked!");
        if(testNameField.getText().isEmpty()) {
            System.out.println("Test name is empty!");
            return;
        }

        List<AbstractQuestionData> questionsData = new ArrayList<>();
        for (CreateAbstractQestionController question : questions) {
            questionsData.add(question.getQuestionData());
        }
        TestData testdata = new TestData(questionsData);
        Test test = new Test(questionsData, true);
        testdata.setName(testNameField.getText());

        App.getInstance().setTest(test);
        App.getInstance().runExampleTest();
        // App.addTest(test);
        App.getInstance().client.sendMessage("Save test", testdata);
        App.setRoot("QuizMenuScene");
        // App.getInstance().runExampleTest();
        // App.addTest(test);

    }

    @FXML
    private void handleGoBack() {
        System.out.println("Cancel button clicked!");
        // @TODO add pop up do you whana to discard changes...
        App.setRoot("QuizMenuScene");
    }

}
