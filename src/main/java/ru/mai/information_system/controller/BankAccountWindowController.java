package ru.mai.information_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import static ru.mai.information_system.controller.NewStageOpener.closeWindow;
import static ru.mai.information_system.controller.NewStageOpener.openNewStage;

public class BankAccountWindowController {

    @FXML
    private Label accountNameLabel;

    @FXML
    private Button addTransactionCategoryButton;

    @FXML
    private Button backToAllBankAccountsButton;

    @FXML
    private Label balanceLabel;

    @FXML
    private Button createReportButton;

    @FXML
    private ChoiceBox<?> incomeTransactionChoiceBox;

    @FXML
    private ChoiceBox<?> incomeTransactionByDateChoiceBox;

    @FXML
    private TextField inputAmountIncomeTransaction;

    @FXML
    private TextField inputAmountIncomeTransactionByDate;

    @FXML
    private TextField inputAmountSpendingTransaction;

    @FXML
    private TextField inputDateIncomeTransactionByDate;

    @FXML
    private Button saveIncomeTransactionButton;

    @FXML
    private Button saveIncomeTransactionByDateButton;

    @FXML
    private Button saveSpendingTransactionButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ChoiceBox<?> spendingTransactionChoiceBox;

    @FXML
    void addTransactionCategory(ActionEvent event) {

    }

    @FXML
    void backToAllBankAccounts(ActionEvent event) {
        closeWindow(backToAllBankAccountsButton);

        String file = "main-window-view.fxml";
        openNewStage(file);
    }

    @FXML
    void createReport(ActionEvent event) {

    }

    @FXML
    void saveIncomeTransaction(ActionEvent event) {

    }

    @FXML
    void saveIncomeTransactionByDate(ActionEvent event) {

    }

    @FXML
    void saveSpendingTransaction(ActionEvent event) {

    }
}
