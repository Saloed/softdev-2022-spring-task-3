package graph;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.Logic;

public class Game {

    public static final int STAGE_SIZE = 512;

    private final Stage stage;
    IdImage[] idImages;
    TileView[] tileViews;
    private Group tileGroup;
    private final int size;

    public Game(int size, Image img) {
        this.size = size;
        stage = new Stage();
        stage.setTitle("Tag");
        stage.getIcons().add(new Image("file:res/icon.png"));
        stage.setMinWidth(STAGE_SIZE);
        stage.setResizable(false);
        idImages = new IdImage[size * size];
        tileViews = new TileView[size * size];
        int step = STAGE_SIZE / size;
        Logic logic = new Logic(size);
        logic.newGame();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Image cur = new WritableImage(img.getPixelReader(), j * step, i * step, step, step);
                if (i * size + j == size * size - 1) {
                    cur = new Image("file:res/white.jpg");
                }
                idImages[i * size + j] = new IdImage(i * size + j, cur);
            }
        }
        tileGroup = new Group();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int pos = i * size + j;
                tileViews[pos] = new TileView(pos, j * step, i * step, step, idImages[logic.getTiles()[pos]]);
                tileViews[pos].addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    logic.onClick((int) event.getX(), (int) event.getY());
                    update(logic);
                    if (logic.isSolved()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("You cool!");
                        alert.setHeaderText("You won!");
                        alert.setContentText("You're super-mega-ultra-alfa COOL!");
                        alert.showAndWait();
                        stage.close();
                    }
                });
                tileGroup.getChildren().add(tileViews[pos]);

            }
        }
        Scene scene = new Scene(tileGroup);
        stage.setScene(scene);
        stage.setMinHeight(STAGE_SIZE);
        stage.show();
    }

    private void update(Logic logic) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int pos = i * size + j;
                tileViews[pos].setIdImage(idImages[logic.getTiles()[pos]]);

            }
        }
    }
}
