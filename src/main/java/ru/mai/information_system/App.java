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

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Scene scene;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("authorization-window-view.fxml"));
            scene = new Scene(fxmlLoader.load());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

            Label label = new Label("Fatal error!");

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
        stage.setTitle("Система учета личных финансов");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}