package ru.mai.information_system.controller;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.mai.information_system.communication.Communication;
import ru.mai.information_system.communication.Url;
import ru.mai.information_system.dto.Support;

import java.io.IOException;

import static ru.mai.information_system.controller.NewStageOpener.closeWindow;
import static ru.mai.information_system.controller.NewStageOpener.openResponseStage;

public class SupportController {

    @FXML
    private Button buttonSendMessage;

    @FXML
    private TextField emailInput;

    @FXML
    private TextArea messageInput;

    @FXML
    public void sendMessage(ActionEvent event) {
        String email = emailInput.getText();
        String message = messageInput.getText();

        String response;

        if (email.isEmpty()) {
            response = "Почта не указана";
            openResponseStage(false, response);
            System.out.println("Field email empty");
            return;
        }

        if (message.isEmpty()) {
            response = "Сообщение не указано";
            openResponseStage(false, response);
            System.out.println("Field message empty");
            return;
        }

        if (email.length() > 50) {
            response = "Поле почты слишком длинное";
            openResponseStage(false, response);
            System.out.println("Field email too long");
            return;
        }

        if (message.length() > 150) {
            response = "Сообщение слишком длинное";
            openResponseStage(false, response);
            System.out.println("Field message too long");
            return;
        }

        Support support = new Support(email, message);
        try {
            String responseFromServer = Communication.sendPostRequest(Url.getSupportUrl(), new Gson().toJson(support));
            System.out.println(responseFromServer);
        } catch (IOException e) {
            response = "Ошибка сервера";
            openResponseStage(false, response);
            System.out.println(e.getMessage());
            return;
        }

        closeWindow(buttonSendMessage);
    }
}
