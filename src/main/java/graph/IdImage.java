package graph;

import javafx.scene.image.Image;

public class IdImage {

    public Image img;
    public int id;

    public IdImage(int id, Image img) {
        this.img = img;
        this.id = id;
    }

    public Image getImg() {
        return img;
    }

    public int getId() {
        return id;
    }
}
