package javafx;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Menu {
    public static class ui {
        private static final String buttonPressedStyle = "-fx-background-color: transparent; -fx-background-image: url(file:src/main/java/resources/PressedButton.png);";
        private static final String buttonStyle = "-fx-background-color: transparent; -fx-background-image: url(file:src/main/java/resources/Button.png);";
        private static final String fontPath = "src/main/java/resources/kenvector_future.ttf";

        public static Button createButton(String text, double x, double y) {
            Button button = new Button(text);
            button.setLayoutX(x);
            button.setLayoutY(y);
            button.setMinSize(190, 49);
            button.setFocusTraversable(false);
            button.setStyle(buttonStyle);
            try {
                button.setFont(Font.loadFont(new FileInputStream(fontPath), 15));
            } catch (FileNotFoundException e) {
                button.setFont(Font.font("Verdana", 15));
            }
            button.setOnMousePressed(event -> {
                button.setStyle(buttonPressedStyle);
                button.setPrefHeight(45);
                button.setMinHeight(45);
                button.setLayoutY(button.getLayoutY() + 4);
            });
            button.setOnMouseReleased(event -> {
                button.setStyle(buttonStyle);
                button.setPrefHeight(49);
                button.setLayoutY(button.getLayoutY() - 4);
            });
            button.setOnMouseEntered(event -> button.setEffect(new DropShadow()));
            button.setOnMouseExited(event -> button.setEffect(null));
            return button;
        }

        public static Label createLabel(String text, double x, double y) {
            Label label = new Label();
            label.setLayoutX(x);
            label.setLayoutY(y);
            label.setText(text);
            try {
                label.setFont(Font.loadFont(new FileInputStream(fontPath), 11));
            } catch (FileNotFoundException e) {
                label.setFont(Font.font("Verdana", 8));
            }
            return label;
        }

        public static RadioButton createRadioButton(String text, double x, double y) {
            RadioButton button = new RadioButton(text);
            button.setLayoutX(x);
            button.setLayoutY(y);
            try {
                button.setFont(Font.loadFont(new FileInputStream(fontPath), 11));
            } catch (FileNotFoundException e) {
                button.setFont(Font.font("Verdana", 8));
            }
            return button;
        }
    }


    public static void createMenu(AnchorPane root, Stage stage) {
        Button start = ui.createButton("Start", 110, 70);
        Button rules = ui.createButton("Rules", 110, 170);
        Button exit = ui.createButton("Exit", 110, 270);
        exit.setOnAction(event -> {
            stage.close();
            System.exit(0);
        });
        start.setOnAction(event -> {
            stage.close();
            ChooseGameModeScene scene = new ChooseGameModeScene();
        });
        root.getChildren().add(start);
        root.getChildren().add(rules);
        root.getChildren().add(exit);
    }
}
