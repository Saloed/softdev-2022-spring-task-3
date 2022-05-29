package graph;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class TileView extends Rectangle {

    final int position;
    IdImage idImage;

    public TileView(int position, int x, int y, int sizePixels, IdImage img) {
        super(x, y, sizePixels, sizePixels);
        this.position = position;
        this.idImage = img;
        setFill(new ImagePattern(idImage.getImg()));
    }

}
