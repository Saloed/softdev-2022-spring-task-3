package javafx;

import core.Logic;
import javafx.Menu.ui;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameEndScene {
    AnchorPane pane;
    Scene scene;
    Stage stage;

    public GameEndScene(Logic logic) {
        this.pane = new AnchorPane();
        int width = 300;
        int height = 300;
        this.scene = new Scene(pane, width, height);
        this.stage = new Stage();
        stage.setScene(scene);
        stage.show();
        pane.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(0), new Insets(0))));
        Label results = Menu.ui.createLabel("" + logic.getScore(), 140, 130);
        results.setText("" + logic.getScore());
        Button exit = ui.createButton("Exit", 55, 200);
        exit.setOnAction(event -> logic.close());
        pane.getChildren().add(results);
        pane.getChildren().add(exit);
    }
}
