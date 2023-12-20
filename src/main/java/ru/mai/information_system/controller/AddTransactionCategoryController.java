package ru.mai.information_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import static ru.mai.information_system.controller.NewStageOpener.closeWindow;

public class AddTransactionCategoryController {

    @FXML
    private Button addTransactionCategoryButton;

    @FXML
    private TextField categoryNameInput;

    @FXML
    private RadioButton incomeCategoryRB;

    @FXML
    private RadioButton spendingCategoryRB;

    @FXML
    void addTransactionCategory(ActionEvent event) {
        closeWindow(addTransactionCategoryButton);
        // сохранить категорию для пользователя
    }
}
