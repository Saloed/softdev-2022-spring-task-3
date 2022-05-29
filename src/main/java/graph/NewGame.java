package graph;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import logic.Logic;

public class NewGame {
    private Stage stage;
    IdImage[] idImages;
    TileView[] tileViews;
    private Group tileGroup;

    public NewGame(int size, Image img) {
        stage = new Stage();
        stage.setTitle("Tag");
        stage.getIcons().add(new Image("file:res/icon.png"));
        stage.setHeight(512);
        stage.setWidth(512);
        stage.setResizable(false);
        idImages = new IdImage[size * size];
        tileViews = new TileView[size * size];
        int step = 512 / size;
        Logic logic = new Logic(size);
        logic.newGame();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Image cur = new WritableImage(img.getPixelReader(), j * step, i * step, step, step);
                if (i * size + j == size * size - 1) {
                    cur = new Image("file:res/null.jpg");
                }
                idImages[i * size + j] = new IdImage(i * size + j, cur);
            }
        }
        tileGroup = new Group();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int pos = i * size + j;
                tileViews[pos] = new TileView(pos, j * step, i * step, step, idImages[logic.getTiles()[pos]]);
                tileGroup.getChildren().add(tileViews[pos]);

            }
        }
        Scene scene = new Scene(tileGroup);
        stage.setScene(scene);
        stage.show();
    }


}
