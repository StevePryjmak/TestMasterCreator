
package com.LerningBara.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.List;

import com.LerningBara.app.App;

import TestData.TestInfoData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class MainLayoutController {

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;

    @FXML
    private VBox contentBox;

    @FXML
    private Button returnButton;

    private List<TestInfoData> testsInfo;
    private List<TestInfoData> filteredTestsInfo;

    public void handleReturn() {
        App.setRoot("QuizMenuScene");
    }

    public void addTestBox(VBox testBox) {
        contentBox.getChildren().add(testBox);
    }

    public void setTests(List<TestInfoData> testsInfo) {
        // TODO: implement filtering and paging
        this.testsInfo = testsInfo;
        this.updateTests(testsInfo);
    }

    public void updateTests(List<TestInfoData> testsInfo) {
        contentBox.getChildren().clear();
        for (TestInfoData testInfo : testsInfo) {
            FXMLLoader testBoxLoader = new FXMLLoader(getClass().getResource("/fxml/TestBox.fxml"));
            try {
                VBox testBox = testBoxLoader.load();
                TestBoxController testBoxController = testBoxLoader.getController();

                testBoxController.setData(testInfo);
                addTestBox(testBox);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    private void initialize() {
        ObservableList<String> filterOptions = FXCollections.observableArrayList("All", "Math", "Physics", "Chemistry",
                "Biology");
        filterComboBox.setItems(filterOptions);
    }

    @FXML
    private void handleSearch() {
        String filter = filterComboBox.getValue();
        String searchText = searchTextField.getText();
        if (filter == null) {
            filter = "All";
        }
        filterTests(filter, testsInfo);
        filterTestsBySearch(searchText, filteredTestsInfo);
        updateTests(filteredTestsInfo);
    }

    private void filterTests(String filter, List<TestInfoData> testsInfo) {
        if (filter == "All") {
            filteredTestsInfo = testsInfo;
            return;
        }
        filteredTestsInfo = new ArrayList<>();
        for (TestInfoData testInfo : testsInfo) {
            if (testInfo.field.equals(filter)) {
                filteredTestsInfo.add(testInfo);
            }
        }
    }

    private void filterTestsBySearch(String searchText, List<TestInfoData> testsInfo) {
        if (searchText.isEmpty()) {
            return;
        }
        filteredTestsInfo = new ArrayList<>();
        for (TestInfoData testInfo : testsInfo) {
            if (testInfo.name.contains(searchText) || testInfo.description.contains(searchText)) {
                filteredTestsInfo.add(testInfo);
            }
        }
    }
}
