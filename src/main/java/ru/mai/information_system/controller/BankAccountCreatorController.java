package ru.mai.information_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import ru.mai.information_system.communication.Communication;
import ru.mai.information_system.communication.Url;
import ru.mai.information_system.dto.BankAccountTypes;

import java.io.IOException;

import static ru.mai.information_system.controller.NewStageOpener.closeWindow;

public class BankAccountCreatorController {

    private KeyValuePair[] keyValuePairs;

    @FXML
    private TextField bankAccountNameInput;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private Button createBankAccountButton;

    public void initialize() {
        try {
            String[] bankAccountTypes = BankAccountTypes.getBankAccountTypes(Communication
                    .sendGetRequest(Url.getBankAccountTypesUrl()));
            keyValuePairs = new KeyValuePair[bankAccountTypes.length];

            for (int i = 0; i < bankAccountTypes.length; i++) {
                categoryChoiceBox.getItems().add(bankAccountTypes[i]);
                keyValuePairs[i] = new KeyValuePair(((i + 1) + ""), bankAccountTypes[i]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void createBankAccount(ActionEvent event) {
        String bankAccountName = bankAccountNameInput.getText();
        String bankAccountCategory = categoryChoiceBox.getValue();

        if (bankAccountName.isEmpty()) {
            System.out.println("Bank account name field empty");
            return;
        }

        if (bankAccountCategory.isEmpty()) {
            System.out.println("Bank account type field empty");
            return;
        }

        int bankAccountCategoryId = 0;
        for (KeyValuePair keyValuePair : keyValuePairs) {
            if (keyValuePair.getValue().equals(bankAccountCategory)) {
                bankAccountCategoryId = Integer.parseInt(keyValuePair.getKey());
                break;
            }
        }

//        BankAccount bankAccount = new BankAccount(bankAccountName, userId, bankAccountCategoryId);
//        try {
//            String strBankAccount = Communication.sendPostRequest(Url.getBankAccountsUrl(), new Gson().toJson(bankAccount));
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }

        closeWindow(createBankAccountButton);
        // возможно открывать и закрывать основное окно, чтобы всё подгрузилось из бд
    }
}

class KeyValuePair {

    private String key;
    private String value;

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
