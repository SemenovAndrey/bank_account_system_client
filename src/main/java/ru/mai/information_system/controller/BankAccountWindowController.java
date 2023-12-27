package ru.mai.information_system.controller;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ru.mai.information_system.App;
import ru.mai.information_system.communication.Communication;
import ru.mai.information_system.communication.Url;
import ru.mai.information_system.dto.*;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static ru.mai.information_system.App.dropCurrentBankAccount;
import static ru.mai.information_system.controller.NewStageOpener.*;

public class BankAccountWindowController {

    private static BankAccountWindowController bankAccountWindowController;

    private List<KeyValuePair> keyValuePairsIncome;
    private List<KeyValuePair> keyValuePairsSpending;

    private final double MAX_AMOUNT = 1_000_000_000;

    @FXML
    private Label accountNameLabel;

    @FXML
    private Button backToAllBankAccountsButton;

    @FXML
    private Label balanceLabel;

    @FXML
    private ChoiceBox<String> incomeTransactionChoiceBox;

    @FXML
    private ChoiceBox<String> incomeTransactionByDateChoiceBox;

    @FXML
    private TextField inputAmountIncomeTransaction;

    @FXML
    private TextField inputAmountIncomeTransactionByDate;

    @FXML
    private TextField inputAmountSpendingTransaction;

    @FXML
    private TextField inputDateIncomeTransactionByDate;

    @FXML
    private ChoiceBox<String> spendingTransactionChoiceBox;

    @FXML
    private VBox transactionsVBox;

    @FXML
    void initialize() {
        bankAccountWindowController = this;

        accountNameLabel.setText(App.getCurrentBankAccount().getName());
        balanceLabel.setText(String.valueOf(App.getCurrentBankAccount().getBalance()));

        List<TransactionCategory> transactionCategories;
        try {
            transactionCategories = TransactionCategory.getTransactionCategoriesList(Communication
                    .sendGetRequest(Url.getTransactionCategoriesUrl() + "/userId/" + App.getCurrentUserId()));

            keyValuePairsIncome = new ArrayList<>();
            keyValuePairsSpending = new ArrayList<>();

            for (int i = 0; i < transactionCategories.size(); i++) {
                TransactionCategory transactionCategory = transactionCategories.get(i);

                if (transactionCategories.get(i).isType()) {
                    keyValuePairsIncome.add(new KeyValuePair((i + 1) + "", transactionCategory.getCategory()));
                    incomeTransactionChoiceBox.getItems().add(transactionCategory.getCategory());
                    incomeTransactionByDateChoiceBox.getItems().add(transactionCategory.getCategory() + " (по дате)");
                } else {
                    keyValuePairsSpending.add(new KeyValuePair((i + 1) + "", transactionCategory.getCategory()));
                    spendingTransactionChoiceBox.getItems().add(transactionCategory.getCategory());
                }
            }

            addLastTransactions(App.getCurrentBankAccount());
        } catch (IOException e) {
            String response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void addTransactionCategory(ActionEvent event) {
        String file = "add-transaction-view.fxml";
        String title = "добавление категории";
        openNewStage(file, title);
    }

    @FXML
    void backToAllBankAccounts(ActionEvent event) {
        dropCurrentBankAccount();
        closeWindow(backToAllBankAccountsButton);

        String file = "main-window-view.fxml";
        openNewStage(file);
    }

    @FXML
    void createReport(ActionEvent event) {
        try {
            BankAccount currentBankAccount = App.getCurrentBankAccount();
            List<Transaction> transactions;
            List<TransactionByDate> transactionsByDate;
            try {
                transactions = Transaction.getTransactionsList(Communication
                        .sendGetRequest(Url.getTransactionsUrl() + "/bankAccountId/"
                        + currentBankAccount.getId()));

                transactionsByDate = TransactionByDate.getTransactionsBydateList(Communication
                        .sendGetRequest(Url.getTransactionsByDateUrl() + "/bankAccountId/"
                        + currentBankAccount.getId()));
            } catch (IOException e) {
                String response = "Ошибка сервера";
                openResponseStage(false, response);
                System.out.println(e.getMessage());
                return;
            }

            String fileName = "report.txt";
            File file = new File(fileName);
            PrintWriter writer = new PrintWriter(file);

            String name = Communication.sendGetRequest(Url.getUsersUrl() + "/nameById/"
                    + App.getCurrentUserId());
            writer.println("Пользователь: " + name);
            writer.println("Счет: " + currentBankAccount.getName().replace("'", ""));
            writer.println("Дата создания счета: " + currentBankAccount.getCreationDate()
                    .replace("-", ".").replace("'", ""));
            writer.println("Баланс: " + currentBankAccount.getBalance() + "\n");

            writer.println("Операции:");
            if (transactions != null) {
                for (int i = transactions.size() - 1; i > -1; i--) {
                    Transaction transaction = transactions.get(i);
                    String sign = getSign(transaction);
                    String category = getCategory(transaction);
                    String transactionText = sign + transaction.getAmount() + " (" + category + ")     "
                            + transaction.getTransactionDate().replace("-", ".");
                    writer.println(transactionText);
                }
            }

            writer.println("\nОперации на определённую дату:");
            if (transactionsByDate != null) {
                for (TransactionByDate transactionByDate : transactionsByDate) {
                    String sign = getSign(transactionByDate);
                    String category = getCategory(transactionByDate);
                    String transactionText = sign + transactionByDate.getAmount() + " (" + category + ")     "
                            + transactionByDate.getTransactionDate().replace("-", ".");
                    writer.println(transactionText);
                }
            }

            String response = "Файл успешно создан";
            openResponseStage(true, response);
            System.out.println("File created successfully");

            writer.close();
        } catch (IOException e) {
            String response = "Файл не найден";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void saveIncomeTransaction(ActionEvent event) {
        String inputAmount = inputAmountIncomeTransaction.getText();
        String category = incomeTransactionChoiceBox.getValue();

        String response;

        if (inputAmount.isEmpty()) {
            response = "Введите сумму";
            openResponseStage(false, response);
            System.out.println("Amount field empty");
            return;
        }

        if (category == null) {
            response = "Выберите категорию";
            openResponseStage(false, response);
            System.out.println("Category choice box empty");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(inputAmount);
        } catch (Exception e) {
            response = "Введите корректную сумму";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return;
        }

        if (amount <= 0 || amount >= MAX_AMOUNT) {
            response = "Сумма должна быть больше 0";
            openResponseStage(false, response);
            System.out.println("Amount should be more than 0");
            return;
        }

        int categoryId = 0;
        try {
            List<TransactionCategory> transactionCategories = TransactionCategory
                    .getTransactionCategoriesList(Communication.sendGetRequest(Url.getTransactionCategoriesUrl()
                            + "/userId/" + App.getCurrentUserId()));

            for (TransactionCategory transactionCategory : transactionCategories) {
                if (transactionCategory.getCategory().equals(category)) {
                    categoryId = transactionCategory.getId();
                    break;
                }
            }

            if (categoryId == 0) {
                response = "Категория не найдена";
                openResponseStage(false, response);
                System.out.println("Transaction category not found");
                return;
            }
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return;
        }

        BankAccount currentBankAccount = App.getCurrentBankAccount();
        Transaction transaction = new Transaction(currentBankAccount.getId(), amount, categoryId);
        try {
            String responseFromServer = Communication.sendPostRequest(Url.getTransactionsUrl(),
                    new Gson().toJson(transaction));
            System.out.println(responseFromServer);
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return;
        }

        currentBankAccount.updateBalance(true, amount);
        balanceLabel.setText(String.valueOf(App.getCurrentBankAccount().getBalance()));
        try {
            BankAccount newBankAccount = new BankAccount(currentBankAccount.getId(), currentBankAccount.getName(),
                    currentBankAccount.getUserId(), currentBankAccount.getBankAccountTypeId(),
                    currentBankAccount.getCreationDate(), currentBankAccount.getBalance());
            String responseFromServer = Communication.sendPutRequest(Url.getBankAccountsUrl(),
                    newBankAccount.toJson());
            System.out.println(responseFromServer);
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return;
        }

        inputAmountIncomeTransaction.clear();
        updateWindow();
    }

    @FXML
    void saveIncomeTransactionByDate(ActionEvent event) {
        String inputAmount = inputAmountIncomeTransactionByDate.getText();
        String date = inputDateIncomeTransactionByDate.getText();
        String category = incomeTransactionByDateChoiceBox.getValue();

        String response;

        if (inputAmount.isEmpty()) {
            response = "Введите сумму";
            openResponseStage(false, response);
            System.out.println("Amount field empty");
            return;
        }

        if (date.isEmpty()) {
            response = "Введите дату";
            openResponseStage(false, response);
            System.out.println("Date field empty");
            return;
        }

        if (category == null) {
            response = "Выберите категорию";
            openResponseStage(false, response);
            System.out.println("Category choice box empty");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(inputAmount);
        } catch (Exception e) {
            response = "Введите корректную сумму";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return;
        }

        if (amount <= 0 || amount >= MAX_AMOUNT) {
            response = "Сумма должна быть больше 0";
            openResponseStage(false, response);
            System.out.println("Amount should be more than 0");
            return;
        }

        LocalDate today = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkDate;
        try {
            checkDate = LocalDate.parse(date, dtf);
        } catch (DateTimeException e) {
            response = "Введите корректную дату по примеру";
            openResponseStage(false, response);
            System.out.println("Incorrect date");
            return;
        }

        if (today.isAfter(checkDate)) {
            response = "Введите корректную дату";
            openResponseStage(false, response);
            System.out.println("Date before now");
            return;
        }

        int categoryId = 0;
        try {
            List<TransactionCategory> transactionCategories = TransactionCategory
                    .getTransactionCategoriesList(Communication.sendGetRequest(Url.getTransactionCategoriesUrl()
                            + "/userId/" + App.getCurrentUserId()));

            for (TransactionCategory transactionCategory : transactionCategories) {
                if (transactionCategory.getCategory().equals(category.substring(0, category.length() - 10))) {
                    categoryId = transactionCategory.getId();
                    break;
                }
            }

            if (categoryId == 0) {
                response = "Категория не найдена";
                openResponseStage(false, response);
                System.out.println("Transaction category not found");
                return;
            }
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return;
        }

        BankAccount currentBankAccount = App.getCurrentBankAccount();
        TransactionByDate transactionByDate = new TransactionByDate(currentBankAccount.getId(),
                amount, categoryId, date);
        try {
            String responseFromServer = Communication.sendPostRequest(Url.getTransactionsByDateUrl(),
                    new Gson().toJson(transactionByDate));
            System.out.println(responseFromServer);
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return;
        }

        inputAmountIncomeTransactionByDate.clear();
        inputDateIncomeTransactionByDate.clear();
        updateWindow();

        response = "Транзакция на " + date.replace("-", ".") + " добавлена";
        openResponseStage(true, response);
    }

    @FXML
    void saveSpendingTransaction(ActionEvent event) {
        String inputAmount = inputAmountSpendingTransaction.getText();
        String category = spendingTransactionChoiceBox.getValue();

        String response;

        if (inputAmount.isEmpty()) {
            response = "Введите сумму";
            openResponseStage(false, response);
            System.out.println("Amount field empty");
            return;
        }

        if (category == null) {
            response = "Выберите категорию";
            openResponseStage(false, response);
            System.out.println("Category choice box empty");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(inputAmount);
        } catch (Exception e) {
            response = "Введите корректную сумму";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return;
        }

        if (amount <= 0 || amount >= MAX_AMOUNT) {
            response = "Сумма должна быть больше 0";
            openResponseStage(false, response);
            System.out.println("Amount should be more than 0");
            return;
        }

        if (App.getCurrentBankAccount().getBalance() - amount < 0) {
            response = "Недостаточно денег на счете";
            openResponseStage(false, response);
            System.out.println("Not enough money");
            return;
        }

        int categoryId = 0;
        try {
            List<TransactionCategory> transactionCategories = TransactionCategory
                    .getTransactionCategoriesList(Communication.sendGetRequest(Url.getTransactionCategoriesUrl()
                            + "/userId/" + App.getCurrentUserId()));

            for (TransactionCategory transactionCategory : transactionCategories) {
                if (transactionCategory.getCategory().equals(category)) {
                    categoryId = transactionCategory.getId();
                    break;
                }
            }

            if (categoryId == 0) {
                response = "Категория не найдена";
                openResponseStage(false, response);
                System.out.println("Transaction category not found");
                return;
            }
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return;
        }

        BankAccount currentBankAccount = App.getCurrentBankAccount();
        Transaction transaction = new Transaction(currentBankAccount.getId(), amount, categoryId);
        try {
            String responseFromServer = Communication.sendPostRequest(Url.getTransactionsUrl(),
                    new Gson().toJson(transaction));
            System.out.println(responseFromServer);
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return;
        }

        currentBankAccount.updateBalance(false, amount);
        balanceLabel.setText(String.valueOf(App.getCurrentBankAccount().getBalance()));
        try {
            BankAccount newBankAccount = new BankAccount(currentBankAccount.getId(), currentBankAccount.getName(),
                    currentBankAccount.getUserId(), currentBankAccount.getBankAccountTypeId(),
                    currentBankAccount.getCreationDate(), currentBankAccount.getBalance());
            String responseFromServer = Communication.sendPutRequest(Url.getBankAccountsUrl(),
                    newBankAccount.toJson());
            System.out.println(responseFromServer);
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return;
        }

        inputAmountSpendingTransaction.clear();
        updateWindow();
    }

    public void addLastTransactions(BankAccount bankAccount) {
        String response;

        try {
            List<Transaction> transactions = Transaction.getTransactionsList(Communication
                    .sendGetRequest(Url.getTransactionsUrl() + "/bankAccountId/" + bankAccount.getId()));

            if (transactions == null) {
                System.out.println("No one transaction");
                return;
            }

            int counter = 0;
            for (int i = transactions.size() - 1; i > -1; i--) {
                addTransaction(transactions.get(i));

                counter++;
                if (counter == 10) {
                    break;
                }
            }
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
        }
    }

    private void addTransaction(Transaction transaction) {
        Label labelAmount = new Label(getSign(transaction) + "" + transaction.getAmount());
        labelAmount.setFont(Font.font("Arial", 16));

        Label labelCategory = new Label(getCategory(transaction));
        labelCategory.setFont(Font.font("Arial", 12));

        VBox vBox = new VBox(labelAmount, labelCategory);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setPrefWidth(240);

        Label labelTransactionDate = new Label(transaction.getTransactionDate());
        labelTransactionDate.setFont(Font.font("Arial", 16));

        HBox hBox = new HBox(vBox, labelTransactionDate);
        hBox.setAlignment(Pos.CENTER_LEFT);

        transactionsVBox.getChildren().add(hBox);
    }

    private String getSign(Transaction transaction) {
        String sign;
        String response;
        try {
            TransactionCategory transactionCategory = TransactionCategory.convertFromString(Communication
                    .sendGetRequest(Url.getTransactionCategoriesUrl() + "/"
                            + transaction.getTransactionCategoryId()));

            boolean type;
            if (transactionCategory != null) {
                type = transactionCategory.isType();
            } else {
                System.out.println("Category not found");
                return null;
            }

            if (type) {
                sign = "+";
            } else {
                sign = "-";
            }
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return null;
        }

        return sign;
    }

    private String getSign(TransactionByDate transactionByDate) {
        String sign;
        String response;
        try {
            TransactionCategory transactionCategory = TransactionCategory.convertFromString(Communication
                    .sendGetRequest(Url.getTransactionCategoriesUrl() + "/"
                            + transactionByDate.getTransactionCategoryId()));

            boolean type;
            if (transactionCategory != null) {
                type = transactionCategory.isType();
            } else {
                System.out.println("Category not found");
                return null;
            }

            if (type) {
                sign = "+";
            } else {
                sign = "-";
            }
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return null;
        }

        return sign;
    }

    private String getCategory(Transaction transaction) {
        TransactionCategory transactionCategory;
        String response;
        try {
            transactionCategory = TransactionCategory.convertFromString(Communication
                    .sendGetRequest(Url.getTransactionCategoriesUrl() + "/" + transaction.getTransactionCategoryId()));
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return null;
        }

        if (transactionCategory != null) {
            return transactionCategory.getCategory();
        }

        return null;
    }

    private String getCategory(TransactionByDate transactionByDate) {
        TransactionCategory transactionCategory;
        String response;
        try {
            transactionCategory = TransactionCategory.convertFromString(Communication
                    .sendGetRequest(Url.getTransactionCategoriesUrl() + "/" + transactionByDate.getTransactionCategoryId()));
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return null;
        }

        if (transactionCategory != null) {
            return transactionCategory.getCategory();
        }

        return null;
    }

    public void updateWindow() {
        clearCategories();
        clearLastTransactions();
        initialize();
    }

    private void clearCategories() {
        incomeTransactionChoiceBox.getItems().clear();
        spendingTransactionChoiceBox.getItems().clear();
        incomeTransactionByDateChoiceBox.getItems().clear();

        keyValuePairsIncome.clear();
        keyValuePairsSpending.clear();
    }

    private void clearLastTransactions() {
        transactionsVBox.getChildren().clear();
    }

    public static BankAccountWindowController getBankAccountWindowController() {
        return bankAccountWindowController;
    }

    public List<KeyValuePair> getKeyValuePairsIncome() {
        return keyValuePairsIncome;
    }

    public List<KeyValuePair> getKeyValuePairsSpending() {
        return keyValuePairsSpending;
    }
}
