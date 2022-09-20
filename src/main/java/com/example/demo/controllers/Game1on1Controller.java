package com.example.demo.controllers;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import com.example.demo.logic.Matrix;
import com.example.demo.logic.WinLine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class Game1on1Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label firstPlayer;

    @FXML
    private Label playerTurn;

    @FXML
    private Label secondPlayer;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label info;

    @FXML
    void initialize() {
        int rows = 15;
        int columns = 15;
        String[][] data = new String[rows][columns];
        Matrix matrix = new Matrix(15, 15, data);
        for (int row = 0; row < 15; row++) {
            for (int column = 0; column < 15; column++) {
                WinLine winLine = new WinLine(matrix);
                Button button = new Button();
                button.setMaxSize(24, 24);
                button.setMinSize(24, 24);
                gridPane.add(button, row, column);
                int finalRow = row;
                int finalColumn = column;
                button.setOnAction(event -> {
                    if (Objects.equals(playerTurn.getText(), "Ход первого игрока")) {
                        if ((matrix.getElement(finalRow, finalColumn) == null)) {
                            matrix.setElement(finalRow, finalColumn, "red");
                            button.setStyle("-fx-background-color: red");
                            playerTurn.setText("Ход второго игрока");
                        } else
                            info.setText("Поле занято");
                        if (winLine.redWin(matrix, finalRow, finalColumn) == "Победил красный") {
                            info.setText("Красный");

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Выйти?");
                            alert.setTitle("Выйти?");
                            alert.setHeaderText("Победил красный");
                            ButtonType buttonType = new ButtonType("Выйти", ButtonBar.ButtonData.CANCEL_CLOSE);
                            alert.getButtonTypes().setAll(buttonType);
                            Optional<ButtonType> result = alert.showAndWait();
                            Platform.exit();
                        }

                    } else {
                        if (matrix.getElement(finalRow, finalColumn) == null) {
                            matrix.setElement(finalRow, finalColumn, "green");
                            button.setStyle("-fx-background-color: green");
                            playerTurn.setText("Ход первого игрока");
                        } else info.setText("Поле занято");
                        if (winLine.greenWin(matrix, finalRow, finalColumn) == "Победил зелёный") {
                            info.setText("Зелёный");

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Выйти?");
                            alert.setTitle("Выйти?");
                            alert.setHeaderText("Победил зелёный");
                            ButtonType buttonType = new ButtonType("Выйти", ButtonBar.ButtonData.CANCEL_CLOSE);
                            alert.getButtonTypes().setAll(buttonType);
                            Optional<ButtonType> result = alert.showAndWait();
                            Platform.exit();
                        }
                    }
                });
            }
        }
    }
}