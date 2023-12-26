package ru.mai.information_system.controller;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.mai.information_system.App;
import ru.mai.information_system.communication.Communication;
import ru.mai.information_system.communication.Url;
import ru.mai.information_system.dto.TransactionCategory;
import ru.mai.information_system.dto.User;

import java.io.IOException;

import static ru.mai.information_system.controller.NewStageOpener.*;

public class AuthorizationController {

    @FXML
    private TextField nameInputRegistration;

    @FXML
    private TextField emailInputAuthorization;

    @FXML
    private TextField emailInputRegistration;

    @FXML
    private PasswordField passwordInputAuthorization;

    @FXML
    private PasswordField passwordInputRegistration;

    @FXML
    private Button buttonEnter;

    @FXML
    private Button buttonRegistration;

    @FXML
    private Button supportButtonAuth;

    @FXML
    private Button supportButtonReg;

    @FXML
    void enter(ActionEvent event) {

        String email = emailInputAuthorization.getText().trim();
        String password = passwordInputAuthorization.getText();

        String response;

        if (email.isEmpty()) {
            response = "Почта не указана";
            openResponseStage(false, response);
            System.out.println("Field email empty");
            return;
        } else if (password.isEmpty()) {
            response = "Пароль не указан";
            openResponseStage(false, response);
            System.out.println("Field password empty");
            return;
        }

        try {
            User user = User.convertFromString(Communication
                    .sendGetRequest(Url.getUsersUrl() + "/email/" + email));

            if (user == null) {
                response = "Пользователь не найден";
                openResponseStage(false, response);
                System.out.println("User not found");
                return;
            }

            if (!user.getPassword().equals(password)) {
                response = "Неверный пароль";
                openResponseStage(false, response);
                System.out.println("Incorrect password");
                return;
            }

            App.setCurrentUser(user);

            closeWindow(buttonEnter);
            String file = "main-window-view.fxml";
            openNewStage(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response = "Ошибка сервера";
            openResponseStage(false, response);
        }
    }

    @FXML
    void register(ActionEvent event) {
        String name = nameInputRegistration.getText().trim();
        String email = emailInputRegistration.getText().trim();
        String password = passwordInputRegistration.getText();

        String response;

        if (name.isEmpty()) {
            response = "Имя пользователя не указано";
            openResponseStage(false, response);
            System.out.println("Field name empty");
            return;
        } else if (email.isEmpty()) {
            response = "Почта не указана";
            openResponseStage(false, response);
            System.out.println("Field email empty");
            return;
        } else if (password.isEmpty()) {
            response = "Пароль не указана";
            openResponseStage(false, response);
            System.out.println("Field password empty");
            return;
        }

        if (name.length() > 50) {
            response = "Максимальная длина имени 50";
            openResponseStage(false, response);
            System.out.println("Field name is too long");
            return;
        }

        if (email.length() > 50) {
            response = "Максимальная длина почты 50";
            openResponseStage(false, response);
            System.out.println("Field email is too long");
            return;
        }

        if (password.length() > 30) {
            response = "Максимальная длина пароля 50";
            openResponseStage(false, response);
            System.out.println("Field password too long");
            return;
        }

        int index = email.indexOf("@");
        if (index == -1) {
            response = "При вводе почты должен быть символ @";
            openResponseStage(false, response);
            System.out.println("Incorrect email (@)");
            return;
        }

        int spaceIndex = email.indexOf(" ");
        if (spaceIndex != -1) {
            response = "При вводе почты не должно быть пробелов";
            openResponseStage(false, response);
            System.out.println("Incorrect email ( )");
            return;
        }

        if (!email.endsWith(".ru") && !email.endsWith(".com")) {
            response = "Почта должна оканчиваться на .ru или .com";
            openResponseStage(false, response);
            System.out.println("Incorrect email (ru/com)");
            return;
        }

        try {
            User user = User.convertFromString(Communication
                    .sendGetRequest(Url.getUsersUrl() + "/email/" + email));

            if (user != null) {
                response = "Введённая почта уже используется";
                openResponseStage(false, response);
                System.out.println("User with this email already created");
                return;
            }

            User newUser = new User(name, email, password);
            String responseFromServer = Communication.sendPostRequest(Url.getUsersUrl(), new Gson().toJson(newUser));
            System.out.println(responseFromServer);

            App.setCurrentUser(User.convertFromString(Communication
                    .sendGetRequest(Url.getUsersUrl() + "/email/" + email)));

            User currentUser = App.getCurrentUser();
            TransactionCategory transactionCategory1 = new TransactionCategory(currentUser.getId(),
                    true, "Зарплата");
            String response1 = Communication.sendPostRequest(Url.getTransactionCategoriesUrl(),
                    new Gson().toJson(transactionCategory1));
            System.out.println(response1);

            TransactionCategory transactionCategory2 = new TransactionCategory(currentUser.getId(),
                    false, "Продукты");
            String response2 = Communication.sendPostRequest(Url.getTransactionCategoriesUrl(),
                    new Gson().toJson(transactionCategory2));
            System.out.println(response2);

            TransactionCategory transactionCategory3 = new TransactionCategory(currentUser.getId(),
                    false, "Развлечения");
            String response3 = Communication.sendPostRequest(Url.getTransactionCategoriesUrl(),
                    new Gson().toJson(transactionCategory3));
            System.out.println(response3);

            closeWindow(buttonRegistration);
            String file = "main-window-view.fxml";
            openNewStage(file);
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
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
