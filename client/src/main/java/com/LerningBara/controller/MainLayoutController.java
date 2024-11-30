
package com.LerningBara.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.List;

import TestData.TestInfoData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    private List<TestInfoData> testsInfo;

    public void addTestBox(VBox testBox) {
        contentBox.getChildren().add(testBox);
    }

    public void setTests(List<TestInfoData> testsInfo) {
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
        ObservableList<String> filterOptions = FXCollections.observableArrayList("Test Field", "Subject");
        filterComboBox.setItems(filterOptions);
    }

    private void handleSearch() {
        String filter = filterComboBox.getValue();
        String searchText = searchTextField.getText();

        System.out.println("Search for: " + searchText + " in " + filter);
    }
}
