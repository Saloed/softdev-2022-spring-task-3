package javafx;

import controller.GameListener;
import core.Logic;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

import static javafx.Menu.ui.createRadioButton;

public class ChooseGameModeScene {
    private boolean speedChange;
    private boolean stageChange;
    private final Stage stage;

    public ChooseGameModeScene() {
        AnchorPane pane = new AnchorPane();
        int width = 300;
        int height = 300;
        Scene scene = new Scene(pane, width, height);
        this.stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ToggleGroup speedGroup = new ToggleGroup();
        ToggleGroup stageGroup = new ToggleGroup();
        RadioButton speedChangeFalse = createRadioButton("No", 50, 70);
        RadioButton speedChangeTrue = createRadioButton("Yes", 200, 70);
        RadioButton stageChangeFalse = createRadioButton("No", 50, 200);
        RadioButton stageChangeTrue = createRadioButton("Yes", 200, 200);
        speedChangeFalse.isPressed();
        speedChangeFalse.setToggleGroup(speedGroup);
        speedChangeTrue.setToggleGroup(speedGroup);
        stageChangeFalse.setToggleGroup(stageGroup);
        stageChangeTrue.setToggleGroup(stageGroup);
        speedChangeFalse.setSelected(true);
        stageChangeFalse.setSelected(true);
        Button start = Menu.ui.createButton("Start game!", 55, 245);
        start.setOnAction(event -> {
            stage.close();
            ViewManager viewManager = new ViewManager();
            Logic logic = new Logic(speedChange, stageChange);
            GameListener listener = new GameListener(viewManager, logic);
            logic.assignListener(listener);
            viewManager.assignListener(listener);
        });
        Label speedLabel1 = Menu.ui.createLabel("Do you want to scale the square speed", 3, 15);
        Label speedLabel2 = Menu.ui.createLabel("with combo?", 105, 30);
        Label stageLabel = Menu.ui.createLabel("Do you want to turn on stage switch?", 10, 160);
        pane.getChildren().addAll(speedChangeFalse, speedChangeTrue, stageChangeFalse, stageChangeTrue, start, speedLabel1, speedLabel2, stageLabel);
        speedGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton rb = (RadioButton) speedGroup.getSelectedToggle();
            if (rb != null) {
                if (Objects.equals(rb.getText(), "No")) speedChange = false;
                if (Objects.equals(rb.getText(), "Yes")) speedChange = true;
            }
        });
        stageGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton rb = (RadioButton) stageGroup.getSelectedToggle();
            if (rb != null) {
                if (Objects.equals(rb.getText(), "No")) stageChange = false;
                if (Objects.equals(rb.getText(), "Yes")) stageChange = true;
            }
        });
    }
}
