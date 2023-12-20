package ru.mai.information_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static ru.mai.information_system.controller.NewStageOpener.openNewStage;

public class MainWindowController {

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
    void addBankAccount(ActionEvent event) {
        String file = "create-bank-account-view.fxml";
        String title = "Создание нового счета";
        openNewStage(file, title);
    }

    @FXML
    void quit(ActionEvent event) {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();

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
}
