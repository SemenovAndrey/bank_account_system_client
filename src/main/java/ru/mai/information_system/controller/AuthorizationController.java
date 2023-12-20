package ru.mai.information_system.controller;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.mai.information_system.App;
import ru.mai.information_system.communication.Communication;
import ru.mai.information_system.communication.Url;
import ru.mai.information_system.dto.User;

import java.io.IOException;

public class AuthorizationController {

    @FXML
    private Button buttonEnter;

    @FXML
    private Button buttonRegistration;

    @FXML
    private TextField emailInputAuthorization;

    @FXML
    private TextField emailInputRegistration;

    @FXML
    private TextField nameInputRegistration;

    @FXML
    private PasswordField passwordInputAuthorization;

    @FXML
    private PasswordField passwordInputRegistration;

    @FXML
    private Button supportButtonAuth;

    @FXML
    private Button supportButtonReg;

    @FXML
    private Tab tabAuthorization;

    @FXML
    private Tab tabRegistration;

    @FXML
    void enter(ActionEvent event) {
        String email = emailInputAuthorization.getText();
        String password = passwordInputAuthorization.getText();

        if (email.equals("")) {
            System.out.println("Field email empty");
            return;
        } else if (password.equals("")) {
            System.out.println("Field password empty");
            return;
        }

        try {
            User user = User.convertFromString(Communication
                    .sendGetRequest(Url.getUsersUrl() + "/email/" + email));

            if (user == null) {
                System.out.println("User not found");
                return;
            }

            if (!user.getEmail().equals(email)) {
                System.out.println("User not found");
                return;
            }

            if (!user.getPassword().equals(password)) {
                System.out.println("Incorrect password");
                return;
            }

            closeWindow(buttonEnter);
            String file = "main-window-view.fxml";
            openNewStage(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void register(ActionEvent event) {
        String name = nameInputRegistration.getText();
        String email = emailInputRegistration.getText();
        String password = passwordInputRegistration.getText();

        if (name.equals("")) {
            System.out.println("Field name empty");
            return;
        } else if (email.equals("")) {
            System.out.println("Field email empty");
            return;
        } else if (password.equals("")) {
            System.out.println("Field password empty");
            return;
        }

        try {
            User user = User.convertFromString(Communication
                    .sendGetRequest(Url.getUsersUrl() + "/email/" + email));

            if (user != null) {
                System.out.println("User with this email already created");
                return;
            }

            User newUser = new User(name, email, password);
            String response = Communication.sendPostRequest(Url.getUsersUrl(), new Gson().toJson(newUser));
            System.out.println(response);

            closeWindow(buttonRegistration);
            String file = "main-window-view.fxml";
            openNewStage(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void writeSupportMessage(ActionEvent event) {
        String file = "support-view.fxml";
        String title = "Поддержка";
        openNewStage(file, title);
    }

    private void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.hide();
    }

    private void openNewStage(String fxmlFile, String title) {
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

    private void openNewStage(String fxmlFile) {
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
}
