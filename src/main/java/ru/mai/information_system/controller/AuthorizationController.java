package ru.mai.information_system.controller;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.mai.information_system.App;
import ru.mai.information_system.communication.Communication;
import ru.mai.information_system.communication.Url;
import ru.mai.information_system.dto.TransactionCategory;
import ru.mai.information_system.dto.User;

import java.io.IOException;

import static ru.mai.information_system.controller.NewStageOpener.closeWindow;
import static ru.mai.information_system.controller.NewStageOpener.openNewStage;

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

        if (email.isEmpty()) {
            System.out.println("Field email empty");
            return;
        } else if (password.isEmpty()) {
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

            App.setCurrentUser(user);

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

        if (name.isEmpty()) {
            System.out.println("Field name empty");
            return;
        } else if (email.isEmpty()) {
            System.out.println("Field email empty");
            return;
        } else if (password.isEmpty()) {
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

            App.setCurrentUser(User.convertFromString(Communication.sendGetRequest(Url.getUsersUrl() + "/email/"
                    + newUser.getEmail())));

            TransactionCategory transactionCategory1 = new TransactionCategory(App.getCurrentUser().getId(),
                    true, "Зарплата");
            String response1 = Communication.sendPostRequest(Url.getTransactionCategoriesUrl(),
                    new Gson().toJson(transactionCategory1));
            System.out.println(response1);

            TransactionCategory transactionCategory2 = new TransactionCategory(App.getCurrentUser().getId(),
                    false, "Продукты");
            String response2 = Communication.sendPostRequest(Url.getTransactionCategoriesUrl(),
                    new Gson().toJson(transactionCategory2));
            System.out.println(response2);

            TransactionCategory transactionCategory3 = new TransactionCategory(App.getCurrentUser().getId(),
                    false, "Развлечения");
            String response3 = Communication.sendPostRequest(Url.getTransactionCategoriesUrl(),
                    new Gson().toJson(transactionCategory3));
            System.out.println(response3);

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
}
