package ru.mai.information_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.mai.information_system.dto.BankAccount;

import java.io.IOException;

public class App extends Application {

    private static int currentUserId = 0;
    private static BankAccount currentBankAccount = null;

    @Override
    public void start(Stage stage) {
        Scene scene;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("authorization-window-view.fxml"));
            scene = new Scene(fxmlLoader.load());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());

            Label label = new Label("Error!");

            Button button = new Button("OK");
            button.setOnMouseClicked(mouseEvent -> {
                stage.close();
            });

            VBox vBox = new VBox(label, button);
            vBox.setSpacing(10);
            vBox.setPadding(new Insets(20));
            vBox.setAlignment(Pos.CENTER);

            scene = new Scene(vBox);
        }

        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Система учета личных финансов: Авторизация");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setCurrentUserId(int id) {
        currentUserId = id;
    }

    public static void setCurrentBankAccount(BankAccount bankAccount) {
        currentBankAccount = bankAccount;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static BankAccount getCurrentBankAccount() {
        return currentBankAccount;
    }

    public static void dropCurrentUserId() {
        currentUserId = 0;
    }

    public static void dropCurrentBankAccount() {
        currentBankAccount = null;
    }
}