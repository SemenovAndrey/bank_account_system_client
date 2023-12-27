package ru.mai.information_system.controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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

    public static void openResponseStage(boolean type, String response) {
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Результат");

        Label label = new Label(response);
        if (type) {
            label.setStyle("-fx-text-fill: green");
        } else {
            label.setStyle("-fx-text-fill: red");
        }

        Button button = new Button("OK");
        button.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });

        VBox vBox = new VBox(label, button);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }

    public static void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.hide();
    }
}
