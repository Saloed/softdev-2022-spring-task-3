package javafx;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Timer;
import java.util.TimerTask;

public class LifeBar {
    private long life;
    private final ViewManager viewManager;
    private int burn;
    private Double healthPercentage;
    private Rectangle healthBar;
    private Timer lifeTimer;

    public LifeBar(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.life = 100;
        burn = 1;
        activate();
    }

    public void activate() {
        healthBar = new Rectangle(100.0, 20.0);
        healthPercentage = 1.0;
        healthBar.setFill(Color.CYAN);
        viewManager.getPane().getChildren().add(healthBar);
        this.lifeTimer = new Timer();
        TimerTask lifeTask = new TimerTask() {
            @Override
            public void run() {
                life -= burn;
                healthPercentage -= burn * 0.01;
                healthBar.setWidth(100.0 * healthPercentage);
                if (life <= 0) {
                    Platform.runLater(() -> {
                        viewManager.close();
                        new GameEndScene(viewManager);
                        lifeTimer.cancel();
                    });
                }
            }
        };
        lifeTimer.schedule(lifeTask, 1000, 100);
    }

    public void increase() {
        if (healthPercentage <= 0.9) {
            healthPercentage += 0.1;
            life += 10;
        }
        if (healthPercentage > 0.9) {
            healthPercentage = 1.0;
            life = 100;
        }
        healthBar.setWidth(100.0 * healthPercentage);
    }
     public  void deactivate() {
        lifeTimer.cancel();
    }

    public void setBurn(int setBurn) {
        burn = setBurn;
    }

}
