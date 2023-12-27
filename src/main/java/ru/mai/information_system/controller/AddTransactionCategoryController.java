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
import static ru.mai.information_system.controller.NewStageOpener.openResponseStage;

public class AddTransactionCategoryController {

    private BankAccountWindowController bankAccountWindowController;

    @FXML
    private Button addTransactionCategoryButton;

    @FXML
    private TextField categoryNameInput;

    @FXML
    private RadioButton incomeCategoryRB;

    @FXML
    private RadioButton spendingCategoryRB;

    public void initialize() {
        bankAccountWindowController = BankAccountWindowController.getBankAccountWindowController();

        ToggleGroup toggleGroup = new ToggleGroup();
        incomeCategoryRB.setToggleGroup(toggleGroup);
        spendingCategoryRB.setToggleGroup(toggleGroup);
    }

    @FXML
    void addTransactionCategory(ActionEvent event) {
        String response;

        boolean categoryType;
        if (incomeCategoryRB.isSelected()) {
            categoryType = true;
        } else if (spendingCategoryRB.isSelected()) {
            categoryType = false;
        } else {
            response = "Выберите тип транзакции";
            openResponseStage(false, response);
            System.out.println("Category type didn't choose");
            return;
        }

        String categoryName = categoryNameInput.getText();
        if (categoryName.isEmpty()) {
            response = "Введите название для транзакции";
            openResponseStage(false, response);
            System.out.println("Category name field empty");
            return;
        }

        if (categoryName.length() > 40) {
            response = "Название категории слишком длинное";
            openResponseStage(false, response);
            System.out.println("Transaction category name too long");
            return;
        }

        TransactionCategory transactionCategory = new TransactionCategory(App.getCurrentUserId(),
                categoryType, categoryName);

        try {
            response = Communication.sendPostRequest(Url.getTransactionCategoriesUrl(),
                    new Gson().toJson(transactionCategory));

            System.out.println(response);
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return;
        }

        bankAccountWindowController.updateWindow();
        closeWindow(addTransactionCategoryButton);
        openResponseStage(true, "Категория успешно создана");
    }
}
