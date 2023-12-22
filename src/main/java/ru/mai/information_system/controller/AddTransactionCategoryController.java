package ru.mai.information_system.controller;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import ru.mai.information_system.App;
import ru.mai.information_system.communication.Communication;
import ru.mai.information_system.communication.Url;
import ru.mai.information_system.dto.TransactionCategory;

import java.io.IOException;

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

    public void initialize() {
        ToggleGroup toggleGroup = new ToggleGroup();
        incomeCategoryRB.setToggleGroup(toggleGroup);
        spendingCategoryRB.setToggleGroup(toggleGroup);
    }

    @FXML
    void addTransactionCategory(ActionEvent event) {
        boolean categoryType;
        if (incomeCategoryRB.isSelected()) {
            categoryType = true;
        } else if (spendingCategoryRB.isSelected()) {
            categoryType = false;
        } else {
            System.out.println("Category type didn't choose");
            return;
        }
        String categoryName = categoryNameInput.getText();
        TransactionCategory transactionCategory = new TransactionCategory(App.getCurrentUser().getId(),
                categoryType, categoryName);

        String response;
        try {
            response = Communication.sendPostRequest(Url.getTransactionCategoriesUrl(),
                    new Gson().toJson(transactionCategory));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println(response);
        closeWindow(addTransactionCategoryButton);
    }
}
