package ru.mai.information_system.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ru.mai.information_system.App;

import java.io.IOException;

public class NewStageOpener {

    public static void openNewStage(String fxmlFile, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFile));
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Система учета личных финансов: " + title);
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void openNewStage(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFile));
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Система учета личных финансов");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.hide();
    }
}
