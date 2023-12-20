package ru.mai.information_system.controller;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.mai.information_system.App;
import ru.mai.information_system.communication.Communication;
import ru.mai.information_system.communication.Url;
import ru.mai.information_system.dto.Support;

import java.io.IOException;

import static ru.mai.information_system.controller.NewStageOpener.closeWindow;

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

        if (email.isEmpty()) {
            System.out.println("Email field empty");
            return;
        } else if (message.isEmpty()) {
            System.out.println("Message field empty");
            return;
        }

        Support support = new Support(email, message);
        try {
            String response = Communication.sendPostRequest(Url.getSupportUrl(), new Gson().toJson(support));
            System.out.println(response);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        closeWindow(buttonSendMessage);
    }
}
