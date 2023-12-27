package ru.mai.information_system.controller;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.mai.information_system.communication.Communication;
import ru.mai.information_system.communication.Url;
import ru.mai.information_system.dto.BankAccount;
import ru.mai.information_system.dto.BankAccountTypes;

import java.io.IOException;

import static ru.mai.information_system.App.getCurrentUser;
import static ru.mai.information_system.controller.NewStageOpener.closeWindow;
import static ru.mai.information_system.controller.NewStageOpener.openResponseStage;

public class BankAccountCreatorController {

    private MainWindowController mainWindowController;
    private KeyValuePair[] keyValuePairs;

    @FXML
    private TextField bankAccountNameInput;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private Button createBankAccountButton;

    public void initialize() {
        mainWindowController = MainWindowController.getMainWindowController();

        try {
            String[] bankAccountTypes = BankAccountTypes.getBankAccountTypes(Communication
                    .sendGetRequest(Url.getBankAccountTypesUrl()));
            keyValuePairs = new KeyValuePair[bankAccountTypes.length];

            for (int i = 0; i < bankAccountTypes.length; i++) {
                categoryChoiceBox.getItems().add(bankAccountTypes[i]);
                keyValuePairs[i] = new KeyValuePair(((i + 1) + ""), bankAccountTypes[i]);
            }
        } catch (IOException e) {
            String response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void createBankAccount(ActionEvent event) {
        String bankAccountName = bankAccountNameInput.getText();
        String bankAccountCategory = categoryChoiceBox.getValue();

        String response;
        if (bankAccountName.isEmpty()) {
            response = "Введите название счета";
            openResponseStage(false, response);
            System.out.println("Bank account name field empty");
            return;
        } else if (bankAccountCategory == null) {
            response = "Выберите категорию счета";
            openResponseStage(false, response);
            System.out.println("Bank account type field empty");
            return;
        }

        if (bankAccountName.length() > 40) {
            response = "Название счета слишком длинное";
            openResponseStage(false, response);
            System.out.println("Bank account name too long");
            return;
        }

        int bankAccountCategoryId = 0;
        for (KeyValuePair keyValuePair : keyValuePairs) {
            if (keyValuePair.getValue().equals(bankAccountCategory)) {
                bankAccountCategoryId = Integer.parseInt(keyValuePair.getKey());
                break;
            }
        }

        BankAccount bankAccount = new BankAccount(bankAccountName, getCurrentUser().getId(), bankAccountCategoryId);
        try {
            String responseFromServer = Communication.sendPostRequest(Url.getBankAccountsUrl(),
                    new Gson().toJson(bankAccount));
            System.out.println(responseFromServer);

            if (responseFromServer.equals("Bank account with this name already created")) {
                response = "Введенное название уже используется";
                openResponseStage(false, response);
                return;
            }
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return;
        }

        closeWindow(createBankAccountButton);
        mainWindowController.clearBankAccounts();
        mainWindowController.initialize();
    }
}
