module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.junit.jupiter.api;
    requires junit;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.logic;
    opens com.example.demo.logic to javafx.fxml;
    exports com.example.demo.controllers;
    opens com.example.demo.controllers to javafx.fxml;
    exports com.example.demo.tests;
    opens com.example.demo.tests to javafx.fxml;
}