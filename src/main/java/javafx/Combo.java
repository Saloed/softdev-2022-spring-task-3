package javafx;

import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Combo {
    private int size;
    private SubScene scene;
    private Label counter;
    private final ViewManager viewManager;

    public Combo(ViewManager viewManager) {
        size = 0;
        this.viewManager = viewManager;
    }

    public void increase() {
        size += 1;
    }

    public void nullify() {
        size = 0;
    }

    public int getCombo() {
        return size;
    }

    public Label getText() {
        return counter;
    }

    public void activateCombo(AnchorPane gamePane) {
        scene = new SubScene(new AnchorPane(), 100, 50);
        scene.prefHeight(100);
        scene.prefWidth(50);
        scene.setLayoutX((viewManager.getPane().getWidth() - 100) / 2);
        scene.getRoot().setLayoutX((viewManager.getPane().getWidth() - 100) / 2);
        counter = Menu.ui.createLabel("0   " + size + "X",(viewManager.getPane().getWidth() - 100) / 2 + 30, 0 );
        counter.setPrefSize(100, 50);
        counter.setText("0   " + size + "X");
        counter.setWrapText(true);
        counter.setLayoutX((viewManager.getPane().getWidth() - 100) / 2 + 30);
        scene.setFill(Color.AQUAMARINE);
        gamePane.getChildren().add(scene);
        gamePane.getChildren().add(counter);
    }
}
