package launcher;

import javafx.Menu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();
        Menu.createMenu(root, stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
