package ru.mai.information_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import ru.mai.information_system.App;
import ru.mai.information_system.communication.Communication;
import ru.mai.information_system.communication.Url;
import ru.mai.information_system.dto.BankAccount;

import java.io.IOException;
import java.util.List;

import static ru.mai.information_system.App.dropCurrentUser;
import static ru.mai.information_system.controller.NewStageOpener.closeWindow;
import static ru.mai.information_system.controller.NewStageOpener.openNewStage;

public class MainWindowController {

    private static MainWindowController mainWindowController;

    @FXML
    private Button addBankAccountButton;

    @FXML
    private Pane mainWindowPane;

    @FXML
    private Button quitButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button supportButton;

    @FXML
    private VBox vBoxBankAccountsField;

    @FXML
    void addBankAccount(ActionEvent event) {
        String file = "create-bank-account-view.fxml";
        String title = "Создание нового счета";
        openNewStage(file, title);
    }

    @FXML
    void quit(ActionEvent event) {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();

        dropCurrentUser();

        String file = "authorization-window-view.fxml";
        String title = "Авторизация";
        openNewStage(file, title);
    }

    @FXML
    void writeSupportMessage(ActionEvent event) {
        String file = "support-view.fxml";
        String title = "Поддержка";
        openNewStage(file, title);
    }

    @FXML
    void initialize() {
        mainWindowController = this;

        String response;
        try {
            response = Communication.sendGetRequest(Url.getBankAccountsUrl());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (response.length() == 2) {
            return;
        }

        try {
            List<BankAccount> bankAccounts = BankAccount.getBankAccounts(Communication
                    .sendGetRequest(Url.getBankAccountsUrl()));

            for (BankAccount bankAccount : bankAccounts) {
                addBankAccountOnWindow(bankAccount, addBankAccountButton);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addBankAccountOnWindow(BankAccount bankAccount, Button button) {
        Label labelName = new Label("Счет: ");
        labelName.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Hyperlink hyperlinkName = new Hyperlink(bankAccount.getName());
        hyperlinkName.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        hyperlinkName.setOnAction(actionEvent -> {
            closeWindow(button);
            App.setCurrentBankAccount(bankAccount);
            String newFile = "bank-account-window-view.fxml";
            String newTitle = "счет " + hyperlinkName.toString();
            openNewStage(newFile, newTitle);
        });

        HBox hBox = new HBox(labelName, hyperlinkName);
        hBox.setAlignment(Pos.CENTER_LEFT);

        Label labelBalance = new Label(String.valueOf(bankAccount.getBalance()));
        labelBalance.setFont(Font.font("Arial", 14));

        VBox vBox = new VBox(hBox, labelBalance);
        vBox.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px; -fx-spacing: 5px;");

        vBoxBankAccountsField.getChildren().add(vBox);
    }

    public VBox getvBoxBankAccountsField() {
        return vBoxBankAccountsField;
    }

    public static MainWindowController getMainWindowController() {
        return mainWindowController;
    }
}
