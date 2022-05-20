package javafx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LifeBar {
    private final ViewManager viewManager;
    private Rectangle healthBar;

    public LifeBar(ViewManager viewManager) {
        this.viewManager = viewManager;
        activate();
    }

    public void activate() {
        healthBar = new Rectangle(100.0, 20.0);
        healthBar.setFill(Color.CYAN);
        viewManager.getPane().getChildren().add(healthBar);
    }

    public void setHealth(int life)
    {
        healthBar.setWidth(life);
    }




}
