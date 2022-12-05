package minesweep;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import minesweep.logic.Tile;

import java.util.HashMap;
import java.util.Map;

public class TileView extends Polygon {
    private final static double r = 20; // the inner radius from hexagon center to outer corner
    private final static double innerRadius = Math.sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
    private final static double TILE_WIDTH = 2 * innerRadius;
    private final static double TILE_HEIGHT = 2 * r;
    private final String EXTENSION = ".png";
    private Coordinates coordinates;
    int xStartOffset = 40; // offsets the entire field to the right
    int yStartOffset = 40; // offsets the entire fields downwards

    Tile tile;

    public TileView(Tile tile) {
        this.tile = tile;
        coordinates = tile.getCoordinates();
        int row = coordinates.getY();
        int col = coordinates.getX();

        double x = col * TILE_WIDTH + (row % 2) * innerRadius + xStartOffset;
        double y = row * TILE_HEIGHT * 0.75 + yStartOffset;
        // creates the polygon using the corner coordinates
        getPoints().addAll(
                x, y,
                x, y + r,
                x + innerRadius, y + r * 1.5,
                x + TILE_WIDTH, y + r,
                x + TILE_WIDTH, y,
                x + innerRadius, y - r * 0.5
        );
        setImage("cover");
        setStrokeWidth(1);
        setStroke(Color.BLACK);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isFlagged() {
        return tile.isFlagged();
    }

    public void setFlagged(boolean flagged) {
        tile.setFlagged(flagged);
        if (flagged) setImage("flag");
        else setImage("cover");
    }

    public boolean isBomb() {
        return tile.isBomb();
    }

    public void setBomb(boolean bomb) {
        tile.setBomb(bomb);
    }

    public boolean isEmpty() {
        return tile.isEmpty();
    }

    public void setEmpty(boolean empty) {
        tile.setEmpty(empty);
    }

    public int getBombsAround() {
        return tile.getBombsAround();
    }

    public void setBombsAround(int bombsAround) {
        tile.setBombsAround(bombsAround);
    }

    public void setImage(String name) {
        if (images.containsKey(name)) setFill(images.get(name));
        else {
            ImagePattern img = new ImagePattern(
                    new Image(String.valueOf(
                            getClass().getResource(String.format("%s%s", name, EXTENSION)))));
            setFill(img);
            images.put(name, img);
        }

    }

    private Map<String, ImagePattern> images = new HashMap<>();


}