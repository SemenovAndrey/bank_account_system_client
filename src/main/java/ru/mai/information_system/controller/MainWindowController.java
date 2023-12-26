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
import static ru.mai.information_system.controller.NewStageOpener.*;

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
            response = Communication
                    .sendGetRequest(Url.getBankAccountsUrl() + "/userId/" + App.getCurrentUser().getId());
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return;
        }

        if (response.length() == 2) {
            return;
        }

        List<BankAccount> bankAccounts = BankAccount.getBankAccounts(response);
        if (bankAccounts != null) {
            for (BankAccount bankAccount : bankAccounts) {
                addBankAccountOnWindow(bankAccount, addBankAccountButton);
            }
        }
    }

    public void addBankAccountOnWindow(BankAccount bankAccount, Button button) {
        Label labelName = new Label("Счет: ");
        labelName.setPrefWidth(80);
        labelName.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Hyperlink hyperlinkName = new Hyperlink(bankAccount.getName());
        hyperlinkName.setPrefWidth(400);
        hyperlinkName.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        hyperlinkName.setOnAction(actionEvent -> {
            closeWindow(button);
            App.setCurrentBankAccount(bankAccount);
            String newFile = "bank-account-window-view.fxml";
            String newTitle = "счет '" + hyperlinkName.getText() + "'";
            openNewStage(newFile, newTitle);
        });

        HBox hBox1 = new HBox(labelName, hyperlinkName);
        hBox1.setAlignment(Pos.CENTER_LEFT);

        Button buttonDelete = new Button("x");
        buttonDelete.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        buttonDelete.setOnAction(actionEvent -> {
            String response;
            try {
                response = Communication.sendDeleteRequest(Url.getBankAccountsUrl() + "/" + bankAccount.getId());
                System.out.println(response);
            } catch (IOException e) {
                response = "Ошибка сервера";
                openResponseStage(false, response);
                System.out.println(e.getMessage());
                return;
            }

            clearBankAccounts();
            try {
                List<BankAccount> bankAccounts = BankAccount.getBankAccounts(Communication
                        .sendGetRequest(Url.getBankAccountsUrl() + "/userId/" + App.getCurrentUser().getId()));

                if (bankAccounts == null) {
                    return;
                }

                for (BankAccount bankAccountForAdd : bankAccounts) {
                    addBankAccountOnWindow(bankAccountForAdd, addBankAccountButton);
                }
            } catch (IOException e) {
                response = "Ошибка сервера";
                openResponseStage(false, response);
                System.out.println(e.getMessage());
            }
        });

        HBox hBox = new HBox(hBox1, buttonDelete);
        hBox.setAlignment(Pos.CENTER_LEFT);

        Label labelBalance = new Label(String.valueOf(bankAccount.getBalance()));
        labelBalance.setFont(Font.font("Arial", 14));

        VBox vBox = new VBox(hBox, labelBalance);
        vBox.setStyle("-fx-padding: 10px; -fx-spacing: 5px;");

        vBoxBankAccountsField.getChildren().add(vBox);
    }

    public void clearBankAccounts() {
        vBoxBankAccountsField.getChildren().clear();
    }

    public static MainWindowController getMainWindowController() {
        return mainWindowController;
    }
}
