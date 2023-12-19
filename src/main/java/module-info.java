module ru.mai.information_system.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens ru.mai.information_system to javafx.fxml;
    exports ru.mai.information_system;
    exports ru.mai.information_system.controller;
    opens ru.mai.information_system.controller to javafx.fxml;
    exports ru.mai.information_system.communication;
    opens ru.mai.information_system.communication to javafx.fxml;
    exports ru.mai.information_system.dto;
    opens ru.mai.information_system.dto to com.google.gson;
}
